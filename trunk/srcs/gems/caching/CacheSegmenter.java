package gems.caching;

public interface CacheSegmenter<T> {

	int getSegment(T object);

	int maxSegments();

}
