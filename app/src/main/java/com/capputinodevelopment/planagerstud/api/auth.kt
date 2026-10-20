package com.capputinodevelopment.planagerstud.api

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ContainedLoadingIndicator
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.viewinterop.AndroidView
import io.ktor.client.HttpClient
import io.ktor.client.statement.HttpResponse
import io.ktor.client.request.*
import io.ktor.client.engine.cio.*
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun Authentication(url: String) {
    var cookies by remember{ mutableStateOf("")};

    LaunchedEffect(cookies) {
        println("cookies:" + cookies);

        val client = HttpClient(CIO)

        // get user ID
        val me: HttpResponse = client.get("https://studip.tu-braunschweig.de/jsonapi.php/v1/users/me") {
            header(HttpHeaders.Cookie, cookies)
        }
        val json = Json.parseToJsonElement(me.bodyAsText()).jsonObject
        val userId = json["data"]!!.jsonObject["id"]!!.jsonPrimitive.content
        val response: HttpResponse = client.get("https://studip.tu-braunschweig.de/jsonapi.php/v1/users/${userId}/schedule") {
            header(HttpHeaders.Cookie, cookies)
        }
        println(response)
    }

    if (cookies.isEmpty()) {
        WebViewScreen(url) {
            cookies = it
        }
    }else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            ContainedLoadingIndicator()
        }
    }

}

@Composable
fun WebViewScreen(url: String, onExit: (String) -> Unit) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebAuthClient {onExit(it)}
                settings.apply {
                    javaScriptEnabled = true
                    domStorageEnabled = true
                    databaseEnabled = true
                }
                loadUrl(url)
            }
        }
    )
}

class WebAuthClient(
    private val onExit: (String) -> Unit,
) : WebViewClient() {
    override fun shouldOverrideUrlLoading(
        view: WebView?,
        request: WebResourceRequest?
    ): Boolean {
        val url = request?.url.toString()

        if (url.startsWith("https://studip.tu-braunschweig.de/dispatch.php/start")) {
            val cookieManager = CookieManager.getInstance()
            val cookies = cookieManager.getCookie("https://studip.tu-braunschweig.de/")
            onExit(cookies)
            return true
        }else {
            return false
        }
    }


    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
        // Show loading indicator
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        // Hide loading indicator
    }
}