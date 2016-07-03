/**
 * Copyright (c) CI Wise Inc.  All rights reserved.  http://www.ciwise.com
 * The software in this package is published under the terms of the Apache
 * version 2.0 license, a copy of which has been included with this distribution 
 * in the LICENSE.md file.
 * 
 */ 

package org.ciwise.commons.command;

import java.io.IOException;
import java.util.Map;

import org.ciwise.api.client.GoogleDriveHelper;
import org.ciwise.notification.ConsoleCharRunner;
import org.ciwise.notification.ConsoleNotifier;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;

/**
 * This class implements a file upload process to Google Drive.
 *  @author <a href="mailto:david@ciwise.com">David L. Whitehurst</a>
 *
 */
public class UploadCommandRunner extends ConsoleCommandRunner {

    static {
        try {
            GoogleDriveHelper.HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            GoogleDriveHelper.DATA_STORE_FACTORY = new FileDataStoreFactory(GoogleDriveHelper.DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * A Map containing key/value pairs of configuration data for use when
     * the command process runs.
     */
    private Map<String,String> configuration;
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {

        final String process1 = "Google Drive Upload (JIRA)";

        ConsoleNotifier.getInstance().header(process1);
        ConsoleNotifier.getInstance().beginProcess();
        ConsoleCharRunner r = new ConsoleCharRunner();
        
        r.setStrChar("+");
        Thread t = new Thread(r);
        t.start();

        // Build a new authorized API client service.
        Drive service = null;
        try {
            service = GoogleDriveHelper.getDriveService();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // add the file
        GoogleDriveHelper.insertFile(service, getConfiguration().get("title"), 
                getConfiguration().get("description"), 
                getConfiguration().get("parentId"), 
                getConfiguration().get("mimeType"), 
                getConfiguration().get("fullpath"));
 
        // tell thread to stop printing periods
        r.setRunning(false);
        ConsoleNotifier.getInstance().footer(process1);
        
    }

    /* (non-Javadoc)
     * @see org.ciwise.commons.command.ConsoleCommandRunner#loadConfiguration()
     */
    @Override
    public void loadConfiguration(Map<String,String> config) {
        this.configuration = config;
    }

    /* (non-Javadoc)
     * @see org.ciwise.commons.command.ConsoleCommandRunner#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Google Drive Client: File Upload Command").append("\n");
        sb.append("org.ciwise.commons.command.UploadCommandRunner:ConsoleCommandRunner implementation").append("\n");
        return sb.toString();
    }

    /* (non-Javadoc)
     * @see org.ciwise.commons.command.ConsoleCommandRunner#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see org.ciwise.commons.command.ConsoleCommandRunner#hashCode()
     */
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return 0;
    }

    public Map<String, String> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, String> configuration) {
        this.configuration = configuration;
    }
    
}
