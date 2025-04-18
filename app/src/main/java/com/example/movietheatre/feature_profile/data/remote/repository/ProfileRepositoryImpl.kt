package com.example.movietheatre.feature_profile.data.remote.repository

import android.net.Uri
import android.util.Log
import com.example.movietheatre.core.domain.util.Resource
import com.example.movietheatre.core.domain.util.error.NetworkError
import com.example.movietheatre.feature_profile.domain.repository.ProfileRepository
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageMetadata
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
) : ProfileRepository {

    private val storageRef = storage.reference

    override suspend fun uploadProfileImage(uid: String, uri: Uri): Resource<Uri, NetworkError> =
        try {
            val ref = storageRef.child("profile_images/$uid.jpg")

            // Create a metadata object
            val metadata = StorageMetadata.Builder()
                .setContentType("image/jpeg")
                .build()

            // Upload with metadata
            val uploadTask = ref.putFile(uri, metadata)

            // Wait for completion and handle specific errors
            uploadTask.await()

            // Get download URL only if upload was successful
            val downloadUri = ref.downloadUrl.await()
            Resource.Success(downloadUri)
        } catch (e: IOException) {
            Log.e("ProfileRepo", "IO Exception during upload: ${e.message}", e)
            Resource.Error(NetworkError.ConnectionError)
        } catch (e: StorageException) {
            Log.e(
                "ProfileRepo",
                "Storage Exception during upload: Code ${e.errorCode}, Message: ${e.message}",
                e
            )
            Resource.Error(NetworkError.ServerError(e.errorCode))
        } catch (e: Exception) {
            Log.e("ProfileRepo", "Unknown error during upload: ${e.message}", e)
            Resource.Error(NetworkError.UnknownError)
        }

    override fun observeProfileImage(uid: String): Flow<Resource<Uri, NetworkError>> =
        flow {
            val ref = storageRef.child("profile_images/$uid.jpg")
            try {
                val uri = ref.downloadUrl.await()
                emit(Resource.Success(uri))
            } catch (e: StorageException) {
                if (e.errorCode == StorageException.ERROR_OBJECT_NOT_FOUND) {
                    emit(Resource.Error(NetworkError.EmptyResponse))
                } else {
                    emit(Resource.Error(NetworkError.ServerError(e.errorCode)))
                }
            } catch (e: Exception) {
                emit(Resource.Error(NetworkError.UnknownError))
            }
        }
}
