package ru.mipt;

import junit.framework.Assert;
import org.junit.Test;

/**
 * MIPT
 * Autor: aspcartman
 * Date: 13.09.13
 */
public class ListerTest_Object
{
	@Test
	public void testIsEmptyTrue() throws Exception
	{
		@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
		Lister<String> lister = new Lister<String>();
		Assert.assertTrue(lister.isEmpty());
	}

	@Test
	public void testIsEmptyFalse() throws Exception
	{
		Lister<String> lister = ListerWithOneElement();
		Assert.assertFalse(lister.isEmpty());
	}

	private Lister<String> ListerWithOneElement()
	{
		Lister<String> lister = new Lister<String>();
		lister.add("lol");
		return lister;
	}

	@Test
	public void testContainsTrue() throws Exception
	{
		Lister<String> lister = ListerWithOneElement();
		String str = lister.get(0);

		Assert.assertTrue(lister.contains(str));
	}

	@Test
	public void testContainsFalse() throws Exception
	{
		Lister<String> lister = ListerWithOneElement();

		Assert.assertFalse(lister.contains("something that doesn't exist"));
	}


	@Test
	public void testAddingOne() throws Exception
	{
		Lister<String> lister = new Lister<String>();
		String str = "Lol";

		boolean answer = lister.add(str);
		Assert.assertTrue(answer);

		Assert.assertTrue(lister.contains(str));
	}

	@Test
	public void testAddingMultiple() throws Exception
	{
		Lister<String> lister = new Lister<String>();
		String str = "Lol";
		for (int i = 0; i < 10; ++ i)
		{
			boolean answer = lister.add(str+i);
			Assert.assertTrue(answer);
			Assert.assertTrue(lister.contains(str+i));

		}
	}

	@Test
	public void testRemoveByObject() throws Exception
	{
		Lister<String> lister = ListerWithOneElement();
		String str = lister.get(0);

		boolean answer = lister.remove(str);
		Assert.assertTrue(answer);

		boolean contains = lister.contains(str);
		Assert.assertFalse(contains);
	}

	@Test(expected = NullPointerException.class)
	public void testRemoveByObjectNullException() throws Exception
	{
		@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
		Lister<String> lister = new Lister<String>();
		lister.remove(null);
	}

	@Test
	public void testAddNull() throws Exception
	{
		Lister<String> lister = new Lister<String>();

		boolean answer = lister.add(null);

		Assert.assertFalse(answer);
		Assert.assertTrue(lister.isEmpty());
	}

	//	@Test(expected = ClassCastException.class)
	//	public void testRemoveObjectCastException() throws Exception
	//	{
	//		@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	//		Lister<String> lister = new Lister<String>();
	//		Float someFloat = 4.4f;
	//		lister.remove(someFloat);
	//	}

	@Test
	public void testClear() throws Exception
	{
		Lister<String> lister = ListerWithOneElement();

		lister.clear();

		Assert.assertEquals(0,lister.size());
	}

	@Test
	public void testRemoveNothing() throws Exception
	{
		Lister<String> lister = ListerWithOneElement();
		Assert.assertFalse(lister.remove("Something that doesn't exist in list"));
	}
}
