/**
 * Copyright (c) CI Wise Inc.  All rights reserved.  http://www.ciwise.com
 * The software in this package is published under the terms of the Apache
 * version 2.0 license, a copy of which has been included with this distribution 
 * in the LICENSE.md file.
 * 
 */ 

package org.ciwise.notification.utils;

import java.util.ArrayList;
import java.util.List;

/**
 *  @author <a href="mailto:david@ciwise.com">David L. Whitehurst</a>
 *
 */
public class ConsoleHelpTemplate {

    /**
     * 
     */
    private static final String GENERAL = "General Help Template";
    
    /**
     * 
     */
    private static final String COMMAND = "Command Help Template";

    /**
     * 
     */
    private static final String UNKNOWN = "Unknown";
    
    /**
     * 
     */
    private String type = UNKNOWN; // Default
    
    /**
     * 
     */
    private String name;
    
    private String subjectCommand;
    
    /**
     * 
     */
    private List<String> globalOptions = new ArrayList<String>();
    
    /**
     * 
     */
    private List<String> commands = new ArrayList<String>();
    
    /**
     * 
     */
    private List<String> commandOptions = new ArrayList<String>();
    
    /**
     * 
     */
    private String description;
    
    /**
     * 
     * @param helpType
     */
    public ConsoleHelpTemplate(ConsoleHelpType helpType) {
        switch (helpType) {
        case GENERAL_HELP:
            this.type = GENERAL;
            break;
        case COMMAND_HELP:
            this.type = COMMAND;
            break;
        default:
            throw new RuntimeException("Improper use of Enum!");
        }
    }
    
    public void addGlobalOption(String opt) {
        getGlobalOptions().add(opt);
    }
    
    public void addCommand(String command) {
        getCommands().add(command);
    }
    
    public void addCommandOption(String opt) {
        getCommandOptions().add(opt);
    }
    
    /**
     * This method returns a console help output string based on the type
     * specified. If the class is not <i>loaded</i> i.e. required members
     * populated, the method will return an error message for the help
     * request.
     * @return a multi-line String that represents a help text output
     */
    public String toString() {
        String retVal = null;
        
        if (type.equals(GENERAL)) {
            retVal = buildGeneralHelpTemplateString();
        }
        if (type.equals(COMMAND)) {
            retVal = buildCommandHelpTemplateString();
        }
        if (type.equals(UNKNOWN)) {
            retVal = "ERROR: The help template hasn't been correctly created or populated";
        }
        
        return retVal;
    }

    /**
     * @return
     */
    private String buildCommandHelpTemplateString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NAME").append("\n");
        sb.append("\t").append(subjectCommand).append("\n\n");
        sb.append("SYNOPSIS").append("\n");
        sb.append("\t").append(name).append(" [global options] " + subjectCommand + " [command options] [arguments...]").append("\n\n");
        sb.append("DESCRIPTION").append("\n");
        sb.append("\t").append(description).append("\n\n");

        sb.append("GLOBAL OPTIONS").append("\n");
        for (String option: getGlobalOptions()) {
            sb.append("\t").append(option).append("\n");
            
        }
        sb.append("\n");
        
        sb.append("COMMAND OPTIONS").append("\n");
        for (String option: getCommandOptions()) {
            sb.append("\t").append(option).append("\n");
            
        }
       
        return sb.toString();
    }

    /**
     * @return
     */
    private String buildGeneralHelpTemplateString() {
        StringBuilder sb = new StringBuilder();
        sb.append("NAME").append("\n");
        sb.append("\t").append(name).append("\n\n");
        sb.append("SYNOPSIS").append("\n");
        sb.append("\t").append(name).append(" [global options] command [command options] [arguments...]").append("\n\n");
        
        sb.append("GLOBAL OPTIONS").append("\n");
        for (String option: getGlobalOptions()) {
            sb.append("\t").append(option).append("\n");
            
        }
        sb.append("\n");

        sb.append("COMMANDS").append("\n");
        for (String command: getCommands()) {
           sb.append("\t").append(command).append("\n"); 
        }
        
        return sb.toString();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getGlobalOptions() {
        return globalOptions;
    }

    public void setGlobalOptions(List<String> globalOptions) {
        this.globalOptions = globalOptions;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public List<String> getCommandOptions() {
        return commandOptions;
    }

    public void setCommandOptions(List<String> commandOptions) {
        this.commandOptions = commandOptions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubjectCommand() {
        return subjectCommand;
    }

    public void setSubjectCommand(String subjectCommand) {
        this.subjectCommand = subjectCommand;
    }

}
