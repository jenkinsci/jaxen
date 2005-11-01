/* $Header$
 * $Revision$
 * $Date$
 *
 * ====================================================================
 *
 * Copyright (C) 2005 Elliotte Rusty Harold.
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
 * $Id$
 */
package org.jaxen.test;

import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jaxen.JaxenException;
import org.jaxen.SimpleNamespaceContext;
import org.jaxen.UnresolvableException;
import org.jaxen.XPath;
import org.jaxen.dom.DOMXPath;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import junit.framework.TestCase;

public class NamespaceTest extends TestCase {

    private org.w3c.dom.Document doc;

    public NamespaceTest(String name) {
        super(name);
    }
    
    protected void setUp() throws ParserConfigurationException {
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        doc = factory.newDocumentBuilder().newDocument();
        
    }     
    
    public void testMultipleNamespaceAxis() throws JaxenException {
        
        Element root = doc.createElement("root");
        doc.appendChild(root);
        Element child = doc.createElementNS("http://www.example.org", "child");
        child.setAttributeNS("http://www.w3.org/2000/xmlns/" , "xmlns:pre", "value");
        root.appendChild(child);
        
        XPath xpath = new DOMXPath("namespace::node()");
        List result = xpath.selectNodes(child);
        assertEquals(3, result.size());
   
    }
    
    public void testNumberOfNamespaceNodes() throws JaxenException {
        
        Element root = doc.createElement("root");
        doc.appendChild(root);
        Element child = doc.createElementNS("http://www.example.org", "foo:child");
        root.appendChild(child);
        
        XPath xpath = new DOMXPath("//namespace::node()");
        List result = xpath.selectNodes(doc);
        assertEquals(3, result.size());
        // 1 for xml prefix on root; 1 for foo prefix on child; 1 for xml prefix on child
   
    }
    
    
    public void testNamespaceAxis() throws JaxenException {
        
        Element root = doc.createElement("root");
        doc.appendChild(root);
        Element child = doc.createElementNS("http://www.example.org", "foo:child");
        root.appendChild(child);
        
        XPath xpath = new DOMXPath("namespace::node()");
        List result = xpath.selectNodes(child);
        assertEquals(2, result.size());
   
    }
    
    
    public void testUnprefixedNamespaceAxis() throws JaxenException {
        
        Element root = doc.createElement("root");
        doc.appendChild(root);
        Element child = doc.createElementNS("http://www.example.org", "child");
        root.appendChild(child);
        
        XPath xpath = new DOMXPath("namespace::node()");
        List result = xpath.selectNodes(child);
        assertEquals(2, result.size());
   
    }   
    
    
    public void testNamespaceNodesReadFromAttributes() throws JaxenException {
        
        Element root = doc.createElement("root");
        doc.appendChild(root);
        Attr a = doc.createAttributeNS("http://www.example.org/", "a");
        a.setNodeValue("value");
        root.setAttributeNode(a);
        
        XPath xpath = new DOMXPath("namespace::node()");
        List result = xpath.selectNodes(root);
        // one for the xml prefix; one from the attribute node
        assertEquals(2, result.size());
   
    }   
    
    
    public void testUnboundNamespaceUsedInXPathExpression() throws JaxenException {
        
        Element root = doc.createElementNS("http://www.example.org/", "root");
        doc.appendChild(root);
        XPath xpath = new DOMXPath("/pre:root");
        try {
            xpath.selectNodes(root);
            fail("Used unresolvable prefix");
        }
        catch (UnresolvableException ex) {
            assertNotNull(ex.getMessage());
        }
   
    }   
    
    
    public void testQueryDefaultNamespace() throws JaxenException {
        
        Element root = doc.createElementNS("http://www.example.org/", "root");
        doc.appendChild(root);
        XPath xpath = new DOMXPath("/pre:root");
        xpath.addNamespace("pre", "http://www.example.org/");
        List result = xpath.selectNodes(root);
        assertEquals(1, result.size());
   
    }   
    
    
    public void testQueryDefaultNamespaceWithContext() throws JaxenException {
        
        Element root = doc.createElementNS("http://www.example.org/", "root");
        doc.appendChild(root);
        XPath xpath = new DOMXPath("/pre:root");
        SimpleNamespaceContext context = new SimpleNamespaceContext();
        context.addNamespace("pre", "http://www.example.org/");
        xpath.setNamespaceContext(context);
        List result = xpath.selectNodes(root);
        assertEquals(1, result.size());
   
    }   
    
    
}
