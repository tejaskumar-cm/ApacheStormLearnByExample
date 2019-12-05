package examples.ex11_wordCount;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import examples.ex11_wordCount.spouts.WordReader;
import examples.ex11_wordCount.bolts.WordCounter;
import examples.ex11_wordCount.bolts.WordNormalizer;

public class TopologyMain {
	public static void main(String[] args) throws InterruptedException {
         
        //Topology definition
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("word-reader", new WordReader());
		builder.setBolt("word-normalizer", new WordNormalizer())
			.shuffleGrouping("word-reader");
		builder.setBolt("word-counter", new WordCounter(),2)
			.fieldsGrouping("word-normalizer", new Fields("word"));
		
        //Configuration
		Config conf = new Config();
		conf.put("fileToRead", "/Users/swethakolalapudi/Desktop/sample.txt");
		conf.put("dirToWrite", "/Users/swethakolalapudi/Desktop/ex11output/");

		conf.setDebug(true);
		//Topology run
		LocalCluster cluster = new LocalCluster();

		try{
			cluster.submitTopology("WordCounter-Topology", conf, builder.createTopology());
			Thread.sleep(30000);
		}
		finally {
			cluster.shutdown();
		}
	}
}
