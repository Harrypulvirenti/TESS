package com.tess.core.database

import kotlinx.coroutines.Deferred

interface IDataRepository<T : Any> {

    fun addDocument(name: String, data: T)

    fun updateDocument(name: String, fields: Map<String, Any>)

    fun getDocument(name: String): Deferred<T>
}