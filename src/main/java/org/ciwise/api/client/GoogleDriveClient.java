/**
 * Copyright (c) CI Wise Inc.  All rights reserved.  http://www.ciwise.com
 * The software in this package is published under the terms of the Apache
 * version 2.0 license, a copy of which has been included with this distribution 
 * in the LICENSE.md file.
 * 
 */ 

package org.ciwise.api.client;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.ciwise.commons.api.BaseAPIClient;
import org.ciwise.commons.command.ConsoleCommand;
import org.ciwise.commons.command.UploadCommandRunner;
import org.ciwise.notification.utils.ConsoleHelpTemplate;
import org.ciwise.notification.utils.ConsoleHelpType;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;

/**
 *  @author <a href="mailto:david@ciwise.com">David L. Whitehurst</a>
 *
 */
public class GoogleDriveClient extends BaseAPIClient implements ConsoleCommand {

    /**
     * A unique serial identifier.
     */
    private static final long serialVersionUID = 5135857948751927177L;

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
     *
     */
    private static final String HELP_OPTION = "--help";

    /**
     *
     */
    private static final String FILE_OPTION_SHORT = "-f=";

    /**
     *
     */
    private static final String FILE_OPTION_LONG = "--filename=";

    /**
     *
     */
    private static final String PARENT_OPTION_SHORT = "-p=";

    /**
     *
     */
    private static final String PARENT_OPTION_LONG = "--parent=";

    /**
     *
     */
    private static final String DELETE_COMMAND = "delete";

    /**
     *
     */
    private static final String UPLOAD_COMMAND = "upload";

    /**
     *
     */
    private static final String ERROR_SHOW_HELP = "Usage: $ java jar google-drive-client-${version}.jar --help";

    /**
     *
     */
    private ConsoleHelpTemplate genHelpTempl;

    /**
     *
     */
    private ConsoleHelpTemplate uploadHelpTempl;

    /**
     *
     */
    private ConsoleHelpTemplate deleteHelpTempl;

    /**
     * Default constructor.
     */
    public GoogleDriveClient() {
        createGeneralHelpTemplate();
        createCommandHelpTemplates();
    }

    /**
     * Our Google Drive Client application main().
     * 
     * @param args
     */
    public static void main(String[] args) {

        GoogleDriveClient client = new GoogleDriveClient();

        
        if (args.length > 0) {

            
            if (args[0].equals(HELP_OPTION)) {
                if (args.length > 1) {
                    
                    if (args[1] != null && args[1].equals(UPLOAD_COMMAND)) {
                        System.out.println(client.getUploadHelpTempl().toString());
                        System.exit(1);
                    }
                    
                    if (args[1] != null && args[1].equals(DELETE_COMMAND)) {
                        System.out.println(client.getDeleteHelpTempl().toString());
                        System.exit(1);
                    } 
                }
                
                System.out.println(client.getGenHelpTempl().toString());
                System.exit(1);
            }
            
            if (args[0].startsWith(FILE_OPTION_SHORT) || args[0].startsWith(FILE_OPTION_LONG)) {
                if (args.length > 1) {
                    String fullPathFileName = null;
                    String parent = null;

                    if (args[1].equals(DELETE_COMMAND)) {
                        // do the delete thing
                        System.out.println("Delete is under construction!");
                        System.exit(1);
                    }            
                    
                    if (args[1].equals(UPLOAD_COMMAND)) {

                        if (!args[0].contains(FILE_OPTION_SHORT) && !args[0].contains(FILE_OPTION_LONG)) {
                            System.out.println("ERROR: filename option not specified correctly.");
                            System.out.println(client.getGenHelpTempl().toString());
                            System.exit(1);
                        } else {
                            if (args[0].contains(FILE_OPTION_SHORT)) {
                                fullPathFileName = args[0].substring(3);
                            }
                            if (args[0].contains(FILE_OPTION_LONG)) {
                                fullPathFileName = args[0].substring(11);
                            } 
                        }
                        System.out.println("The filename: " + fullPathFileName);

                        if (args.length > 2) {
                            // get command option
                            if (!args[2].contains(PARENT_OPTION_SHORT) && !args[2].contains(PARENT_OPTION_LONG)) {
                                System.out.println("ERROR: parent folder command-line option not specified correctly.");
                                System.out.println(client.getUploadHelpTempl().toString());
                                System.exit(1);
                            } else {
                                if (args[2].contains(PARENT_OPTION_SHORT)) {
                                    parent = args[2].substring(3);
                                }
                                if (args[2].contains(PARENT_OPTION_LONG)) {
                                    parent = args[2].substring(9);
                                } 
                            }
                            System.out.println("The parent: " + parent);
                        }
                        
                        // run the command parentId = 0ByyBtZRbAvz8OTNLdmJmbnFySWM
                        UploadCommandRunner r = new UploadCommandRunner();
                        Map<String,String> map = createConfigurationMap(getFileName(fullPathFileName), 
                                fullPathFileName, "Local File", "application/octet-stream", parent);
                        r.loadConfiguration(map);
                        r.run();
                        System.exit(1);
                        
                        
                    }
                }
            }

        } else {
            System.out.println(ERROR_SHOW_HELP);
            System.exit(1);
        }
    }
    
