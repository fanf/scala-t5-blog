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

import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.ServiceBinder;
import org.apache.tapestry5.ioc.annotations.Local;
import org.apache.tapestry5.ioc.services
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.RequestFilter;
import org.apache.tapestry5.services.RequestHandler;
import org.apache.tapestry5.services.Response;
import org.slf4j.Logger;


import org.example.blog.data._

import org.example.blog.services.impl.dao.InmemoryArticleDao

/**
* This module is automatically included as part of the Tapestry IoC Registry, it's a good place to
* configure and extend Tapestry, or to place your own service definitions.
*/
object AppModule {
  def bind(binder : ServiceBinder) {
    // binder.bind(MyServiceInterface.class, MyServiceImpl.class);

    // Make bind() calls on the binder object to define most IoC services.
    // Use service builder methods (example below) when the implementation
    // is provided inline, or requires more initialization than simply
    // invoking the constructor.
    //binder.bind(classOf[Echo], classOf[EchoImpl])
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
    a1.content = "Content content content content"
    
    val a2 = new Article(None)
    a2.title = "First article"
    a2.content = "Content content content content"
    
    val m = new InmemoryArticleDao()
    m.save(a1)
    m.save(a2)
    m
  }
  
  
  def contributeApplicationDefaults(configuration : MappedConfiguration[String, String]) {
    // Contributions to ApplicationDefaults will override any contributions to
    // FactoryDefaults (with the same key). Here we're restricting the supported
    // locales to just "en" (English). As you add localised message catalogs and other assets,
    // you can extend this list of locales (it's a comma separated series of locale names;
    // the first locale name is the default when there's no reasonable match).

    configuration.add(SymbolConstants.SUPPORTED_LOCALES, "en");

    // The factory default is true but during the early stages of an application
    // overriding to false is a good idea. In addition, this is often overridden
    // on the command line as -Dtapestry.production-mode=false
    configuration.add(SymbolConstants.PRODUCTION_MODE, "false");
  }


  /**
   * This is a service definition, the service will be named "TimingFilter". The interface,
   * RequestFilter, is used within the RequestHandler service pipeline, which is built from the
   * RequestHandler service configuration. Tapestry IoC is responsible for passing in an
   * appropriate Logger instance. Requests for static resources are handled at a higher level, so
   * this filter will only be invoked for Tapestry related requests.
   * 
   * <p>
   * Service builder methods are useful when the implementation is inline as an inner class
   * (as here) or require some other kind of special initialization. In most cases,
   * use the static bind() method instead. 
   * 
   * <p>
   * If this method was named "build", then the service id would be taken from the 
   * service interface and would be "RequestFilter".  Since Tapestry already defines
   * a service named "RequestFilter" we use an explicit service id that we can reference
   * inside the contribution method.
   */    
  def buildTimingFilter(log : Logger) : RequestFilter  = 	{
    new RequestFilter() {
      override def service(request:Request, response:Response, handler:RequestHandler) : Boolean = {
        var startTime = System.currentTimeMillis()
        try {
          // The responsibility of a filter is to invoke the corresponding method 
          // in the handler. When you chain multiple filters together, each filter
          // received a handler that is a bridge to the next filter.
          handler.service(request, response);
        } finally {
        	val elapsed = System.currentTimeMillis() - startTime;
        	log.info("Request time: %d ms".format(elapsed))
        }
      }
    };
   }

   /**
   * This is a contribution to the RequestHandler service configuration. This is how we extend
   * Tapestry using the timing filter. A common use for this kind of filter is transaction
   * management or security. The @Local annotation selects the desired service by type, but only
   * from the same module.  Without @Local, there would be an error due to the other service(s)
   * that implement RequestFilter (defined in other modules).
   */
   def contributeRequestHandler(configuration:OrderedConfiguration[RequestFilter], 
                                @Local filter:RequestFilter) {
     // Each contribution to an ordered configuration has a name, When necessary, you may
     // set constraints to precisely control the invocation order of the contributed filter
     // within the pipeline.
     configuration.add("Timing", filter);
   }
   
}
