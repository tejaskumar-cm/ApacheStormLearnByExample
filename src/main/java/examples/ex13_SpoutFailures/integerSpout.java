package examples.ex13_SpoutFailures;


import org.apache.log4j.Logger;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


public class integerSpout extends BaseRichSpout {

    private static Integer MAX_FAILS = 3;
    private SpoutOutputCollector collector;
    private Map<Integer,Integer> integerFailureCount;
    private List<Integer> toSend;

    static Logger LOG = Logger.getLogger(integerSpout.class);

    public void open(Map conf, TopologyContext context,
                     SpoutOutputCollector collector) {

        this.toSend = new ArrayList<Integer>();

        for(int i = 0; i< 100; i++){
            toSend.add(i);
        }

        this.integerFailureCount = new HashMap<Integer,Integer>();

        this.collector = collector;
    }


    public void nextTuple() {

        if(!toSend.isEmpty()){
            for (Integer current:toSend){
                Integer intBucket = (current/10);
            this.collector.emit(new Values(current.toString(),intBucket.toString()),current);
        }

        toSend.clear();
        }

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer)
    {
        declarer.declare(new Fields("integer","bucket"));
    }

    public void ack(Object msgId) {
        System.out.println(msgId +" Successful");
    }


    public void fail(Object msgId) {
        Integer failures =1;

        Integer failedId = (Integer) msgId;

        if (integerFailureCount.containsKey(failedId)){
            failures =integerFailureCount.get(failedId)+1;
        }

        if(failures < MAX_FAILS) {
            integerFailureCount.put(failedId, failures);
            toSend.add(failedId);
            LOG.info("Re-sending message [" + failedId + "]");
        } else {

            LOG.info("Sending message [" + failedId + "] failed!");

        }

    }

}
