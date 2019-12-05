package examples.ex24_twitterHashtagTrending;

import org.apache.storm.Config;

import org.apache.storm.task.TopologyContext;

import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.spout.IBatchSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class twitterTridentSpout implements IBatchSpout {
    //Queue for tweets
    private LinkedBlockingQueue<Status> queue;
    //stream of tweets
    private TwitterStream twitterStream;

    public void open(Map conf, TopologyContext context) {



        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("tpAestpXtM2pYAlomfZr6LN7d")
                .setOAuthConsumerSecret("MCQ1aVPypaBOZIlg7MDp36znULAIcmf9Cj8xfxodyVyLpILpQu")
                .setOAuthAccessToken("124163864-koQiHbqAF1QvLUzGqMb2ITvWk60jaa5yOsgJeaT7")
                .setOAuthAccessTokenSecret("qdMSjnab0O49k1pnck0fgtVQre60VN7pb0qkSC2vSYwJE");
        this.twitterStream = new TwitterStreamFactory(cb.build()).getInstance();


        //Open the stream
        //this.twitterStream = new TwitterStreamFactory().getInstance();
        //Create the queue
        this.queue = new LinkedBlockingQueue<Status>();

        //Create a listener for tweets (Status)
        final StatusListener listener = new StatusListener() {

            //If there's a tweet, add to the queue

            public void onStatus(Status status) {

                queue.offer(status);
                //System.out.println(queue.peek().getUser().getName() + " : " + queue.peek().getText());
            }

            //Everything else is empty because we
            //only care about the status (tweet)

            public void onDeletionNotice(StatusDeletionNotice sdn) {
            }


            public void onTrackLimitationNotice(int i) {
            }


            public void onScrubGeo(long l, long l1) {
            }


            public void onException(Exception e) {
            }


            public void onStallWarning(StallWarning warning) {
            }
        };

        //Add the listener to the stream
        twitterStream.addListener(listener);
        //twitterStream.sample();
        //Create a filter for the topics we want
        //to find trends for
        final FilterQuery query = new FilterQuery();
        //topics
        query.track(new String[]{"chocolate"});
        //Apply the filter
        twitterStream.filter(query);
    }

    public void emitBatch(long batchId, TridentCollector collector)  {

        final Status status = queue.poll();


        if (status == null) {
            Utils.sleep(50);
        } else {
            collector.emit(new Values(status));
        }
    }


    public void close() {
        twitterStream.shutdown();
    }


    public void ack(long batchId) {
    }


    public Map getComponentConfiguration() {
        return new Config();
    }


    public Fields getOutputFields() {
        return new Fields("tweet");
    }


}
