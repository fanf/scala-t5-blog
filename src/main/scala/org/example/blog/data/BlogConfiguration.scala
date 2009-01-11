package org.example.blog.data

import scala.reflect.BeanProperty

class BlogConfiguration(val author:User) {

  @BeanProperty
  var blogTitle = "%s's blog".format(author.login)
  
  def getAuthor = author
}


class User(val login : String) {  
  @BeanProperty
  var commonname = ""

  @BeanProperty
  var surname = ""

  @BeanProperty
  var email = ""

  def getLogin = login
}
