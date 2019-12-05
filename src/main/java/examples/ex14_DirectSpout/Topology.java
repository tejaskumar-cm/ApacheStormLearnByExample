package examples.ex14_DirectSpout;


import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;


public class Topology {
 
	public static void main(String[] args) throws InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();
		builder.setSpout("tweets-collector", new twitterSpout());
		builder.setBolt("text-extractor", new extractStatusBolt()).
			shuffleGrouping("tweets-collector"); 
		
		LocalCluster cluster = new LocalCluster();
		Config conf = new Config();
		conf.setDebug(true);
		cluster.submitTopology("twitter-direct", conf, builder.createTopology());
		Thread.sleep(10000);
	cluster.shutdown();
	}
}
