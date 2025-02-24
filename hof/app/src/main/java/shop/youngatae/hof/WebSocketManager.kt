package shop.youngatae.hof

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

object WebSocketManager {
    private const val SERVER_URL = "ws://10.0.2.2:8080/api/v1/ws/notify"
    private var webSocketClient: WebSocketClient? = null
    private const val CHANNEL_ID = "websocket_notifications"

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
                        sendNotification(context, "새로운 알림", it) // ✅ 바로 푸시 알림 발송
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

    // ✅ **알림 직접 발송 메서드**
    private fun sendNotification(context: Context, title: String, message: String) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "WebSocket 알림",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
