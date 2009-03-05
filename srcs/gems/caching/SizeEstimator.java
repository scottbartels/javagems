package gems.caching;

public interface SizeEstimator<T> {  // todo: move one package up; it seems quite general.
	
	long estimate(T object);

}
