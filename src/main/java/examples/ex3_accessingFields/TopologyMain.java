package examples.ex3_accessingFields;


import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;


public class TopologyMain {
    public static void main(String[] args) throws InterruptedException {

        //Topology definition
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("Read-Fields-Spout", new readFieldsSpout());
        builder.setBolt("Filter-Fields-Bolt", new filterFieldsBolt()).shuffleGrouping("Read-Fields-Spout");

        //Configuration
        Config conf = new Config();
        conf.setDebug(true);
        conf.put("fileToRead", "/Users/swethakolalapudi/Desktop/fields.txt");


        LocalCluster cluster = new LocalCluster();
        try{cluster.submitTopology("Read-Fields-Topology", conf, builder.createTopology());
        Thread.sleep(10000);}
       finally{
        cluster.shutdown();}
    }
}
