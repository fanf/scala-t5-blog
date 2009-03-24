package org.example.blog.services

import com.thoughtworks.xstream.converters.ConverterMatcher

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
//for package aliasing
class Palias(_1:String,_2:String) extends scala.Tuple2[String,String](_1,_2)
//for class aliasing
class Calias(_1:String,_2:Class[_]) extends Tuple2[String,Class[_]](_1,_2)
//for field aliasing
class Falias(_1:String,_2:Class[_],_3:String) extends Tuple3[String,Class[_],String](_1,_2,_3)
//for implicit collection
class Icoll(_1:Class[_],_2:String) extends Tuple2[Class[_],String](_1,_2)
//ommiting fields
class Ofield(_1:Class[_],_2:String) extends Tuple2[Class[_],String](_1,_2)
//for using field as attribute
class Uattr(_1:Class[_],_2:String) extends Tuple2[Class[_],String](_1,_2)
//for loval Concerters
class Lconv(_1:Class[_],_2:String,_3:ConverterMatcher) extends Tuple3[Class[_],String,ConverterMatcher](_1,_2,_3)

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
  def getConverters : Collection[ConverterMatcher]
}

trait XstreamRegisterLocalConverter {
  def getLocalConverters : Collection[Lconv]
}

trait XstreamOmitField {
  def getOmitFields : Collection[Ofield]
}

trait XstreamUseAttribute {
  def getUseAttributes : Collection[Uattr]
}
