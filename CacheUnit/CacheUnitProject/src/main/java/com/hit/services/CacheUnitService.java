package com.hit.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;

/**
 * 
 * @author ъош
 *
 *         This service holds references to the cache and disk and perform the
 *         coming requests by the suitable command.
 * @param <T>
 */

public class CacheUnitService<T> {

	private CacheUnit<T> cache;
	private IDao<Long, DataModel<T>> disk;
	private String algo = "LRU";
	//data for statistics
	private int capacity = 5, diskCapacity = 40, countRequests, countData, countSwaps;

	public CacheUnitService() {
		cache = new CacheUnit<>(new LRUAlgoCacheImpl<>(capacity));
		disk = new DaoFileImpl<>("src/main/resources/datasource.json", diskCapacity);
		countRequests = 0;
		countData = 0;
		countSwaps = 0;
	}

	public boolean update(DataModel<T>[] dataModels) {
		try {
			countRequests++;
			countData += dataModels.length;
			
			DataModel<T>[] removedDataModels = cache.putDataModels(dataModels);
			
			//save swaps datamodels on disk
			for (DataModel<T> dataModel : removedDataModels) {
				if (dataModel != null) {
					countSwaps++;
					disk.save(dataModel);
				}
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public boolean delete(DataModel<T>[] dataModels) {
		try {
			countRequests++;
			
			//get id's of data models
			Long[] ids = new Long[dataModels.length];
			for (int i = 0; i < dataModels.length; i++) {
				ids[i] = dataModels[i].getDataModelId();
			}
			//delete from cache
			cache.removeDataModels(ids);
			//delete from disk
			for (DataModel<T> dataModel : dataModels) {
				disk.delete(dataModel);
			}
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	@SuppressWarnings("unchecked")
	public DataModel<T>[] get(DataModel<T>[] dataModels) {
		try {
			countRequests++;
			// get the id's of datamodels.
			Long[] ids = new Long[dataModels.length];
			for (int i = 0; i < dataModels.length; i++) {
				ids[i] = dataModels[i].getDataModelId();
			}
			DataModel<T>[] returnedModels = cache.getDataModels(ids);// models from cache.

			List<DataModel<T>> dataFromDisk = new ArrayList<>();// store models from disk
			// find missing models from cache at disk.
			for (int i = 0; i < returnedModels.length; i++) {
				if (returnedModels[i] == (null)) {
					returnedModels[i] = disk.find(ids[i]);
					((ArrayList<DataModel<T>>) dataFromDisk).add(returnedModels[i]);
				}
			}

			// save data form disk in the cache.
			DataModel<T>[] dataFromDiskArr = new DataModel[dataFromDisk.size()];
			DataModel<T>[] removedData = cache.putDataModels(dataFromDisk.toArray(dataFromDiskArr));
			for (DataModel<T> dataModel : removedData) {
				disk.save(dataModel);
				countSwaps++;
			}

			return returnedModels;

		} catch (IllegalArgumentException e) {
			System.out.println("CacheUnitService: Id mustn't be null");
			return null;
		} catch (IOException | NullPointerException e) {
			return null;
		}

	}

	public Boolean updateDiskByCache() {
		DataModel<T>[] allCache = cache.getAll();
		for (DataModel<T> dataModel : allCache) {
			try {
				disk.save(dataModel);
			} catch (IOException e) {
				System.out.println("failed to save data from cache to disk before shutDown");
			}
		}
		return true;
	}

	public String showStatistics() {
		return "Capacity: " + capacity + "\nAlgorithm: " + algo + "\nTotal numbers of requests: " + countRequests
				+ "\nTotal number of data models: " + countData + "\nTotal number of dataModels swaps: " + countSwaps;
	}

}
