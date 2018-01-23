//Gui
package fi.tamk.tikoot.shoppinglist;

import com.dropbox.core.DbxException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Gui class provides graphical user interface for shoppinglist.
 *
 * @author Joni Tuominen {@literal <joni.tuominen@cs.tamk.fi>}
 * @version 1.0
 * @since 1.0
 */

public class Gui extends Application{

    private ShoppingList shoppingList;
    private TxtHandler txtHandler;
    private DropboxHandler dropboxHandler;
    private StoreData storeData;

    private Stage primaryStage;

    // GridPane
    private GridPane gridPane;

    // menuBar
    private MenuBar menuBar;

    // menuBar components
    private Menu fileMenuButton;
    private Menu editMenuButton;

    // menuBar items
    // file
    private MenuItem saveListTxt;
    private MenuItem saveListDb;
    private MenuItem saveListDrop;
    private MenuItem openListTxt;
    private MenuItem openListDb;
    private MenuItem openListDrop;
    private MenuItem close;

    // edit
    private MenuItem addItem;
    private MenuItem deleteItem;
    private MenuItem clearList;

    // tableview and its columns
    private TableView<ShoppingItem> tableView;

    // columns
    private TableColumn amount;
    private TableColumn items;

    // HBox
    private HBox hBox;

    // buttons
    private Button addItemButton;
    private Button deleteItemButton;
    private Button clearListButton;

    //status box
    private TextArea infoBox;

    // Scene
    private Scene scene;

    /**
     * Constructor for Gui class.
     */
    public Gui(){
        shoppingList = new ShoppingList(true);
        txtHandler = new TxtHandler();
        dropboxHandler = new DropboxHandler();
        dropboxHandler.connect("");
        storeData = new StoreData();
    }

    /**
     * Initializes gui components.
     *
     * @param primaryStage stage for gui
     */
    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        // GridPane
        gridPane = new GridPane();

        // menuBar
        menuBar = new MenuBar();

        // menuBar components
        fileMenuButton = new Menu("File");
        editMenuButton = new Menu("Edit");

        // menuBar items
        // file
        saveListTxt = new MenuItem("Save list as .txt file");
        saveListDb = new MenuItem("Save list to database");
        saveListDrop = new MenuItem("Save list to Dropbox");
        //separator for menu
        SeparatorMenuItem separatorMenuItem1 = new SeparatorMenuItem();
        openListTxt = new MenuItem("Open list from .txt file");
        openListDb = new MenuItem("Open list from database");
        openListDrop = new MenuItem("Open list from Dropbox");
        //separator for menu
        SeparatorMenuItem separatorMenuItem2 = new SeparatorMenuItem();
        close = new MenuItem("Close");



        //add menu items to file menu
        fileMenuButton.getItems().addAll(saveListTxt, saveListDb, saveListDrop, separatorMenuItem1,
                openListTxt, openListDb, openListDrop,separatorMenuItem2, close);

        // edit
        addItem = new MenuItem("Add item");
        deleteItem = new MenuItem("Delete item");
        clearList = new MenuItem("Clear list");

        //add menu items to edit menu
        editMenuButton.getItems().addAll(addItem, deleteItem, clearList);

        // add menus to menubar
        menuBar.getMenus().addAll(fileMenuButton, editMenuButton);

        // tableview and its columns
        tableView = new TableView();
        tableView.setMinHeight(600);
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // columns
        amount = new TableColumn("Amount");
        amount.setMinWidth(200);
        amount.setEditable(true);
        amount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        amount.setCellFactory(TextFieldTableCell.<ShoppingItem, Integer>forTableColumn(new IntegerStringConverter()));

        items = new TableColumn("Item");
        items.setMinWidth(975);
        items.setEditable(true);
        items.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        items.setCellFactory(TextFieldTableCell.forTableColumn());

        // add columns
        tableView.getColumns().addAll(amount, items);

        // HBox
        hBox = new HBox();

        // buttons
        addItemButton = new Button("Add item");
        addItemButton.setMinSize(200, 180);

        deleteItemButton = new Button("Delete item");
        deleteItemButton.setMinSize(200, 180);

        clearListButton = new Button("Clear list");
        clearListButton.setMinSize(200, 180);


