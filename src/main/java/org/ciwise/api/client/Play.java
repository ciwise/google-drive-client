/**
 * Copyright (c) CI Wise Inc.  All rights reserved.  http://www.ciwise.com
 * The software in this package is published under the terms of the Apache
 * version 2.0 license, a copy of which has been included with this distribution 
 * in the LICENSE.md file.
 * 
 */ 

package org.ciwise.api.client;

/**
 *  @author <a href="mailto:david@ciwise.com">David L. Whitehurst</a>
 *
 */
public class Play {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        String play = "C:\\RiverOfSight\\RoS_01_Interface.zip";
        String[] parts = play.split("\\\\");
        
        int pieces = parts.length;
        int index = pieces-1;
        String last = parts[index];
        System.out.println(last);
        
    }

}
