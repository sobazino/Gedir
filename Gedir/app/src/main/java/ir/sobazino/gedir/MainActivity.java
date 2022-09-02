package ir.sobazino.gedir;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static public WebView mWebView;
    private RelativeLayout loading;
    private RelativeLayout start;
    private TextView load;
    CountDownTimer ycdt;
    int k = 0;

    private class mWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {

            new CountDownTimer(1000,300){
                @Override
                public void onTick(long millisUntilFinished){}

                @Override
                public void onFinish(){
                    loading.animate()
                            .alpha(0.0f)
                            .setDuration(500)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    ycdt.cancel();
                                    loading.setVisibility(View.GONE);
                                }
                            });
                }
            }.start();
            new CountDownTimer(2000,1000){
                @Override
                public void onTick(long millisUntilFinished){}

                @Override
                public void onFinish(){
                    start.animate()
                            .alpha(0.0f)
                            .setDuration(2000)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    start.setVisibility(View.GONE);
                                }
                            });
                }
            }.start();
            mWebView.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = findViewById(R.id.web_view);
        loading = findViewById(R.id.loading);
        start = findViewById(R.id.start);
        load = findViewById(R.id.load);

        loading();
        mWebView.loadUrl("file:///android_asset/index.html");
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new sz(this), "Android");
        mWebView.setWebViewClient(new mWebViewClient());
    }
    public void loading(){
        ycdt = new CountDownTimer(15000,100){
            @Override
            public void onTick(long millisUntilFinished){
                if(k==0){
                    load.setText("▄ . . . . .");
                    k=1;
                }
                else if(k==1){
                    load.setText(". ▄ . . . .");
                    k=2;
                }
                else if(k==2){
                    load.setText(". . ▄ . . .");
                    k=3;
                }
                else if(k==3){
                    load.setText(". . . ▄ . .");
                    k=4;
                }
                else if(k==4){
                    load.setText(". . . . ▄ .");
                    k=5;
                }
                else if(k==5){
                    load.setText(". . . . . ▄");
                    k=0;
                }
            }
            @Override
            public void onFinish(){
                Toast.makeText(MainActivity.this, "There seems to be a problem !", Toast.LENGTH_SHORT).show();
            }
        }.start();
    }
}