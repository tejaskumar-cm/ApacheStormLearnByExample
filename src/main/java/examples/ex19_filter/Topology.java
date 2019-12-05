package examples.ex19_filter;


import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.trident.TridentTopology;


public class Topology {

	public static void main(String[] args) throws Exception {





		LocalDRPC drpc = new LocalDRPC();
		TridentTopology topology = new TridentTopology();
		topology.newDRPCStream("simple", drpc)
				.map(new lowercase())
				.flatMap(new split())
				.filter(new filterShort());
		;

		Config conf = new Config();
		conf.setDebug(true);


		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("trident-topology", conf, topology.build());

		for (String word : new String[]{ "this is" , "a very very short short book", "sentence in a book"}) {
			System.out.println("Result for " + word + ": " + drpc.execute("simple", word));
		}

		cluster.shutdown();
	}
}
