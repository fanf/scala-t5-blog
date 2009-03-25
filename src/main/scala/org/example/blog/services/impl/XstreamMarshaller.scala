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

object ConfigureArticle4Xstream {
  def configure(x:XStream) {
    import org.example.blog.data.{Article,NoneArticle}
    //output "article" in place of org.example.blog.data.Article
    x.alias("article",classOf[Article])
    
    //title and id will be output as attribute of article...
    x.useAttributeFor(classOf[Article],"title")
    x.useAttributeFor(classOf[Article],"id")

    //...and since Id is not a simple string, we have to provide a converter to make it works
    x.registerLocalConverter(classOf[Article],"id",new ArticleIdConverter())
    
    //we don't want to display comments
    x.omitField(classOf[Article],"comments")
    
    //special config for NoneArticle
    x.alias("article",NoneArticle.getClass)
  }
}

import com.thoughtworks.xstream.converters.SingleValueConverter
class ArticleIdConverter extends SingleValueConverter {
  def fromString(s:String) = if("none" == s) None else Some(s)
  def toString(a:Object) = a match { 
    case None => "none" 
    case Some(x) => x.toString 
    case _ => error("Not supported type: " + a.getClass.getName)
  }
  def canConvert(c:Class[_]) = {
    if(c == classOf[scala.Option[_]]) true
    else false
  }  
}
