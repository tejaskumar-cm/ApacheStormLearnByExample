package examples.ex14_DirectSpout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;


import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class twitterSpout extends BaseRichSpout {
    //Queue for tweets
    private LinkedBlockingQueue<Status> queue;
    //stream of tweets
    private TwitterStream twitterStream;

    private SpoutOutputCollector collector;
    public void open(Map conf, TopologyContext context,SpoutOutputCollector collector) {

        this.collector = collector;

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("tpAestpXtM2pYAlomfZr6LN7d")
                .setOAuthConsumerSecret("MCQ1aVPypaBOZIlg7MDp36znULAIcmf9Cj8xfxodyVyLpILpQu")
                .setOAuthAccessToken("124163864-koQiHbqAF1QvLUzGqMb2ITvWk60jaa5yOsgJeaT7")
                .setOAuthAccessTokenSecret("qdMSjnab0O49k1pnck0fgtVQre60VN7pb0qkSC2vSYwJE");


        this.twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        this.queue = new LinkedBlockingQueue<Status>();

        final StatusListener listener = new StatusListener() {
            public void onStatus(Status status) {

                queue.offer(status);
            }

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

        twitterStream.addListener(listener);
        final FilterQuery query = new FilterQuery();
        query.track(new String[]{"chocolate"});
        twitterStream.filter(query);
    }

    public void nextTuple() {

        final Status status = queue.poll();


        if (status == null) {
            Utils.sleep(50);
        } else {
            collector.emit(new Values(status));
        }
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("tweet"));
    }

    public void close() {
        twitterStream.shutdown();
    }


}
