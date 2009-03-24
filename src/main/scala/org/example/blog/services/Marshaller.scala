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