    /* (non-Javadoc)
     * @see org.ciwise.commons.api.BaseAPIClient#getServiceHostEntity()
     */
    @Override
    public String getServiceHostEntity() {
        final String retVal = "Provider Entity: \tGoogle";
        return retVal;
    }

    /* (non-Javadoc)
     * @see org.ciwise.commons.api.BaseAPIClient#getServiceBaseURL()
     */
    @Override
    public String getServiceBaseURL() {
        final String retVal = "BaseURL: \t\thttps://developers.google.com/api-client-library/java/apis/drive/v2";
        return retVal;
    }

    /* (non-Javadoc)
     * @see org.ciwise.commons.api.BaseAPIClient#getAPIVersion()
     */
    @Override
    public String getAPIVersion() {
        final String retVal = "API Version: \t\tv2";
        return retVal;
    }

    /* (non-Javadoc)
     * @see org.ciwise.commons.api.BaseAPIClient#getLastMetadataUpdate()
     */
    @Override
    public Date getLastMetadataUpdate() {
        // TODO - not using until BaseAPIClient method here updated to return string
        return null;
    }

    /* (non-Javadoc)
     * @see org.ciwise.commons.api.BaseAPIClient#toString()
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Console Client: \tgoogle-drive-client").append("\n");
        
        sb.append(getServiceHostEntity()).append("\n");
        sb.append(getAPIVersion()).append("\n");
        sb.append(getServiceBaseURL()).append("\n");
        return sb.toString();
    }

    /* (non-Javadoc)
     * @see org.ciwise.commons.api.BaseAPIClient#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    /* (non-Javadoc)
     * @see org.ciwise.commons.api.BaseAPIClient#hashCode()
     */
    @Override
    public int hashCode() {
        // TODO Auto-generated method stub
        return 0;
    }

    /* (non-Javadoc)
     * @see org.ciwise.commons.command.ConsoleCommand#createGeneralHelpTemplate()
     */
    public void createGeneralHelpTemplate() {
        ConsoleHelpTemplate helpTempl = new ConsoleHelpTemplate(ConsoleHelpType.GENERAL_HELP);
        helpTempl.setName("google-drive-client");
        helpTempl.addGlobalOption("-f, --filename=pathtofile");
        helpTempl.addGlobalOption("--help, show this message.");
        helpTempl.addCommand("upload");
        helpTempl.addCommand("delete");
        this.genHelpTempl = helpTempl;
    }

    /* (non-Javadoc)
     * @see org.ciwise.commons.command.ConsoleCommand#getCommandHelpTemplates()
     */
    public void createCommandHelpTemplates() {
        // upload
        ConsoleHelpTemplate helpTempl = new ConsoleHelpTemplate(ConsoleHelpType.COMMAND_HELP);
        helpTempl.setName("google-drive-client");
        helpTempl.setSubjectCommand("upload");
        helpTempl.setDescription("This command uploads a file from your computer to Google Drive");
        helpTempl.addGlobalOption("-f, --filename=pathtofile");
        this.uploadHelpTempl = helpTempl;
        
        // delete
        ConsoleHelpTemplate delHelpTempl= new ConsoleHelpTemplate(ConsoleHelpType.COMMAND_HELP);
        delHelpTempl.setName("google-drive-client");
        delHelpTempl.setSubjectCommand("delete");
        delHelpTempl.setDescription("This command deletes a file that currently exists on Google Drive");
        delHelpTempl.addGlobalOption("-f, --filename=pathtofile");
        this.deleteHelpTempl = delHelpTempl;
    }

    public ConsoleHelpTemplate getGenHelpTempl() {
        return genHelpTempl;
    }

    public void setGenHelpTempl(ConsoleHelpTemplate genHelpTempl) {
        this.genHelpTempl = genHelpTempl;
    }

    public ConsoleHelpTemplate getUploadHelpTempl() {
        return uploadHelpTempl;
    }

    public void setUploadHelpTempl(ConsoleHelpTemplate uploadHelpTempl) {
        this.uploadHelpTempl = uploadHelpTempl;
    }

    public ConsoleHelpTemplate getDeleteHelpTempl() {
        return deleteHelpTempl;
    }

    public void setDeleteHelpTempl(ConsoleHelpTemplate deleteHelpTempl) {
        this.deleteHelpTempl = deleteHelpTempl;
    }

    private static Map<String,String> createConfigurationMap(String name, String fullpath, String description, String mimeType, String parentId) {
        
        Map<String,String> map = new HashMap<String,String>();
        
        map.put("title", name); // later just filename getFileName() and filename will be fullpath
        map.put("fullpath", fullpath);
        map.put("description", description);
        map.put("mimeType", mimeType); // getMimeType() using extension TODO
        map.put("parentId", parentId);
    
        return map;
    }
    
    private static String getFileName(String fullpath) {
        String retVal = null;
        String[] parts = fullpath.split("\\\\");
        
        int pieces = parts.length;
        int index = pieces-1;
        retVal = parts[index];
        System.out.println(retVal);
                
        return retVal;
    }
}
