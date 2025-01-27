package shop.youngatae.socket.receiver

import NotificationUtil
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocketListener

//브로드캐스트 (WebView에서 데이터를 Android로 전달하는 역할)
class MessageBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
            val webSocketClient by lazy {
            val client = OkHttpClient()
            val request = Request.Builder().url("ws://10.0.2.2:8080/chat").build()
            client.newWebSocket(request, object : WebSocketListener() {})
        }
        val message = intent.getStringExtra("message")
        if (message != null) {
            // WebSocket 서버로 메시지 전송
            webSocketClient.send(message)
            println("WebSocket으로 메시지 전송됨: $message")
        }
    }
}
