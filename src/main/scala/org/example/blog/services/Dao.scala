package org.example.blog.services

import java.io.Serializable

trait ReadDao[T, K <: Serializable] {
  
  def get(id:K) : Option[T]

  def getAll() : List[T]
 
  def find(filter: T => Boolean) : List[T]
}

trait WriteDao[T, K <: Serializable] {
  def save(entity:T) : Option[T]
}

trait ReadWriteDao[T, K <: Serializable] extends ReadDao[T,K] with WriteDao[T,K]