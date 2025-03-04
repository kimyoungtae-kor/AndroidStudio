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
        Log.d("WebSocketService", " WebSocket Foreground Service 시작")

        startForeground(1, createNotification()) //  Foreground Service 실행
        WebSocketManager.connectWebSocket(applicationContext) // WebSocket 유지
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("WebSocketService", " WebSocketService 재시작됨")
        // WebSocket 재연결 로직 추가
        if (WebSocketManager.isClosed()) {
            WebSocketManager.connectWebSocket(applicationContext)
        }
        return START_STICKY //  서비스가 종료되지 않도록 설정
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d("WebSocketService", "WebSocket Foreground Service 종료")
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
//            .setSilent(true) // ✅ 소리 없음 설정
//            .setContentTitle("가구의 집")
//            .setContentText("WebSocket을 유지 중...")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
//            .setSmallIcon(android.R.drawable.ic_notification_clear_all) // ✅ 투명 아이콘 사용
//            .setSilent(true) // ✅ 소리, 진동 없음
//            .setPriority(NotificationCompat.PRIORITY_MIN) // ✅ 우선순위 최소
            .setVisibility(NotificationCompat.VISIBILITY_SECRET) // ✅ 잠금화면에서도 보이지 않음
            .build()
    }
}
