/*
 * Copyright 2001 (C) bob mcwhirter and James Strachan. All Rights Reserved.
 * 
 * This software is open source. 
 * See the LICENCE.txt that came with this distribution for the licence.
 * 
 * $Id: NameTest.java 88 2001-08-08 11:24:19Z jstrachan $
 */

package org.jaxen.pattern;

import org.jaxen.Context;
import org.jaxen.Navigator;

/** <p><code>NameTest</code> tests for a node name.</p>
  *
  * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
  * @version $Revision: 88 $
  */
public class NameTest extends NodeTest {
    
    /** The name to match against */
    private String name;
    
    /** The type of node to match - either attribute or element */
    private short nodeType;
    
    public NameTest(String name, short nodeType)   
    {
        this.name = name;
        this.nodeType = nodeType;
    }
        
    /** @return true if the pattern matches the given node
      */
    public boolean matches( Object node, Context context ) 
    {
        Navigator navigator = context.getNavigator();
        if ( nodeType == Pattern.ELEMENT_NODE ) 
        {
            return navigator.isElement( node ) 
                && name.equals( navigator.getElementName( node ) );
        }
        else if ( nodeType == Pattern.ATTRIBUTE_NODE ) 
        {
            return navigator.isAttribute( node )
                && name.equals( navigator.getAttributeName( node ) );
        }
        return false;
    }
    
    public double getPriority() 
    {
        return 0.0;
    }


    public short getMatchType() 
    {
        return nodeType;
    }
}
