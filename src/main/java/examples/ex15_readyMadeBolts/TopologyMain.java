package examples.ex15_readyMadeBolts;


import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.hdfs.bolt.HdfsBolt;
import org.apache.storm.hdfs.bolt.format.DefaultFileNameFormat;
import org.apache.storm.hdfs.bolt.format.DelimitedRecordFormat;
import org.apache.storm.hdfs.bolt.format.RecordFormat;
import org.apache.storm.hdfs.bolt.rotation.FileRotationPolicy;
import org.apache.storm.hdfs.bolt.rotation.FileSizeRotationPolicy;
import org.apache.storm.hdfs.bolt.sync.CountSyncPolicy;
import org.apache.storm.hdfs.bolt.sync.SyncPolicy;
import org.apache.storm.topology.TopologyBuilder;


public class TopologyMain {
    public static void main(String[] args) throws InterruptedException {


        RecordFormat format = new DelimitedRecordFormat().withFieldDelimiter(",");
        SyncPolicy syncPolicy = new CountSyncPolicy(1000);

        //Rotate files after 127MB
        FileRotationPolicy rotationPolicy = new FileSizeRotationPolicy(127.0f, FileSizeRotationPolicy.Units.MB);

        DefaultFileNameFormat fileNameFormat = new DefaultFileNameFormat();

        //The files are written in this HDFS folder
        fileNameFormat.withPath("/storm-data");

        //Files start with the following filename prefix
        fileNameFormat.withPrefix("records-");

        //Files end with the following suffix
        fileNameFormat.withExtension(".csv");

        //HDFS bolt
        HdfsBolt hdfsbolt = new HdfsBolt().withFsUrl("hdfs://localhost:9000")
                        .withFileNameFormat(fileNameFormat)
                        .withRecordFormat(format)
                        .withRotationPolicy(rotationPolicy)
                        .withSyncPolicy(syncPolicy);

        //Topology definition
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("Read-Fields-Spout", new readFieldsSpout());
        builder.setBolt("Filter-Fields-Bolt", new filterFieldsBolt()).shuffleGrouping("Read-Fields-Spout");
        builder.setBolt("HDFS-Bolt", hdfsbolt).shuffleGrouping("Filter-Fields-Bolt");

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
