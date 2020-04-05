package com.tess.core.database

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.tess.core.extensions.consume
import com.tess.core.models.Setting
import com.tess.core.network.ObjectParser
import com.tess.core.network.parseJson
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred

private const val COLLECTION_NAME = "Settings"

class SettingsRepositoryImpl(
    private val objectParser: ObjectParser
) : SettingsRepository {

    private val collection: CollectionReference = FirebaseFirestore.getInstance().collection(COLLECTION_NAME)

    override fun addDocument(name: String, data: Setting) {
        collection.document(name).set(data)
    }

    override fun updateDocument(name: String, fields: Map<String, Any>) {
    }

    override fun getDocument(name: String): Deferred<Setting> {
        val deferred = CompletableDeferred<Setting>()

        collection.document(name).get()
            .addOnSuccessListener {
                it.data?.let { data ->
                    objectParser.parseJson<Setting>(data.toMap()).consume { obj ->
                        deferred.complete(obj)
                    }
                } ?: deferred.cancel()
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
