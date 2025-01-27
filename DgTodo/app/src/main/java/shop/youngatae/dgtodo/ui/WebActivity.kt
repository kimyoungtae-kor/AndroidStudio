package shop.youngatae.dgtodo.ui

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import shop.youngatae.dgtodo.R

class WebActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        webView = findViewById(R.id.webView)

        webView.apply {
            settings.javaScriptEnabled = true //JS 활성화
            settings.loadWithOverviewMode = true //컨텐츠 크기에맞게 fit
            settings.useWideViewPort = true//ViewPort 적용
            settings.cacheMode = WebSettings.LOAD_DEFAULT
            webViewClient = WebViewClient()
        }
        val url = "https://m.naver.com/"
        webView.loadUrl(url)
    }
    //뒤로가기 적용
    override fun onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack()
        }else{
            super.onBackPressed()
        }
    }
}