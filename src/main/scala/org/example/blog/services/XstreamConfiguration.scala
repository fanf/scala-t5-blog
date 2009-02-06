package org.example.blog.services

import com.thoughtworks.xstream.converters.SingleValueConverter

/*
 * Theses traits are the differents way to configure
 * Xstream.
 */


/*
 * We have to define concrete implementation of each types used
 * in T5-IoC configuration because it does not support
 * multi-parametrized types. That's a lot less cool
 * than direct uses of tuples :/
 */
trait Palias extends scala.Tuple2[String,String]
class Calias(_1:String,_2:Class[_]) extends Tuple2[String,Class[_]](_1,_2)
class Falias(_1:String,_2:Class[_],_3:String) extends Tuple3[String,Class[_],String](_1,_2,_3)
trait Icoll extends Tuple2[Class[_],String]
trait Ofield extends Tuple2[Class[_],String]

/*
 * In get*Alias methods, the first parameter 
 * is always the alias name
 */
trait XstreamPackageAlias {
  def getPackageAliases : Collection[Palias]
}

trait XstreamClassAlias {
  def getClassAliases : Collection[Calias]
}

trait XstreamFieldAlias {
  def getFieldAliases : Collection[Falias]
}

trait XstreamImplicitCollection {
  def getImplicitCollections : Collection[Icoll]
}

trait XstreamRegisterConverter {
  def getConverters : Collection[SingleValueConverter]
}

trait XstreamOmitField {
  def getOmitFields : Collection[Ofield]
}
