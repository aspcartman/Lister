package ru.mipt;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * MIPT
 * Autor: aspcartman
 * Date: 17.09.13
 */
public class ListerTest_ToArray
{
	String[] someArray;
	Lister<String> lister;
	@Before
	public void setUp() throws Exception
	{
		someArray = new String[]{"Omg","Wtf","Huh"};
		lister = new Lister<String>();
		for (String s : someArray)
		{
			lister.add(s);
		}
	}

	@After
	public void tearDown() throws Exception
	{
		someArray = null;
		lister = null;
	}

	@Test
	public void testToArray() throws Exception
	{
		Object[] lol = lister.toArray();
		Assert.assertArrayEquals(someArray,lol);

		String[] array = new String[10];
		array = lister.toArray(array);
		Assert.assertTrue(Arrays.asList(array).containsAll(Arrays.asList(someArray)));
		Assert.assertNull(array[9]);
	}


}
