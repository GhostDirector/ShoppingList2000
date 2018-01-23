//Cell
package fi.tamk.tikoot.shoppinglist;

/**
 * Cell class provides Cell for MyLinkedList class.
 *
 * @author Joni Tuominen {@literal <joni.tuominen@cs.tamk.fi>}
 * @version 1.0
 * @since 1.0
 */

public class Cell<T> {
    protected Cell next;
    protected T object;

    /**
     * Constructor for Cell class.
     */
    public Cell() {

    }

    /**
     * Constructor for Cell class.
     *
     * @param object that is to be stored in cell
     * @param next cell that is next in the list
     */
    public Cell(T object, Cell next) {
        this.object = object;
        this.next = next;
    }

    /**
     * Accessor for Cell's object.
     *
     * @return object that is stored in cell
     */
    public T getObject() {
        return object;
    }

    /**
     * Mutator for Cell's object.
     *
     * @param object that is to be stored in cell
     */
    public void setObject(T object) {
        this.object = object;
    }

    /**
     * Accessor for next cell of the list.
     *
     * @return next cell in the list
     */
    public Cell getNext() {
        return next;
    }

    /**
     * Mutator for next Cell in the list.
     *
     * @param next Cell that is linked to this Cell
     */
    public void setNext(Cell next) {
        this.next = next;
    }
}
