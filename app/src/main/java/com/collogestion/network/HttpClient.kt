package com.collogestion.network

import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

object HttpClient {
    private const val BASE_URL = "http://10.0.2.2:8000"

    private val client = OkHttpClient()
    val gson = Gson()

    private var token: String? = null

    suspend fun getRequest(url: String): String = withContext(Dispatchers.IO) {
        val request = createRequestBuilder(url).get().build()
        executeRequest(request)
    }

    suspend fun postRequest(url: String, requestBody: RequestBody? = null): String =
        withContext(Dispatchers.IO) {
            val request =
                createRequestBuilder(url).post(requestBody ?: FormBody.Builder().build()).build()
            executeRequest(request)
        }

    suspend fun postRequest(url: String, requestBody: Map<String, Any>?): String =
        withContext(Dispatchers.IO) {
            val json = gson.toJson(requestBody)
            val body = json.toRequestBody("application/json; charset=utf-8".toMediaType())
            val request = createRequestBuilder(url).post(body).build()
            executeRequest(request)
        }

    suspend fun putRequest(url: String, requestBody: Map<String, Any>?): String =
        withContext(Dispatchers.IO) {
            val json = gson.toJson(requestBody)
            val body = json.toRequestBody("application/json; charset=utf-8".toMediaType())
            val request = createRequestBuilder(url).put(body).build()
            executeRequest(request)
        }

    suspend fun deleteRequest(url: String): String = withContext(Dispatchers.IO) {
        val request = createRequestBuilder(url).delete().build()
        executeRequest(request)
    }

    private fun createRequestBuilder(url: String): Request.Builder {
        val builder = Request.Builder().url(BASE_URL + url)
        token?.let {
            builder.addHeader("Authorization", "Bearer $it")
        }
        return builder
    }

    fun setToken(token: String) {
        this.token = token
    }

    fun removeToken() {
        this.token = null
    }

    private suspend fun executeRequest(request: Request): String = withContext(Dispatchers.IO) {
        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")
                response.body?.string() ?: throw IOException("Empty response body")
            }
        } catch (e: IOException) {
            Log.e("HttpClient", "Error: ${e.message}")
            throw e
        }
    }

    suspend fun isApiReachable(): Boolean = withContext(Dispatchers.IO) {
        val request = createRequestBuilder("/docs").get().build()
        try {
            client.newCall(request).execute().use { response ->
                return@withContext response.isSuccessful
            }
        } catch (e: IOException) {
            Log.e("HttpClient", "Error: ${e.message}")
            return@withContext false
        }
    }
}
