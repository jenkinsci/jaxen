/*
 * Copyright 2001 (C) bob mcwhirter and James Strachan. All Rights Reserved.
 * 
 * This software is open source. 
 * See the LICENCE.txt that came with this distribution for the licence.
 * 
 * $Id: AnyNodeTest.java 90 2001-08-08 21:29:49Z jstrachan $
 */

package org.jaxen.pattern;

import org.jaxen.Context;

/** <p><code>AnyNodeTest</code> matches any node.</p>
  *
  * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
  * @version $Revision: 90 $
  */
public class AnyNodeTest extends NodeTest {

    private static AnyNodeTest instance = new AnyNodeTest();
    
    public static AnyNodeTest getInstance() 
    {
        return instance;
    }
    
    public AnyNodeTest() 
    {
    }
    
    /** @return true if the pattern matches the given node
      */
    public boolean matches( Object node, Context context ) 
    {
        return true;
    }
    
    public double getPriority() 
    {
        return -0.5;
    }

    public short getMatchType() 
    {
        return ANY_NODE;
    }
    
    public String getText() 
    {
        return "*";
    }
}