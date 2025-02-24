package shop.youngatae.hof

import android.content.Context
import android.content.Intent
import android.util.Log
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

object WebSocketManager {
    private const val SERVER_URL = "ws://10.0.2.2:8080/api/v1/ws/notify"

    private var webSocketClient: WebSocketClient? = null

    fun connectWebSocket(context: Context) {
        try {
            val uri = URI(SERVER_URL)
            webSocketClient = object : WebSocketClient(uri) {
                override fun onOpen(handshakedata: ServerHandshake?) {
                    Log.d("WebSocket", "✅ WebSocket 연결 성공")
                }

                override fun onMessage(message: String?) {
                    message?.let {
                        Log.d("WebSocket", "📩 받은 메시지: $it")

                        // 🔹 BroadcastReceiver에게 메시지 전달
                        val intent = Intent("shop.youngatae.hof.NOTIFY")
                        intent.putExtra("message", it)
                        context.sendBroadcast(intent)
                    }
                }

                override fun onClose(code: Int, reason: String?, remote: Boolean) {
                    Log.d("WebSocket", "🚫 WebSocket 연결 종료: $reason")
                }

                override fun onError(ex: Exception?) {
                    Log.e("WebSocket", "❌ WebSocket 오류 발생: ${ex?.message}")
                }
            }
            webSocketClient?.connect()
        } catch (e: Exception) {
            Log.e("WebSocket", "WebSocket 연결 오류: ${e.message}")
        }
    }

    fun disconnectWebSocket() {
        webSocketClient?.close()
        webSocketClient = null
    }
}
