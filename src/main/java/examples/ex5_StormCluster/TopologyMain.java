package examples.ex5_StormCluster;


import org.apache.storm.Config;
import org.apache.storm.StormSubmitter;
import org.apache.storm.topology.TopologyBuilder;


public class TopologyMain {
    public static void main(String[] args) throws Exception {

        //Topology definition
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("My-First-Spout", new myFirstSpout());
        builder.setBolt("My-First-Bolt", new myFirstBolt()).shuffleGrouping("My-First-Spout");

        //Configuration
        Config conf = new Config();
        conf.setDebug(true);

        //Topology run
        StormSubmitter.submitTopology("My-First-Topology", conf, builder.createTopology());

}}
