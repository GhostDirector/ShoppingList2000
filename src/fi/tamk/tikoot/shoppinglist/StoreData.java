//StoreData
package fi.tamk.tikoot.shoppinglist;

import org.hibernate.*;
import org.hibernate.cfg.*;
import java.util.List;

/**
 * StoreData class handles database uploading and downloading.
 *
 * @author Joni Tuominen {@literal <joni.tuominen@cs.tamk.fi>}
 * @version 1.0
 * @since 1.0
 */

public class StoreData {

    private Configuration cfg;
    private SessionFactory factory;
    private Session session;
    MyLinkedList<ShoppingItem> items;

    /**
     * Uploads data to database from given lists.
     *
     * @param amountList amount of items
     * @param itemList names of items
     */
    public void uploadData(MyLinkedList<Integer> amountList, MyLinkedList<String> itemList){

        // Create configuration object
        cfg = new Configuration();
        // Populate the data of the default configuration file which name
        // is hibernate.cfg.xml
        cfg.configure("/fi/tamk/tikoot/shoppinglist/hibernate.cfg.xml");
        cfg.addAnnotatedClass(ShoppingItem.class);
        // Create SessionFactory that can be used to open a session
        factory = cfg.buildSessionFactory();
        // Session is an interface between Java app and Database
        // Session is used to create, read, delete operations
        session = factory.openSession();

        for (int i = 0; i < amountList.size(); i++){
            Transaction tx = session.beginTransaction();
            // Save to database
            ShoppingItem tmp = new ShoppingItem();
            tmp.setAmount(amountList.get(i));
            tmp.setItemName(itemList.get(i));
            session.persist(tmp);
            tx.commit();
        }
        factory.close();
    }

    /**
     * Downloads lists from database.
     *
     * @return item list
     */
    public MyLinkedList<ShoppingItem> downloadData(){
        items = new MyLinkedList<>();
        // Create configuration object
        cfg = new Configuration();
        // Populate the data of the default configuration file which name
        // is hibernate.cfg.xml
        cfg.configure("/fi/tamk/tikoot/shoppinglist/hibernateQuery.cfg.xml");
        cfg.addAnnotatedClass(ShoppingItem.class);
        // Create SessionFactory that can be used to open a session
        factory = cfg.buildSessionFactory();
        // Session is an interface between Java app and Database
        // Session is used to create, read, delete operations
        session = factory.openSession();

        List<ShoppingItem> list = session.createQuery("from ShoppingItem").list();
        for (int i = 0; i < list.size(); i++){
            items.add(new ShoppingItem(list.get(i).getAmount(), list.get(i).getItemName()));
        }
        System.out.println(items.size());
        for (int i = 0; i < items.size(); i++){
            System.out.println(items.get(i).getAmount() + " | " + items.get(i).getItemName());
        }
        session.close();
        factory.close();
        return items;
    }
}