This project was originally based upon the geckobrowser sample (see below) and the Orweb browser project from Guardian Project.

It is basically feature complete with Orweb, but without any preference screen yet! 

Here are the privacy-enhancing preferences that are on by default:

Match Tor Browser "generic" user-agent:

  setUserAgent("Mozilla/5.0 (Windows NT 6.1; rv:17.0) Gecko/20100101 Firefox/17.0","en-us,en;q=0.5");

Turn on proxying to local Tor / Orbot proxying by default:
               
    PrefsHelper.setPref("network.proxy.type",1); //manual proxy settings
    
    PrefsHelper.setPref("network.proxy.http","localhost"); //manual proxy settings
    PrefsHelper.setPref("network.proxy.http_port",8118); //manual proxy settings
    
    PrefsHelper.setPref("network.proxy.socks","localhost"); //manual proxy settings
    PrefsHelper.setPref("network.proxy.socks_port",9050); //manual proxy settings
    PrefsHelper.setPref("network.proxy.socks_version",5); //manual proxy settings

Disable dish cacheing:

                PrefsHelper.setPref("browser.cache.disk.enable",false);
                PrefsHelper.setPref("browser.cache.memory.enable",true); 
                    
                PrefsHelper.setPref("browser.cache.disk.capacity",0);

Ensure data is cleared on shutdown:

                PrefsHelper.setPref("privacy.clearOnShutdown.cache",true);
                PrefsHelper.setPref("privacy.clearOnShutdown.cookies",true);
                PrefsHelper.setPref("privacy.clearOnShutdown.downloads",true);
                PrefsHelper.setPref("privacy.clearOnShutdown.formdata",true);
                PrefsHelper.setPref("privacy.clearOnShutdown.history",true);
                PrefsHelper.setPref("privacy.clearOnShutdown.offlineApps",true);
                PrefsHelper.setPref("privacy.clearOnShutdown.passwords",true);
                PrefsHelper.setPref("privacy.clearOnShutdown.sessions",true);
                PrefsHelper.setPref("privacy.clearOnShutdown.siteSettings",true);

Do Not Track!

                PrefsHelper.setPref("privacy.donottrackheader.enabled",false);
                PrefsHelper.setPref("privacy.donottrackheader.value",1);

Disable 3rd party cookies:                
                PrefsHelper.setPref("network.cookie.cookieBehavior", 1);
                
Don't send a referrer:
                PrefsHelper.setPref("network.http.sendRefererHeader", 0);

Make sure certificates are up-to-date:        
                PrefsHelper.setPref("security.OCSP.require", true);
                PrefsHelper.setPref("security.checkloaduri",true);

Don't display mixed content (i.e. not secure content on a secure page)                
                PrefsHelper.setPref("security.mixed_content.block_display_content", true);
                
Disable peer-to-peer WebRTC leak:                
                PrefsHelper.setPref("media.peerconnection.enabled",false); //webrtc disabled

Disable ciphersuites that are not safe:

                //disable rc4
                PrefsHelper.setPref("security.ssl3.ecdh_ecdsa_rc4_128_sha",false);
                PrefsHelper.setPref("security.ssl3.ecdh_rsa_rc4_128_sha",false);
                PrefsHelper.setPref("security.ssl3.ecdhe_ecdsa_rc4_128_sha",false);
                PrefsHelper.setPref("security.ssl3.ecdhe_rsa_rc4_128_sha",false);
                PrefsHelper.setPref("security.ssl3.rsa_rc4_128_md5",false);
                PrefsHelper.setPref("security.ssl3.rsa_rc4_128_sha",false);

******
GeckoView assets and libraries from [here](http://ftp.mozilla.org/pub/mozilla.org/mobile/nightly/latest-mozilla-central-android/). You want the geckoview_library.zip and geckoview_assets.zip files.
