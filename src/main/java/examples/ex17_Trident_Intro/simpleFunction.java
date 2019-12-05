package examples.ex17_Trident_Intro;


import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;
import twitter4j.HashtagEntity;
import twitter4j.Status;

public class simpleFunction extends BaseFunction {


    public void execute(TridentTuple tuple, TridentCollector collector) {

        collector.emit(new Values(tuple.getString(0) + " After Processing"));
    }

}