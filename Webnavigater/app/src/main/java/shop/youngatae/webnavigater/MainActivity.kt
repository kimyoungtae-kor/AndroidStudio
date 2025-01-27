package shop.youngatae.webnavigater

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import shop.youngatae.webnavigater.ui.theme.WebnavigaterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val webView : WebView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()
        var url ="https://www.naver.com"
        webView.loadUrl(url)
        webView.settings.javaScriptEnabled = true

        findViewById<Button>(R.id.button).setOnClickListener {
            webView.loadUrl("https://www.apple.com/kr/")
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            webView.loadUrl("https://mail.google.com/")
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            webView.loadUrl("https://www.google.com/?hl=ko/")
        }
        findViewById<Button>(R.id.button4).setOnClickListener {
            webView.loadUrl("https://www.kakaocorp.com/page/")
        }

        findViewById<Button>(R.id.button5).setOnClickListener {
            webView.goBack()
        }
        findViewById<Button>(R.id.button6).setOnClickListener {
            webView.reload()
        }
        findViewById<Button>(R.id.button7).setOnClickListener {
            webView.goForward()
        }




//        private lateinit var webView: WebView
//
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            setContentView(R.layout.activity_webview)
//
//            webView = findViewById(R.id.webView)
//
//            webView.apply {
//                settings.javaScriptEnabled = true //JS 활성화
//                settings.loadWithOverviewMode = true //컨텐츠 크기에맞게 fit
//                settings.useWideViewPort = true//ViewPort 적용
//                settings.cacheMode = WebSettings.LOAD_DEFAULT
//                webViewClient = WebViewClient()
//            }
//            val url = "https://m.naver.com/"
//            webView.loadUrl(url)
//        }
//        //뒤로가기 적용
//        override fun onBackPressed() {
//            if (webView.canGoBack()){
//                webView.goBack()
//            }else{
//                super.onBackPressed()
//            }
//        }
    }
}