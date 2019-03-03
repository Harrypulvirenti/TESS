package com.hpdev.smartthermostatcore.database

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.hpdev.smartthermostatcore.models.Setting
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

private const val COLLECTION_NAME = "Settings"

class SettingsRepositoryImpl : SettingsRepository {

    private val collection: CollectionReference = FirebaseFirestore.getInstance().collection(COLLECTION_NAME)

    override fun addDocument(name: String, data: Setting) {
        collection.document(name).set(data)
    }

    override fun updateDocument(name: String, fields: Map<String, Any>) {
    }

    override fun getDocument(name: String): Deferred<Setting> {

        val mapper = ObjectMapper()
        val deferred = CompletableDeferred<Setting>()

        collection.document(name).get()
            .addOnSuccessListener {
                val obj = mapper.convertValue(it.data, Setting::class.java)
                deferred.complete(obj)
            }
            .addOnCanceledListener {
                deferred.cancel()
            }
            .addOnFailureListener {
                deferred.completeExceptionally(it)
            }

        return deferred
    }
}