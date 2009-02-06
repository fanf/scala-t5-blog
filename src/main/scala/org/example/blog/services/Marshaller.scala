package org.example.blog.services

/*
 * Marshaller are unit conversion betwin a
 * specific string format and a object hierarchie.
 * 
 */

//an (real) marshaller, from object to string
trait WoMarshaller {
  def to(o:Any) : String
}

//an unmarshaller
trait RoMarshaller {
  def from(s:String) : Any
}

trait Marshaller extends WoMarshaller with RoMarshaller


/*
 * The client wand a more straighfoward call where
 * she gives a wanted type and the object, and
 * return the string in the wanted type
 * (or throw an error)
 * 
 * That's a web client function, it's as little typed as possible ;)
 */
trait ClientMarshaller {
  def to(format:String, obj:Any) : String
  def from(format:String, s:String) : Any
}

/*
 * An abstract class that represent
 * different type of Marshaller and
 * which is destinated to
 * be extended by case class, like :
 * final case class Xml extends MarshallerType
 */
abstract class MarshallerType

/*
 * A service that retrieve a MarshallerType
 * given a String. 
 * It will be use in Chain of command like
 * pattern 
 */ 

trait MarshallerAnalyzer {
  //return null if can not handle the format, the good marshaller implementation else
  def get(format:String) : MarshallerType
}


