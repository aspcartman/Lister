package ru.mipt;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * MIPT
 * Autor: aspcartman
 * Date: 13.09.13
 */
public class ListerTest_Iterator
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

	@Test
	public void testIterator() throws Exception
	{
		Lister<String> lister = MakeLister();

		Iterator<String> iterator = lister.iterator();
		for (int i = 0; iterator.hasNext(); ++ i)
		{
			String value = iterator.next();
			Assert.assertEquals(testArray[i], value);
		}
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
	public void testIteratorRemoving() throws Exception
	{
		Lister<String> lister = MakeLister();

		Iterator<String> iterator = lister.iterator();
		while (iterator.hasNext())
		{
			iterator.next();
			iterator.remove();
		}

		Assert.assertTrue(lister.isEmpty());
	}

	@Test(expected = IllegalStateException.class)
	public void testIteratorRemovingBung() throws Exception
	{
		@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
		Lister<String> lister = new Lister<String>();
		Iterator<String> it = lister.iterator();
		it.remove();
	}

	@Test(expected = IllegalStateException.class)
	public void testIteratorRemovingTwice() throws Exception
	{
		Lister<String> lister = new Lister<String>();
		lister.add("Lol");
		Iterator<String> it = lister.iterator();
		it.next();
		it.remove();
		it.remove();
	}

	@Test(expected = NoSuchElementException.class)
	public void testIteratorNoSuchElement() throws Exception
	{
		@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
		Lister<String> lister = new Lister<String>();
		Iterator it = lister.iterator();
		it.next();
	}

	@Test
	public void testListIterator() throws Exception
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
	public void testListIteratorOverflow() throws Exception
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
	public void testListIteratorBackwards() throws Exception
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
	public void testListIteratorBackwardsOverflow() throws Exception
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
	public void testListIteratorIndexOutOfBounds() throws Exception
	{
		Lister<String> lister = MakeLister();
		lister.listIterator(-1);
	}

	@Test
	public void testListIteratorRemoving() throws Exception
	{
		Lister<String> lister = MakeLister();
		ListIterator<String> it = lister.listIterator();

		String str = it.next();
		it.remove();

		Assert.assertFalse(lister.contains(str));
	}


}
