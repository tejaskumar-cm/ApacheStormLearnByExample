package examples.ex17_Trident_Intro;



import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.drpc.DRPCSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.DRPCClient;


public class Topology {

	public static void main(String[] args) throws Exception {


		LocalDRPC drpc = new LocalDRPC();
		TridentTopology topology = new TridentTopology();
		topology.newDRPCStream("simple",drpc)
				.each(new Fields("args"),
						new simpleFunction(),
						new Fields("processed_word"));

		Config conf = new Config();
		conf.setDebug(true);


		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("trident-topology", conf, topology.build());

		for (String word : new String[]{ "word 1" , "word 2", "word 3"}) {
			System.out.println("Result for " + word + ": " + drpc.execute("simple", word));
		}


		cluster.shutdown();




	}
}
