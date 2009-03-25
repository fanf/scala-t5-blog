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
import org.example.blog.services.Marshaller

import org.example.blog.data.{Article,NoneArticle}
import org.example.blog.data.BlogConfiguration

import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.annotations.{Property,Service}
import org.apache.tapestry5.util.TextStreamResponse

import org.example.blog.services.impl.XstreamMarshaller

class ViewArticle {

  @Inject
  var articleDao : ReadDao[Article,String] = _

  @Inject
  var conf : BlogConfiguration = _
  
  @Inject @Service("xmlMarshaller")
  var xmlMarshaller : Marshaller = _
  
  @Inject @Service("jsonMarshaller")
  var jsonMarshaller : Marshaller = _

  @Property
  var article : Article = _
  
  var id : String = _
  
  def onActivate(id:String) { this.id = id } 
  
  def getAuthorName() = this.conf.getAuthor.getLogin
  
  def setupRender {
    this.article = this.get(id)
  }
  
  def onXml = new TextStreamResponse("text/xml",this.xmlMarshaller.to(NoneArticle))
  def onJson = new TextStreamResponse("text/plain",this.jsonMarshaller.to(NoneArticle))
  
  def onXml(id:String) = {
    new TextStreamResponse("text/xml",this.xmlMarshaller.to(this.get(id)))
  }

  def onJson(id:String) = {
    new TextStreamResponse("text/plain",this.jsonMarshaller.to(this.get(id)))
  }
  
  private def get(id:String) = 
    if(null == id ) NoneArticle
    else this.articleDao.get(id).getOrElse(NoneArticle)
}
