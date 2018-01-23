//CLI
package fi.tamk.tikoot.shoppinglist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Cli class provides command line interface for shopping list application.
 *
 * @author Joni Tuominen {@literal <joni.tuominen@cs.tamk.fi>}
 * @version 1.0
 * @since 1.0
 */

public class Cli {

    private BufferedReader inputReader; //Reader for input reading
    private ShoppingList shoppingList; //Shopping list to provide item lists and functionality

    /**
     * Constructor for Cli class.
     */
    public Cli(){
        shoppingList = new ShoppingList(false);
        inputReader = new BufferedReader(new InputStreamReader(System.in));
        menu();
    }

    /**
     * Provides interface for application usage.
     */
    public void menu(){
        boolean loop = true;
        while (loop == true){
            System.out.println("SHOPPING LIST");
            System.out.println("Tampere University of Applied Sciences");
            System.out.println("Give shopping list (example: 1 milk;2 tomato;3 carrot;)");
            try {
                checkInput(inputReader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Your Shopping List now:");
            printItems();
            System.out.println("");
        }
    }

    /**
     * Checks if input is valid.
     *
     * @param input text that is checked
     */
    public void checkInput(String input){
        if (input != null){
            if (input.equalsIgnoreCase("clear")){
                shoppingList.clearList();
            } else {
                String[] tmp = input.split(";");
                for (int i = 0; i < tmp.length; i++){
                    if(Character.isDigit(tmp[i].charAt(0))){
                        if (tmp[i].charAt(1) == ' '){
                            shoppingList.toList(Character.getNumericValue(tmp[i].charAt(0)), tmp[i].substring(2,tmp[i].length()));
                        }
                    }
                }
            }
        }
    }

    /**
     * Prints items in the shopping list.
     */
    public void printItems(){
        for (int i = 0; i < shoppingList.getAmountList().size(); i++){
            System.out.print("  " + shoppingList.getAmountList().get(i));
            System.out.println(" " + shoppingList.getItemList().get(i));
        }
    }
}
