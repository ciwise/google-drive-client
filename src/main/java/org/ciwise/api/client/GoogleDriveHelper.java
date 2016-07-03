/**
 * Copyright (c) CI Wise Inc.  All rights reserved.  http://www.ciwise.com
 * The software in this package is published under the terms of the Apache
 * version 2.0 license, a copy of which has been included with this distribution 
 * in the LICENSE.md file.
 * 
 */ 

package org.ciwise.api.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.ciwise.api.client.GoogleDriveHelper;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files.Delete;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;

/**
 *  @author <a href="mailto:david@ciwise.com">David L. Whitehurst</a>
 *
 */
public class GoogleDriveHelper {
    
    /**
     * Application name
     */
    private static final String APPLICATION_NAME = "CI Wise-google-drive-cleht";
    
    /** Directory to store user credentials for this application. */
    public static final java.io.File DATA_STORE_DIR = new java.io.File(
        System.getProperty("user.home"), ".credentials/google-drive-client");

    /** Global instance of the {@link FileDataStoreFactory}. */
    public static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    public static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    public static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart.json
     */
    public static final List<String> SCOPES = Arrays.asList(DriveScopes.DRIVE);

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    private static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
            GoogleDriveHelper.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(GoogleDriveHelper.JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        GoogleDriveHelper.HTTP_TRANSPORT, GoogleDriveHelper.JSON_FACTORY, clientSecrets, GoogleDriveHelper.SCOPES)
                .setDataStoreFactory(GoogleDriveHelper.DATA_STORE_FACTORY)
                .setAccessType("online")
                .setApprovalPrompt("auto")          
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + GoogleDriveHelper.DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws IOException
     */
    public static Drive getDriveService() throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(
                GoogleDriveHelper.HTTP_TRANSPORT, GoogleDriveHelper.JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public static boolean deleteFile(Drive svc, String fileID) {
        boolean retVal = true;
        try {
            Delete request = svc.files().delete(fileID);
            request.execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return retVal;
    }
    
    /**
     * 
     * @param svc
     * @param fileName
     * @param type
     * @param mimetype
     * @param fullPathName
     */
    public static void insertBackupFiles(Drive svc, String fileName, String type, String mimetype, String fullPathName) {
            insertFile(svc, fileName, type, "", mimetype, fullPathName);
    }

    /**
     * Insert new file.
     *
     * @param service Drive API service instance.
     * @param title Title of the file to insert, including the extension.
     * @param description Description of the file to insert.
     * @param parentId Optional parent folder's ID.
     * @param mimeType MIME type of the file to insert.
     * @param filename Filename of the file to insert.
     * @return Inserted file metadata if successful, {@code null} otherwise.
     */
    public static File insertFile(Drive service, String title, String description,
        String parentId, String mimeType, String filename) {
      // File's metadata.
      File body = new File();
      body.setTitle(title);
      body.setDescription(description);
      body.setMimeType(mimeType);

      // Atlassian folder ID
      //parentId = "0ByyBtZRbAvz8OTNLdmJmbnFySWM";
      
      // Set the parent folder.
      if (parentId != null && parentId.length() > 0) {
        body.setParents(
            Arrays.asList(new ParentReference().setId(parentId)));
      }

      // File's content.
      java.io.File fileContent = new java.io.File(filename);
      FileContent mediaContent = new FileContent(mimeType, fileContent);
      try {
        File file = service.files().insert(body, mediaContent).execute();

        // Uncomment the following line to print the File ID.
         System.out.println("\nFile ID: " + file.getId());

        return file;
      } catch (IOException e) {
        System.out.println("An error occured: " + e);
        return null;
      }
    }
    

}
