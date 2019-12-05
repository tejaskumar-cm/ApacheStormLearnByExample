package examples.ex12_DRPC;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class plusTenBolt extends BaseBasicBolt {
    public void cleanup() {
    }


    public void execute(Tuple tuple, BasicOutputCollector collector) {
       Integer input = Integer.parseInt(tuple.getString(1));
        Integer output = input +10;
        collector.emit(new Values(tuple.getValue(0),output.toString()));
    }


    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(
                new Fields("id", "result"));
    }



}