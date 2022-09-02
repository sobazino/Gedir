package ir.sobazino.gedir;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static ir.sobazino.gedir.MainActivity.mWebView;

class at extends AsyncTask<String, String, String> {
    String cht;
    Context mContext;
    String OUTPUT;
    public at(String chat,Context context)
    {
        cht = chat;
        mContext = context;
    }
    @Override
    protected String doInBackground(String... args) {
        if (args[0] == "send_get"){
            ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni != null) {
                // Server IP => http://192.168.1.124/
                BufferedReader br = send_get("http://192.168.1.124/AI_core/?T="+cht,"GET");
                if (br != null){
                    return streamToString(br);
                }
            }
        }
        return null;
    }
    protected void onPostExecute(String end) {
        if (end == "1"){
            mWebView.loadUrl(OUTPUT);
        }
    }
    BufferedReader send_get(String urlString, String method){
        try {
            URL url=new URL(urlString);
            HttpURLConnection huc=(HttpURLConnection)url.openConnection();
            huc.setReadTimeout(15000);
            huc.setConnectTimeout(20000);
            huc.setRequestMethod(method);
            huc.setDoInput(true);
            huc.connect();
            InputStream jsonStream = huc.getInputStream();
            return new BufferedReader(new InputStreamReader(jsonStream));
        } catch (Exception e) {return null;}
    }
    String streamToString(BufferedReader br){
        String line = null;
        try {
            while((line=br.readLine())!=null){
                if (cht.length() > 30) {
                    cht = cht.substring(0,30) + " ...";
                }
                OUTPUT = "javascript: (function() { Response(\""+line+"\",\""+cht+"\") })()";
            }
            return "1";
        }
        catch (Exception e){}
        return null;
    }
}
