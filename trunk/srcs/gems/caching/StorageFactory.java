package gems.caching;

import gems.Identifiable;

public interface StorageFactory<K, V extends Identifiable<K>> {

	Storage<K, V> getStorage();

}
