package io.madgamingdev.abslots.libs;

import android.content.Context;

public interface IWebView {
    Context getContext();

    void loadUrl(String url);
}
