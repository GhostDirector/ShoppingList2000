//FileName
package fi.tamk.tikoot.shoppinglist;

/**
 * FileName class provides storage for file names used in DropboxHandler.
 *
 * @author Joni Tuominen {@literal <joni.tuominen@cs.tamk.fi>}
 * @version 1.0
 * @since 1.0
 */

public class FileName {

    private String itemName;

    /**
     * Constructor for the FileName class
     *
     * @param itemName name of the file
     */
    public FileName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Accessor for itemName.
     *
     * @return name of the file
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Mutator for itemName.
     *
     * @param itemName name of the file
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
