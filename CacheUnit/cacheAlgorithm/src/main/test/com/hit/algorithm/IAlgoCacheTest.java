package com.hit.algorithm;

import static org.junit.Assert.*;

import org.junit.Test;


public class IAlgoCacheTest {

    private MFUAlgoCacheImpl<Integer,String> MFU;
    LRUAlgoCacheImpl<Integer, String> LRU;

    public IAlgoCacheTest() {

        MFU=new MFUAlgoCacheImpl<>(4);
        LRU=new LRUAlgoCacheImpl<>(6);

    }

    @Test
    public void testMFUAlgoCacheImpl(){

        for (int i = 0; i < 4; i++) {
            MFU.putElement(i,Integer.toString(i));
            for (int j = 0; j < i; j++) {
                MFU.getElement(i);
            }
            assertNotNull(MFU.getElement(i));
            assertNull(MFU.putElement(i, Integer.toString(i)));
        }

        assertEquals(MFU.putElement(4,"4"),"3");
        assertEquals(MFU.putElement(5,"5"),"2");

        assertNull(MFU.putElement(4,"java"));
        assertNotNull(MFU.getElement(5));

        assertNull(MFU.getElement(3));
        assertNull(MFU.getElement(2));

        MFU.removeElement(0);
        assertNull(MFU.getElement(0));

        assertNull(MFU.putElement(6,"6"));
        MFU.putElement(7,"7");

        assertNotNull(MFU.putElement(8,"8"));

    }

    @Test
    public void testLRUAlgoCacheImpl(){

        for (int i = 0; i < 6; i++) {
            LRU.putElement(i,Integer.toString(i));
            if (i<5){
                LRU.getElement(i);
            }

        }

        assertEquals(LRU.putElement(7,"7"),"0");/*Entry 0 was not used*/
        assertEquals(LRU.putElement(8,"8"),"1");/*Entry 1 was not used*/

        LRU.getElement(8);

        assertEquals(LRU.putElement(9,"9"),"2");/*Entry 2 was used first*/

        assertNotNull(LRU.getElement(8));

        assertNull(LRU.putElement(8,"hello"));/*Key 8 is already exist*/

        assertNull(LRU.getElement(0));/*Entry 5 was removed*/

        LRU.removeElement(5);
        assertNull(LRU.getElement(1));/*Entry 1 was removed*/
        assertNull(LRU.putElement(10,"10"));/*There is empty memory*/

    }
}
