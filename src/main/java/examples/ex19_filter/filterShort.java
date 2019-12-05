package examples.ex19_filter;


import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.operation.MapFunction;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

public class filterShort extends BaseFilter {

    public boolean isKeep(TridentTuple tuple) {
        return tuple.getString(0).length()>3;
    }
}