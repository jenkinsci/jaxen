/*

 * $Header$

 * $Revision: 270 $

 * $Date: 2002-05-20 01:34:12 -0700 (Mon, 20 May 2002) $

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

 * $Id: DefaultPathExpr.java 270 2002-05-20 08:34:12Z jstrachan $

 */







package org.jaxen.expr;



import org.jaxen.Context;

import org.jaxen.JaxenException;



import java.util.Iterator;



class DefaultPathExpr extends DefaultExpr implements PathExpr

{

    private Expr         filterExpr;

    private LocationPath locationPath;



    public DefaultPathExpr(Expr filterExpr,

                           LocationPath locationPath)

    {

        this.filterExpr   = filterExpr;

        this.locationPath = locationPath;

    }



    public Expr getFilterExpr()

    {

        return this.filterExpr;

    }



    public void setFilterExpr(Expr filterExpr)

    {

        this.filterExpr = filterExpr;

    }



    public LocationPath getLocationPath()

    {

        return this.locationPath;

    }



    public String toString()

    {

        if ( getLocationPath() != null )

        {

            return "[(DefaultPathExpr): " + getFilterExpr() + ", " + getLocationPath() + "]";

        }



        return "[(DefaultPathExpr): " + getFilterExpr() + "]";

    }



    public String getText()

    {

        StringBuffer buf = new StringBuffer();



        if ( getFilterExpr() != null )

        {

            buf.append( getFilterExpr().getText() );

        }



        if ( getLocationPath() != null )

        {

            buf.append( getLocationPath().getText() );

        }



        return buf.toString();

    }



    public Expr simplify()
    {
        if ( getFilterExpr() != null )
        {
            setFilterExpr( getFilterExpr().simplify() );
        }

        if ( getLocationPath() != null )
        {
            getLocationPath().simplify();
        }

        if ( getFilterExpr() == null && getLocationPath() == null){
            return null;
        }


        if ( getLocationPath() == null )
        {
            return getFilterExpr();
        }

        if ( getFilterExpr() == null )
        {
            return getLocationPath();
        }

        return this;
    }

    public Object evaluate(Context context) throws JaxenException
    {
        Object results = null;
        if (getFilterExpr() != null){
            results = getFilterExpr().evaluate( context );



        context.setNodeSet( convertToList( results ) );
        }
        if (getLocationPath() != null){
            return getLocationPath().evaluate( context );
        }
        return results;
    }
    
    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }
}

