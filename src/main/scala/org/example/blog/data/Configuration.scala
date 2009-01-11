package org.example.blog.data

import scala.reflect.BeanProperty
  

class Configuration(val author:User) {

  @BeanProperty
  var blogTitle = "%s's blog".format(author.login)
  
  def getAuthor = author
}


class User(val login : String) {  
  @BeanProperty
  var surname : String = _

  @BeanProperty
  var commonname : String = _

  @BeanProperty
  var email : String = _

  /*
   * That's a grave limitation of Scala
   * with Tapestry : Tapestry does not 
   * understand Scala automatique 
   * getter/setter, it wants a real get.
   * 
   * A solution would be to enhanced 
   * Tapestry to find "property_" as
   * setter and "property" as getter method
   * but it would be hard as T5 relies upon
   * BeanProperty to do that. So
   */
  def getLogin = login
}
