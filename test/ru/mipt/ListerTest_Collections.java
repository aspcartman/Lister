package ru.mipt;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * MIPT
 * Autor: aspcartman
 * Date: 14.09.13
 */
public class ListerTest_Collections
{
	List<String> list;
	int listCount;
	private Lister<String> lister;

	@Before
	public void setUp() throws Exception
	{
		list = new ArrayList<String>();
		list.add("Lol0");
		list.add("Lol1");
		list.add("Lol2");
		list.add("Lol3");
		listCount = list.size();
	}

	@After
	public void tearDown() throws Exception
	{
		list = null;
	}

	@Test
	public void testContainsAll() throws Exception
	{
		InitTestingLister();

		lister.addAll(list);
		Assert.assertTrue(lister.containsAll(list));
	}

	@Test
	public void testAddAll() throws Exception
	{
		Lister<String> lister = new Lister<String>();

		boolean changed = lister.addAll(list);
		Assert.assertTrue(changed);

		Assert.assertEquals(list.size(),lister.size());
		for (int i=0; i<listCount; ++i)
		{
			Assert.assertEquals(list.get(i),lister.get(i));
		}
	}

	@Test
	public void testAddAllAtIndex() throws Exception
	{
		InitTestingLister();

		boolean changed = lister.addAll(1,list);
		Assert.assertTrue(changed);

		Assert.assertTrue(lister.containsAll(list));
		Assert.assertTrue(lister.get(1).equals("Lol0"));
	}

	@Test
	public void testRemoveAll() throws Exception
	{
		InitTestingLister();
		Assert.assertTrue(lister.containsAll(list));

		boolean changed = lister.removeAll(list);
		Assert.assertTrue(changed);

		Assert.assertFalse(lister.containsAll(list));
	}

	@Test
	public void testRetainAll() throws Exception
	{
		InitTestingLister();
		Assert.assertTrue(lister.containsAll(list));

		boolean changed = lister.retainAll(list);
		Assert.assertTrue(changed);

		Assert.assertTrue(lister.containsAll(list));
		Assert.assertEquals(list.size(),lister.size());
	}

	private void InitTestingLister()
	{
		lister = new Lister<String>();
		for (int i=0; i<10; ++i)
		{
			lister.add("Lol"+i);
		}
	}
}
