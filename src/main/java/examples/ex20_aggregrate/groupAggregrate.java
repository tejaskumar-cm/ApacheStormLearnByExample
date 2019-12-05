package examples.ex20_aggregrate;


import examples.ex19_filter.filterShort;
import examples.ex19_filter.lowercase;
import examples.ex19_filter.split;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.tuple.Fields;


public class groupAggregrate {

	public static void main(String[] args) throws Exception {





		LocalDRPC drpc = new LocalDRPC();
		TridentTopology topology = new TridentTopology();
		topology.newDRPCStream("simple", drpc)
				.map(new lowercase())
				.flatMap(new split())
				.groupBy(new Fields("args"))
				.aggregate(new Count(), new Fields("count"));
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
