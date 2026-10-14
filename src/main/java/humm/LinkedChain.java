package humm;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An unordered generic linked chain that permits duplicate, non-null entries.
 * Add, clear, size, isEmpty, and iterator creation are O(1). Remove, contains,
 * count, and full iteration are O(n).
 *
 * @author CS 2114 Staff
 * @author Preston Moore
 * @version October 2026
 * @param <T> type of item held in the chain
 */
public class LinkedChain<T>
    implements Iterable<T>
{
    private Node<T> firstNode;
    private int numberOfEntries;

    /** Constructs an empty chain. */
    public LinkedChain()
    {
        firstNode = null;
        numberOfEntries = 0;
    }


    /**
     * Adds an entry to the front of the chain.
     * 
     * @param item
     *            non-null entry to add
     * @return true after insertion
     * @throws IllegalArgumentException
     *             if item is null
     */
    public boolean add(T item)
    {
        if (item == null)
        {
            throw new IllegalArgumentException("Cannot add null to chain.");
        }
        firstNode = new Node<>(item, firstNode);
        numberOfEntries++;
        return true;
    }


    /**
     * Removes only the first equal entry.
     * 
     * @param item
     *            entry to remove
     * @throws NoSuchElementException
     *             if item is null or no matching entry exists
     */
    public void remove(T item)
    {
        if (item == null)
        {
            throw new NoSuchElementException("Cannot remove a null item.");
        }
        if (firstNode == null)
        {
            throw new NoSuchElementException("That item is no longer there.");
        }
        if (item.equals(firstNode.data))
        {
            firstNode = firstNode.next;
            numberOfEntries--;
            return;
        }
        Node<T> previous = firstNode;
        Node<T> current = firstNode.next;
        while (current != null)
        {
            if (item.equals(current.data))
            {
                previous.next = current.next;
                numberOfEntries--;
                return;
            }
            previous = current;
            current = current.next;
        }
        throw new NoSuchElementException("That item is no longer there.");
    }


    /**
     * Checks whether an equal entry is present.
     * 
     * @param item
     *            item to find, possibly null
     * @return true if a matching entry exists; false for null
     */
    public boolean contains(T item)
    {
        if (item == null)
        {
            return false;
        }
        Node<T> current = firstNode;
        while (current != null)
        {
            if (item.equals(current.data))
            {
                return true;
            }
            current = current.next;
        }
        return false;
    }


    /**
     * Counts entries equal to the given item.
     * 
     * @param item
     *            item to count, possibly null
     * @return number of matches, or zero for null
     */
    public int count(T item)
    {
        if (item == null)
        {
            return 0;
        }
        int matches = 0;
        Node<T> current = firstNode;
        while (current != null)
        {
            if (item.equals(current.data))
            {
                matches++;
            }
            current = current.next;
        }
        return matches;
    }


    /** @return number of entries, including duplicates */
    public int size()
    {
        return numberOfEntries;
    }


    /** @return true if the chain has no entries */
    public boolean isEmpty()
    {
        return numberOfEntries == 0;
    }


    /** Removes all entries, including duplicates. */
    public void clear()
    {
        firstNode = null;
        numberOfEntries = 0;
    }


    /**
     * Makes an iterator over the linked nodes.
     * 
     * @return a fresh iterator starting at the first node
     */
    @Override
    public Iterator<T> iterator()
    {
        return new ChainIterator();
    }


    /** @return entries formatted as [a, b, c], or [] if empty */
    @Override
    public String toString()
    {
        StringBuilder result = new StringBuilder("[");
        Node<T> current = firstNode;
        while (current != null)
        {
            result.append(current.data);
            if (current.next != null)
            {
                result.append(", ");
            }
            current = current.next;
        }
        return result.append("]").toString();
    }

    /** Traverses actual nodes without copying their data. */
    private class ChainIterator
        implements Iterator<T>
    {
        private Node<T> current = firstNode;

        /** @return true if there is another entry */
        @Override
        public boolean hasNext()
        {
            return current != null;
        }


        /**
         * Retrieves the next entry.
         * 
         * @return next entry
         * @throws NoSuchElementException
         *             when iteration is exhausted
         */
        @Override
        public T next()
        {
            if (current == null)
            {
                throw new NoSuchElementException("No more items in the chain.");
            }
            T value = current.data;
            current = current.next;
            return value;
        }
    }


    /** Holds one entry and the reference to the following node. */
    private static class Node<E>
    {
        private E data;
        private Node<E> next;

        /**
         * @param data
         *            item to hold
         */
        private Node(E data)
        {
            this(data, null);
        }


        /**
         * @param data
         *            item to hold
         * @param next
         *            following node
         */
        private Node(E data, Node<E> next)
        {
            this.data = data;
            this.next = next;
        }
    }
}
