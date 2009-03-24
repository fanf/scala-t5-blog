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

    binder.bind[XstreamClassAlias](classOf[XstreamClassAlias], classOf[impl.XstreamClassAliasImpl])
    binder.bind[XstreamPackageAlias](classOf[XstreamPackageAlias], classOf[impl.XstreamPackageAliasImpl])
    binder.bind[XstreamFieldAlias](classOf[XstreamFieldAlias], classOf[impl.XstreamFieldAliasImpl])
    binder.bind[XstreamImplicitCollection](classOf[XstreamImplicitCollection], classOf[impl.XstreamImplicitCollectionImpl])
    binder.bind[XstreamOmitField](classOf[XstreamOmitField], classOf[impl.XstreamOmitFieldImpl])
    binder.bind[XstreamRegisterConverter](classOf[XstreamRegisterConverter], classOf[impl.XstreamRegisterConverterImpl])
    binder.bind[XstreamRegisterLocalConverter](classOf[XstreamRegisterLocalConverter], classOf[impl.XstreamRegisterLocalConverterImpl])
    binder.bind[XstreamUseAttribute](classOf[XstreamUseAttribute], classOf[impl.XstreamUseAttributeImpl])

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

  /*
   * Marshaller configuration. 
   * That is the part where we weave the web that
   * link interfaces, types and actual implementation
   * of Marshaller, so that when the user will call
   * ----
   * injectedMarshaller.to("format", data) 
   * ----
   * the actual marshaller for 'format' will be called
   * on 'data'. 
   * 
   * For that, we rely on a two step binding:
   * - we bind string into MarshallerType ;
   * - we bind MarshallerTypes to marshaller service
   * Marshaller service are build and configured inpendently
   * of their use. 
   * 
   */
      
  /*
   * Xstream configuration
   * 
   * That is the really intersting part. It allows any tapestry module 
   * (read: module from other jars) to contribute class configuration.
   * That mean that a module which comes with its domain class configuration
   * is able to configure them, even if the marshaller service is in 
   * an other module. That's great in term of loosly coupling. 
   */
  def contributeXstreamClassAlias(configuration:OrderedConfiguration[Calias]) {
     configuration.add("article",new Calias("article",classOf[org.example.blog.data.Article]))
  } 

  def contributeXstreamUseAttribute(configuration:OrderedConfiguration[Uattr]) {
    configuration.add("article_id",new Uattr(classOf[Article],"id"))
    configuration.add("article_title",new Uattr(classOf[Article],"title"))
    configuration.add("article_published",new Uattr(classOf[Article],"published"))
  }

  def contributeXstreamOmitField(configuration:OrderedConfiguration[Ofield]) {
    configuration.add("article_comments", new Ofield(classOf[Article],"comments"))
  }
  
  def contributeXstreamRegisterLocalConverter(configuration:OrderedConfiguration[Lconv]) {
    configuration.add("optionConverter", 
                      new Lconv(classOf[Article],"id",
                                new SingleValueConverter() {
                                  def fromString(s:String) = if("none" == s) None else Some(s)
                                  def toString(a:Object) = a match { 
                                    case None => "none" 
                                    case Some(x) => x.toString 
                                    case _ => error("Not supported type: " + a.getClass.getName)
                                  }
                                  def canConvert(c:Class[_]) = {
                                    if(c == classOf[scala.Option[_]]) true
                                    else false
                                  }	
                                }
                      )
    )
  }


   
   
  def buildClientMarshaller(
    configuration:java.util.Map[MarshallerType,Marshaller],
    analyzer:MarshallerAnalyzer
  ) = {
    new impl.ClientMarshallerImpl(configuration,analyzer.get)
  }

  /*
   * Tapestry way of implementing the Chain of Command
   * pattern, {@see http://tapestry.apache.org/tapestry5/tapestry-ioc/command.html}
   * 
   * We will use this facade as the "MarshallerAnalyzer" service,
   * and delegate the actual type look-up to the commands.
   * 
   * As we are in Scala, we may have simply use the 
   * iterator#dropWhile method, but it wouldn't have played nicely with
   * what is provided by the framework
   */
  def buildMarshallerAnalyzer(commands:java.util.List[MarshallerAnalyzer],
                              chainBuilder:ChainBuilder
  ) = chainBuilder.build(classOf[MarshallerAnalyzer], commands)


  /*
   * Here, we add a command into the chain of command 
   * "MarshallerAnalyzer" for XML and Json type.
   */
  def contributeMarshallerAnalyzer(configuration:OrderedConfiguration[MarshallerAnalyzer]) {
    configuration.add("xml",new FormatMarshallerAnalyzer("xml",Xml))
    configuration.add("json",new FormatMarshallerAnalyzer("json",Json))
  }
   
  /*
   * ClientMarshaller configuration
   * 
   * Now that we have configured marshaller, a mathcing
   * between types and format name, we only lack the
   * binding between types and marshaller.
   * 
   * Here, we say to Tapestry : when you encouter 
   * something that need to use the [MarshallerType],
   * use the given implementation. OK, it's exactly 
   * a strategy pattern. 
   * 
   * Implementation note : T5 provides an @InjectService("id")
   * annotation to inject named service directly in the methode
   * parameters. 
   * We can't use it because of a bug in Scala compiler (perhaps)
   * {@see http://fanf42.blogspot.com/2009/03/java-annotation-scala-object-and.html}
   * 
   */
  def contributeClientMarshaller(
    configuration:MappedConfiguration[MarshallerType,Marshaller],
    locator:ObjectLocator
  ) {
     
    configuration.add(Xml,locator.getService("xmlMarshaller",classOf[Marshaller]))
    configuration.add(Json,locator.getService("jsonMarshaller",classOf[Marshaller]))
  }
   
}
