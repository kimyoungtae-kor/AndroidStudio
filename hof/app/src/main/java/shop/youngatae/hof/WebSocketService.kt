package shop.youngatae.hof

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat

class WebSocketService : Service() {
    override fun onCreate() {
        super.onCreate()
        Log.d("WebSocketService", "✅ WebSocket Foreground Service 시작")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE_DATA_SYNC)
                != PackageManager.PERMISSION_GRANTED) {
                Log.e("WebSocketService", "🚨 Foreground Service 실행 권한이 없음")
                return
            }
        }

        startForeground(1, createNotification()) // Foreground Service 실행
        WebSocketManager.connectWebSocket(applicationContext)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("WebSocketService", "❌ WebSocket Foreground Service 종료")
        WebSocketManager.disconnectWebSocket()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
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
            .setContentTitle("WebSocket Service")
            .setContentText("웹소켓 연결을 유지 중...")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()
    }
}
