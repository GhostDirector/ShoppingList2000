//ShoppingList
package fi.tamk.tikoot.shoppinglist;

/**
 * ShoppingList class provides lists for gui and cli.
 *
 * @author Joni Tuominen {@literal <joni.tuominen@cs.tamk.fi>}
 * @version 1.0
 * @since 1.0
 */

public class ShoppingList {

    private MyLinkedList<Integer> amountList;
    private MyLinkedList<String> itemList;
    private boolean isGui;

    /**
     * Constructor for ShoppingList class.
     *
     * @param isGui Determines amount increase method for different interfaces.
     */
    public ShoppingList(boolean isGui){
        amountList = new MyLinkedList<>();
        itemList = new MyLinkedList<>();
        this.isGui = isGui;
    }

    /**
     * Accessor for amount list.
     *
     * @return amount list
     */
    public MyLinkedList<Integer> getAmountList() {
        return amountList;
    }

    /**
     * Accessor for item list.
     *
     * @return item list
     */
    public MyLinkedList<String> getItemList() {
        return itemList;
    }

    /**
     * Mutator for amount list.
     *
     * @param amountList
     */
    public void setAmountList(MyLinkedList<Integer> amountList) {
        this.amountList = amountList;
    }

    /**
     * Mutator for item list.
     *
     * @param itemList
     */
    public void setItemList(MyLinkedList<String> itemList) {
        this.itemList = itemList;
    }

    /**
     * Adds items to list or modifies amount of existing item.
     *
     * @param amount
     * @param item
     */
    public void toList(int amount, String item){
        boolean notInTheList = true;
        for (int i = 0; i < itemList.size(); i++){
            if (itemList.get(i).equalsIgnoreCase(item)){
                if (this.isGui){
                    amountList.set(i, amount);
                } else {
                    amountList.set(i, amountList.get(i) + amount);
                }
                notInTheList = false;
            }
        }
        if (notInTheList){
            amountList.add(amount);
            itemList.add(item);
        }
    }

    /**
     * Clears lists.
     */
    public void clearList() {
        amountList.clear();
        itemList.clear();
    }
}
