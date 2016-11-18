package io.muic.dcom.p2;

//import java.util.ArrayList;
//import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
//import java.util.stream.Collectors;

public class DataModel {
    public static class ParcelObserved {
        private String parcelId;
        private String stationId;
        private long timeStamp;

        ParcelObserved(String parcelId_, String stationId_, long ts_) {
            this.parcelId = parcelId_;
            this.stationId = stationId_;
            this.timeStamp = ts_;
        }

        public String getParcelId() { return parcelId; }
        public String getStationId() { return stationId; }
        public long getTimeStamp() { return timeStamp; }
    }

    private ConcurrentHashMap<String, List> ParcelCollectors;
    private ConcurrentHashMap<String, Long> StationCollectors;
//    private List<ParcelObserved> transactions;

    DataModel() {
        ParcelCollectors = new ConcurrentHashMap<>();
        StationCollectors = new ConcurrentHashMap<>();
//        transactions = new ArrayList<>();
    }

    public void postObserve(String parcelId, String stationId, long timestamp) {
        ParcelObserved parcelObserved = new ParcelObserved(parcelId, stationId, timestamp);
//        transactions.add(parcelObserved);
        if (StationCollectors.containsKey(stationId)) {
            StationCollectors.put(stationId, StationCollectors.get(stationId) + 1);
        }

        else {
            StationCollectors.put(stationId, 1L);
        }

        if (ParcelCollectors.containsKey(parcelId)) {
            ParcelCollectors.get(parcelId).add(parcelObserved);
//            ParcelCollectors.put(parcelId, ParcelCollectors.get(parcelId).add(parcelObserved));
        }
        else {
            List<ParcelObserved> parcel = new ArrayList<>();
            parcel.add(parcelObserved);
            ParcelCollectors.put(parcelId, parcel);
        }
    }

//    public synchronized List<ParcelObserved> getParcelTrail(String parcelId) {
//        return transactions.stream()
//                .filter(observeEvent -> observeEvent.parcelId.equals(parcelId))
//                .collect(Collectors.toList());
//    }

    public  List<ParcelObserved> getParcelTrail(String parcelId) {
        return new ArrayList<>(ParcelCollectors.get(parcelId));

    }


//    public synchronized long getStopCount(String stationId) {
//        return transactions.stream()
//                .filter(observeEvent -> observeEvent.stationId.equals(stationId))
//                .count();
//    }

    public Long getStopCount(String stationId) {
        return StationCollectors.get(stationId);
    }
}
