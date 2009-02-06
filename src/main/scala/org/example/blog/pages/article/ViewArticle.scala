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
import org.example.blog.services.ClientMarshaller

import org.example.blog.data.Article
import org.example.blog.data.BlogConfiguration

import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.annotations.Property
import org.apache.tapestry5.util.TextStreamResponse

class ViewArticle {

  @Inject
  var articleDao : ReadDao[Article,String] = _

  @Inject
  var conf : BlogConfiguration = _
  
  @Inject 
  var marshaller : ClientMarshaller = _
    
  @Property
  var article : Article = _
  
  var id : String = _
  
  def onActivate(id:String) = this.id = id
  
  def getAuthorName() = this.conf.getAuthor.getLogin
  
  def setupRender {
    this.article = this.get(id)
  }

  
  def onXml = new TextStreamResponse("text/xml",this.marshaller.to("null",null))
  def onJson = onXml
  
  def onXml(id:String) = {
    new TextStreamResponse("text/xml",this.marshaller.to("xml",this.get(id)))
  }

  def onJson(id:String) = {
    new TextStreamResponse("text/plain",this.marshaller.to("json",this.get(id)))
  }
  
  private def get(id:String) = 
    if(null == id ) new Article(None) 
    else this.articleDao.get(id).getOrElse(new Article(None))
}
