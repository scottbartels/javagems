package gems;

public final class Checks {

    private Checks() {
        throw new UnsupportedOperationException();
    }

    public static <T> T assertNotNull(final T object) {
        assert object != null;
        return object;
    }

    public static <T> T ensureNotNull(final T object) {
        if (object == null) {
            throw new NullPointerException();
        }
        return object;
    }

}
