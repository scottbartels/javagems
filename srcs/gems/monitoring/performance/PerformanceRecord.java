package gems.monitoring.performance;

import gems.UnexpectedNullException;

public final class PerformanceRecord {

    private final long created = System.nanoTime();

    private final String name;

    private long duration = Long.MIN_VALUE;

    public PerformanceRecord(final String name) {
        if (name == null) {
            throw new UnexpectedNullException();
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void closeTo(final PerformanceRecorder recorder) {
        if (recorder == null) {
            throw new UnexpectedNullException();
        }
        synchronized (this) {
            if (isClosed()) {
                throw new IllegalStateException();
            }
            duration = System.nanoTime() - created;
        }
    }

    public long duration() {
        synchronized (this) {
            if (!isClosed()) {
                throw new IllegalStateException();
            }
            return duration;
        }
    }

    private boolean isClosed() {
        return duration != Long.MIN_VALUE;
    }

}
