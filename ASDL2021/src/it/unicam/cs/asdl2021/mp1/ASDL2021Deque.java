/**
 * 
 */
package it.unicam.cs.asdl2021.mp1;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Implementation of the Java SE Double-ended Queue (Deque) interface
 * (<code>java.util.Deque</code>) based on a double linked list. This deque does
 * not have capacity restrictions, i.e., it is always possible to insert new
 * elements and the number of elements is unbound. Duplicated elements are
 * permitted while <code>null</code> elements are not permitted. Being
 * <code>Deque</code> a sub-interface of
 * <code>Queue<code>, this class can be used also as an implementaion of a <code>Queue</code>
 * and of a <code>Stack</code>.
 * 
 * The following operations are not supported:
 * <ul>
 * <li><code>public <T> T[] toArray(T[] a)</code></li>
 * <li><code>public boolean removeAll(Collection<?> c)</code></li>
 * <li><code>public boolean retainAll(Collection<?> c)</code></li>
 * <li><code>public boolean removeFirstOccurrence(Object o)</code></li>
 * <li><code>public boolean removeLastOccurrence(Object o)</code></li>
 * </ul>
 * 
 * @author Template: Luca Tesei, Implementation: Niccolò Cuartas
 * niccolo.cuartas@studenti.unicam.it
 *
 */
public class ASDL2021Deque<E> implements Deque<E> {

    /*
     * Current number of elements in this deque
     */
    private int size;

    /*
     * Pointer to the first element of the double-linked list used to implement
     * this deque
     */
    private Node<E> first;

    /*
     * Pointer to the last element of the double-linked list used to implement
     * this deque
     */
    private Node<E> last;


    private int nMod;//numero di modifiche

    /**
     * Constructs an empty deque.
     */
    public ASDL2021Deque() {
        this.size = 0;
        this.first = null;
        this.last = null;
        this.nMod = 0;
    }

