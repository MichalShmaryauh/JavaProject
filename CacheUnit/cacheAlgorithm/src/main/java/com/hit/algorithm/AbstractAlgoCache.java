package com.hit.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * An abstract class which implements IAlgoCache methods by abstract methods of
 * inherited classes.
 * 
 * @author ъош
 *
 * @param <K>
 * @param <V>
 */

public abstract class AbstractAlgoCache<K, V> implements IAlgoCache<K, V> {

	protected int capacity;
	protected Map<K, V> elements;

	public AbstractAlgoCache(int capacity) {
		this.elements = new HashMap<>();
		this.capacity = capacity > 0 ? capacity : 5;
	}

	@Override
	public V getElement(K key) {
		if (!elements.containsKey(key)) {
			return null;
		}
		updateCounters(key);
		return elements.get(key);
	}

	@Override
	public V putElement(K key, V value) {
		if (elements.containsKey(key)) {
			updateCounters(key);
			elements.replace(key, value);
			return null;
		}
		if (elements.size() < capacity) {
			elements.put(key, value);
			addNewCounter(key);
			return null;
		}
		K elementToRemove = chooseVictim();
		destroyCounter(elementToRemove);
		V removedElement = elements.remove(elementToRemove);
		elements.put(key, value);
		addNewCounter(key);
		return removedElement;
	}

	@Override
	public void removeElement(K key) {
		elements.remove(key);
		destroyCounter(key);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public V[] getAll(){
		V[] valuesToArray = (V[]) new Object[elements.size()];
		return elements.values().toArray(valuesToArray);
	}

	/**
	 * Initialize new counter for new element which added to cache.
	 * 
	 * @param key
	 */
	public abstract void addNewCounter(K key);

	/**
	 * Update the counter of element which has been "touched".
	 * 
	 * @param key
	 */
	public abstract void updateCounters(K key);

	/**
	 * Destroy counter of element which has been removed.
	 * 
	 * @param key
	 */
	public abstract void destroyCounter(K key);

	/**
	 * Choose victim to remove from cache in order to enter a new one.
	 * 
	 * @return
	 */
	public abstract K chooseVictim();
}
