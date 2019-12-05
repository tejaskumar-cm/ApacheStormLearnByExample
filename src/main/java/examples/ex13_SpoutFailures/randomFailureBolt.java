package examples.ex13_SpoutFailures;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.Random;


public class randomFailureBolt extends BaseRichBolt {

	private static final Integer MAX_PERCENT_FAIL = 80;
	Random random = new Random();
	private OutputCollector collector;

	public void execute(Tuple input) {
		Integer r = random.nextInt(100);
		if(r < MAX_PERCENT_FAIL){
			collector.emit(input, new Values(input.getString(0),input.getString(1)));
			collector.ack(input);
		}else{
			collector.fail(input);
		}
	}

	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this.collector = collector;
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {

		declarer.declare(new Fields("integer","bucket"));
	}

}
