package examples.ex20_aggregrate;


import org.apache.storm.trident.operation.BaseFilter;
import org.apache.storm.trident.tuple.TridentTuple;

public class filterShort extends BaseFilter {

    public boolean isKeep(TridentTuple tuple) {
        return tuple.getString(0).length()>3;
    }
}