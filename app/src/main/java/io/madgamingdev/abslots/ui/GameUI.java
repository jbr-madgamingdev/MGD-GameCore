package io.madgamingdev.abslots.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.google.gson.Gson;

import io.madgamingdev.abslots.R;
import io.madgamingdev.abslots.codes.AppJSInterface;
import io.madgamingdev.abslots.libs.BridgeWebView;
import io.madgamingdev.abslots.libs.OnBridgeCallback;

public class GameUI extends AppCompatActivity {

    int resultCodeFix = 0;
    ValueCallback<Uri> mUploadMessage;
    ValueCallback<Uri[]> mUploadMessageArray;
    BridgeWebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(1);
        setContentView(R.layout.activity_game_ui);

        webView = findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient() {

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
                this.openFileChooser(uploadMsg);
            }

            @SuppressWarnings("unused")
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
                this.openFileChooser(uploadMsg);
            }

            public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                mUploadMessage = uploadMsg;
                pickFile();
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                mUploadMessageArray = filePathCallback;
                pickFile();
                return true;
            }
        });

        webView.addJavascriptInterface(new AppJSInterface(webView.getCallbacks(), webView), "pushEvents");
        webView.setGson(new Gson());
        webView.loadUrl("https://www.ab.bet/abgames?referralCode=Google02");

    }

    /**
     * @deprecated Will be updated for full functionality
     */
    @Deprecated
    public void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent, resultCodeFix);
    }

    /**
     * @param requestCode The integer request code originally supplied to
     *                    startActivityForResult(), allowing you to identify who this
     *                    result came from.
     * @param resultCode  The integer result code returned by the child activity
     *                    through its setResult().
     * @param intent      An Intent, which can return result data to the caller
     *                    (various data can be attached to Intent "extras").
     *
     * @deprecated Will be updated for full functionality
     */
    @Deprecated
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == resultCodeFix) {
            if (null == mUploadMessage && null == mUploadMessageArray) {
                return;
            }
            if (null != mUploadMessage && null == mUploadMessageArray) {
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }

            if (null == mUploadMessage && null != mUploadMessageArray) {
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                if (result != null) {
                    mUploadMessageArray.onReceiveValue(new Uri[]{result});
                }
                mUploadMessageArray = null;
            }

        }
    }

}