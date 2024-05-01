package com.collogestion

import android.media.session.MediaSession
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.collogestion.data.Token
import com.collogestion.ui.theme.ColloGestionTheme
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
    private val gson = Gson()

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

    fun auth(email: String, password: String, callback: (String?) -> Unit) {
        postRequest(
            "http://10.0.2.2:8000/auth/token", FormBody.Builder()
                .add("username", email)
                .add("password", password)
                .build()
        ) { response ->
            response?.let {
                val token = gson.fromJson(it, Token::class.java)
                this.token = token.access_token
            }
            callback(response)
        }
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HttpClient.auth("string@email.com", "string") {
        }
        setContent {
            ColloGestionTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

fun req() {
    val url = "http://10.0.2.2:8000/dues/user/1"


    HttpClient.getRequest(url) { response ->
        Log.d("MainActivity", "Response: $response")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    Button(onClick = { req() }) {
        Text(text = "Hello !")

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ColloGestionTheme {
        Greeting("Android")
    }
}