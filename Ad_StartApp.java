#########################################
Copy file StartAppInApp-3.5.6.jar to ../YourProject/app/libs

#########################################
compile files('libs/StartAppInApp-3.5.6.jar')

#########################################
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
<uses-permission android:name="android.permission.BLUETOOTH" />

#########################################
<activity android:name="com.startapp.android.publish.ads.list3d.List3DActivity"
          android:theme="@android:style/Theme" />

<activity android:name="com.startapp.android.publish.adsCommon.activities.OverlayActivity"
          android:theme="@android:style/Theme.Translucent"
          android:configChanges="orientation|keyboardHidden|screenSize" />

<activity android:name="com.startapp.android.publish.adsCommon.activities.FullScreenActivity"
          android:theme="@android:style/Theme"
          android:configChanges="orientation|keyboardHidden|screenSize" />

#########################################		  
<service android:name="com.startapp.android.publish.common.metaData.PeriodicMetaDataService" />
<service android:name="com.startapp.android.publish.common.metaData.InfoEventService" />
<receiver android:name="com.startapp.android.publish.common.metaData.BootCompleteListener" >
    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED" />
    </intent-filter>
</receiver>

#########################################
StartAppSDK.init(this, "Your App ID", true);

StartAppAd.disableSplash() after calling StartAppSDK.init.

Return ads are enabled and activated by default. If you want to disable this feature, simply pass "false" as the 3th parameter of the StartAppSDK.init method:
StartAppSDK.init(this, "Your App ID", false);

#########################################
Banner Ads
<com.startapp.android.publish.ads.banner.Banner
        android:id="@+id/startAppBanner"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"/>
		
#########################################
Interstitial Ads are full page ads
StartAppAd.showAd(this);
