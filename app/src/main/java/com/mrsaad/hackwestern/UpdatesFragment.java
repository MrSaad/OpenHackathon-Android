package com.mrsaad.hackwestern;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class UpdatesFragment extends Fragment {

    WebView webview;
    String url;

    public UpdatesFragment() {
        url = "https://hackwestern.com/updates";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        //set view layout
        final View root = inflater.inflate(R.layout.fragment_updates, container, false);
        setHasOptionsMenu(true);

        //set webView
        try{
            webview = (WebView)root.findViewById(R.id.webView1);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setDomStorageEnabled(true);
            webview.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    //hide loading image
                    root.findViewById(R.id.loading_webView1).setVisibility(View.GONE);
                    //show webview
                    root.findViewById(R.id.webView1).setVisibility(View.VISIBLE);
                }
            });
            webview.loadUrl(url);
        }catch (Exception e){
            Log.v("DEBUG", e.getMessage());
        }


        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_refresh){
            webview.reload();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
