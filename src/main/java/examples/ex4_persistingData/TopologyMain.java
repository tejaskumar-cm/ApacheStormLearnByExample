package examples.ex4_persistingData;


import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;


public class TopologyMain {
    public static void main(String[] args) throws InterruptedException {

        //Topology definition
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("Read-Fields-Spout", new readFieldsSpout());
        builder.setBolt("Filter-Fields-to-File-Bolt", new filterFieldsToFileBolt())
                .shuffleGrouping("Read-Fields-Spout");

        //Configuration
        Config conf = new Config();
        conf.setDebug(true);
        conf.put("fileToRead", "/Users/swethakolalapudi/Desktop/fields.txt");
        conf.put("dirToWrite", "/Users/swethakolalapudi/Desktop/stormoutput/");


        LocalCluster cluster = new LocalCluster();
        try{cluster.submitTopology("Write-to-File-Topology", conf, builder.createTopology());
        Thread.sleep(10000);}
       finally{
        cluster.shutdown();}
    }
}
