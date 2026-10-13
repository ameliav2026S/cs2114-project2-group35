package humm;

import java.util.Iterator;

/*
 * SKELETON: LinkedChain.java
 * Owner: Preston
 *
 * PURPOSE
 *   The one generic storage class for the whole app. LinkedChain<Song> and
 *   LinkedChain<Playlist> both use it. It stores items and knows nothing
 *   about songs, playlists or screens.
 *
 * PUBLIC API (use exactly these)
 *  - public class LinkedChain<T> implements Iterable<T>
 *  - LinkedChain()
 *  - boolean add(T item)   - front of the chain; returns true; null throws
 *    IllegalArgumentException
 *  - void remove(T item)   - removes the FIRST match only; null, empty
 *    chain or no match throws NoSuchElementException
 *  - boolean contains(T item)   - false when empty or null
 *  - int count(T item)   - 0 when empty or null
 *  - int size()    boolean isEmpty()    void clear()    String toString()
 *  - Iterator<T> iterator()   - new; lets a for-each loop visit every item
 *    once
 *
 * YOUR JOB
 *  - Start from the provided LinkedChain.java and edit it. Node<E> stays a
 *    private nested class in this file; there is no separate Node.java.
 *  - Add "implements Iterable<T>" and iterator(). Write the iterator as a
 *    small private nested class in this file. It keeps a reference to the
 *    next node to visit: hasNext() is "that reference is not null", next()
 *    returns its data and moves on, and next() with nothing left throws
 *    NoSuchElementException. Do not override Iterator.remove(). Walk the
 *    real nodes; do not copy the chain into an array or list.
 *  - Change remove(T) so it returns nothing and THROWS
 *    NoSuchElementException (with a short message) when the item is null,
 *    the chain is empty, or nothing equals it. With duplicates, remove only
 *    the first match. A failed remove must leave the chain exactly as it
 *    was.
 *  - Delete get(int), addToEnd, remove() and toArray. Our chain is
 *    unordered, so nothing may depend on position.
 *  - Keep add (front, rejects null), contains, count, size, isEmpty, clear
 *    and toString as provided. Keep numberOfEntries correct after every
 *    change.
 *  - Update the class Javadoc and its Big-O list to match what is left.
 *    Keep the "CS 2114 Staff" credit and add your own @author line.
 *  - Never sort, and never mention Song, Playlist or Swing in this file.
 *  - Push this file as soon as it works, BEFORE you write the tests. Tanvi
 *    is waiting on it.
 *  - Time targets: add, clear, size, isEmpty O(1); remove, contains, count,
 *    toString O(n); iterator() O(1) to create.
 *
 * HOW THE SCREENS USE THIS FILE
 *  - The screens never touch a LinkedChain. Playlist and Library hold the
 *    chains and call your methods.
 *  - A failed remove MUST throw: Playlist and Library pass that exception
 *    up, and the screen shows its message to the user.
 *  - size() must always be exact. Buttons such as "Clear songs" are greyed
 *    out by checking the count.
 *  - Every call to iterator() starts fresh and shows the current contents,
 *    because screens rebuild their rows after every change.
 *
 * YOU DO NOT HAVE TO WAIT FOR ANYONE
 *  - This file needs nothing from anyone else.
 *  - Starter versions of LinkedChain and Song are in the repo, so the
 *    project compiles. Code and test against what is there. A test that
 *    needs the REAL LinkedChain or Song (a "not found" exception, Song
 *    equality, looping with the iterator) may fail until Preston pushes
 *    his, and that is expected.
 *
 * RULES
 *  - Package humm. Keep every class, method, parameter and return type
 *    exactly as listed in PUBLIC API and in the spec (section 6). Do not
 *    add, rename or remove public methods, and do not add public fields.
 *    Helpers must be private.
 *  - Pull the repo before you start and pull again often. LinkedChain.java
 *    and Song.java are already in it as starter files; replace their
 *    placeholder bodies and keep their headers. For any other file of
 *    yours, first write the class and method headers from PUBLIC API with
 *    placeholder bodies, so it compiles, and then fill in the real code.
 *  - Everything is due Tuesday night, so Amelia can put it together on
 *    Wednesday.
 *  - Write a Javadoc comment on the class and on every public method,
 *    directly above the method, with @param, @return and @throws where they
 *    apply. It keeps the code easy to explain in the oral exam.
 *  - Exceptions: use only the ones named here, and always give them a short
 *    message. Never return null to signal an error, never catch an
 *    exception just to hide it, and never print to the console.
 *  - A failed action must leave everything unchanged. Check first, then
 *    change.
 *  - Edit only your own files. If you find a problem in someone else’s
 *    file, or need a signature changed, tell the group and change the spec
 *    first.
 *  - Delete this comment block before submitting.
 *
 * Depends on: nothing. Everything else is built on it.
 * Tests for this file: LinkedChainTest.java.
 */



