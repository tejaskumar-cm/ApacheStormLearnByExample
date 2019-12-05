package examples.ex16_pythonStorm;


import org.apache.storm.spout.ShellSpout;

import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;

import org.apache.storm.tuple.Fields;


import java.util.Map;


public class integerSpout extends ShellSpout implements IRichSpout {


    public integerSpout() {
        super("python",
                "/Users/swethakolalapudi/Movies/Loonycorn Videos/Udemy/Storm/SourceCode/src/main/java/multilang/resources/integerSpout.py");
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer)
    {
        declarer.declare(new Fields("integer","bucket"));
    }

    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}

