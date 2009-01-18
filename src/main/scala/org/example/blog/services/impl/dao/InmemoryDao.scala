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
  
  val memory : mutable.Map[String, Article] = new mutable.HashMap()

  var id = 0
  
  private def newId = {
    id = id + 1
    id
  }
  
  def get(id:String) = this.memory.get(id)

  def getAll() = this.memory.values.toList
 
  def find(filter: Article => Boolean) = 
    this.memory.filter( (pair:(String,Article)) => filter(pair._2) ).map( (p:(String,Article)) => p._2).toList

  def save(a:Article) = {
    val a2 = a.id match {
      case None => 
        val i = this.newId
        Article.from(i.toString,a)
      case Some(id) => a
    }
    assert(a2.id.isDefined)
    this.memory.put(a2.id.get,a2)
    this.memory.get(a2.id.get)
  }
  
  def delete(id:String) = {
    !((this.memory - id) isDefinedAt(id))
  }
}