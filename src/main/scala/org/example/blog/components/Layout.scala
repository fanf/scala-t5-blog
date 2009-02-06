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

package org.example.blog.components

import org.apache.tapestry5.ioc.annotations.Inject
import org.apache.tapestry5.annotations._

import org.apache.tapestry5.RenderSupport
import org.apache.tapestry5.ComponentResources

import org.example.blog.data.BlogConfiguration

@IncludeStylesheet(Array("context:theme/red/style.css"))
class Layout {

  @Inject
  var resources : ComponentResources = _
  
  @PageLoaded
  def load() {
    println("+++ component loaded: "  + resources.getCompleteId)
  }
  
  def onActivate() {
    println("+++ component activation: "  + resources.getCompleteId)
  }
  
  @Inject @Property
  var conf : BlogConfiguration = _
  
}
