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
 * Copyright 2008 Francois Armand
 *           fanf42@gmail.com
 *           http://fanf42.blogspot.com
 */

package org.example.blog.services;

import java.io.IOException;

import org.apache.tapestry5.SymbolConstants
import org.apache.tapestry5.ioc.annotations.{InjectService,EagerLoad}
import org.apache.tapestry5.ioc.{MappedConfiguration,OrderedConfiguration,ServiceBinder,ObjectLocator}
import org.apache.tapestry5.ioc.annotations.Local
import org.apache.tapestry5.ioc.services
import org.apache.tapestry5.ioc.services.ChainBuilder
import org.apache.tapestry5.services.Request
import org.apache.tapestry5.services.RequestFilter
import org.apache.tapestry5.services.RequestHandler
import org.apache.tapestry5.services.Response
import org.apache.tapestry5.ioc.services.StrategyBuilder
import org.apache.tapestry5.ioc.util.StrategyRegistry
import org.slf4j.Logger


import org.example.blog.data._
import org.example.blog.services.impl._
import org.example.blog.services.impl.dao.InmemoryArticleDao
import com.thoughtworks.xstream.converters.{ConverterMatcher,SingleValueConverter}

/**
* This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
* configure and extend Tapestry, or to place your own service definitions.
*/
object AppModule {
  
  def bind(binder : ServiceBinder) {
    binder.bind[Marshaller](classOf[Marshaller],classOf[impl.XmlXstreamMarshaller]) withId "xmlMarshaller"
    binder.bind[Marshaller](classOf[Marshaller],classOf[impl.JsonXstreamMarshaller]) withId "jsonMarshaller"
  }

  def buildBlogConfiguration = {
    val author = new User("jondoe")
    author.commonname = "Jon"
    author.surname = "Doe"
    author.email = "jondoe@foo.bar"
    
    new BlogConfiguration(author)
  }
  
  
  def buildArticleDao = {
    val a1 = new Article(None)
    a1.title = "First article"
    a1.content = "Content for the first article"
    a1.published = true
    
    val a2 = new Article(None)
    a2.title = "Second article"
    a2.content = "Content for the second article"
    
    val m = new InmemoryArticleDao()
    m.save(a1)
    m.save(a2)
    m
  }
  
  
  def contributeApplicationDefaults(configuration : MappedConfiguration[String, String]) {
    configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");
    configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
  }

  def buildTimingFilter(log : Logger) = {
    new RequestFilter() {
      override def service(request:Request, response:Response, handler:RequestHandler) : Boolean = {
        var startTime = System.currentTimeMillis()
        try {
          handler.service(request, response)
        } finally {
        	val elapsed = System.currentTimeMillis() - startTime
        	log.info("Request time: %d ms".format(elapsed))
        }
      }
    }
  }

  def contributeRequestHandler(configuration:OrderedConfiguration[RequestFilter], 
                               locator:ObjectLocator) {

    configuration.add("Timing", locator.getService("timingFilter",classOf[RequestFilter]))
  }
   
}
