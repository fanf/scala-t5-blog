package org.example.blog.services

/**
 * This trait generate a normalized
 * string from a title. 
 */
trait ArticleIdGenerator {

  def normalize(a:String) : String
}
