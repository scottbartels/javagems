/**
 * A cache is a likely transient storage of indentifiable objects which can be
 * retrieved back according their identifiers. Stored objects may be evicted
 * meantime by cache internal processes, so the client have to be ready for
 * the situation when previously stored object is not in cache anymore. If you
 * are using a cache, probably the {@code gesm.caching.CachingObjectProvider}
 * wrapper can do your life even easyier.
 *
 * @since 2009.01
 */
@Experimental package gems.caching;

import gems.Experimental;