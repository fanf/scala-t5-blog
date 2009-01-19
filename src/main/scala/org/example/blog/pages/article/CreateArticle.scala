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

import org.apache.tapestry5.annotations.{Property,Persist,InjectPage}
import org.apache.tapestry5.ioc.annotations.Inject

import org.example.blog.data.Article
import org.example.blog.services.WriteDao
 
import java.text.DateFormat

class CreateArticle {

  @Inject
  var articleDao : WriteDao[Article,String] = _
  
  @Persist @Property
  var article : Article = _
    
  @InjectPage
  var redirectPage : ManageArticle = _
  
  def setupRender {
    if(null == this.article) {
      this.article = new Article(None)
    } 
  }
  
  def onSuccessFromNewArticleForm = {
    articleDao.save(article) match {
      case None => error("Dao error ! Please retry.")
      case _ => this.article = null ; redirectPage
    }
  }
}
