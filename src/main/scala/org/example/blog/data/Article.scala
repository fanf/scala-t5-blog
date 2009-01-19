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
  /*
   * "none" is not allowed for article ID
   */
  assert( id.forall( (s:String)=> !(s.toLowerCase == "none" ))) 
  
  @BeanProperty var creationDate = new Date()
  
  @BeanProperty var title = ""
  
  @BeanProperty var content = ""
  
  @BeanProperty var comments = List[Comment]()
  
  @BeanProperty var published = false
  
  def getId = id
  
  /*
   * String translation of the ID. Of course, it 
   * requires that id can't be the "None" String, 
   * and that's why we add the "assert" requirement
   */
  def getDisplayId = this.id.getOrElse("None") 
}

object Article {
  
  /*
   * Create a copy Article from a source one, 
   * setting the ID to a new value.
   */
  def apply(id:String, source:Article) : Article = {
    val a = new Article(Some(id))
    a.title = source.title
    a.content = source.content
    a.comments = source.comments
    a.creationDate = source.creationDate
    a.published = source.published
    a
  }
  
}