package org.example.blog.services.impl

class ArticleIdGeneratorImpl extends ArticleIdGenerator {

  val chars = Map(
    'a' -> 'a',
    'à' -> 'a'
  )
  
  private def normChar(c:Char) : Char = {
    chars.get(c).getOrElse('_')
  }
  
  /*
   * Remove space,strange letters,etc
   */
  override def normalize(title:String) : String = {
    title.map(normChar(_)).toString
  }
}
