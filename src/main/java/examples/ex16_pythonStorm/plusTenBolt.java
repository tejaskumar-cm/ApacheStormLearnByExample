package examples.ex16_pythonStorm;

import org.apache.storm.task.ShellBolt;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;

import java.util.Map;




public class plusTenBolt extends ShellBolt implements IRichBolt {

	public plusTenBolt() {
		super("python", "/Users/swethakolalapudi/Movies/Loonycorn Videos/Udemy/Storm/SourceCode/src/main/java/multilang/resources/plusTenBolt.py");
	}
	


	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("number"));
	}

	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
}