package org.example.blog.data

import scala.reflect.BeanProperty

class BlogConfiguration(val author:User) {

  @BeanProperty
  var blogTitle = "%s's blog".format(author.login)
  
  @BeanProperty
  var blogDescription = "Blog description"
  
  @BeanProperty
  var persistencePath = "/tmp/blog_scala"
  
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
