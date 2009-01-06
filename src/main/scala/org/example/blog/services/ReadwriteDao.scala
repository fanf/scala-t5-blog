package org.example.myapp.services

import java.io.Serializable

trait ReadwriteDao[T, K <: Serializable] {
  
  def findById(id:K) : T

  def findAll() : List[T]
 
  def save(entity:T)

}
