/*
 * $Header$
 * $Revision: 440 $
 * $Date: 2005-02-07 17:22:25 -0800 (Mon, 07 Feb 2005) $
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
 * $Id: LocalNameFunction.java 440 2005-02-08 01:22:25Z elharo $
 */


package org.jaxen.function;

import java.util.List;

import org.jaxen.Context;
import org.jaxen.Function;
import org.jaxen.FunctionCallException;
import org.jaxen.Navigator;

/**
 *   <p><b>4.1</b> <code><i>string</i> local-name(<i>node-set?</i>)</code> 
 *  
 *  @author bob mcwhirter (bob @ werken.com)
 */
public class LocalNameFunction implements Function
{

    public Object call(Context context,
                       List args) throws FunctionCallException
    {
        if ( args.size() == 0 )
        {
            return evaluate( context.getNodeSet(),
                             context.getNavigator() ); 
        }

        if ( args.size() == 1 )
        {
            return evaluate( args,
                             context.getNavigator() );
        }

        throw new FunctionCallException( "local-name() requires zero or one argument." );
    }

    public static String evaluate(List list,
                                  Navigator nav) throws FunctionCallException
    {
        if ( ! list.isEmpty() )
        {            
            Object first = list.get(0);

            if (first instanceof List)
            {
                return evaluate( (List) first,
                                 nav );
            }
            else if ( nav.isElement( first ) )
            {
                return nav.getElementName( first );
            }
            else if ( nav.isAttribute( first ) )
            {
                return nav.getAttributeName( first );
            }
            else if ( nav.isProcessingInstruction( first ) )
            {
                return nav.getProcessingInstructionTarget( first );
            }
            else if ( nav.isNamespace( first ) )
            {
                return nav.getNamespacePrefix( first );
            }
            else if ( nav.isDocument( first ) )
            {
                return "";
            }
            else if ( nav.isComment( first ) )
            {
                return "";
            }
            else if ( nav.isText( first ) )
            {
                return "";
            }
            else {
                throw new FunctionCallException("The argument to the local-name function must be a node-set");   
            }
        }

        return "";
    }
}

