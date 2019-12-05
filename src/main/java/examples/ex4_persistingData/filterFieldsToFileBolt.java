package examples.ex4_persistingData;

import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.io.PrintWriter;
import java.util.Map;

public class filterFieldsToFileBolt extends BaseBasicBolt {

    private PrintWriter writer;

    public void prepare(Map stormConf, TopologyContext context) {

        String fileName = "output"+"-"+context.getThisTaskId()+"-"+context.getThisComponentId()+".txt";
        try{
            this.writer = new PrintWriter(stormConf.get("dirToWrite").toString()+fileName, "UTF-8");
        }
        catch (Exception e){}
    }


    public void execute(Tuple input,BasicOutputCollector collector) {

    String firstName = input.getStringByField("first_name");
    String lastName = input.getString(2);

        writer.println(firstName+","+lastName);
        collector.emit(new Values(firstName, lastName));
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("first_name", "last_name"));
    }

    public void cleanup() { writer.close();}
}