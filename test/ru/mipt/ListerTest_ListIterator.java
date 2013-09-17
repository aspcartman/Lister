package ru.mipt;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * MIPT
 * Autor: aspcartman
 * Date: 13.09.13
 */
public class ListerTest_ListIterator
{
	private String[] testArray;

	@Before
	public void setUp() throws Exception
	{
		testArray = new String[10];
		for (int i = 0; i < 10; ++ i)
		{
			testArray[i] = "lol" + i;
		}
	}

	@After
	public void tearDown() throws Exception
	{
		testArray = null;
	}

	private Lister<String> MakeLister()
	{
		Lister<String> lister = new Lister<String>();
		for (int i = 0; i < 10; ++ i)
		{
			lister.add(testArray[i]);
		}
		return lister;
	}

	@Test
	public void testIterator() throws Exception
	{
		Lister<String> lister = MakeLister();
		ListIterator<String> it = lister.listIterator();
		for (int i = 0; it.hasNext(); ++ i)
		{
			Assert.assertEquals(i,it.nextIndex());
			String value = it.next();
			Assert.assertEquals(testArray[i], value);
		}
	}

	@Test(expected = NoSuchElementException.class)
	public void testOverflow() throws Exception
	{
		Lister<String> lister = MakeLister();
		ListIterator<String> it = lister.listIterator();
		//noinspection InfiniteLoopStatement
		for (;;)
		{
			it.next();
		}
	}

	@Test
	public void testBackwards() throws Exception
	{
		Lister<String> lister = MakeLister();
		ListIterator<String> it = lister.listIterator(lister.size());
		for (int i = lister.size()-1; it.hasPrevious(); --i)
		{
			Assert.assertEquals(i,it.previousIndex());
			String value = it.previous();
			Assert.assertEquals(testArray[i], value);
		}
	}

	@Test(expected = NoSuchElementException.class)
	public void testBackwardsOverflow() throws Exception
	{
		Lister<String> lister = MakeLister();
		ListIterator<String> it = lister.listIterator(lister.size());
		//noinspection InfiniteLoopStatement
		for (;;)
		{
			it.previous();
		}
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testIndexOutOfBounds() throws Exception
	{
		Lister<String> lister = MakeLister();
		lister.listIterator(-1);
	}

	@Test
	public void testRemoving() throws Exception
	{
		Lister<String> lister = MakeLister();
		ListIterator<String> it = lister.listIterator();

		String str = it.next();
		it.remove();

		Assert.assertFalse(lister.contains(str));
	}

	@Test (expected = IllegalStateException.class)
	public void testRemovingIlligalState() throws Exception
	{
		Lister<String> lister = MakeLister();
		ListIterator<String> it = lister.listIterator();
		it.remove();
	}

	@Test (expected = IllegalStateException.class)
	public void testRemovingAlreadyRemoved() throws Exception
	{
		Lister<String> lister = MakeLister();
		ListIterator<String> it = lister.listIterator();
		it.next();
		it.remove();
		it.remove();
	}

	@Test
	public void testSet() throws Exception
	{
		Lister<String> lister = MakeLister();
		ListIterator<String> it = lister.listIterator();

		String str = it.next();
		it.set(str+"lol");

		Assert.assertEquals(str+"lol",it.previous());
	}

	@Test (expected = IllegalStateException.class)
	public void testSetIllegalState() throws Exception
	{
		Lister<String> lister = MakeLister();
		ListIterator<String> it = lister.listIterator();
		it.set("lol");
	}

	@Test
	public void testAdd() throws Exception
	{
		Lister<String> lister = MakeLister();
		ListIterator<String> it = lister.listIterator();

		it.add("lol");
		it.add("lol");

		Assert.assertEquals("lol",it.previous());

		it = lister.listIterator(lister.size());
		it.add("lol");
		Assert.assertEquals("lol",it.previous());
	}
}
