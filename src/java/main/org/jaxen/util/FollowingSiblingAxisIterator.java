/*
 * $Header$
 * $Revision: 253 $
 * $Date: 2002-04-26 10:17:37 -0700 (Fri, 26 Apr 2002) $
 *
 * ====================================================================
 *
 * Copyright (C) 2000-2002 bob mcwhirter & James Strachan.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions, and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions, and the disclaimer that follows 
 *    these conditions in the documentation and/or other materials 
 *    provided with the distribution.
 *
 * 3. The name "Jaxen" must not be used to endorse or promote products
 *    derived from this software without prior written permission.  For
 *    written permission, please contact license@jaxen.org.
 * 
 * 4. Products derived from this software may not be called "Jaxen", nor
 *    may "Jaxen" appear in their name, without prior written permission
 *    from the Jaxen Project Management (pm@jaxen.org).
 * 
 * In addition, we request (but do not require) that you include in the 
 * end-user documentation provided with the redistribution and/or in the 
 * software itself an acknowledgement equivalent to the following:
 *     "This product includes software developed by the
 *      Jaxen Project (http://www.jaxen.org/)."
 * Alternatively, the acknowledgment may be graphical using the logos 
 * available at http://www.jaxen.org/
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE Jaxen AUTHORS OR THE PROJECT
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 * ====================================================================
 * This software consists of voluntary contributions made by many 
 * individuals on behalf of the Jaxen Project and was originally 
 * created by bob mcwhirter <bob@werken.com> and 
 * James Strachan <jstrachan@apache.org>.  For more information on the 
 * Jaxen Project, please see <http://www.jaxen.org/>.
 * 
 * $Id: FollowingSiblingAxisIterator.java 253 2002-04-26 17:17:37Z jstrachan $
 */



package org.jaxen.util;

import org.jaxen.Navigator;
import org.jaxen.UnsupportedAxisException;

import java.util.Iterator;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class FollowingSiblingAxisIterator implements Iterator
{
    private Object    contextNode;
    private Navigator navigator;

    private Iterator  siblingIter;

    private Object    nextObj;

    public FollowingSiblingAxisIterator(Object contextNode,
                                        Navigator navigator) throws UnsupportedAxisException
    {
        this.contextNode = contextNode;
        this.navigator   = navigator;

        init();

        stepAhead();
    }

    private void init() throws UnsupportedAxisException
    {
        Object parent = this.navigator.getParentNode( this.contextNode );

        List siblings = Collections.EMPTY_LIST;

        boolean foundSelf = false;

        if ( parent != null )
        {
            Iterator childIter = this.navigator.getChildAxisIterator( parent );
            Object   eachChild = null;
            
            siblings = new ArrayList();
            
            while ( childIter.hasNext() )
            {
                eachChild = childIter.next();

                if ( foundSelf )
                {
                    siblings.add( eachChild );
                }
                else if ( eachChild == this.contextNode )
                {
                    foundSelf = true;
                }
            }
        }

        this.siblingIter = siblings.iterator();
    }

    public boolean hasNext()
    {
        return ( this.nextObj != null );
    }

    public Object next() throws NoSuchElementException
    {
        if ( ! hasNext() )
        {
            throw new NoSuchElementException();
        }

        Object obj = this.nextObj;

        this.nextObj = null;

        stepAhead();

        return obj;
    }

    public void remove() throws UnsupportedOperationException
    {
        throw new UnsupportedOperationException();
    }

    private void stepAhead()
    {
        if ( this.nextObj != null )
        {
            return;
        }

        Object obj = null;

        while ( siblingIter.hasNext() )
        {
            obj = siblingIter.next();

            if ( this.navigator.isElement( obj ) )
            {
                this.nextObj = obj;
                break;
            }
        }
    }
}