    @Override
    public boolean isEmpty() {
        //return this.first==null;
        return this.size==0;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        Node<E> node = this.first;
        int i=0;

        while(node.next!=null)
        {
            array[i]=node;
            i++;
            node=node.next;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException(
                "This class does not implement this service.");
    }


    @Override
    public boolean containsAll(Collection<?> c) {
        /*if (c == null||c.isEmpty())
            return true;
        Node<E> index;
        boolean found;
        for (Object item : c) {//per ogni oggetto item nella collezione c
            index = this.first;//ricomincio da capo
            found = false;
            while (index != null) {//finchè il nodo non è null(possiede un prossimo)
                if (this.contains(item)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                return false;
        }
        return true;*/
        if (c == null || c.isEmpty())
            return true;
        Node<E> index = this.first;
        boolean found=false;
        while (index.next != null) {
            for (Object item : c) {
                if (this.contains(item)) {
                    found = true;
                    break;
                }
                else found=false;
                index = index.next;
            }
            if (!found)
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for(E e:c)
        {
            if(e==null)
                throw new NullPointerException();
            add(e);
        }
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException(
                "This class does not implement this service.");
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException(
                "This class does not implement this service.");
    }

    @Override
    public void clear() {
        this.first=null;
        this.last=null;
        nMod++;
        size=0;
    }

    @Override
    public void addFirst(E e) {
        if(e==null)
            throw new NullPointerException();
        if(isEmpty()) {
            this.last = this.first = new Node<>(null, e, null);
            nMod++;
            size = 1;
        }
        else {
//          Node<E> oldFirst = this.first;
            Node<E> newFirst = new Node<>(null, e, this.first);
            this.first.prev = (newFirst);
            first=newFirst;
            nMod++;
            size++;
        }
    }

    @Override
    public void addLast(E e) {
        if(e==null)
            throw new NullPointerException();
        //Node<E> oldLast=this.last;//variabile di comodo per contenere l'ultimo nodo corrente che andrà poi modificato
        Node<E> newLast=new Node<>(null,e,null);//creo nuovo nodo in fondo alla lista(con riferimento next null quindi)

        if(isEmpty())
        {
            first=last=newLast;
        }
        else
        {
            last.next = (newLast);//penultimo nodo
            newLast.prev=last;
            last = newLast;//il nuovo last è l'ultimo nodo della lista
        }
        nMod++;
        size++;
    }

    @Override
    public boolean offerFirst(E e) {
        addFirst(e);
        return true;
    }

    @Override
    public boolean offerLast(E e) {
        addLast(e);
        return true;
    }

    @Override
    public E removeFirst() {
        if(isEmpty())
            throw new NoSuchElementException();
        return pollFirst();
    }

    @Override
    public E removeLast() {
        if(isEmpty())
            throw new NoSuchElementException();
        return pollLast();
    }

    @Override
    public E pollFirst() {
        if(isEmpty()) return null;
        E item=first.item;
        if(size==1)
        {
            clear();
            return item;
        }

        first=first.next;
        first.prev=null;
        nMod++;
        size--;
        return item;
    }

    @Override
    public E pollLast() {
        if(isEmpty()) return null;
        E item=last.item;
        if(size==1)
        {
            clear();
            return item;
        }

        last=last.prev;
        last.next=null;
        nMod++;
        size--;
        return item;
    }

    @Override
    public E getFirst() {
        if(isEmpty()) throw new NoSuchElementException();
        return this.first.item;
    }

    @Override
    public E getLast() {
        if(isEmpty()) throw new NoSuchElementException();
        return this.last.item;
    }

    @Override
    public E peekFirst() {
        if(first==null)
            return null;
        return this.first.item;
    }

    @Override
    public E peekLast() {
        if(last==null)
            return null;
        return this.last.item;
    }

    @Override
    public boolean removeFirstOccurrence(Object o) {
        throw new UnsupportedOperationException(
                "This class does not implement this service.");
    }

    @Override
    public boolean removeLastOccurrence(Object o) {
        throw new UnsupportedOperationException(
                "This class does not implement this service.");
    }

    @Override
    public boolean add(E e) {
        addLast(e);
        return true;
    }

    @Override
    public boolean offer(E e) {
        return offerLast(e);
    }

    @Override
    public E remove() {
        if(isEmpty())
            throw new NoSuchElementException();
        return removeFirst();
    }

    @Override
    public E poll() {
        return pollFirst();
    }

    @Override
    public E element() {
        if(isEmpty()) throw new NoSuchElementException();
        return getFirst();
    }

    @Override
    public E peek() {
        return peekFirst();
    }

    @Override
    public void push(E e) {
        addFirst(e);
    }

    @Override
    public E pop() {
        return removeFirst();
    }

    @Override
    public boolean remove(Object o) {
        if(isEmpty()||o==null)
            throw new NullPointerException();
        if(contains(o)) {
            Node<E> index = this.first;

            if(index.item.equals(o))
            {
                this.removeFirst();
                return true;
            }
            if(this.last.item.equals(o))
            {
                this.removeLast();
                return true;
            }//dopo aver controllato che gli estremi non contengano l'oggetto, controllo il resto della lista
            while (!index.item.equals(o)) {
                index = index.next;
            }

            Node<E> previous = index.prev;
            Node<E> next = index.next;
            index.prev=null;//darlo in pasto al garbage collector
            index.next=null;
            previous.next=next;
            next.prev=previous;
            nMod++;
            size--;
            return true;
        }
        else return false;//l'oggetto non è presente e non viene rimosso nulla
    }

    @Override
    public boolean contains(Object o) {
        if(o==null)
            throw new NullPointerException();
        boolean contains=false;//flag
        Node<E> index = this.first;//creo un nodo inserendoci il primo elemento della lista (per posizionarmi)
        while (index != null && !contains)
        {//mentre il nodo non è nullo (ha successivi) e allo stesso tempo il flag contains è false, ciclo
            contains=o.equals(index.item);//contains assume il risultato booleano comparando l'oggetto passato con l'item di index (si presuppone che l'oggetto passato sia un item comparabile)
            index=index.next;//scorro l'indice(nodo) successivo
        }
        return contains;
    }

    @Override
    public int size() {
        return this.size;
    }

    /*
     * Class for representing the nodes of the double-linked list used to
     * implement this deque. The class and its members/methods are protected
     * instead of private only for JUnit testing purposes.
     */
    protected static class Node<E> {
        protected E item;

        protected Node<E> next;

        protected Node<E> prev;

        protected Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    /*
     * Class for implementing an iterator for this deque. The iterator is
     * fail-safe: it detects if during the iteration a modification to the
     * original deque was done and, if so, it launches a
     * <code>ConcurrentModificationException</code> as soon as a call to the
     * method <code>next()</code> is done.
     */
    private class Itr implements Iterator<E> {

        private Node<E> lastReturned;

        private int numeroModificheAtteso;

        Itr() {
            // All'inizio il cursore è null
            this.lastReturned = null;
            this.numeroModificheAtteso = ASDL2021Deque.this.nMod;
        }

        public boolean hasNext() {
            if (isEmpty()) return false;
            return lastReturned.next!=null;
        }

        public E next() {
            if(hasNext())
                return lastReturned.next.item;
            else throw new NoSuchElementException();
        }
    }

    @Override
    public Iterator<E> descendingIterator() {
        return new DescItr();
    }

    /*
     * Class for implementing a descending iterator for this deque. The iterator
     * is fail-safe: it detects if during the iteration a modification to the
     * original deque was done and, if so, it launches a
     * <code>ConcurrentModificationException</code> as soon as a call to the
     * method <code>next()</code> is done.
     */
    private class DescItr implements Iterator<E> {
        private Node<E> lastReturned;

        private int numeroModificheAtteso;

        DescItr() {
            this.lastReturned=null;
            this.numeroModificheAtteso=ASDL2021Deque.this.nMod;
        }

        public boolean hasNext() {
            return lastReturned.prev!=null;
        }

        public E next() {
            if(hasNext())
                return lastReturned.prev.item;
            else throw new NoSuchElementException();
        }

    }

    // TODO implement: possibly add private methods for implementation purposes

    /*
     * This method is only for JUnit testing purposes.
     */
    protected Node<E> getFirstNode() {
        return this.first;
    }

    /*
     * This method is only for JUnit testing purposes.
     */
    protected Node<E> getLastNode() {
        return this.last;
    }

}
