package com.hit.algorithm;

import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * MFU algorithm give the priority to the elements by number of being used. The
 * element which has been used the most will be removed.
 * 
 * @author ъош
 *
 * @param <K>
 * @param <V>
 */
public class MFUAlgoCacheImpl<K, V> extends AbstractAlgoCache<K, V> {

	private PriorityQueue<Entry<K, Integer>> counters;
	private HashMap<K, Integer> keysValues;

	public MFUAlgoCacheImpl(int capacity) {
		super(capacity);
		this.counters = new PriorityQueue<>((new Comparator<Entry<K, Integer>>() {
			@Override
			public int compare(Entry<K, Integer> o1, Entry<K, Integer> o2) {
				return o1.compareTo(o2);
			}
		}).reversed());
		this.keysValues = new HashMap<>();
	}

	@Override
	public void addNewCounter(K key) {
		keysValues.put(key, 1);
		counters.add(new Entry<>(key, 1));
	}

	@Override
	public void updateCounters(K key) {
		Integer val = keysValues.get(key);
		counters.remove(new Entry<>(key, val));
		counters.add(new Entry<>(key, val + 1));
		keysValues.replace(key, val + 1);
	}

	@Override
	public void destroyCounter(K key) {
		counters.remove(new Entry<>(key, keysValues.get(key)));
		keysValues.remove(key);
	}

	@Override
	public K chooseVictim() {
		Entry<K, Integer> victim = counters.poll();
		return victim.getKey();
	}

}
