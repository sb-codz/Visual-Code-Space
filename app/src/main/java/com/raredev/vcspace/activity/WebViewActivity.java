package com.raredev.vcspace.activity;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.webkit.WebSettingsCompat;
import com.blankj.utilcode.util.ClipboardUtils;
import com.blankj.utilcode.util.FileUtils;
import com.google.android.material.snackbar.Snackbar;
import com.raredev.vcspace.util.FileUtil;
import com.raredev.vcspace.databinding.ActivityWebviewBinding;
import com.raredev.vcspace.R;

public class WebViewActivity extends VCSpaceActivity {
  private ActivityWebviewBinding binding;

  @Override
  public View getLayout() {
    binding = ActivityWebviewBinding.inflate(getLayoutInflater());
    return binding.getRoot();
  }

  @Override
  public void onCreate() {
    setSupportActionBar(binding.toolbar);

    binding.toolbar.setNavigationOnClickListener((v) -> onBackPressed());
    binding.webView.getSettings().setAllowContentAccess(true);
    binding.webView.getSettings().setAllowFileAccess(true);
    binding.webView.getSettings().setAllowFileAccessFromFileURLs(true);
    binding.webView.getSettings().setJavaScriptEnabled(true);
    binding.webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    binding.webView.getSettings().setSupportZoom(true);
    binding.webView.getSettings().setBuiltInZoomControls(true);
    binding.webView.getSettings().setDisplayZoomControls(false);

    String executableFilePath = getIntent().getStringExtra("executable_file");
    String htmlContent = getIntent().getStringExtra("html_content");
    if (executableFilePath != null) binding.webView.loadUrl("file://" + executableFilePath);
    else binding.webView.loadDataWithBaseURL(null, htmlContent, "text/html", "UTF-8", null);
    binding.toolbar.setOnClickListener(
        v -> {
          ClipboardUtils.copyText(getSupportActionBar().getSubtitle());
          Snackbar.make(binding.getRoot(), R.string.url_copied, Snackbar.LENGTH_SHORT).show();
        });

    binding.webView.setWebChromeClient(
        new WebChromeClient() {
          @Override
          public void onProgressChanged(WebView view, int progress) {
            binding.progressIndicator.setVisibility(progress == 100 ? View.GONE : View.VISIBLE);
            binding.progressIndicator.setProgressCompat(progress, true);
            getSupportActionBar().setTitle(view.getTitle() == "about:blank" ? getString(R.string.app_name) : view.getTitle());
            getSupportActionBar().setSubtitle(view.getUrl() == "about:blank" ? "Preview" : view.getUrl());
          }
        });
    binding.webView.setWebViewClient(
        new WebViewClient() {
          @Override
          public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
          }
        });
  }

  @Override
  public void onBackPressed() {
    if (binding.webView.canGoBack()) {
      binding.webView.goBack();
      return;
    }
    super.onBackPressed();
  }
}
