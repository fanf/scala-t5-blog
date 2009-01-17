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

import org.example.blog.services.ReadDao
import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.annotations.Property

import org.example.blog.data.Article
import org.example.blog.data.BlogConfiguration

class ViewArticle {

  @Inject
  var articleDao : ReadDao[Article,String] = _

  @Inject
  var conf : BlogConfiguration = _
  
  @Property
  var article : Article = _
  
  def getAuthorName() = this.conf.getAuthor.getLogin
  
  def setupRender {
    this.article = this.articleDao.get("a").getOrElse(new Article(None))
  }
  
}
