package ir.sobazino.gedir;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class sz {
    Context mContext;
    sz(Context c) {
        mContext = c;
    }
    @JavascriptInterface
    public void Response(String txt){
        at at = new at(txt,mContext);
        at.execute("send_get");
    }
}
