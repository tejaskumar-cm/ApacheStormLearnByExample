package examples.ex6_10_StreamGrouping;


import examples.ex6_10_StreamGrouping.spoutNBolts.integerSpout;
import examples.ex6_10_StreamGrouping.spoutNBolts.writeToFileBolt;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;


public class ex6_shuffle {
    public static void main(String[] args) throws InterruptedException {

        //Topology definition
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("Integer-Spout", new integerSpout());
        builder.setBolt("Write-to-File-Bolt", new writeToFileBolt(),2).shuffleGrouping("Integer-Spout");

        //Configuration
        Config conf = new Config();
        conf.setDebug(true);
        conf.put("dirToWrite", "/Users/swethakolalapudi/Desktop/ex7output/");
        //Topology run
        LocalCluster cluster = new LocalCluster();
        try{
            cluster.submitTopology("Shuffle-Grouping-Topology", conf, builder.createTopology());
            Thread.sleep(10000);
        }
        finally{
        cluster.shutdown();}
        }
}
