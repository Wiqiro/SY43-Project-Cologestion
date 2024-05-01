package com.collogestion.services

import android.util.Log
import com.collogestion.data.Token
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException

object HttpClient {

    private val client = OkHttpClient()
    val gson = Gson()

    private var token: String? = null

    fun getRequest(url: String, callback: (String?) -> Unit) {
        val request = createRequestBuilder(url).get().build()
        executeRequest(request, callback)
    }

    fun postRequest(url: String, requestBody: RequestBody?, callback: (String?) -> Unit) {
        val request =
            createRequestBuilder(url).post(requestBody ?: FormBody.Builder().build()).build()
        executeRequest(request, callback)
    }

    fun putRequest(url: String, requestBody: RequestBody?, callback: (String?) -> Unit) {
        val request =
            createRequestBuilder(url).put(requestBody ?: FormBody.Builder().build()).build()
        executeRequest(request, callback)
    }

    fun deleteRequest(url: String, callback: (String?) -> Unit) {
        val request = createRequestBuilder(url).delete().build()
        executeRequest(request, callback)
    }

    private fun createRequestBuilder(url: String): Request.Builder {
        val builder = Request.Builder().url(url)
        token?.let { token ->
            builder.addHeader("Authorization", "Bearer $token")
        }
        return builder
    }

    fun setToken(token: String) {
        this.token = token
    }

    fun removeToken() {
        this.token = null
    }

    private fun executeRequest(request: Request, callback: (String?) -> Unit) {
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback(null)
                Log.e("HttpClient", "Error: ${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                callback(body)
            }
        })
    }


}