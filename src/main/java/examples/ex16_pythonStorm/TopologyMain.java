package examples.ex16_pythonStorm;


import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;


public class TopologyMain {
    public static void main(String[] args) throws InterruptedException {

        //Topology definition
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("My-First-Spout", new integerSpout());
        builder.setBolt("My-First-Bolt", new plusTenBolt()).shuffleGrouping("My-First-Spout");

        //Configuration
        Config conf = new Config();
        conf.setDebug(true);

        //Topology run

        LocalCluster cluster = new LocalCluster();
        try{cluster.submitTopology("My-First-Topology", conf, builder.createTopology());
        Thread.sleep(30000);}
       finally{
        cluster.shutdown();}
    }
}
