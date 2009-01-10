package org.example.blog.data

class Configuration(val author:User) {

}

class User(login:String) {  
  var surname : String = _
  var commonname : String = _

  /*
   * That's a grave limitation of Scala
   * with Tapestry : Tapestry does not 
   * understand Scala automatique 
   * getter/setter, it wants a real get.
   * 
   * A solution would be to enhanced 
   * Tapestry to find "property_" as
   * setter and "property" as getter method
   */
  def getLogin = login
}
