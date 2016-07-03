/**
 * Copyright (c) CI Wise Inc.  All rights reserved.  http://www.ciwise.com
 * The software in this package is published under the terms of the Apache
 * version 2.0 license, a copy of which has been included with this distribution 
 * in the LICENSE.md file.
 * 
 */ 

package org.ciwise.commons.command;

import java.util.Map;

/**
 *  @author <a href="mailto:david@ciwise.com">David L. Whitehurst</a>
 *
 */
public class DeleteCommandRunner extends ConsoleCommandRunner {

    private Map<String,String> configuration;
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    public void run() {
        // TODO Auto-generated method stub
        
    }

    /* (non-Javadoc)
     * @see org.ciwise.commons.command.ConsoleCommandRunner#loadConfiguration(java.util.Map)
     */
    @Override
    public void loadConfiguration(Map<String, String> config) {
        this.configuration = config;
    }

    /* (non-Javadoc)
     * @see org.ciwise.commons.command.ConsoleCommandRunner#toString()
     */
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return null;
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
