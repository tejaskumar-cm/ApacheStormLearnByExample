package examples.ex6_10_StreamGrouping;


import examples.ex6_10_StreamGrouping.spoutNBolts.directGroupingSpout;

import examples.ex6_10_StreamGrouping.spoutNBolts.writeToFileBolt;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;


public class ex10_Direct {
    public static void main(String[] args) throws InterruptedException {

        //Topology definition
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("Integer-Spout", new directGroupingSpout());
        builder.setBolt("Write-to-File-Bolt", new writeToFileBolt(),2).directGrouping("Integer-Spout");

        //Configuration
        Config conf = new Config();
        conf.setDebug(true);
        conf.put("dirToWrite", "/Users/swethakolalapudi/Desktop/ex10output/");
        //Topology run

        LocalCluster cluster = new LocalCluster();
        try{
            cluster.submitTopology("Direct-Grouping-Topology", conf, builder.createTopology());
            Thread.sleep(10000);
        }
        finally{
        cluster.shutdown();}
        }
}
