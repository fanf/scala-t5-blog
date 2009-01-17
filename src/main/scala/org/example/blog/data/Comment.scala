package org.example.blog.data

import java.util.Date
import scala.reflect.BeanProperty

class Comment(authorName:String, comment:String) {

  @BeanProperty val creationDate = new Date()
    
}
