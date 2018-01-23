//ShoppingItem
package fi.tamk.tikoot.shoppinglist;

/**
 * ShoppingItem class provides item class for tableview and hibernate.
 *
 * @author Joni Tuominen {@literal <joni.tuominen@cs.tamk.fi>}
 * @version 1.0
 * @since 1.0
 */

public class ShoppingItem {

    private int id;
    private int amount;
    private String itemName;

    /**
     * Constructor for ShoppingItem class.
     */
    public ShoppingItem() {

    }

    /**
     * Constructor for ShoppingItem class.
     */
    public ShoppingItem(int amount, String itemName) {
        this.amount = amount;
        this.itemName = itemName;
    }

    /**
     * Accessor for id of item.
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Mutator for id of item.
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Accessor for item amount.
     *
     * @return amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Mutator for item amount.
     *
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Accessor for item name.
     *
     * @return item name
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Mutator for item name.
     *
     * @param itemName
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
}
