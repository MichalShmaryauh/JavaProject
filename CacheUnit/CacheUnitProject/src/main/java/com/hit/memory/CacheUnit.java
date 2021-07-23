package com.hit.memory;

import com.hit.algorithm.IAlgoCache;
import com.hit.dm.DataModel;

/**
 * Concrete class which manages actions of some entities with the cache by
 * specific cache algorithm that accepted in the c'tor.
 * 
 * @author ъош
 *
 * @param <T>
 */
public class CacheUnit<T> {

	private IAlgoCache<Long, DataModel<T>> algo;

	public CacheUnit(IAlgoCache<Long, DataModel<T>> algo) {
		this.algo = algo;
	}

	@SuppressWarnings("unchecked")
	public DataModel<T>[] getDataModels(Long[] ids) {
		DataModel<T>[] elements = new DataModel[ids.length];
		for (int i = 0; i < ids.length; i++) {
			elements[i] = algo.getElement(ids[i]);
		}
		return elements;
	}

	@SuppressWarnings("unchecked")
	public DataModel<T>[] putDataModels(DataModel<T>[] datamodels) {
		DataModel<T>[] elements = new DataModel[datamodels.length];
		for (int i = 0; i < datamodels.length; i++) {
			elements[i] = algo.putElement(datamodels[i].getDataModelId(), datamodels[i]);
		}
		return elements;
	}

	public void removeDataModels(Long[] ids) {
		for (Long id : ids) {
			algo.removeElement(id);
		}
	}
	
	public DataModel<T>[] getAll(){
		return algo.getAll();
	}
	
	
	
	
}
