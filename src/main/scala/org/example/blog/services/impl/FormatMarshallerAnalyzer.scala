package org.example.blog.services.impl


/*
 * A default implementation of the 
 * marchaller analyzer that heavely couple a
 * string (say, a format) with a MarshallerType.
 */
//In this format, the analyze is done base on the string format comparaison
class FormatMarshallerAnalyzer(format:String, mtype:MarshallerType) extends MarshallerAnalyzer {
  require(null != format && !format.isEmpty)
  require(null != mtype)
  override def get(f:String) = {
    if(this.format.equalsIgnoreCase(f)) mtype else null
  }
}