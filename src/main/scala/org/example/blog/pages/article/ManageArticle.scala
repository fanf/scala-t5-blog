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

package org.example.blog.pages.article

import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.annotations.Property

import org.example.blog.data.Article
import org.example.blog.services.ReadWriteDao

class ManageArticle {
  
  @Inject
  var rwDao : ReadWriteDao[Article, String] = _
  
  @Property
  var article : Article = _
  
  def getArticles = this.rwDao.getAll.sort( _.creationDate.getTime > _.creationDate.getTime ).toArray
  
  def getChangePublication = if(article.published) "Un-published" else "Published"

  
  def onActionFromChangePublication(id:String) {
    val a = this.rwDao.get(id).getOrElse(error("No such article, id: " + id) )
    a.published = !a.published
    this.rwDao.save(a)
  }
  
  def onActionFromDelete(id:String) {
    if(!this.rwDao.delete(id)) {
      error("Can not delete this article")
    }
  }  
}