/**
 * A generic chain of linked nodes. Use it as the storage inside your
 * own classes, and extend or modify it as your project needs.
 *
 * This class owns storage only: nodes, the head reference, and the
 * count. It contains no application logic. A class that needs a
 * collection should hold a LinkedChain field and call its methods.
 *
 * add() places the new entry at the FRONT of the chain, so entries added
 * with add() come back from toArray() in reverse order of insertion.
 * addToEnd() preserves insertion order.
 *
 * Time complexity, where n is the number of entries:
 *   add              O(1)
 *   addToEnd         O(n)
 *   remove()         O(1)
 *   remove(T)        O(n)
 *   clear            O(1)
 *   get              O(n)
 *   contains         O(n)
 *   count            O(n)
 *   size             O(1)
 *   isEmpty          O(1)
 *   toArray          O(n)
 *
 * @author CS 2114 Staff
 * @version September 2026
 *
 * @param <T> the type of entries stored in the chain
 */
public class LinkedChain<T> implements Iterable<T>
{
    private Node<T> firstNode;   // reference to the first node, or null
    private int numberOfEntries; // number of nodes in the chain

    /**
     * Creates an empty chain.
     */
    public LinkedChain()
    {
        firstNode = null;
        numberOfEntries = 0;
    }

    /**
     * Adds a new entry to the front of this chain.
     *
     * @param newEntry the object to be added; must not be null
     * @return true
     * @throws IllegalArgumentException if newEntry is null
     */
    public boolean add(T newEntry)
    {
        requireNonNull(newEntry);
        firstNode = new Node<T>(newEntry, firstNode);
        numberOfEntries++;
        return true;
    }

    /**
     * Adds a new entry to the end of this chain.
     *
     * @param newEntry the object to be added; must not be null
     * @return true
     * @throws IllegalArgumentException if newEntry is null
     */
    public boolean addToEnd(T newEntry)
    {
        requireNonNull(newEntry);
        Node<T> newNode = new Node<T>(newEntry);
        if (firstNode == null)
        {
            firstNode = newNode;
        }
        else
        {
            Node<T> current = firstNode;
            while (current.next != null)
            {
                current = current.next;
            }
            current.next = newNode;
        }
        numberOfEntries++;
        return true;
    }

    /**
     * Removes and returns the first entry in this chain, if possible.
     *
     * @return the removed entry, or null if the chain is empty
     */
    public T remove()
    {
        if (firstNode == null)
        {
            return null;
        }
        T result = firstNode.data;
        firstNode = firstNode.next;
        numberOfEntries--;
        return result;
    }

