package shop.youngatae.hof

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.net.URI

object WebSocketManager {
    private const val SERVER_URL = "wss://hof.lshwan.com/api/v1/ws/notify"
    private var webSocketClient: WebSocketClient? = null
    private const val CHANNEL_ID = "websocket_notifications"

    fun connectWebSocket(context: Context) {
        try {
            val uri = URI(SERVER_URL)
            val headers = mapOf(
                "Upgrade" to "websocket",
                "Connection" to "Upgrade"
            )
            webSocketClient = object : WebSocketClient(uri,headers) {
                override fun onOpen(handshakedata: ServerHandshake?) {
                    Log.d("WebSocket", "✅ WebSocket 연결 성공")
                }

                override fun onMessage(message: String?) {
                    message?.let {
                        Log.d("WebSocket", "📩 받은 메시지: $it")
                        sendNotification(context, "오늘의 가구의 집", it) // ✅ 바로 푸시 알림 발송
                    }
                }

                override fun onClose(code: Int, reason: String?, remote: Boolean) {
                    Log.d("WebSocket", "🚫 WebSocket 연결 종료: $reason")

                    // ✅ 웹소켓이 닫혔을 경우 10초 후 재연결
                    Handler(Looper.getMainLooper()).postDelayed({
                        if (isClosed()) {  // 🔹 웹소켓이 닫혀 있으면 다시 연결
                            Log.d("WebSocket", "🔄 WebSocket 재연결 시도...")
                            connectWebSocket(context)
                        }
                    }, 10000)
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

    fun isClosed(): Boolean {
        return webSocketClient == null || !webSocketClient!!.isOpen
    }

    fun disconnectWebSocket() {
        webSocketClient?.close()
        webSocketClient = null
    }
    fun disableBatteryOptimization(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            if (!pm.isIgnoringBatteryOptimizations(context.packageName)) {
                val intent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
                    .setData(Uri.parse("package:" + context.packageName))
                context.startActivity(intent)
            }
        }
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
