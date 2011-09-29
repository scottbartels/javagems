package gems.monitoring.performance;

import gems.AbstractIdentifiable;
import gems.UnexpectedNullException;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public final class PerformanceRecorder extends AbstractIdentifiable<String> {

    private final Map<String, Collection<PerformanceRecord>> records = new HashMap<String, Collection<PerformanceRecord>>();

    public PerformanceRecorder(final String id) {
        super(id);
    }

    public void add(final PerformanceRecord record) {
        if (record == null) {
            throw new UnexpectedNullException();
        }
        synchronized (records) {
            Collection<PerformanceRecord> collection = records.get(record.getName());
            if (collection == null) {
                collection = new LinkedList<PerformanceRecord>();
                records.put(record.getName(), collection);
            }
            collection.add(record);
        }
    }

    public void clear(final String name) {
        if (name == null) {
            throw new UnexpectedNullException();
        }
        synchronized (records) {
            records.remove(name);
        }
    }

    public void clearAll() {
        synchronized (records) {
            records.clear();
        }
    }

    @Override public String toString() {
        final StringBuilder result = new StringBuilder();
        result.append("PerformanceRecorder [").append(getId()).append("]\n");
        synchronized (records) {
            for (final String name : records.keySet()) {
                final Collection<PerformanceRecord> collection = records.get(name);
                result.append('\t').append(name).append(' ').append(collection.size()).append("\t-->\t");
                result.append("AVG: ").append(avg(collection)).append("\t").append("MAX: ").append(max(collection));
            }
        }
        return result.toString();

    }

    private static long avg(final Collection<PerformanceRecord> records) {
        return -1; // todo: implement
    }

    private static long max(final Collection<PerformanceRecord> records) {
        return -1; // todo: implement
    }

}
