package com.github.coderodde.util;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class ArrayDequeList<E> extends AbstractList<E> implements List<E>, Deque<E> {

    private static final int INITIAL_CAPACITY = 4;
    
    private int size;
    private int startIndex;
    private E[] storage = (E[]) new Object[INITIAL_CAPACITY];
    
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (size == 0) {
            setAll(c);
        } else if (index == 0) {
            prependAll(c);
        } else if (index == size) {
            appendAll(c);
        } else {
            insertAll(index, c);
        }
        
        return true;
    }

    @Override
    public void clear() {
        
    }

    @Override
    public E get(int index) {
        return storage[(startIndex + index) % storage.length];
    }

    @Override
    public E set(int index, E element) {
        E old = storage[(startIndex + index) % storage.length];
        storage[(startIndex + index) % storage.length] = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        if (isFull()) {
            addOnFull(index, element);
        } else {
            addOnNonFull(index, element);
        }
        
        size++;
    }
    
    private void addOnFull(int index, E element) {
        
    }
    
    private void addOnNonFull(int index, E element) {
        //    2            1      4
        if (startIndex + size > storage.length) {
            addOnNonFullWrapped(index, element);  
        } else {
            addOnNonFullUnwrapped(index, element);
        }
    }

    /**
     * Adds an element {@code element} between indices {@code index - 1} and
     * {@code index}. This method must handle two cases: 
     * <ol>
     *      <li>the internal array is wrapped into two parts,</li>
     *      <li>the internal array is not wrapped.</li>
     * </ol>
     * In case (1), we might have the following data layout:
     * <code>
     * data  : e         a b c d
     * memory: 0 1 2 3 4 5 6 7 8
     * </code>
     * In case (2), we might have the following data layout:
     * <code>
     * data  :   a b c d e f g 
     * memory: 0 1 2 3 4 5 6 7 8
     * </code>
     * 
     * @param index   the index at which {@code element} will be inserted.
     * @param element the element to insert.
     */    
    private void addOnNonFullWrapped(int index, E element) {
        final int rightPartLength = storage.length - startIndex;
        
        if (index < rightPartLength) {
            if (index < size - index) {
                // Move the left chunk of right part one position to the left:
                System.arraycopy(storage,
                                 startIndex,
                                 storage,
                                 startIndex - 1, 
                                 index);
                
                storage[startIndex-- + index - 1] = element;
            } else {
                // Rotate the right chunk of right part one position to the 
                // right:
                final int leftPartLength = size - rightPartLength;
                
                System.arraycopy(storage,
                                 0,
                                 storage, 
                                 1, 
                                 leftPartLength);
                
                storage[0] = storage[storage.length - 1];
                
                System.arraycopy(storage, 
                                 startIndex + index, 
                                 storage, 
                                 startIndex + 1, 
                                 rightPartLength - index);
            }
        } else {
            // Once here, the index points to the left part:
            int pointerIndex = index
            
            System.arraycopy(storage,
                             pointerIndex, 
                             storage, 
                             pointerIndex + 1, 
                             leftPart - pointerIndex);
            
            storage[pointerIndex] = element;
        }
    }
    
    private void addOnNonFullUnwrapped(int index, E element) {
        
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void addFirst(E e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void addLast(E e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E removeFirst() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E removeLast() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E getFirst() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E getLast() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean offerFirst(E e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean offerLast(E e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E pollFirst() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E pollLast() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E peekFirst() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E peekLast() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean offer(E e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E remove() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E poll() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E element() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E peek() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void push(E e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public E pop() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Iterator<E> descendingIterator() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private boolean isFull() {
        return size == storage.length;
    }
}
