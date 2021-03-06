package org.example.blog.services.impl

import com.thoughtworks.xstream.converters.SingleValueConverter

import org.example.blog.services._

import scala.collection.jcl.Conversions._


class XstreamPackageAliasImpl(aliases:java.util.List[Palias]) extends XstreamPackageAlias {
  override def getPackageAliases : Collection[Palias] = aliases
}

class XstreamClassAliasImpl(aliases:java.util.List[Calias]) extends XstreamClassAlias {
  def getClassAliases : Collection[Calias] = aliases
}

class XstreamFieldAliasImpl(aliases:java.util.List[Falias]) extends XstreamFieldAlias {
  def getFieldAliases : Collection[Falias] = aliases
}

class XstreamImplicitCollectionImpl(implicitCollections:java.util.List[Icoll]) extends XstreamImplicitCollection {
  def getImplicitCollections : Collection[Icoll] = implicitCollections
}

class XstreamRegisterConverterImpl(converters:java.util.List[SingleValueConverter]) extends XstreamRegisterConverter {
  def getConverters : Collection[SingleValueConverter] = converters
}

class XstreamOmitFieldImpl(omitFields:java.util.List[Ofield]) extends XstreamOmitField {
  def getOmitFields : Collection[Ofield] = omitFields
}

