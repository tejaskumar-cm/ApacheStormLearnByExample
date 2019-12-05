package examples.ex22_Windows;


import examples.ex19_filter.lowercase;
import examples.ex19_filter.split;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.Aggregator;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.operation.TridentOperationContext;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.operation.builtin.Debug;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.trident.testing.CountAsAggregator;
import org.apache.storm.trident.testing.FixedBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.trident.windowing.InMemoryWindowsStoreFactory;
import org.apache.storm.trident.windowing.WindowsStoreFactory;
import org.apache.storm.trident.windowing.config.*;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.util.Map;
import java.util.concurrent.TimeUnit;


public class WindowTopology {

	public static void main(String[] args) throws Exception {




		WindowsStoreFactory windowStore = new InMemoryWindowsStoreFactory();

		WindowConfig windowConfig =TumblingDurationWindow.of(
				new BaseWindowedBolt.Duration(100, TimeUnit.MILLISECONDS));


		TridentTopology topology = new TridentTopology();

		topology.newStream("spout1", new logSpout())
				.window(windowConfig, windowStore, new Fields("log"),
						new errorAggregrator(), new Fields("count"))
						.each(new Fields("count"), new Debug());

		Config conf = new Config();
		conf.setDebug(true);


		LocalCluster cluster = new LocalCluster();

		try{
			cluster.submitTopology("trident-topology", conf, topology.build());
			Thread.sleep(15000);
		}
		finally {
			cluster.shutdown();
		}

	}
}
//SlidingCountWindow.of(100, 10);
//				TumblingCountWindow.of(100)
//				SlidingDurationWindow.of(new BaseWindowedBolt.Duration(100, TimeUnit.MILLISECONDS), new BaseWindowedBolt.Duration(10, TimeUnit.MILLISECONDS))
//				TumblingDurationWindow.of(new BaseWindowedBolt.Duration(100, TimeUnit.MILLISECONDS)
//

