package examples.ex3_accessingFields;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class filterFieldsBolt extends BaseBasicBolt {

    public void cleanup() {}


    public void execute(Tuple input,BasicOutputCollector collector) {

    String firstName = input.getStringByField("first_name");
    String lastName = input.getString(2)    ;

        collector.emit(new Values(firstName,lastName));
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("first_name","last_name"));
    }
}