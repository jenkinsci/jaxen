/*
 * $Header$
 * $Revision: 1011 $
 * $Date: 2005-09-05 06:23:32 -0700 (Mon, 05 Sep 2005) $
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
 *      Jaxen Project <http://www.jaxen.org/>."
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
 * $Id: FunctionCallException.java 1011 2005-09-05 13:23:32Z elharo $
 */


package org.jaxen;

/** <code>FunctionCallException</code> is thrown if an exception
 * occurs during the evaluation of a function.
 * This exception may include a root exception, such as if the 
 * real exception was failure to load an XML document via the
 * document() function call.
 *
 * @author bob mcwhirter (bob @ werken.com)
 * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
 */
public class FunctionCallException extends JaxenException
{

    /**
     * Create a new FunctionCallException with the specified detail message.
     * 
     * @param message the detail message
     */
    public FunctionCallException(String message) {
        super( message );
    }

    /**
     * Create a new FunctionCallException with the specified root cause.
     * 
     * @param nestedException the cause of this exception
     */
    public FunctionCallException(Throwable nestedException) {
        super( nestedException );
    }

    /**
     * Create a new FunctionCallException with the specified detail message
     * and root cause.
     * 
     * @param message the detail message
     * @param nestedException the cause of this exception
     */
    public FunctionCallException(String message, Exception nestedException) {
        super( message, nestedException );
    }
       
    /**
     * <p>
     * Returns the exception that caused this function call to fail.
     * Use getCause() instead.
     * </p>
     * 
     * @return the exception that caused this fucntion call to fail
     * 
     * @deprecated replaced by {@link #getCause()}
     */
    public Throwable getNestedException() {
        return getCause();
    }
    
}
