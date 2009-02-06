package org.example.blog.services.impl

class ClientMarshallerImpl(
  configuration:java.util.Map[MarshallerType,Marshaller], 
  analyzer: String => MarshallerType)
                           
extends ClientMarshaller {
  
  def to(format:String, o:Any) = {
    configuration.get(analyzer(format)) match {
      case null => error("Unknow format type: " + format)
      case m@_  => m.to(o)
    }
  }
  
  def from(format:String, s:String) = 
    configuration.get(analyzer(format)) match {
      case null => error("Unknow format type: " + format)
      case m@_  => m.from(s);
  }
}
  