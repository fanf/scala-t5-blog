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

package org.example.blog.pages;

import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.annotations.Property

import org.example.blog.data.Article
import org.example.blog.services.ReadDao

/**
 * Start page of application myapp.
 * 
 * @author <a href="mailto:fanf42@gmail.com">Francois Armand</a> 
 */
class Index {
  
  @Inject
  var readArticleDao : ReadDao[Article, String] = _
  
  def getArticles = readArticleDao.find( _.published == true ).toArray
  
  @Property
  var article : Article = _
}
