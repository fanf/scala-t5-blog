package org.example.blog.services

import java.io.Serializable

/**
 * A read-only dao
 * @param T is the type of entities handled by this DAO
 * @param K is the type of the key that is used to identify entities.
 */
trait ReadDao[T, K <: Serializable] {
  
  /*
   * Retrieve an entity from its key. If no
   * entities are known for the given key, None is returned.
   * @param K the unique id of the entity to fetch
   * @return None if no entity match that key,
   *         Some[entity] else
   */
  def get(id:K) : Option[T]

  /**
   * Retrieve all the entities known by that DAO.
   * Be carefull, that may be a huge number.
   * @return the list of all entities for that DAO
   */
  def getAll() : List[T]
 
  /**
   * Find all the entities that match the given requirement
   * @param T => Boolean : the function to apply to find if
   *        an entity should be included in the returned list.
   *        (on a "true" return) or excluded (on a "false" return)
   * @return the list of enetities matching the filter.
   */
  def find(filter: T => Boolean) : List[T]
}

/**
 * A write-only dao
 * @param T is the type of entities handled by this DAO
 * @param K is the type of the key that is used to identify entities.
 */
trait WriteDao[T, K <: Serializable] {
  
  /**
   * Persist the given entity: create a new one if
   * the entity wasn't know in that DAO (entity
   * key was None), or update an existing entity 
   * (it's key was Some[K]).
   * @param T the entity to persist
   * @return the key of the persisted entity if the process 
   *         succeded, or None the the peristence failed.
   */
  def save(entity:T) : Option[K]
  
  /**
   * Delete the article matching this id. 
   * If no article match this id, does nothing.
   * Return true is the deletion is successful
   */
  def delete(id:K) : Boolean

}

/**
 * A read-write DAO, that combine read and write DAO traits.
 */
trait ReadWriteDao[T, K <: Serializable] extends ReadDao[T,K] with WriteDao[T,K]
