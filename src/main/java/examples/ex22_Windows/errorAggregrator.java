package examples.ex22_Windows;


import org.apache.storm.trident.operation.BaseAggregator;
import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.testing.CountAsAggregator;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

public class errorAggregrator extends BaseAggregator<errorAggregrator.State> {

    public errorAggregrator() {
    }

    public errorAggregrator.State init(Object batchId, TridentCollector collector) {
        return new errorAggregrator.State();
    }

    public void aggregate(errorAggregrator.State state, TridentTuple tuple, TridentCollector collector) {
       if(tuple.getString(0).equals("ERROR"))
        ++state.count;
    }

    public void complete(errorAggregrator.State state, TridentCollector collector) {
        collector.emit(new Values(new Object[]{Long.valueOf(state.count)}));
    }

    static class State {
        long count = 0L;

        State() {
        }
    }


}