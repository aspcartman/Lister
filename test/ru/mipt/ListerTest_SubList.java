package ru.mipt;

import junit.framework.Assert;
import org.junit.Test;

/**
 * MIPT
 * Autor: aspcartman
 * Date: 16.09.13
 */
public class ListerTest_SubList
{
	private Lister<String> lister;
	private Lister<String> subLister;

	@Test
	public void testSubListSize() throws Exception
	{
		InitTest();

		Assert.assertEquals(3,subLister.size());
	}

	@Test
	public void testSubListContains() throws Exception
	{
		InitTest();

		Assert.assertFalse(subLister.contains(lister.get(0)));
		Assert.assertTrue(subLister.contains(lister.get(3)));
		Assert.assertFalse(subLister.contains(lister.get(9)));
	}

	@Test
	public void testAddingAfter() throws Exception
	{
		InitTest();

		String spy = "Olol";
		subLister.add(spy);

		Assert.assertEquals(spy,lister.get(6));
	}

	private void InitTest()
	{
		InitTestingLister();
		InitTestingSublister();
	}

	private void InitTestingSublister()
	{
		subLister = (Lister<String>)lister.subList(3,6);
	}

	private void InitTestingLister()
	{
		lister = new Lister<String>();
		for (int i=0; i<10; ++i)
		{
			lister.add("lol"+i);
		}
	}
}
