package shop.youngatae.socket

import shop.youngatae.socket.receiver.MessageBroadcastReceiver
import android.content.Intent
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview)
        WebView.setWebContentsDebuggingEnabled(true)
        // Notification 채널 생성
        NotificationUtil.createNotificationChannel(this)
        val webView : WebView = findViewById(R.id.webView)
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
        }
        //자바스크립트 크롬 클라이언트 허용
        webView.webChromeClient = WebChromeClient()

        webView.addJavascriptInterface(object {
            @android.webkit.JavascriptInterface
            fun sendBroadcast(message: String) {
                val intent = Intent("shop.youngatae.socket.MESSAGE_RECEIVED").apply {
                    putExtra("message", message)
                }
                sendBroadcast(intent)
                println("Broadcast sent with message: $message")
            }
        }, "Android")

        webView.webViewClient = WebViewClient()
        var url ="http://10.0.2.2:3000"

        webView.loadUrl(url)
        webView.settings.javaScriptEnabled = true
    }
}