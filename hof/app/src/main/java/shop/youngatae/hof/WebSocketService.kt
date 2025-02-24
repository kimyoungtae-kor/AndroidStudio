package shop.youngatae.hof

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

class WebSocketService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d("WebSocketService", "✅ WebSocket Foreground Service 시작")

        startForeground(1, createNotification()) // ✅ Foreground Service 실행
        WebSocketManager.connectWebSocket(applicationContext) // ✅ WebSocket 유지
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("WebSocketService", "❌ WebSocket Foreground Service 종료")
        WebSocketManager.disconnectWebSocket()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null // Foreground Service이므로 null 반환
    }

    private fun createNotification(): Notification {
        val channelId = "websocket_service"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "WebSocket Service",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
        }

        return NotificationCompat.Builder(this, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // 아이콘만 표시 (텍스트는 최소화)
//            .setSilent(true) // ✅ 소리 없음 설정
//            .setContentTitle("가구의 집")
//            .setContentText("WebSocket을 유지 중...")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
    }
}
