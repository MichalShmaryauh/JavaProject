package com.hit.algorithm;

import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * LRU algorithm give the priority to the elements by using time. The element
 * which has been used earlier will be removed.
 * 
 * @author ъош
 *
 * @param <K>
 * @param <V>
 */
public class LRUAlgoCacheImpl<K, V> extends AbstractAlgoCache<K, V> {

	private PriorityQueue<Entry<K, Long>> counters;
	private HashMap<K, Long> keysValues;
	private Long timer;

	public LRUAlgoCacheImpl(int capacity) {
		super(capacity);
		this.counters = new PriorityQueue<>(Entry::compareTo);
		this.keysValues = new HashMap<>();
		this.timer = 0L;
	}

	@Override
	public void addNewCounter(K key) {
		keysValues.put(key, timer);
		counters.add(new Entry<>(key, timer++));
	}

	@Override
	public void updateCounters(K key) {
		counters.remove(new Entry<>(key, keysValues.get(key)));
		counters.add(new Entry<>(key, timer));
		keysValues.replace(key, timer++);
	}

	@Override
	public void destroyCounter(K key) {
		counters.remove(new Entry<>(key, keysValues.get(key)));
		keysValues.remove(key);
	}

	@Override
	public K chooseVictim() {
		Entry<K, Long> victim = counters.poll();
		return victim.getKey();
	}

}
