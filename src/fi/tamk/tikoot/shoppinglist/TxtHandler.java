//TxtHandler
package fi.tamk.tikoot.shoppinglist;

import java.io.*;

/**
 * TxtHandler class handles txt file writing and reading.
 *
 * @author Joni Tuominen {@literal <joni.tuominen@cs.tamk.fi>}
 * @version 1.0
 * @since 1.0
 */

public class TxtHandler {

    private File file;
    private String textFromFile;

    /**
     * Constructor for TxtHandler class.
     */
    public TxtHandler(){
        textFromFile = "";
    }

    /**
     * Reads line from file to string.
     *
     * @param file to be read
     */
    public void readFile(File file){
        if (file != null){
            this.file = file;
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                textFromFile = br.readLine();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Writes text file.
     *
     * @param text
     * @param file
     */
    public void toFile(String text, File file) {
        try {
            this.file = file;

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(text);
            bw.close();
            //System.out.println("Done writing to " + fileName); //For testing
        }
        catch(IOException e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }
    }

    /**
     * Builds string from given lists.
     *
     * @param amountList
     * @param itemNameList
     * @return temp string created from lists
     */
    public String stringBuilder(MyLinkedList amountList, MyLinkedList itemNameList){
        String tmp = "";
        for (int i = 0; i < itemNameList.size(); i++){
            tmp = tmp + amountList.get(i) + " " + itemNameList.get(i) + ";";
        }
        return tmp;
    }

    /**
     * Builds amount list from String.
     *
     * @return temp list created from string
     */
    public MyLinkedList<Integer> buildAmountList(){
        MyLinkedList<Integer> tmpList = new MyLinkedList<>();
        String[] tmp = textFromFile.split(";");
        if (tmp[0] != null){
            for (int i = 0; i < tmp.length; i++){
                String tmpString = "";
                for (int j = 0; j < tmp[i].length(); j++){
                    if (Character.isDigit(tmp[i].charAt(j))){
                        tmpString = tmpString + tmp[i].charAt(j);
                    }
                }
                tmpList.add(Integer.parseInt(tmpString));
            }
        }
        return tmpList;
    }

    /**
     * Builds item name list from String.
     *
     * @return temp list created from string
     */
    public MyLinkedList<String> buildItemNameList(){
        MyLinkedList<String> tmpList = new MyLinkedList();
        String[] tmp = textFromFile.split(";");
        if (tmp[0] != null){
            for (int i = 0; i < tmp.length; i++){
                for (int j = 0; j < tmp[i].length(); j++){
                    if (tmp[i].charAt(j) == ' '){
                        tmpList.add(tmp[i].substring(j + 1));
                    }
                }
            }
        }
        return tmpList;
    }
}
