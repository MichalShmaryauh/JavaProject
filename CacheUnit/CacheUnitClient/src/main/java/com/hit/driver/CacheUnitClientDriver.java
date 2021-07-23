package com.hit.driver;


/**
 * Driver class to start client action.
 */

import com.hit.client.CacheUnitClientObserver;
import com.hit.view.CacheUnitView;


public class CacheUnitClientDriver {

	public static void main(String[] args) {

		CacheUnitClientObserver cacheUnitClientObserver = new CacheUnitClientObserver();
		CacheUnitView view = new CacheUnitView();
		view.addPropertyChangeListener(cacheUnitClientObserver);
		view.start();
		
		
		
	}
	
}
