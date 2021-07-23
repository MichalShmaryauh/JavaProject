package com.hit.memory;

import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
//import static org.junit.jupiter.api.Assertions.*;

public class CacheUnitTest {

	private IDao<Long, DataModel<String>> dao;
	private CacheUnit<String> cacheUnit;

	public CacheUnitTest() {
		this.dao = new DaoFileImpl<String>("src/main/resources/datasource.json", 10);
		this.cacheUnit = new CacheUnit<String>(new LRUAlgoCacheImpl<Long, DataModel<String>>(10));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void dao() {
		try{

		DataModel<String>[] data = new DataModel[10];

		for (int i = 0; i < 10; i++) {
			dao.save(new DataModel<String>((long) i, Integer.toString(i)));
			data[i] = new DataModel<String>((long) i, Integer.toString(i));
		}

		/** Success to find entity in dao */
		assertEquals(dao.find(2L), new DataModel<String>(2L, "2"));

		dao.delete(new DataModel<String>(1L, "1"));
		/** Check entity was deleted */
		assertNull(dao.find(1L));

		dao.save(new DataModel<String>(100L, "100"));

		/** Memory is full so entity is not saved */
		dao.save(new DataModel<String>(20L, "20"));
		assertNull(dao.find(20L));

		dao.save(new DataModel<String>(100L, "50"));

		/**
		 * Check that Entity(id=100,content=50) was not added because there is
		 * an entity with such an id
		 */

		DataModel<String> dm = (DataModel<String>) dao.find(100L);
		assertNotEquals(dm.getContent(), "50");

//		assertThrows(IllegalArgumentException.class, () -> {
//			dao.delete(null);
//		});
//
//		assertThrows(IllegalArgumentException.class, () -> {
//			dao.find(null);
//		});
		}catch(IOException e){}

	}

	@SuppressWarnings("unchecked")
	@Test
	public void cacheUnit() {
		try{

		DataModel<String>[] data = new DataModel[10];

		for (int i = 0; i < 10; i++) {
			dao.save(new DataModel<String>((long) i, Integer.toString(i)));
			data[i] = new DataModel<String>((long) i, Integer.toString(i));
		}

		cacheUnit.putDataModels(data);

		Long[] ids = { 0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L };

		assertArrayEquals(cacheUnit.getDataModels(ids), data);

		ids = new Long[] { 0L };

		/** Remove entity with id 0 */
		cacheUnit.removeDataModels(ids);

		/** Check if entity was removed */
		assertNull(cacheUnit.getDataModels(ids)[0]);
		

		/** Put 10 new elements */
		for (int i = 0; i < 10; i++) {
			data[i] = new DataModel<String>((long) i + 10, Integer.toString(i + 10));
		}

		/**
		 * Check if removed elements are not null because capacity is 10 so
		 * memory is full
		 */

		DataModel<String>[] removedElements = cacheUnit.putDataModels(data);

		for (int i = 1; i < 10; i++ ) {
			assertNotNull(removedElements[i]);
		}
		}catch(IOException e){}
	}
}