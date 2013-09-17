package ru.mipt;


import java.util.*;

/**
 * MIPT
 * Autor: aspcartman
 * Date: 11.09.13
 */
public class Lister<E> implements List<E>
{
	private Cell listStart = null;
	private Cell listEnd = null;
	private Lister<E> parentLister = null;

	public boolean isEmpty()
	{
		return listStart == null;
	}

	/* == To Array == */
	public <T> T[] toArray( T[] ts)
	{
		int targetSize = ts.length;
		if (targetSize < size())
		{
			return (T[]) toArray();
		}

		Iterator<E> iterator = iterator();
		for (int i = 0; i < ts.length; ++ i)
		{
			if (iterator.hasNext())
			{
				ts[i] = (T) iterator.next();
			}
			else
			{
				ts[i] = null;
			}
		}
		return ts;
	}

	public Object[] toArray()
	{
		Object[] array = new Object[size()];
		Iterator it = iterator();
		for (int i = 0; it.hasNext(); ++ i)
		{
			array[i] = it.next();
		}
		return array;
	}

	public int size()
	{
		int count = 0;
		for (E el : this)
		{
			count++;
		}
		return count;
	}

	public boolean containsAll(Collection<?> objects)
	{
		for (Object object : objects)
		{
			boolean contains = this.contains(object);
			if (! contains)
			{
				return false;
			}
		}

		return true;
	}

	public boolean contains(Object o)
	{
		for (E el : this)
		{
			if (el.equals(o))
			{
				return true;
			}
		}
		return false;
	}

	public boolean addAll(Collection<? extends E> es)
	{
		int size = es.size();
		int count = 0;
		boolean changed = false;
		for (E element : es)
		{
			if (count == size)
				break;

			boolean added = add(element);
			if (added)
			{
				changed = true;
			}
			count++;
		}
		return changed;
	}

	public boolean add(E e)
	{
		if (e == null)
		{
			return false;
		}

		Cell cell = new Cell(listEnd, e);
		if (listStart == null)
		{
			listStart = cell;
		}
		if (listEnd != null)
		{
			if (listEnd.next != null)
			{
				listEnd.next.prev = cell;
				cell.next = listEnd.next;
			}
			listEnd.next = cell;
		}
		listEnd = cell;
		return true;
	}

	public boolean addAll(int i, Collection<? extends E> objects)
	{
		Lister<E> sublist = (Lister<E>) this.subList(0, i);
		Iterator it = objects.iterator();
		boolean changed = false;
		while (it.hasNext())
		{
			E obj = (E) it.next();
			sublist.add(obj);
			changed = true;
		}
		return changed;
	}

	public List<E> subList(int i, int i2)
	{
		Lister<E> sub = new Lister<E>();
		sub.listStart = this.getCell(i);
		sub.listEnd = this.getCell(i2 - 1);
		sub.parentLister = this;
		return sub;
	}

	private Cell getCell(int i)
	{
		if (i<0)
			throw new IndexOutOfBoundsException(String.format("Index %d out of bounds",i));

		int count = 0;
		ListerIterator<String> it = (ListerIterator<String>) iterator();
		while (it.hasNext())
		{
			it.next();
			if (count == i)
			{
				return it.cell();
			}
			count++;
		}
		throw new IndexOutOfBoundsException(String.format("Index %d of %d objects", i, count));
	}

	public Iterator<E> iterator()
	{
		return new ListerIterator<E>()
		{
			private Cell bung = new Cell(null, null, listStart);
			private Cell current = bung;
			private boolean hasBeenRemoved = false;

			@Override
			public boolean hasNext()
			{
				boolean actuallyHasNext = (current.next != null);
				boolean isAtListEnd = (current == listEnd);
				return actuallyHasNext && ! isAtListEnd;
			}

			@Override
			public E next()
			{
				if (! hasNext())
				{
					throw new NoSuchElementException("End");
				}
				current = current.next;
				hasBeenRemoved = false;
				return current.content;
			}

			@Override
			public void remove()
			{
				if (current == bung)
				{
					throw new IllegalStateException("Next() hasn't been called");
				}
				if (hasBeenRemoved)
				{
					throw new IllegalStateException("Already removed");
				}
				removeCell(current);
				hasBeenRemoved = true;
			}

			public Cell cell() // А не выползет ли эта публик хрень куда?
			{
				/* Нет проверок тк по идее это приватка */
				return current;
			}
		};
	}

	private void removeCell(Cell ptr)
	{
		Cell prev = ptr.prev;
		Cell next = ptr.next;

		if (prev != null)
		{
			prev.next = next;
		}
		else
		{
			listStart = next;
		}

		if (next != null)
		{
			next.prev = prev;
		}
		else
		{
			listEnd = prev;
		}
	}

	public boolean removeAll(Collection<?> objects)
	{
		Iterator it = objects.iterator();
		boolean changed = false;
		while (it.hasNext())
		{
			E obj = (E) it.next();
			this.remove(obj);
			changed = true;
		}
		return changed;
	}

