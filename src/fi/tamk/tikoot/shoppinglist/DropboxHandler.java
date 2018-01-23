//DropboxHandler
package fi.tamk.tikoot.shoppinglist;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;

import java.io.*;
import java.util.Optional;

/**
 * DropboxHandler class provides tools to save list to dropbox or to open list from dropbox.
 *
 * @author Joni Tuominen {@literal <joni.tuominen@cs.tamk.fi>}
 * @version 1.0
 * @since 1.0
 */

public class DropboxHandler {

    private String accessToken;
    private MyLinkedList<FileName> tmp;

    private DbxClientV2 sDbxClient;
    private File file;


    /**
     * Constructor for DropboxHandler class.
     */
    public DropboxHandler() {
        tmp = new MyLinkedList();
    }

    /**
     * Connects to dropbox account using dropbox access token.
     *
     * @param accessToken for user identification
     */
    public void connect(String accessToken){
        this.accessToken = accessToken;
        if (sDbxClient == null) {
            DbxRequestConfig requestConfig = DbxRequestConfig.newBuilder("dropbox/java-tutorial")
                    .build();

            sDbxClient = new DbxClientV2(requestConfig, accessToken);
        }
    }

    /**
     * Accessor for client.
     *
     * @return sDbxClient if not null.
     */
    public DbxClientV2 getClient() {
        if (sDbxClient == null) {
            throw new IllegalStateException("Client not initialized.");
        }
        return sDbxClient;
    }

    /**
     * writes and uploads file to dropbox.
     *
     * @param text for the txt file
     * @param name of the file
     * @throws DbxException if file saving or creating fails
     * @throws IOException if file writing fails
     */
    public  void uploadFile(String text, Optional<String> name) throws DbxException, IOException {
        try {
            this.file = new File(name.get()+".txt");

            // Create file if it doesn't exist
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(text);
            bw.close();
        } catch(IOException e) {
            System.out.println("Error: " + e);
            e.printStackTrace();
        }

        // Create Dropbox client
        DbxClientV2 client = getClient();

        // Upload file to Dropbox
        try (InputStream in = new FileInputStream(file)) {
            try {
                client.files().getMetadata("/Apps/ShoppingList2000/" + file.getName());
                client.files().deleteV2("/Apps/ShoppingList2000/" + file.getName());
            } catch (GetMetadataErrorException e){
                if (e.errorValue.isPath() && e.errorValue.getPathValue().isNotFound()) {
                    System.out.println("File not found.");
                } else {
                    throw e;
                }
            }
            FileMetadata metadata = client.files().upload("/Apps/ShoppingList2000/" + file.getName())
                    .uploadAndFinish(in);
        }
    }

    /**
     * Gets files from dropbox account for file listing.
     */
    public void getFileNames(){
        tmp = new MyLinkedList();

        // Create Dropbox client
        DbxClientV2 client = getClient();

        try {
            // Get files in folder /Apps/ShoppingList2000
            for (int i = 0; i < client.files().listFolder("/Apps/ShoppingList2000/").getEntries().size(); i++){
                tmp.add(new FileName(client.files().listFolder("/Apps/ShoppingList2000/").getEntries().get(i).getName()));
            }
        } catch (DbxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Downloads file from dropbox.
     *
     * @param fileName of the file that is to be downloaded
     * @return file that was downloaded
     */
    public File downloadFile(String fileName){
        System.out.println(fileName);
        // Create Dropbox client
        DbxClientV2 client = getClient();

        try {
            file = File.createTempFile("shopping-", "-list");
            FileOutputStream outputStream = new FileOutputStream(file);
            DbxDownloader<FileMetadata> downloader = client.files().download("/Apps/ShoppingList2000/" + fileName);
            downloader.download(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DownloadErrorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DbxException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * Accessor for temp list for filesnames.
     *
     * @return tmp list containing filenames.
     */
    public MyLinkedList<FileName> getTmp() {
        return tmp;
    }
}
