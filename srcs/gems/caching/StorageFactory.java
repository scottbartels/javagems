package gems.caching;

import gems.Identifiable;

interface StorageFactory<K, V extends Identifiable<K>> {

	Storage<K, V> getStorage();

}
