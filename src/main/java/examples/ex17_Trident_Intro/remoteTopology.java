package examples.ex17_Trident_Intro;



import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.StormSubmitter;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.tuple.Fields;

import java.util.ArrayList;
import java.util.List;


public class remoteTopology {

	public static void main(String[] args) throws Exception {

		Config conf = new Config();
		List<String> dprcServers = new ArrayList<String>();

		dprcServers.add("localhost");

		conf.put(Config.DRPC_SERVERS, dprcServers);

		conf.put(Config.DRPC_PORT, 3772);
		conf.setDebug(true);



		TridentTopology topology = new TridentTopology();
		topology.newDRPCStream("simple",null)
				.each(new Fields("args"),
						new simpleFunction(),
						new Fields("processed_word"));

		StormSubmitter.submitTopology("trident", conf,topology.build() );

	}
}
