package examples.ex18_Trident_Map;




import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;


public class Topology {

	public static void main(String[] args) throws Exception {





		LocalDRPC drpc = new LocalDRPC();
		TridentTopology topology = new TridentTopology();
		topology.newDRPCStream("simple",drpc)
				.map(new lowercase())
				.flatMap(new split());
		;

		Config conf = new Config();
		conf.setDebug(true);


		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("trident-topology", conf, topology.build());

		for (String word : new String[]{ "First Page" , "Second Line", "Third Word in the Book"}) {
			System.out.println("Result for " + word + ": " + drpc.execute("simple", word));
		}


		cluster.shutdown();
	}
}
