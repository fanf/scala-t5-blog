package org.example.blog.services.impl

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

import org.example.blog.services._


/**************************************************
 * This file defined two marshaller based on the
 * Xstream library : one for Json, an other for Xml
 ***************************************************/

/*
 * Define the two MArshaller types
 */
final case object Xml extends MarshallerType
final case object Json extends MarshallerType

/*
 * A marshaller that use XStream, the holly serializer library
 * http://xstream.codehaus.org/
 * 
 * XStream output can be basically configured in 6 ways :
 * - package aliases ;
 * - class aliases ;
 * - attributes aliases ;
 * - implicite collections ;
 * - register new converter ;
 * - omiting fields
 * 
 * So, just let us have on configuration service for each of them
 */
class XstreamMarshaller(
  val xstream:XStream,
  xstreamPackageAlias: XstreamPackageAlias,
  xstreamClassAlias: XstreamClassAlias,
  xstreamFieldAlias: XstreamFieldAlias,
  xstreamImplicitCollection: XstreamImplicitCollection,
  xstreamRegisterConverter: XstreamRegisterConverter,
  xstreamOmitField: XstreamOmitField

) extends Marshaller 
{
  
  xstreamPackageAlias.getPackageAliases.foreach( a => {
    xstream.aliasPackage(a._1,a._2)
  } )
  
  xstreamClassAlias.getClassAliases.foreach( a => {
    xstream.alias(a._1,a._2)
  } )

  xstreamFieldAlias.getFieldAliases.foreach( a => {
    xstream.aliasField(a._1,a._2,a._3)
  } )

  xstreamImplicitCollection.getImplicitCollections.foreach( i => {
    xstream.addImplicitCollection(i._1,i._2)
  } )

  xstreamRegisterConverter.getConverters.foreach( c => {
    xstream.registerConverter(c)
  } )
  
  xstreamOmitField.getOmitFields.foreach( o => {
    xstream.omitField(o._1,o._2)
  } )
  
  override def to(o : Any) = this.xstream.toXML(o)
  
  override def from(s:String) : Any =  this.xstream.fromXML(s) 
}



class XmlXstreamMarshaller(
  xstreamPackageAlias: XstreamPackageAlias,
  xstreamClassAlias: XstreamClassAlias,
  xstreamFieldAlias: XstreamFieldAlias,
  xstreamImplicitCollection: XstreamImplicitCollection,
  xstreamRegisterConverter: XstreamRegisterConverter,
  xstreamOmitField: XstreamOmitField

) extends XstreamMarshaller(
  new XStream(new DomDriver()),
  xstreamPackageAlias, 
  xstreamClassAlias, 
  xstreamFieldAlias, 
  xstreamImplicitCollection, 
  xstreamRegisterConverter, 
  xstreamOmitField)

class JsonXstreamMarshaller(
  xstreamPackageAlias: XstreamPackageAlias,
  xstreamClassAlias: XstreamClassAlias,
  xstreamFieldAlias: XstreamFieldAlias,
  xstreamImplicitCollection: XstreamImplicitCollection,
  xstreamRegisterConverter: XstreamRegisterConverter,
  xstreamOmitField: XstreamOmitField

) extends XstreamMarshaller(
  new XStream(new JsonHierarchicalStreamDriver()),
  xstreamPackageAlias, 
  xstreamClassAlias, 
  xstreamFieldAlias, 
  xstreamImplicitCollection, 
  xstreamRegisterConverter, 
  xstreamOmitField)



