package gems.caching;

import java.awt.*;

/**
 * Defines an action executed upon an evicted item.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <T> type of evicted value.
 */
interface EvictionHandler<T> {

    /**
     * A null-implementation of the interface. It effectively does nothing.
     */
    public static final EvictionHandler<Object> NULL_EVICTION_HANDLER = new EvictionHandler<Object>() {

        /**
         * Does nothing.
         * `
         *
         * @param value {@inheritDoc}
         *
         * @throws IllegalArgumentException if {@code value} is {@code null}.
         */
        public void handle(final Object value) {
            if (value == null) {
                throw new IllegalArgumentException();
            }
        }

    };

    /**
     * An eviction handler for {@code java.awt.Image} objects.
     */
    public static final EvictionHandler<Image> AWT_IMAGE_EVICTION_HANDLER = new EvictionHandler<Image>() {

        /**
         * Calls {@code flush()} method on the image.
         *
         * @param image an evicted image.
         *
         * @throws IllegalArgumentException if {@code image} is {@code null}.
         */
        public void handle(final Image image) {
            if (image == null) {
                throw new IllegalArgumentException();
            }
            image.flush();
        }

    };

    /**
     * Handles evicted value somehow.
     *
     * @param value an evicted value.
     */
    void handle(T value);

}
