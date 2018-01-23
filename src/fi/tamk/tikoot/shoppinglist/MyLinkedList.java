//MyLinkedList
package fi.tamk.tikoot.shoppinglist;

/**
 * MyLinkedList class provides list for shopping list.
 *
 * @author Joni Tuominen {@literal <joni.tuominen@cs.tamk.fi>}
 * @version 1.0
 * @since 1.0
 */

public class MyLinkedList<T> implements MyList<T> {

    private Cell<T> first;
    private Cell<T> last;

    /**
     * Constructor for MyLinkedList class.
     */
    public MyLinkedList() {
        first = null;
        last = null;
    }

    /**
     * Adds item to list.
     *
     * @param e item to be added.
     */
    @Override
    public void add(T e) {
        if (last == null) {
            last = new Cell<>(e, null);
            first = last;
        } else {
            Cell<T> newCell = new Cell<>();
            newCell.setObject(e);
            last.setNext(newCell);
            last = newCell;
        }
    }

    /**
     * Clears list.
     */
    @Override
    public void clear() {
        first = null;
        last = null;
    }

    /**
     * Gets item from list.
     *
     * @param index
     * @return item from given index
     */
    @Override
    public T get(int index) {
        Cell<T> tmpCell = first;
        for (int i = 0; i < index; i++) {
            tmpCell = tmpCell.getNext();
        }
        if (tmpCell == null || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return tmpCell.getObject();
    }

    /**
     * Sets item to given index
     *
     * @param index
     * @param value item to be set.
     */
    public void set(int index , T value) {
        Cell<T> tmpCell = first;
        for (int i = 0; i < index; i++) {
            tmpCell = tmpCell.getNext();
        }
        if (tmpCell == null || index < 0) {
            throw new ArrayIndexOutOfBoundsException();
        }
        tmpCell.setObject(value);
    }

    /**
     * Checks if list is empty.
     *
     * @return true if empty, else false
     */
    @Override
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Removes item in given index.
     *
     * @param index
     */
    @Override
    public void remove(int index) {
        if (index < 0 || index >= size()) {
            throw new ArrayIndexOutOfBoundsException();
        } else {
            if (first == null) {
                // do nothing because list is empty
            } else if(index == 0) {
                first = first.getNext();
            } else if (index == size()) {
                Cell<T> firstTmp = first;
                Cell<T> lastTmp = first;
                while (firstTmp != null) {
                    lastTmp = firstTmp;
                    firstTmp = firstTmp.getNext();
                }
                last = lastTmp;
                last.setNext(null);
            } else {
                Cell<T> tmp1 = first;
                index = index - 1;
                for (int i = 0; i < size(); i++) {

                    if (i == index) {
                        Cell<T> tmp2 = tmp1.getNext();
                        tmp2 = tmp2.getNext();
                        tmp1.setNext(tmp2);
                        break;
                    }
                    tmp1 = tmp1.getNext();
                }
            }
        }
    }

    /**
     * Removes given item from list.
     *
     * @param o item to be removed
     * @return true if succesful, else false
     */
    @Override
    public boolean remove(T o) {
        if (first != null) {
            if (first.getObject().equals(o)) {
                first = first.getNext();
                return true;
            } else if (last.getObject().equals(o)) {
                Cell<T> tmp = first;
                for (int i = 0; i < size()-2; i++) {
                    tmp = tmp.getNext();
                }
                last = tmp;
                last.setNext(null);
                return true;
            } else {
                Cell<T> previous = first;
                Cell<T> tmp = first.getNext();
                while (tmp != null) {
                    if (tmp.getObject().equals(o)) {
                        previous.setNext(tmp.getNext());
                        return true;
                    }

                    previous = tmp;
                    tmp = tmp.getNext();
                }
            }
        }
        return false;
    }

    /**
     * Calculates size of the list.
     *
     * @return list size
     */
    @Override
    public int size() {
        Cell<T> tmp = first;
        int size = 0;
        while (tmp != null){
            tmp = tmp.getNext();
            size++;
        }
        return size;
    }
}