	public boolean remove(Object o)
	{
		if (o == null)
		{
			throw new NullPointerException("Removing NULL");
		}

		Iterator<E> it = iterator();
		while (it.hasNext())
		{
			if (it.next().equals(o))
			{
				it.remove();
				return true;
			}
		}
		return false;
	}

	public boolean retainAll(Collection<?> objects)
	{
		Iterator<E> it = this.iterator();
		boolean changed = false;
		while (it.hasNext())
		{
			E obj = it.next();
			if (! objects.contains(obj))
			{
				this.remove(obj);
				changed = true;
			}
		}
		return changed;
	}

	public void clear()
	{
		listStart = null;
		listEnd = null; // GC will figure himself out.
	}

	public E get(int i)
	{
		return getCell(i).content;
	}

	public E set(int i, E e)
	{
		Cell ptr = getCell(i);
		E ret = ptr.content;
		ptr.content = e;
		return ret;
	}

	public void add(int i, E e)
	{
		Cell ptr = getCell(i);
		Cell prev = ptr.prev;
		Cell cell = new Cell(prev, e, ptr);

		if (prev != null)
		{
			prev.next = cell;
		}
		else
		{
			listStart = cell;
		}
	}

	public E remove(int i)
	{
		Cell ptr = getCell(i);
		removeCell(ptr);
		return ptr.content;
	}

	public int indexOf(Object o)
	{
		int count = 0;
		for (E element : this)
		{
			if (element.equals(o))
			{
				return count;
			}
			count++;
		}
		return - 1;
	}

	public int lastIndexOf(Object o)
	{
		int count = 0;
		int res = - 1;
		for (E element : this)
		{
			if (element.equals(o))
			{
				res = count;
			}
			count++;
		}
		return res;
	}

	public ListIterator<E> listIterator()
	{
		return listIterator(0);
	}

	public ListIterator<E> listIterator(final int i)
	{
		Cell prevCell;
		Cell nextCell;

		try
		{
			nextCell = getCell(i);
		}
		catch (Exception e)
		{
			nextCell = null;
		}

		try
		{
			prevCell = getCell(i - 1);
		}
		catch (Exception e)
		{
			prevCell = null;
		}

		if (prevCell == null && nextCell == null)
		{
			throw new IndexOutOfBoundsException();
		}

		final Cell finalNextCell = nextCell;
		final Cell finalPrevCell = prevCell;

		return new ListIterator<E>()
		{
			private Cell prev = finalPrevCell;
			private Cell next = finalNextCell;
			private Cell lastReturned = null;
			private int index = i;
			boolean removedLastReturned = false;

			@Override
			public boolean hasNext()
			{
				boolean actuallyHasNext = (next != null);
				boolean isAtListEnd = (prev == listEnd);
				return actuallyHasNext && ! isAtListEnd;
			}

			@Override
			public E next()
			{
				if (! hasNext())
				{
					throw new NoSuchElementException("Reached the end");
				}

				E ret = next.content;
				setLastReturned(next);
				stepForward();
				return ret;
			}

			private void stepForward()
			{
				prev = next;
				next = next.next;
				index++;
			}

			@Override
			public boolean hasPrevious()
			{
				boolean actuallyHasPrev = (prev != null);
				boolean isAtListEnd = (next == listStart);
				return actuallyHasPrev && ! isAtListEnd;
			}

			@Override
			public E previous()
			{
				if (! hasPrevious())
				{
					throw new NoSuchElementException("Reached the start");
				}
				E ret = prev.content;
				setLastReturned(prev);
				stepBackwards();
				return ret;
			}

			private void stepBackwards()
			{
				next = prev;
				prev = prev.prev;
				index--;
			}

			private void setLastReturned(Cell cell)
			{
				lastReturned = cell;
				removedLastReturned = false;
			}

			@Override
			public int nextIndex()
			{
				return index;
			}

			@Override
			public int previousIndex()
			{
				return index - 1;
			}

			@Override
			public void remove()
			{
				if (removedLastReturned)
				{
					throw new IllegalStateException("Already removed");
				}
				if (lastReturned == null)
					throw new IllegalStateException("No next/prev called");
				removeCell(lastReturned);
				removedLastReturned = true;
			}

			@Override
			public void set(E e)
			{
				if (lastReturned == null)
				{
					throw new IllegalStateException("No next/prev called");
				}
				lastReturned.content = e;
			}

			@Override
			public void add(E e)
			{
				Cell cell = new Cell(prev, e, next);
				if (prev != null)
				{
					prev.next = cell;
				}
				else
				{
					listStart = cell;
				}
				if (next != null)
				{
					next.prev = cell;
				}
				else
				{
					listEnd = cell;
				}
				prev = cell;
			}
		};
	}

	private abstract class ListerIterator<E> implements Iterator<E>
	{
		public abstract Cell cell();
	}

	private class Cell
	{
		public E content = null;
		public Cell next = null;
		public Cell prev = null;

		private Cell(Cell prev, E content)
		{
			this.content = content;
			this.prev = prev;
		}

		private Cell(Cell prev, E content, Cell next)
		{
			this.content = content;
			this.next = next;
			this.prev = prev;
		}
	}
}