    /**
     * Removes the first occurrence of a given entry from this chain.
     *
     * @param anEntry the entry to be removed
     * @return true if the removal was successful, or false if not
     */
    public boolean remove(T anEntry)
    {
        if (anEntry == null || firstNode == null)
        {
            return false;
        }
        if (anEntry.equals(firstNode.data))
        {
            firstNode = firstNode.next;
            numberOfEntries--;
            return true;
        }
        Node<T> previous = firstNode;
        Node<T> current = firstNode.next;
        while (current != null)
        {
            if (anEntry.equals(current.data))
            {
                previous.next = current.next;
                numberOfEntries--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    /**
     * Removes all entries from this chain.
     */
    public void clear()
    {
        firstNode = null;
        numberOfEntries = 0;
    }

    /**
     * Gets the entry at the given position without removing it.
     *
     * @param index the zero-based position
     * @return the entry at that position
     * @throws IndexOutOfBoundsException if index is negative or
     *         index is greater than or equal to size()
     */
    public T get(int index)
    {
        if (index < 0 || index >= numberOfEntries)
        {
            throw new IndexOutOfBoundsException(
                "Index " + index + " out of bounds for size "
                + numberOfEntries);
        }
        Node<T> current = firstNode;
        for (int i = 0; i < index; i++)
        {
            current = current.next;
        }
        return current.data;
    }

    /**
     * Tests whether this chain contains a given entry.
     *
     * @param anEntry the entry to locate
     * @return true if the chain contains anEntry, or false if not
     */
    public boolean contains(T anEntry)
    {
        return count(anEntry) > 0;
    }

    /**
     * Counts the number of times a given entry appears in this chain.
     *
     * @param anEntry the entry to be counted
     * @return the number of times anEntry appears in the chain
     */
    public int count(T anEntry)
    {
        if (anEntry == null)
        {
            return 0;
        }
        int frequency = 0;
        Node<T> current = firstNode;
        while (current != null)
        {
            if (anEntry.equals(current.data))
            {
                frequency++;
            }
            current = current.next;
        }
        return frequency;
    }

    /**
     * Gets the number of entries currently in this chain.
     *
     * @return the integer number of entries in the chain
     */
    public int size()
    {
        return numberOfEntries;
    }

    /**
     * Sees whether this chain is empty.
     *
     * @return true if the chain has no entries, or false if not
     */
    public boolean isEmpty()
    {
        return numberOfEntries == 0;
    }

    /**
     * Copies every entry in this chain into the given array, first entry
     * first.
     *
     * @param values an array of the correct type to fill; it must have
     *               length at least size()
     * @return the array containing all entries in the chain
     * @throws IllegalArgumentException if values is null or too small
     */
    public T[] toArray(T[] values)
    {
        if (values == null)
        {
            throw new IllegalArgumentException("Array must not be null");
        }
        if (values.length < numberOfEntries)
        {
            throw new IllegalArgumentException(
                "Array of length " + values.length
                + " is too small for " + numberOfEntries + " entries");
        }
        int index = 0;
        Node<T> current = firstNode;
        while (current != null)
        {
            values[index] = current.data;
            index++;
            current = current.next;
        }
        return values;
    }

    /**
     * temp iterator() method to allow for-each loops to visit every entry in the chain.
     */
    @Override
    public Iterator<T> iterator()
    {
        throw new UnsupportedOperationException("Iterator not implemented yet");
    }

    /**
     * Returns a readable listing of the chain, first entry first,
     * e.g. "[a, b, c]".
     *
     * @return a string representation of this chain
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder("[");
        Node<T> current = firstNode;
        while (current != null)
        {
            sb.append(current.data);
            if (current.next != null)
            {
                sb.append(", ");
            }
            current = current.next;
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Rejects null entries.
     *
     * @param entry the entry to check
     * @throws IllegalArgumentException if entry is null
     */
    private void requireNonNull(T entry)
    {
        if (entry == null)
        {
            throw new IllegalArgumentException("Cannot add null to chain");
        }
    }

    /**
     * A node in the chain. Holds one entry and a link to the next node.
     *
     * @param <E> the type of data held in the node
     */
    private static class Node<E>
    {
        private E data;        // entry stored in this node
        private Node<E> next;  // link to the next node, or null

        /**
         * Creates a node with the given data and no next node.
         *
         * @param dataPortion the entry to store
         */
        private Node(E dataPortion)
        {
            this(dataPortion, null);
        }

        /**
         * Creates a node with the given data and next link.
         *
         * @param dataPortion the entry to store
         * @param nextNode the node that follows this one
         */
        private Node(E dataPortion, Node<E> nextNode)
        {
            data = dataPortion;
            next = nextNode;
        }
    }
}
