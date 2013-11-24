package info.guardianproject.browser;

import org.mozilla.gecko.GeckoView;
import org.mozilla.gecko.GeckoView.Browser;
import org.mozilla.gecko.GeckoView.PromptResult;
import org.mozilla.gecko.GeckoViewChrome;
import org.mozilla.gecko.GeckoViewContent;
import org.mozilla.gecko.PrefsHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData.Item;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String LOGTAG = "GeckoBrowser";

    GeckoView mGeckoView;
    EditText mUrlBar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

        setContentView(R.layout.activity_main);

        mGeckoView = (GeckoView) findViewById(R.id.gecko_view);
        
        mGeckoView.setChromeDelegate(new MyGeckoViewChrome());
        mGeckoView.setContentDelegate(new MyGeckoViewContent());

        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        
        MenuItem item = menu.findItem(R.id.actionViewLayout);
        
        View view = item.getActionView();
        
        mUrlBar = (EditText) view.findViewById(R.id.url_bar);
        mUrlBar.setImeActionLabel("Go", KeyEvent.KEYCODE_ENTER);
        
        mUrlBar.setOnEditorActionListener(new OnEditorActionListener(){

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				
				
				if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)
				{
					 GeckoView.Browser browser = mGeckoView.getCurrentBrowser();
		                
		                
		                if (browser == null) {
		                    browser = mGeckoView.addBrowser(mUrlBar.getText().toString());
		                } else {
		                    browser.loadUrl(mUrlBar.getText().toString());
		                }
				}
				
				return false;
			}
        	
        	
        });
        
        /*
        Button goButton = (Button) view.findViewById(R.id.go_button);
        goButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                GeckoView.Browser browser = mGeckoView.getCurrentBrowser();
                
                
                if (browser == null) {
                    browser = mGeckoView.addBrowser(mUrlBar.getText().toString());
                } else {
                    browser.loadUrl(mUrlBar.getText().toString());
                }
            }
        });*/

        
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_exit:
                finish();
                return true;
            	
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
    	Browser selected = mGeckoView.getCurrentBrowser();
    	if (selected != null && selected.canGoBack()) {
    	    selected.goBack();
    	} else {
    	    moveTaskToBack(true);
    	}
    }

    private class MyGeckoViewChrome extends GeckoViewChrome {
        @Override
        public void onReady(GeckoView view) {
            Log.i(LOGTAG, "Gecko is ready");

            PrefsHelper.setPref("devtools.debugger.remote-enabled", false);
            setProxyPrefs();
            setPrivacyPrefs();
            setCipherSuites();
            setJavascriptEnabled(false);
            
            setUserAgent("Mozilla/5.0 (Windows NT 6.1; rv:17.0) Gecko/20100101 Firefox/17.0","en-us,en;q=0.5");
            
            // The Gecko libraries have finished loading and we can use the rendering engine.
            // Let's add a browser (required) and load a page into it.
            Browser b = mGeckoView.addBrowser("https://check.torproject.org");
            
        }
        
        private void setProxyPrefs ()
        {
        	
        	PrefsHelper.setPref("network.proxy.type",1); //manual proxy settings
        	
        	PrefsHelper.setPref("network.proxy.http","localhost"); //manual proxy settings
        	PrefsHelper.setPref("network.proxy.http_port",8118); //manual proxy settings
        	
        	PrefsHelper.setPref("network.proxy.socks","localhost"); //manual proxy settings
        	PrefsHelper.setPref("network.proxy.socks_port",9050); //manual proxy settings
        	PrefsHelper.setPref("network.proxy.socks_version",5); //manual proxy settings
            
        }
        
        private void setPrivacyPrefs ()
        {
        	PrefsHelper.setPref("browser.cache.disk.enable",false);
        	PrefsHelper.setPref("browser.cache.memory.enable",true); 
            	
        	PrefsHelper.setPref("browser.cache.disk.capacity",0);

        	PrefsHelper.setPref("privacy.clearOnShutdown.cache",true);
        	PrefsHelper.setPref("privacy.clearOnShutdown.cookies",true);
        	PrefsHelper.setPref("privacy.clearOnShutdown.downloads",true);
        	PrefsHelper.setPref("privacy.clearOnShutdown.formdata",true);
        	PrefsHelper.setPref("privacy.clearOnShutdown.history",true);
        	PrefsHelper.setPref("privacy.clearOnShutdown.offlineApps",true);
        	PrefsHelper.setPref("privacy.clearOnShutdown.passwords",true);
        	PrefsHelper.setPref("privacy.clearOnShutdown.sessions",true);
        	PrefsHelper.setPref("privacy.clearOnShutdown.siteSettings",true);
        	
        	PrefsHelper.setPref("privacy.donottrackheader.enabled",false);
        	PrefsHelper.setPref("privacy.donottrackheader.value",1);
        	
        	PrefsHelper.setPref("network.cookie.cookieBehavior", 1);
        	PrefsHelper.setPref("network.http.sendRefererHeader", 0);
        
        	PrefsHelper.setPref("security.OCSP.require", true);
        	PrefsHelper.setPref("security.checkloaduri",true);
        	
        	PrefsHelper.setPref("security.mixed_content.block_display_content", true);
        	
        	PrefsHelper.setPref("media.peerconnection.enabled",false); //webrtc disabled
        }
        
        private void setCipherSuites ()
        {
        	
        	//disable rc4
        	PrefsHelper.setPref("security.ssl3.ecdh_ecdsa_rc4_128_sha",false);
        	PrefsHelper.setPref("security.ssl3.ecdh_rsa_rc4_128_sha",false);
        	PrefsHelper.setPref("security.ssl3.ecdhe_ecdsa_rc4_128_sha",false);
        	PrefsHelper.setPref("security.ssl3.ecdhe_rsa_rc4_128_sha",false);
        	PrefsHelper.setPref("security.ssl3.rsa_rc4_128_md5",false);
        	PrefsHelper.setPref("security.ssl3.rsa_rc4_128_sha",false);
        }
        
        
        private void setJavascriptEnabled (boolean enabled)
        {
        	 PrefsHelper.setPref("javascript.enabled", enabled);
             	 
        }
        
        private void setUserAgent (String userAgent, String locale)
        {
        	PrefsHelper.setPref("general.useragent.override", userAgent);
        	
        	PrefsHelper.setPref("general.useragent.locale", locale);
            
        }

        @Override
        public void onAlert(GeckoView view, GeckoView.Browser browser, String message, GeckoView.PromptResult result) {
            Log.i(LOGTAG, "Alert!");
            result.confirm();
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onConfirm(GeckoView view, GeckoView.Browser browser, String message, final GeckoView.PromptResult result) {
            Log.i(LOGTAG, "Confirm!");
	        new AlertDialog.Builder(MainActivity.this)
	        .setTitle("javaScript dialog")
	        .setMessage(message)
	        .setPositiveButton(android.R.string.ok,
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        result.confirm();
	                    }
	                })
	        .setNegativeButton(android.R.string.cancel,
	                new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int which) {
	                        result.cancel();
	                    }
	                })
	        .create()
	        .show();
        }

        @Override
        public void onDebugRequest(GeckoView view, GeckoView.PromptResult result) {
            Log.i(LOGTAG, "Remote Debug!");
            result.confirm();
        }

		@Override
		public void onPrompt(GeckoView arg0, Browser arg1, String arg2,
				String arg3, PromptResult arg4) {
			// TODO Auto-generated method stub
			super.onPrompt(arg0, arg1, arg2, arg3, arg4);
		}
        
        
    }

    private class MyGeckoViewContent extends GeckoViewContent {
        @Override
        public void onReceivedTitle(GeckoView view, GeckoView.Browser browser, String title) {
            Log.i(LOGTAG, "Received a title");
			
            // Use the title returned from Gecko to update the UI
            // TODO: Only if the browser is the selected browser
            setTitle(title);
            
        }

		@Override
		public void onPageShow(GeckoView arg0, Browser arg1) {
			// TODO Auto-generated method stub
			super.onPageShow(arg0, arg1);
			
			   setProgressBarIndeterminateVisibility(Boolean.FALSE); 
			
		}

		@Override
		public void onPageStart(GeckoView arg0, Browser arg1, String arg2) {
			// TODO Auto-generated method stub
			super.onPageStart(arg0, arg1, arg2);
			
			mUrlBar.setText(arg2);

        	mUrlBar.setSelection(0, arg2.length());
        	
			   setProgressBarIndeterminateVisibility(Boolean.TRUE); 

            
		}

		@Override
		public void onPageStop(GeckoView arg0, Browser arg1, boolean arg2) {
			// TODO Auto-generated method stub
			super.onPageStop(arg0, arg1, arg2);
			
			   setProgressBarIndeterminateVisibility(Boolean.FALSE); 

		}

		@Override
		public void onReceivedFavicon(GeckoView arg0, Browser arg1,
				String arg2, int arg3) {
			// TODO Auto-generated method stub
			super.onReceivedFavicon(arg0, arg1, arg2, arg3);
			
		}
        
        
    }
}
