package shop.youngatae.hof

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        WebView.setWebContentsDebuggingEnabled(true)
        val webView: WebView = findViewById(R.id.webView)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            webView.setOnApplyWindowInsetsListener { view, insets ->
                val systemBarInsets = insets.getInsets(WindowInsets.Type.systemBars())
                view.setPadding(0, 0, 0, systemBarInsets.bottom) // 하단 패딩 추가
                insets
            }
        } else {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
        }

        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = WebViewClient()
        val url = "http://10.0.2.2:3000/admin"
        webView.loadUrl(url)

        // ✅ Foreground Service & 알림 권한 요청
        requestPermissions()

        // ✅ WebSocket 서비스 실행 (앱이 백그라운드에서도 실행되도록)
        startWebSocketService()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopWebSocketService()
    }

    // ✅ Foreground Service 실행 권한 요청 (Android 14 이상)
    private fun requestPermissions() {
        val permissions = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.POST_NOTIFICATIONS)
            }
        }

        if (Build.VERSION.SDK_INT >= 34) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.FOREGROUND_SERVICE_DATA_SYNC)
                != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.FOREGROUND_SERVICE_DATA_SYNC)
            }
        }

        if (permissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, permissions.toTypedArray(), 1002)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1002) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("MainActivity", "✅ Foreground Service 실행 권한 허용됨")
            } else {
                Log.e("MainActivity", "🚨 Foreground Service 실행 권한 거부됨")
            }
        }
    }

    // ✅ WebSocket Foreground Service 실행
    private fun startWebSocketService() {
        val intent = Intent(this, WebSocketService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)  // ForegroundService로 실행
        } else {
            startService(intent)
        }
    }

    // ✅ WebSocket Foreground Service 중지
    private fun stopWebSocketService() {
        val intent = Intent(this, WebSocketService::class.java)
        stopService(intent)
    }
}
