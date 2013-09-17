package ru.mipt;

import junit.framework.Assert;
import org.junit.Test;

/**
 * MIPT
 * Autor: aspcartman
 * Date: 12.09.13
 */
public class ListerTest_Index
{
	@Test
	public void testSize() throws Exception
	{
		Lister<String> lister = new Lister<String>();
		String str = "lol";
		int size;

		size = lister.size();
		Assert.assertEquals(0,size);

		boolean answer = lister.add(str);
		Assert.assertTrue(answer);
		size = lister.size();
		Assert.assertEquals(1,size);

		answer = lister.add(str);
		Assert.assertTrue(answer);
		size = lister.size();
		Assert.assertEquals(2,size);

		answer = lister.remove(str);
		Assert.assertTrue(answer);
		size = lister.size();
		Assert.assertEquals(1,size);

		answer = lister.remove(str);
		Assert.assertTrue(answer);
		size = lister.size();
		Assert.assertEquals(0,size);
	}

	@Test
	public void testRemoveByIndex() throws Exception
	{
		Lister<String> lister = new Lister<String>();
		String str = "Lol";

		boolean answer = lister.add(str);
		Assert.assertTrue(answer);

		String actual = lister.remove(0);
		Assert.assertEquals(str, actual);

		boolean contains = lister.contains(str);
		Assert.assertFalse(contains);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveIndexOutOfBoundsExc() throws Exception
	{
		@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
		Lister<String> lister = new Lister<String>();
		lister.remove(0);
	}

	@Test
	public void testSet() throws Exception
	{
		String str = "OMG";
		Lister<String> lister = new Lister<String>();
		lister.add("Lol0");
		lister.add("Lol1");
		lister.add("Lol2");
		lister.add("Lol3");

		String prev = lister.set(2,str);
		Assert.assertEquals("Lol2",prev);

		Assert.assertEquals(str,lister.get(2));
	}

	@Test
	public void testAddAtIndex() throws Exception
	{
		String str = "OMG";
		Lister<String> lister = new Lister<String>();
		lister.add("Lol0");
		lister.add("Lol1");
		lister.add("Lol2");
		lister.add("Lol3");

		lister.add(2,str);

		Assert.assertEquals(str,lister.get(2));
		Assert.assertEquals("Lol2",lister.get(3));
	}

	@Test
	public void testAddAtIndexToStart() throws Exception
	{
		String str = "OMG";
		Lister<String> lister = new Lister<String>();
		lister.add("Lol0");
		lister.add("Lol1");
		lister.add("Lol2");
		lister.add("Lol3");

		lister.add(0,str);

		Assert.assertEquals(str,lister.get(0));
		Assert.assertEquals("Lol0",lister.get(1));
	}

	@Test
	public void testIndexOf() throws Exception
	{
		Lister<String> lister = new Lister<String>();
		lister.add("Lol0");
		lister.add("Lol1");
		lister.add("Lol2");
		lister.add("Lol3");

		Assert.assertEquals(0,lister.indexOf("Lol0"));
		Assert.assertEquals(2,lister.indexOf("Lol2"));
		Assert.assertEquals(3,lister.indexOf("Lol3"));
		Assert.assertEquals(-1,lister.indexOf("smth"));
	}

	@Test
	public void testLastIndexOf() throws Exception
	{
		Lister<String> lister = new Lister<String>();
		String str = "Lol";
		lister.add(str);
		lister.add(str);
		lister.add(str);
		lister.add(str);

		Assert.assertEquals(3,lister.lastIndexOf(str));
		Assert.assertEquals(-1,lister.lastIndexOf("OMG"));
	}
}
