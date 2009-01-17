/**
 * Apache License
 * Version 2.0, January 2004
 * http://www.apache.org/licenses/
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Copyrigth Francois Armand
 *           fanf42@gmail.com
 *           http://fanf42.blogspot.com
 */

package org.example.blog.data

import java.util.Date
import scala.reflect.BeanProperty

class Article(val id:Option[String]) {
  @BeanProperty var creationDate = new Date()
  
  @BeanProperty var title = ""
  
  @BeanProperty var content = ""
  
  @BeanProperty var comments = List[Comment]()
  
  @BeanProperty var published = false
  
  def getId = id
}

object Article {
  
  /*
   * Create a new Article from an other, 
   * setting the ID to a ne value.
   */
  def from(id:String, a:Article) : Article = {
    val aa = new Article(Some(id))
    aa.title = a.title
    aa.content = a.content
    aa.comments = a.comments
    aa.creationDate = a.creationDate
    aa.published = a.published
    aa
  }
  
}