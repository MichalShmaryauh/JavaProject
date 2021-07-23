package com.hit.algorithm;

import java.util.HashMap;

/**
 * Random algorithm give the priority to the elements randomly. The element
 * which has been chosen randomly will be removed.
 * 
 * @author ъош
 *
 * @param <K>
 * @param <V>
 */
public class RandomReplacementAlgoCacheImpl<K, V> extends AbstractAlgoCache<K, V> {

	private K[] elementsKeys;// save the keys of the elements that in the cache
	private int size;
	private HashMap<K, Integer> indexes;// save for each key its index in the
										// elementsKeys array.

	@SuppressWarnings("unchecked")
	public RandomReplacementAlgoCacheImpl(int capacity) {
		super(capacity);
		elementsKeys = (K[]) new Object[this.capacity];
		size = 0;
		indexes = new HashMap<>();
	}

	@Override
	public void addNewCounter(K key) {
		elementsKeys[size] = key;
		indexes.put(key, size++);
	}

	@Override
	public void updateCounters(K key) {
		return;
	}

	@Override
	public void destroyCounter(K key) {
		Integer removedIndex = indexes.get(key);
		elementsKeys[removedIndex] = elementsKeys[--size];
		indexes.replace(elementsKeys[size], removedIndex);
		indexes.remove(key);
	}

	@Override
	public K chooseVictim() {
		return elementsKeys[(int) (Math.random() * (size - 1))];
	}

}
