/*
 * $Header$
 * $Revision: 983 $
 * $Date: 2005-06-28 06:44:46 -0700 (Tue, 28 Jun 2005) $
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
 * $Id: DefaultEqualityExpr.java 983 2005-06-28 13:44:46Z elharo $
 */



package org.jaxen.expr;

import java.util.Iterator;
import java.util.List;
import org.jaxen.Context;
import org.jaxen.JaxenException;
import org.jaxen.Navigator;
import org.jaxen.function.BooleanFunction;
import org.jaxen.function.NumberFunction;
import org.jaxen.function.StringFunction;

abstract class DefaultEqualityExpr extends DefaultTruthExpr implements EqualityExpr 
  {
  DefaultEqualityExpr( Expr lhs, Expr rhs )
    {
    super( lhs, rhs );
    }

  public String toString()
    {
    return "[(DefaultEqualityExpr): " + getLHS() + ", " + getRHS() + "]";
    }
  
  public Object evaluate( Context context ) throws JaxenException
    {
    Object lhsValue = getLHS().evaluate( context );
    Object rhsValue = getRHS().evaluate( context );
    
    if( lhsValue == null || rhsValue == null )
      {
      return Boolean.FALSE;
      }
    
    Navigator nav = context.getNavigator();

    if( bothAreSets( lhsValue, rhsValue ) )
      {
      return evaluateSetSet( (List) lhsValue, (List) rhsValue, nav );
      }
    else if ( eitherIsSet( lhsValue, rhsValue ) )
      {
      if ( isSet( lhsValue ) )
        {
        return evaluateSetSet( (List) lhsValue, convertToList( rhsValue ), nav );                
        }
      else
        {
        return evaluateSetSet( convertToList( lhsValue ), (List) rhsValue, nav );                                
        }
      }  
    else
      {
      return evaluateObjectObject( lhsValue, rhsValue, nav ) ? Boolean.TRUE : Boolean.FALSE;
      }    
    }
  
  private Boolean evaluateSetSet( List lhsSet, List rhsSet, Navigator nav )
    {
    if( setIsEmpty( lhsSet ) || setIsEmpty( rhsSet ) )
      {
      return Boolean.FALSE;
      }
    
    for( Iterator lhsIterator = lhsSet.iterator(); lhsIterator.hasNext(); )
      {
      Object lhs = lhsIterator.next();
      
      for( Iterator rhsIterator = rhsSet.iterator(); rhsIterator.hasNext(); )
        {
        Object rhs = rhsIterator.next();
        
        if( evaluateObjectObject( lhs, rhs, nav ) )
          {
          return Boolean.TRUE;
          }
        }
      }
    
    return Boolean.FALSE;
    }
  
  private boolean evaluateObjectObject( Object lhs, Object rhs, Navigator nav )
    {
    if( eitherIsBoolean( lhs, rhs ) )
      {            
      return evaluateObjectObject( BooleanFunction.evaluate( lhs, nav ),
                                   BooleanFunction.evaluate( rhs, nav ) );
      }
    else if( eitherIsNumber( lhs, rhs ) )
      {
      return evaluateObjectObject( NumberFunction.evaluate( lhs,
                                                            nav ),
                                   NumberFunction.evaluate( rhs,
                                                            nav ) );                                              
      }
    else
      {
      return evaluateObjectObject( StringFunction.evaluate( lhs,
                                                            nav ),
                                   StringFunction.evaluate( rhs,
                                                            nav ) );
      }
    }
  
  protected abstract boolean evaluateObjectObject( Object lhs, Object rhs );
  }
