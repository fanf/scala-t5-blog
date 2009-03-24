package org.example.blog.services.impl

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

import org.example.blog.services.Marshaller


/**************************************************
 * This file defined two marshaller based on the
 * Xstream library : one for Json, an other for Xml
 ***************************************************/



/*
 * A marshaller that use XStream, the holly serializer library
 * http://xstream.codehaus.org/
 * 
 */
class XstreamMarshaller(val xstream:XStream) extends Marshaller  {
  override def to(o : Any) = this.xstream.toXML(o)
  override def from(s:String) : Any =  this.xstream.fromXML(s) 
}



class XmlXstreamMarshaller() extends XstreamMarshaller(new XStream(new DomDriver()))

class JsonXstreamMarshaller() extends XstreamMarshaller(new XStream(new JsonHierarchicalStreamDriver()))



