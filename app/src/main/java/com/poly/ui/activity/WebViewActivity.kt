package com.poly.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.navArgs
import com.poly.databinding.ActivityWebViewBinding
import com.poly.utils.gone
import com.poly.utils.navigateUp

class WebViewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWebViewBinding
//    private val args: WebViewActivityArgs by navArgs()

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            imgButtonBack.setOnClickListener {
                navigateUp(it)
            }

            webView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    view?.loadUrl(url!!)
                    return true
                }

                override fun onPageFinished(view: WebView?, url: String?) {
                    progressCircular.gone()
                }
            }
//            webView.loadUrl(args.url)
            webView.settings.javaScriptEnabled = true
            webView.settings.javaScriptCanOpenWindowsAutomatically = true
        }
    }
}