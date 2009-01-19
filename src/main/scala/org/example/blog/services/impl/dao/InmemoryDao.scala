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

package org.example.blog.services.impl.dao

import org.example.blog.data.Article
import scala.collection.mutable

import org.example.blog.services.ReadWriteDao

/**
 * A simple, naive implementation of the Article DAO.
 * In particular, this implementation is
 * NOT AT ALL THREADSAFE
 */
class InmemoryArticleDao extends ReadWriteDao[Article, String] {
  
  private val memory : mutable.Map[String, Article] = new mutable.HashMap()
  private var id = 0
  private def newId = { id = id + 1 ; id }
  
  def get(id:String) = this.memory.get(id)

  def getAll() = this.memory.values.toList
 
  def plop(filter: Article => Boolean) : List[Article] = {
    var articles = List[Article]()
    for(a <- this.memory.values) {
      if(filter(a)) articles = a :: articles 
    }
    articles
  }

  def find(filter: Article => Boolean) = 
    (for { 
      a <- this.memory.values
      if(filter(a))
    } yield a).toList

  def save(article:Article) = {
    val a = article.id match {
      case None => 
        val i = this.newId
        Article(i.toString,article)
      case Some(id) => article
    }
    assert(a.id.isDefined)
    this.memory.put(a.id.get,a)
    //check if article is in map for the id, return id if OK
    this.memory.get(a.id.get).map( _.id.get)
  }
  
  def delete(id:String) = !( (this.memory - id) isDefinedAt(id) )
}