        //info box
        infoBox = new TextArea("Shopping List 2000\nVersion 1.0\n\nCreated by Joni Tuominen" +
                "\nEmail: joni.tuominen@cs.tamk.fi");
        infoBox.setEditable(false);
        infoBox.setMinSize(200, 180);

        //add components to hbox
        hBox.getChildren().addAll(addItemButton, deleteItemButton, clearListButton, infoBox);

        //add components to gridpane
        gridPane.add(menuBar, 0, 0);
        gridPane.add(tableView, 0, 1);
        gridPane.add(hBox, 0, 2);

        scene = new Scene(gridPane, 1200, 800);

        setListeners();

        this.primaryStage.setTitle("Shopping List 2000");
        this.primaryStage.setResizable(false);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();

    }

    /**
     * Adds existing item to tableview.
     *
     * @param index
     * @param amount
     * @param name
     */
    public void addFromExisting(int index, int amount, String name){
        tableView.getItems().add(index, new ShoppingItem(amount, name));
    }

    /**
     * Adds new item to tableview.
     */
    public void addItem(){
        tableView.getItems().add(0, new ShoppingItem(0, "New Item"));
    }

    /**
     * Removes item from tableview.
     */
    public void removeItem(){
        tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
    }

    /**
     * Converts tableview items to the list.
     */
    public void tableToList(){
        shoppingList.clearList();
        for (int i = 0; i < tableView.getItems().size(); i++){
            if (tableView.getItems().get(i).getAmount() != 0 && !tableView.getItems().get(i).getItemName().equalsIgnoreCase("new item"))
                shoppingList.toList(tableView.getItems().get(i).getAmount(), tableView.getItems().get(i).getItemName());
        }
    }

    /**
     * Clears tableview.
     */
    public void clearTable(){
        for ( int i = 0; i < tableView.getItems().size(); i++) {
            tableView.getItems().clear();
            shoppingList.clearList();
        }
    }

    /**
     * Prints items (for debug purposes).
     */
    public void printItems(){
        for (int i = 0; i < shoppingList.getAmountList().size(); i++){
            System.out.print("  " + shoppingList.getAmountList().get(i));
            System.out.println(" " + shoppingList.getItemList().get(i));
        }
    }

    /**
     * Sets event listeners to gui components.
     */
    public void setListeners(){

        //Tableview cell (amount column)
        amount.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ShoppingItem, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                try {
                    ((ShoppingItem) event.getTableView().getItems().get(event.getTablePosition().getRow())).setAmount((Integer) event.getNewValue());
                    if (((ShoppingItem) event.getTableView().getItems().get(event.getTablePosition().getRow())).getAmount() < 0){
                        ((ShoppingItem) event.getTableView().getItems().get(event.getTablePosition().getRow())).setAmount(0);
                        tableView.refresh();
                    }
                } catch (NumberFormatException e){
                    e.getStackTrace();
                }
            }
        });

        //Tableview cell (item column)
        items.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<ShoppingItem, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent event) {
                ((ShoppingItem) event.getTableView().getItems().get(event.getTablePosition().getRow())).setItemName(event.getNewValue().toString());
            }
        });

        //menulisteners (file)
        saveListTxt.setOnAction(e -> {
            tableToList();
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File tmp = fileChooser.showSaveDialog(primaryStage);

            if(tmp != null){
                txtHandler.toFile(txtHandler.stringBuilder(shoppingList.getAmountList(), shoppingList.getItemList()), tmp);
            }
        });

        //save to database
        saveListDb.setOnAction(e -> {
            tableToList();
            storeData.uploadData(shoppingList.getAmountList(), shoppingList.getItemList());
        });

        //save to dropbox
        saveListDrop.setOnAction(e -> {
            tableToList();
            TextInputDialog dialog = new TextInputDialog("shoppinglist");
            dialog.setTitle("Name");
            dialog.setContentText("Give name for file:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                System.out.println("File name: " + result.get());
                try {
                    dropboxHandler.uploadFile(txtHandler.stringBuilder(shoppingList.getAmountList(), shoppingList.getItemList()), result);
                } catch (DbxException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        //open txt
        openListTxt.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter =
                    new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File tmp = fileChooser.showOpenDialog(primaryStage);

            if(tmp != null){

                txtHandler.readFile(tmp);
                clearTable();
                shoppingList.clearList();
                shoppingList.setAmountList(txtHandler.buildAmountList());
                shoppingList.setItemList(txtHandler.buildItemNameList());
                for (int i = 0; i < shoppingList.getItemList().size(); i++) {
                    addFromExisting(i, shoppingList.getAmountList().get(i), shoppingList.getItemList().get(i));
                }
                tableToList();
                tableView.refresh();
            }
        });

        //open list from database
        openListDb.setOnAction(e -> {

            Platform.runLater(() -> {
                clearTable();
                shoppingList.clearList();
                MyLinkedList<ShoppingItem> itemsTmp = storeData.downloadData();
                for (int i = 0; i < itemsTmp.size(); i++){
                    addFromExisting(i, itemsTmp.get(i).getAmount(), itemsTmp.get(i).getItemName());
                }
                tableToList();
                tableView.refresh();
            });
        });

        //open file from dropbox
        openListDrop.setOnAction(e -> Platform.runLater(this::handleFilePick));

        //close application
        close.setOnAction(e -> System.exit(1));

        //menulisteners (edit)
        //add item to tableview
        addItem.setOnAction(e -> addItem());
        //delete item from tableview
        deleteItem.setOnAction(e -> removeItem());
        //clear tableview
        clearList.setOnAction(e -> clearTable());

        //buttons
        //add item to tableview
        addItemButton.setOnAction(e -> addItem());
        //delete item from tableview
        deleteItemButton.setOnAction(e -> removeItem());
        //clear tableview
        clearListButton.setOnAction(e -> clearTable());
    }

    /**
     * Handles file pick from dropbox and provides windows for selecting file.
     */
    public void handleFilePick(){
        dropboxHandler.getFileNames();
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);

        // gridpane
        GridPane fileGridPane = new GridPane();

        // vbox and buttons
        Button ok = new Button("OK");
        ok.setMinSize(165, 210);
        Button cancel = new Button("Cancel");
        cancel.setMinSize(165,210);
        VBox vBox = new VBox();
        vBox.getChildren().addAll(ok, cancel);

        //tableview and column
        TableView<FileName> fileTableView = new TableView();
        fileTableView.setMinHeight(410);
        fileTableView.setMinWidth(450);
        fileTableView.setEditable(true);
        fileTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        TableColumn files = new TableColumn("Files");
        files.setEditable(false);
        files.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        files.setCellFactory(TextFieldTableCell.<FileName>forTableColumn());
        fileTableView.getColumns().addAll(files);

        //add components to gridpane
        fileGridPane.add(vBox, 1,0);
        fileGridPane.add(fileTableView, 0, 0);

        Scene fileScene = new Scene(fileGridPane, 600, 400);
        dialog.setScene(fileScene);
        dialog.setTitle("Choose file");
        dialog.setResizable(false);

        //fills tableview with items from selected file
        for (int i = 0; i < dropboxHandler.getTmp().size(); i++){
            fileTableView.getItems().add(i,(dropboxHandler.getTmp().get(i)));
        }
        fileTableView.refresh();

        //actionlistener for button used for confirming selection
        ok.setOnAction(ev -> {
            FileName tmp = fileTableView.getSelectionModel().getSelectedItems().get(0);
            txtHandler.readFile(dropboxHandler.downloadFile(tmp.getItemName()));
            clearTable();
            shoppingList.clearList();
            shoppingList.setAmountList(txtHandler.buildAmountList());
            shoppingList.setItemList(txtHandler.buildItemNameList());
            for (int i = 0; i < shoppingList.getItemList().size(); i++) {
                addFromExisting(i, shoppingList.getAmountList().get(i), shoppingList.getItemList().get(i));
            }
            tableToList();
            tableView.refresh();
            dialog.close();
        });

        //actionlistener for cancel button
        cancel.setOnAction(ev -> {
            dialog.close();
        });

        dialog.show();
    }

    /**
     * Main method.
     *
     * @param args for interface selection
     */
    public static void main(String... args) {
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("gui")) {
                launch(args);
            }
        } else {
            new Cli();
        }
    }
}
