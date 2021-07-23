package com.hit.algorithm;

/**
 * An interface with methods to manages page replacement.
 * 
 * @author ъош
 *
 * @param <K>
 * @param <V>
 */
public interface IAlgoCache<K, V> {

	/**
	 * Returns the value to which the specified key is mapped, or null if this
	 * cache contains no mapping for the key.
	 * 
	 * @param key
	 * @return value
	 */
	public V getElement(K key);

	/**
	 * Associates the specified value with the specified key in this cache
	 * according to the current algorithm
	 * 
	 * @param key
	 * @param value
	 * @return removedElement
	 */
	public V putElement(K key, V value);

	/**
	 * Removes the mapping for the specified key from this map if present.
	 * 
	 * @param key
	 */
	public void removeElement(K key);
	
	/**
	 * 
	 * @return all elements
	 */
	public V[] getAll();

}
