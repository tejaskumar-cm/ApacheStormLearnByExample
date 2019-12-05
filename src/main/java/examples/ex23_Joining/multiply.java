package examples.ex23_Joining;


import org.apache.storm.trident.operation.BaseFunction;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

public class multiply extends BaseFunction {


    public void execute(TridentTuple tuple, TridentCollector collector) {

        collector.emit(new Values(tuple.getInteger(0) * tuple.getInteger(1)));
    }

}