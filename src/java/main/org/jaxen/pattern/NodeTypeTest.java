/*
 * Copyright 2001 (C) bob mcwhirter and James Strachan. All Rights Reserved.
 * 
 * This software is open source. 
 * See the LICENCE.txt that came with this distribution for the licence.
 * 
 * $Id: NodeTypeTest.java 87 2001-08-07 19:11:36Z jstrachan $
 */

package org.jaxen.pattern;

import org.jaxen.Context;

/** <p><code>ElementTypeTest</code> matches if the node is an element.</p>
  *
  * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
  * @version $Revision: 87 $
  */
public class NodeTypeTest extends Pattern {
    
    private short matchesNodeType;
    
    public NodeTypeTest(short matchesNodeType)   
    {
        this.matchesNodeType = matchesNodeType;
    }
        
    /** @return true if the pattern matches the given node
      */
    public boolean matches( Object node, Context context ) 
    {
        return matchesNodeType == context.getNavigator().getNodeType( node );
    }
    
    public double getPriority() 
    {
        return -0.5;
    }


    public short getMatchType() 
    {
        return matchesNodeType;
    }
}
