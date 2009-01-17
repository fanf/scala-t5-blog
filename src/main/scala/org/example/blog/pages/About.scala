package org.example.blog.pages
import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.annotations.Property

import org.example.blog.data.BlogConfiguration
class About {
  
  @Inject @Property
  var conf : BlogConfiguration = _
}
