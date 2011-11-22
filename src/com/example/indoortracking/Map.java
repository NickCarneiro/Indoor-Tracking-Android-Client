package com.example.indoortracking;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings.Secure;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.example.indoortracking.AndroidWifiMonitor.WifiReceiver;
import com.example.indoortracking.PointWithRSSI.Point2D;

public class Map extends Activity {
	public static final String address = "http://trillworks.com:3010/coordinates"; 
	public static final String test = "330 -360 00:23:eb:e4:d1:31 -82.0 00:23:eb:e4:d1:30 -83.0 00:25:bc:8a:13:89 -81.95999 00:26:cb:16:dc:51 -70.0 00:26:cb:16:dc:50 -70.0 00:23:eb:e4:c9:8e -92.0 00:26:cb:16:dd:51 -61.7 00:23:eb:e4:47:7f -92.0 00:23:eb:e4:47:70 -77.0 00:26:cb:16:dc:5e -89.0 00:23:eb:e4:47:71 -80.0 06:25:bc:8a:13:89 -86.8 00:26:cb:16:dd:50 -60.68 00:26:cb:16:dd:5e -74.0 00:26:cb:16:dd:5f -73.0 00:23:eb:e4:ca:d0 -64.82001 00:23:eb:e4:cb:01 -80.72 00:23:eb:e4:cb:00 -81.70001 00:23:eb:e4:ca:df -77.0 00:23:eb:e4:ca:d1 -63.84 00:23:eb:e4:ca:de -76.0 00:1e:e5:6c:70:22 -87.0 \r\n" + 
			"330 -450 00:26:cb:16:dc:5f -90.0 00:23:eb:e4:47:70 -75.86001 00:25:bc:8a:13:89 -79.100006 00:23:eb:e4:47:71 -73.899994 00:26:cb:16:dd:b0 -86.0 06:25:bc:8a:13:89 -76.0 00:25:84:86:a2:71 -86.0 00:26:cb:16:dd:50 -52.059994 00:26:cb:16:dd:5e -76.92 00:26:cb:16:dd:5f -76.92 00:26:cb:16:dc:51 -77.88001 00:26:cb:16:dc:50 -76.899994 00:23:eb:e4:ca:d0 -57.1 00:23:eb:e4:cb:01 -75.0 00:23:eb:e4:cb:00 -76.0 00:23:eb:e4:ca:df -71.0 00:23:eb:e4:ca:d1 -57.1 00:23:eb:e4:c9:80 -84.0 00:23:eb:e4:ca:de -71.22 00:26:cb:16:dd:51 -52.1 \r\n" + 
			"330 -544 00:23:eb:e4:d1:31 -84.899994 00:23:eb:e4:47:70 -75.88001 00:25:bc:8a:13:89 -74.100006 00:23:eb:e4:47:71 -75.94 00:26:cb:16:e4:61 -83.0 06:25:bc:8a:13:89 -75.02 00:25:84:86:a2:71 -85.0 00:26:cb:16:dd:b1 -78.0 00:26:cb:16:dd:50 -52.04 00:26:cb:16:dd:5e -66.899994 00:26:cb:16:dd:5f -66.88001 00:26:cb:16:e4:6e -88.0 00:26:cb:16:dc:51 -72.06 00:26:cb:16:dc:50 -71.08 00:23:eb:e4:ca:d0 -57.02 00:23:eb:e4:cb:01 -70.0 00:23:eb:e4:cb:00 -70.0 00:26:cb:16:e4:60 -83.100006 00:23:eb:e4:ca:df -71.100006 00:23:eb:e4:ca:d1 -57.02 00:23:eb:e4:ca:de -71.100006 00:26:cb:16:dd:51 -52.059994 \r\n" + 
			"330 -597 00:26:cb:16:dd:be -86.0 00:25:bc:8a:13:89 -77.11999 00:26:cb:16:dd:b0 -69.17999 00:26:cb:16:e4:61 -72.13999 00:26:cb:16:dd:b1 -69.0 00:26:cb:16:dc:51 -73.0 00:26:cb:16:dc:50 -73.0 00:26:cb:16:e4:60 -72.13999 00:23:eb:e4:c9:80 -84.97999 00:26:cb:16:dd:51 -52.0 00:23:eb:e4:47:70 -77.95999 06:25:bc:8a:13:89 -77.0 00:26:cb:16:dd:50 -52.0 00:26:cb:16:dd:5e -61.18 00:26:cb:16:dd:5f -60.2 00:23:eb:e4:ca:d0 -48.18 00:23:eb:e4:cb:01 -67.100006 00:23:eb:e4:cb:0e -87.0 00:23:eb:e4:cb:00 -66.11999 00:23:eb:e4:ca:df -72.04 00:23:eb:e4:ca:d1 -48.18 00:23:eb:e4:ca:de -73.02001 00:1e:e5:6c:70:22 -87.97999 \r\n" + 
			"330 -642 00:25:bc:8a:13:89 -70.03998 00:26:cb:16:e4:61 -67.0 06:25:bc:8a:13:89 -67.100006 00:26:cb:16:dd:b0 -68.04 00:25:84:86:a2:71 -82.95999 00:26:cb:16:dd:b1 -69.03998 00:26:cb:16:dd:50 -38.16 00:26:cb:ab:f5:90 -87.95999 00:26:cb:16:dd:5e -67.88001 00:26:cb:16:dd:5f -66.899994 00:26:cb:16:e4:6e -77.13999 00:26:cb:16:dc:51 -72.06 00:26:cb:16:e4:6f -78.0 00:26:cb:16:dc:50 -72.0 00:23:eb:e4:ca:d0 -51.02 00:23:eb:e4:cb:0f -84.0 00:23:eb:e4:cb:01 -69.94 00:23:eb:e4:cb:00 -68.97999 00:23:eb:e4:ca:df -67.96 00:23:eb:e4:ca:d1 -52.0 00:23:eb:e4:c9:80 -85.08 00:23:eb:e4:ca:de -67.0 00:26:cb:16:dd:51 -37.2 \r\n" + 
			"257 -642 00:25:bc:8a:13:89 -66.16 00:25:84:86:a2:70 -85.0 00:26:cb:16:dd:b0 -64.100006 00:26:cb:16:e4:61 -67.08 00:25:84:86:a2:71 -84.84 00:26:cb:16:dd:b1 -64.100006 00:26:cb:16:dd:bf -83.02001 00:26:cb:16:e4:6e -76.0 00:26:cb:16:dc:51 -77.98 00:26:cb:16:dc:50 -77.0 00:26:cb:16:e4:6f -75.02 00:26:cb:16:e4:60 -66.100006 00:26:cb:16:dd:51 -56.68 00:23:eb:e4:47:71 -85.82 06:25:bc:8a:13:89 -67.08 00:26:cb:16:dd:50 -56.7 00:26:cb:16:dd:5e -73.92 00:26:cb:16:dd:5f -72.94 00:23:eb:e4:ca:d0 -52.1 00:23:eb:e4:cb:0f -79.13999 00:23:eb:e4:cb:00 -61.22 00:23:eb:e4:ca:df -66.97999 00:23:eb:e4:ca:d1 -52.1 00:23:eb:e4:ca:de -67.96 \r\n" + 
			"172 -642 00:26:cb:16:dd:be -77.100006 00:25:bc:8a:13:89 -73.86001 00:25:84:86:a2:70 -76.0 06:25:bc:8a:13:89 -73.88001 00:26:cb:16:dd:b0 -65.96 00:26:cb:16:e4:61 -70.899994 00:26:cb:16:dd:b1 -66.96 00:26:cb:16:dd:50 -60.8 00:26:cb:16:dd:bf -77.100006 00:26:cb:16:dd:5f -82.8 00:26:cb:16:e4:6e -85.0 00:26:cb:16:dc:51 -77.08 00:26:cb:16:e4:6f -86.0 00:26:cb:16:dc:50 -77.08 00:23:eb:e4:ca:d0 -57.88 00:23:eb:e4:cb:01 -59.0 00:23:eb:e4:cb:0e -77.0 00:23:eb:e4:cb:00 -59.02 00:23:eb:e4:ca:df -77.86001 00:26:cb:16:e4:60 -71.88001 00:23:eb:e4:ca:d1 -59.86 00:23:eb:e4:ca:de -76.88001 00:26:cb:16:dd:51 -61.8 \r\n" + 
			"109 -642 00:26:cb:16:dd:be -73.0 00:25:bc:8a:13:89 -80.94998 00:25:84:86:a2:70 -76.03001 06:25:bc:8a:13:89 -81.93 00:26:cb:16:dd:b0 -58.12 00:26:cb:16:e4:61 -67.05002 00:25:84:86:a2:71 -76.03001 00:26:cb:16:dd:b1 -58.12 00:26:cb:16:dd:50 -62.0 00:26:cb:16:dd:bf -73.0 00:26:cb:16:dc:51 -83.94998 00:26:cb:16:e4:6e -73.05002 00:26:cb:16:e4:6f -73.0 00:26:cb:16:dc:50 -85.92999 00:23:eb:e4:ca:d0 -61.97 00:23:eb:e4:cb:0f -78.08 00:23:eb:e4:cb:01 -62.96 00:23:eb:e4:cb:00 -61.98 00:23:eb:e4:ca:df -80.899994 00:26:cb:16:e4:60 -66.06 00:23:eb:e4:ca:d1 -60.98 00:26:cb:16:dd:51 -61.02 \r\n" + 
			"29 -642 00:26:cb:16:dd:be -73.04 00:25:bc:8a:13:89 -84.06 00:25:84:86:a2:70 -78.0 00:26:cb:16:dd:b0 -60.96 00:26:cb:16:e4:61 -68.899994 00:25:84:86:a2:71 -77.94 00:26:cb:16:dd:b1 -60.0 00:26:cb:16:dd:bf -74.02 00:26:cb:16:e4:6e -75.95999 00:26:cb:16:e4:6f -75.94 00:26:cb:16:e4:60 -67.94 00:26:cb:d1:fc:c0 -79.0 00:26:cb:16:dd:51 -63.88 06:25:bc:8a:13:89 -84.0 00:26:cb:16:dd:50 -64.86001 00:26:cb:16:dd:5e -85.84 00:23:eb:e4:ca:d0 -73.86001 00:23:eb:e4:cb:0f -82.88001 00:23:eb:e4:cb:01 -62.92 00:23:eb:e4:cb:00 -63.9 00:23:eb:e4:ca:d1 -73.0 00:23:eb:e4:ca:de -83.0 00:26:cb:16:e5:91 -88.0 \r\n" + 
			"-44 -642 00:26:cb:16:dd:be -75.94 00:25:bc:8a:13:89 -87.02001 00:25:84:86:a2:70 -75.98 00:26:cb:16:e4:61 -57.0 00:26:cb:16:dd:b0 -58.14 00:25:84:86:a2:71 -76.98 00:26:cb:16:dd:b1 -58.14 00:26:cb:16:dd:bf -74.94 00:26:cb:16:e4:6e -74.08 00:26:cb:16:e4:6f -74.06 00:26:cb:16:e4:60 -57.24 00:26:cb:d1:fc:c1 -80.0 00:26:cb:16:dd:51 -69.86001 06:25:bc:8a:13:89 -84.14 00:26:cb:16:dd:5e -87.0 00:23:eb:e4:ca:d0 -74.0 00:23:eb:e4:cb:0f -82.06 00:23:eb:e4:cb:01 -65.88001 00:23:eb:e4:cb:00 -65.88001 00:23:eb:e4:ca:df -88.0 \r\n" + 
			"-118 -642 00:26:cb:16:dd:be -74.94 00:26:cb:16:dd:b0 -59.94 00:26:cb:16:e4:61 -55.04 00:25:84:86:a2:71 -79.06 00:26:cb:16:dd:b1 -59.92 00:26:cb:16:dd:5e -88.0 00:26:cb:16:dd:bf -74.94 00:26:cb:16:e4:6e -70.08 00:23:eb:e4:ca:d0 -77.95999 00:26:cb:16:e4:6f -69.08 00:23:eb:e4:cb:0f -88.0 00:23:eb:e4:cb:01 -68.96002 00:23:eb:e4:cb:00 -67.96 00:26:cb:16:e4:60 -55.059994 00:23:eb:e4:ca:d1 -79.899994 00:26:cb:16:e5:91 -81.0 00:26:cb:d1:fc:c1 -84.95999 00:26:cb:16:dd:51 -69.06 \r\n" + 
			"-157 -642 00:26:cb:16:dd:be -79.86001 00:25:bc:8a:13:89 -89.0 00:26:cb:16:dd:b0 -60.96 00:26:cb:16:e4:61 -53.059994 00:25:84:86:a2:71 -80.0 00:26:cb:16:dd:b1 -60.98 00:26:cb:16:dd:bf -80.82001 00:26:cb:16:e5:90 -82.0 00:26:cb:16:e4:6e -71.96 00:23:eb:e4:ca:d0 -72.06 00:26:cb:16:e4:6f -72.96 00:23:eb:e4:cb:01 -75.84 00:23:eb:e4:cb:00 -75.82001 00:26:cb:16:e4:60 -54.059994 00:23:eb:e4:ca:d1 -73.04 00:26:cb:16:e5:91 -81.95999 00:26:cb:16:dd:51 -68.02001 \r\n" + 
			"-227 -642 00:25:84:86:a2:70 -84.100006 00:26:cb:16:dd:b0 -67.86001 00:26:cb:16:e4:61 -55.1 00:26:cb:16:dd:b1 -67.86001 00:26:cb:16:dd:50 -70.96 00:26:cb:16:dd:5e -80.0 00:23:eb:e4:cc:60 -87.0 00:26:cb:16:e4:6e -66.100006 00:26:cb:16:e4:6f -67.06 00:23:eb:e4:cb:01 -73.0 00:23:eb:e4:cb:00 -74.0 00:26:cb:16:e4:60 -55.08 00:23:eb:e4:ca:d1 -75.94 00:26:cb:16:e5:91 -78.0 00:26:cb:d1:fc:c0 -88.0 00:26:cb:16:dd:51 -69.96002 \r\n" + 
			"-311 -642 00:23:eb:e4:cc:61 -79.17999 00:23:eb:e4:d0:c1 -72.0 00:23:eb:e4:d0:c0 -72.34 00:26:cb:16:e4:61 -33.0 00:26:cb:16:dd:b0 -75.86001 00:26:cb:16:dd:b1 -74.88001 00:26:cb:16:dd:50 -77.899994 00:26:cb:16:dd:bf -84.0 00:23:eb:e4:d0:ce -89.0 00:26:cb:16:dd:5e -86.0 00:23:eb:e4:cc:60 -78.0 00:26:cb:16:e4:6e -52.2 00:25:84:86:4e:e1 -84.0 00:23:eb:e4:ca:d0 -74.04 00:26:cb:16:e4:6f -51.22 00:23:eb:e4:cb:00 -76.0 00:26:cb:16:e4:60 -33.0 00:23:eb:e4:ca:d1 -73.08 00:26:cb:16:e5:91 -72.11999 00:26:cb:d1:fc:c1 -87.0 00:26:cb:16:dd:51 -78.86001 \r\n" + 
			"-346 -642 00:26:cb:16:dd:be -78.2 00:23:eb:e4:d0:c1 -78.84 00:25:bc:8a:13:89 -87.0 00:25:84:86:a2:70 -87.0 00:26:cb:16:dd:b0 -65.2 00:26:cb:16:e4:61 -49.84 00:26:cb:16:dd:b1 -65.17999 00:26:cb:16:dd:bf -79.0 00:26:cb:16:e4:6e -52.96 00:26:cb:16:e4:6f -52.92 00:26:cb:16:e4:60 -48.74 00:26:cb:d1:fc:c0 -80.11999 00:26:cb:16:dd:51 -72.06 00:26:cb:16:dd:50 -72.06 00:26:cb:16:dd:5e -82.0 00:26:cb:16:dd:5f -81.11999 00:26:cb:16:e5:90 -74.92 00:25:84:86:4e:e0 -87.92 00:23:eb:e4:cb:01 -70.100006 00:23:eb:e4:cb:0e -91.0 00:23:eb:e4:ca:d1 -74.96 00:26:cb:16:e5:91 -74.88001 \r\n" + 
			"-346 -575 00:23:eb:e4:cc:61 -75.06 00:23:eb:e4:d0:c0 -74.06 00:26:cb:16:dd:b0 -75.8 00:26:cb:16:e4:61 -56.84 00:23:eb:e4:d0:cf -84.0 00:23:eb:e4:cc:60 -75.0 00:26:cb:16:e4:6e -58.94 00:26:cb:16:e4:6f -58.94 00:26:cb:16:e4:60 -57.84 00:26:cb:16:dd:51 -76.88001 00:26:cb:16:e5:90 -80.899994 00:25:84:86:4e:e0 -86.0 00:23:eb:e4:cb:01 -87.64 \r\n" + 
			"-346 -498 00:23:eb:e4:cc:61 -77.95999 00:23:eb:e4:d0:c1 -69.100006 00:23:eb:e4:d0:c0 -69.11999 00:26:cb:16:e4:61 -54.04 00:26:cb:16:e2:01 -86.0 00:23:eb:e4:d0:cf -80.0 00:23:eb:e4:d0:ce -80.0 00:23:eb:e4:cc:60 -77.95999 00:26:cb:16:e4:6e -66.94 00:26:cb:16:e4:6f -66.92 00:25:84:86:4e:e0 -84.11999 00:26:cb:16:e4:60 -54.04 00:26:cb:16:dd:51 -89.74 \r\n" + 
			"-346 -411 00:23:eb:e4:cc:61 -71.152176 00:23:eb:e4:d0:c1 -64.416664 00:23:eb:e4:d0:c0 -64.44899 00:26:cb:16:e2:00 -85.0 00:26:cb:16:dd:b0 -83.833336 00:26:cb:16:e4:61 -52.22 00:26:cb:16:e2:01 -85.5 00:26:cb:16:dd:b1 -82.666664 00:23:eb:e4:d0:cf -79.55556 00:23:eb:e4:d0:ce -79.181816 00:23:eb:e4:cc:60 -70.734695 00:26:cb:16:e5:90 -85.947365 00:26:cb:16:e4:6e -66.90002 00:26:cb:16:e4:6f -66.86001 00:25:84:86:4e:e1 -85.36585 00:25:84:86:4e:e0 -87.086975 00:23:eb:e4:cc:6e -89.0 00:26:cb:16:e4:60 -52.444443 00:26:cb:16:e5:91 -85.5 " +
			"-340 -332 00:23:eb:e4:cc:61 -64.2 00:23:eb:e4:d0:c1 -54.44 00:23:eb:e4:d0:c0 -55.4 00:26:cb:16:e2:00 -76.95999 00:26:cb:16:e4:61 -65.0 00:26:cb:16:e2:01 -75.04 00:23:eb:e4:d0:cf -72.0 00:23:eb:e4:d0:ce -72.0 00:23:eb:e4:cc:60 -63.22 00:26:cb:16:e4:6e -76.0 00:26:cb:16:e4:6f -76.0 00:25:84:86:4e:e1 -81.78 00:25:84:86:4e:e0 -81.78 00:26:cb:16:e4:60 -65.0 00:23:eb:e4:cc:6e -87.0 \r\n" + 
			"-340 -250 00:23:eb:e4:cc:61 -61.0 00:23:eb:e4:d0:c1 -55.08 00:23:eb:e4:d0:c0 -56.08 00:26:cb:16:e4:61 -67.96 00:23:eb:e4:cc:31 -88.0 00:26:cb:16:e2:01 -75.04 00:23:eb:e4:d0:cf -67.13999 00:23:eb:e4:d0:ce -67.13999 00:23:eb:e4:cc:60 -60.0 00:25:84:86:4e:e1 -74.100006 00:25:84:86:4e:e0 -74.100006 00:26:cb:16:e4:60 -68.92 00:23:eb:e4:cc:6e -78.17999 00:23:eb:e4:cc:6f -77.0 \r\n" + 
			"-340 -119 00:23:eb:e4:cc:61 -52.32 00:23:eb:e4:d0:c1 -47.16 00:23:eb:e4:d0:c0 -48.139996 00:26:cb:16:e2:00 -78.0 00:26:cb:16:e4:61 -66.13999 00:26:cb:16:e2:01 -78.0 00:23:eb:e4:c9:81 -77.0 00:23:eb:e4:d0:cf -64.06 00:23:eb:e4:d0:ce -65.04 00:23:eb:e4:cc:60 -52.3 00:25:84:86:4e:e1 -71.97999 00:25:84:86:4e:e0 -71.97999 00:26:cb:16:e4:60 -66.13999 00:23:eb:e4:cc:6e -69.16 00:23:eb:e4:c9:80 -78.0 00:23:eb:e4:cc:6f -68.17999 00:1e:e5:6c:70:22 -89.0 \r\n" + 
			"-405 -119 00:23:eb:e4:cc:61 -55.1 00:23:eb:e4:d0:c1 -50.04 00:23:eb:e4:d0:c0 -50.02 00:26:cb:16:e2:00 -75.06 00:26:cb:16:e2:01 -76.02 00:23:eb:e4:d0:cf -64.08 00:23:eb:e4:d0:ce -64.08 00:23:eb:e4:cc:60 -55.08 00:26:cb:16:e4:6e -87.68 00:23:eb:e4:cb:a0 -89.04001 00:26:cb:16:e4:60 -79.82001 00:23:eb:e4:c9:80 -79.13999 00:23:eb:e4:c9:8e -90.0 00:23:eb:e4:47:70 -83.08 00:23:eb:e4:47:71 -83.0 00:23:eb:e4:cc:31 -83.0 00:23:eb:e4:c8:30 -89.0 00:25:84:86:4e:e1 -72.97999 00:25:84:86:4e:e0 -72.97999 00:23:eb:e4:cc:6e -71.97999 00:23:eb:e4:cc:6f -71.0 00:1e:e5:6c:70:22 -89.0 \r\n" + 
			"-405 -14 00:23:eb:e4:cc:61 -59.98 00:23:eb:e4:d0:c1 -65.67998 00:23:eb:e4:d0:c0 -65.66 00:26:cb:16:e2:00 -72.06 00:23:eb:e4:cc:31 -84.06 00:26:cb:16:e2:01 -72.04 00:23:eb:e4:d0:cf -80.72 00:23:eb:e4:d0:ce -79.74 00:23:eb:e4:cc:60 -60.92 00:26:cb:16:e4:6e -92.0 00:25:84:86:4e:e1 -68.08 00:25:84:86:4e:e0 -68.06 00:23:eb:e4:cb:a1 -85.06 00:23:eb:e4:cb:a0 -83.0 00:26:cb:16:e4:60 -82.88001 00:23:eb:e4:cc:6e -73.06 00:23:eb:e4:cc:6f -73.04 \r\n" + 
			"-405 96 00:23:eb:e4:cc:61 -62.08 00:23:eb:e4:d0:c1 -66.96 00:23:eb:e4:d0:c0 -66.94 00:26:cb:16:e2:00 -71.06 00:23:eb:e4:cc:30 -81.04001 00:26:cb:16:e4:61 -75.11999 00:26:cb:16:e2:01 -70.100006 00:23:eb:e4:d0:cf -79.92 00:23:eb:e4:d0:ce -79.899994 00:23:eb:e4:cc:60 -62.08 00:26:cb:16:e4:6e -88.04001 00:25:84:86:4e:e1 -61.1 00:25:84:86:4e:e0 -60.14 00:23:eb:e4:cb:a0 -85.0 00:26:cb:16:e4:60 -74.13999 00:26:cb:ac:00:f1 -85.0 00:23:eb:e4:cc:6f -83.84 \r\n" + 
			"-514 184 00:23:eb:e4:cc:61 -69.88001 00:23:eb:e4:d0:c1 -75.78 00:23:eb:e4:d0:c0 -76.78 00:26:cb:16:e2:00 -73.0 00:26:cb:16:e2:01 -72.0 00:23:eb:e4:cc:60 -69.86001 00:25:84:86:4e:e1 -74.78 00:25:84:86:4e:e0 -74.75999 \r\n" + 
			"-490 109 00:23:eb:e4:cc:61 -68.0 00:25:84:86:4e:e1 -71.0 00:25:84:86:4e:e0 -70.97999 00:23:eb:e4:d0:c1 -79.0 00:23:eb:e4:d0:c0 -79.06 00:26:cb:16:e2:00 -74.0 00:26:cb:16:e4:61 -86.0 00:26:cb:16:e2:01 -73.0 00:23:eb:e4:cc:60 -68.94 00:02:a8:d2:0e:ed -87.94 " + "-406 190 00:23:eb:e4:cc:61 -68.11999 00:23:eb:e4:d0:c1 -77.0 00:26:cb:16:e2:00 -75.04 00:23:eb:e4:cc:30 -70.0 00:23:eb:e4:cc:31 -70.17999 00:26:cb:16:e2:01 -76.02 00:23:eb:e4:d0:ce -91.0 00:23:eb:e4:cc:60 -69.11999 00:25:84:86:4e:e1 -69.16 00:25:84:86:4e:e0 -71.13999 00:23:eb:e4:cb:a1 -81.0 00:23:eb:e4:cb:a0 -80.0 00:23:eb:e4:cb:50 -87.0 \r\n" + 
			"-406 344 00:23:eb:e4:cc:61 -73.84 00:23:eb:e4:d0:c1 -81.92 00:23:eb:e4:cc:30 -79.86001 00:26:cb:16:e2:01 -76.100006 00:23:eb:e4:cc:31 -78.899994 00:23:eb:e4:cc:60 -72.899994 00:26:cb:ac:00:f0 -81.0 00:25:84:86:4e:e1 -76.92 00:25:84:86:4e:e0 -79.88001 00:23:eb:e4:cb:a1 -80.13999 00:23:eb:e4:cc:6e -87.0 00:23:eb:e4:cc:3e -86.0 \r\n" + 
			"-406 427 00:23:eb:e4:cc:61 -71.0 00:23:eb:e4:d0:c1 -84.94 00:23:eb:e4:d0:c0 -84.92 00:23:eb:e4:cc:30 -76.0 00:23:eb:e4:cc:31 -75.0 00:26:cb:16:e2:01 -82.02001 00:23:eb:e4:cc:60 -71.100006 00:26:cb:ac:00:f0 -81.94 00:25:84:86:4e:e1 -76.98 00:23:eb:e4:cb:a1 -75.22 00:23:eb:e4:cc:6e -86.0 00:23:eb:e4:cb:a0 -74.26 00:26:cb:ac:00:f1 -81.0 00:23:eb:e4:cb:51 -85.0 \r\n" + 
			"-406 542 00:26:cb:16:e4:61 -87.0 00:23:eb:e4:cc:30 -69.13999 00:23:eb:e4:cc:31 -70.0 00:23:eb:e4:cc:60 -82.0 00:25:84:86:4e:e1 -82.84 00:23:eb:e4:cb:a0 -85.0 00:26:cb:16:e6:20 -89.0 \r\n" + 
			"-406 648 00:23:eb:e4:cc:61 -83.0 00:25:84:86:4e:e1 -81.02001 00:25:84:86:4e:e0 -80.0 00:26:cb:16:e6:21 -86.0 00:23:eb:e4:cc:30 -71.92 00:23:eb:e4:cc:31 -71.92 00:23:eb:e4:cc:3e -92.0 00:26:cb:16:e6:20 -85.0 02:14:6c:5c:a8:71 -86.0 \r\n" + 
			"-289 512 00:23:eb:e4:cc:61 -78.08 00:23:eb:e4:cc:3f -70.0 00:23:eb:e4:cc:31 -62.24 00:23:eb:e4:cc:60 -78.0 00:25:84:86:4e:e1 -83.94 00:23:eb:e4:cb:ae -92.0 00:23:eb:e4:cb:a1 -79.0 00:23:eb:e4:cb:a0 -79.0 00:23:eb:e4:cc:3e -70.0 00:23:eb:e4:cb:50 -77.0 00:23:eb:e4:cb:51 -77.0 00:25:84:86:b4:f0 -83.0 00:26:cb:ac:00:fe -87.0 \r\n" + 
			"-107 521 00:23:eb:e4:cc:3f -73.899994 00:23:eb:e4:cc:30 -63.02 00:23:eb:e4:cc:31 -63.02 00:23:eb:e4:cc:60 -80.92 00:25:84:86:bb:60 -85.0 00:26:cb:ac:00:f0 -65.13999 00:25:84:86:b4:f1 -76.08 00:23:eb:e4:cb:ae -89.0 00:23:eb:e4:cb:a1 -70.0 00:23:eb:e4:cb:af -90.0 00:23:eb:e4:cb:a0 -70.0 00:26:cb:ac:00:f1 -67.100006 00:23:eb:e4:cc:3e -73.899994 00:23:eb:e4:cb:50 -63.26 00:26:cb:ac:00:ff -84.100006 00:23:eb:e4:cb:51 -64.26 00:26:cb:ac:00:fe -84.0 00:25:84:86:b4:f0 -77.06 \r\n" + 
			"-7 521 00:23:eb:e4:cc:3f -74.94 00:23:eb:e4:cc:30 -70.0 00:23:eb:e4:cc:31 -68.86001 00:26:cb:ac:00:f0 -67.0 00:23:eb:e4:cb:ae -81.0 00:23:eb:e4:cb:af -81.0 00:23:eb:e4:cb:a1 -67.97999 00:23:eb:e4:cb:a0 -67.97999 00:26:cb:ac:00:f1 -67.0 00:23:eb:e4:cc:3e -74.94 00:23:eb:e4:cb:50 -63.14 00:26:cb:ac:00:ff -81.100006 00:23:eb:e4:cb:51 -63.14";
	public static RelativeLayout mainLayout; 
	public static Context context; 
	public static RelativeLayout.LayoutParams layoutParam; 
	public static String device_id;
	public Runnable runnable; 
	public Object temp; 
	public boolean killMe; 
	MyCustomView tempView = null;
	Button buttonTransmit;
	private Handler handler; 
	private static AndroidWifiMonitor aa;
	WifiManager mainWifi;
	static DesiredFunctionality df;		//this will be used to do calibration
	WifiReceiver receiverWifi;
	List<ScanResult> wifiList;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//aa = new AndroidWifiMonitor();
		//aa.readData(null, test);
		readData(test);
		String s = df.newOutput();
		setContentView(R.layout.map);
		Random gen = new Random();
		device_id =  Secure.getString(getContentResolver(), Secure.ANDROID_ID);
		mainLayout = (RelativeLayout)findViewById(R.layout.relLayout);
		context = this; 
		handler = new Handler(); 
		killMe = false; 
		continuouslyTransmit();


		Button buttonBack = (Button) findViewById(R.id.buttonBack);
		buttonBack.setOnClickListener(new View.OnClickListener()  {
			public void onClick(View v) {
				killMe = true; 
				handler.removeCallbacks(runnable);
				startActivity(new Intent(Map.this, indoortracking.class));
				 
			}
		});
	}
	
	private BroadcastReceiver myWifiReceiver
	= new BroadcastReceiver(){

		@Override
		public void onReceive(Context arg0, Intent arg1) {
			//  Auto-generated method stub
			NetworkInfo networkInfo = (NetworkInfo) arg1.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
			if(networkInfo.getType() == ConnectivityManager.TYPE_WIFI){
				//DisplayWifiState();
			}
		}};

	public void continuouslyTransmit(){
		handler.removeCallbacks(runnable); 
		System.out.println("exectuted removeCallbacks"); 
		runnable = new Runnable(){
			public void run(){
				System.out.println("checking how many times this is called"); 
				handler.postAtTime(new Runnable(){
					public void run(){
						
						HttpClient client = new DefaultHttpClient();
						List<NameValuePair> pairsN = new ArrayList<NameValuePair>();
						HttpPost postN = new HttpPost(address); 
						Random gen = new Random();
						
						//dimensions on map image on android

						//black space above map image
						int y_padding_top = 25;
						int y_padding_bottom = 25;
						int map_width = 480;
						int map_height = 724 - (y_padding_top + y_padding_bottom);

						//dimensions of map image in web app
						int web_width = 1279;
						int web_height = 1795;
						
						float x_scale = (float) web_width / map_width;
						float y_scale = (float) web_height / map_height;
						
						int x_mid = web_width / 2;
						int y_mid = web_height / 2;

						//TODO: This is to be obtained from Rohan
						PointWithRSSI actualPoint = lookup();
						Integer x_in = actualPoint.point.getX();
						Integer y_in = actualPoint.point.getY();
						
						Integer x_out;
						Integer y_out; 
						
						if(x_in >= 0){

							x_out = x_in + web_width / 2;


						} else{
							//less than middle
							x_out = web_width / 2 - (-1* x_in);

						}

						if(y_in >= y_mid){
							//past middle
							y_out = web_height / 2 - y_in;
						} else{
							//less than middle
							y_out = web_height / 2 + -1 * y_in;
						}

						x_out = (int) (x_out / x_scale);
						y_out = (int)(y_out / y_scale);

						Integer x_adjusted = x_out - 16;
						Integer y_adjusted = y_padding_top + y_out - 36;

						//remove previous
						if(tempView != null){
							mainLayout.removeView(tempView); 
						}

						//plot marker
						tempView = new MyCustomView(context, x_adjusted, y_adjusted); 

						//remove previous
						if(tempView != null){
							mainLayout.removeView(tempView); 
						}
						
						//plot marker
						tempView = new MyCustomView(context, x_adjusted, y_adjusted); 

						layoutParam = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
						layoutParam.addRule(RelativeLayout.ALIGN_PARENT_TOP);
						mainLayout.addView(tempView,layoutParam); 

						//calculate position for web app (Openlayers coordinate system)

						
						System.out.println("x "+ x_in + "y "+ y_in);
						pairsN.add(new BasicNameValuePair("x", x_in.toString()));
						pairsN.add(new BasicNameValuePair("y", y_in.toString()));
						pairsN.add(new BasicNameValuePair("device_id", device_id));
						System.out.println("Device id is:"+device_id);
						try {
							postN.setEntity(new UrlEncodedFormEntity(pairsN));
							pairsN.clear();

						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							HttpResponse response = client.execute(postN);
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if (!killMe){
							handler.postDelayed(this,4000);
						}
					}

				}, 0);
			}
		};
		new Thread(runnable).start(); 
	}

	public void onClose(){
		handler.removeCallbacks(runnable); 		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menuItemMap: startActivity(new Intent(this, Map.class));  
		break;
		case R.id.menuItemHelp:startActivity(new Intent(this, Help.class));    
		break;
		case R.id.menuItemAbout: startActivity(new Intent(this, About.class)); 
		break;
		}
		return true;
	}
	
	@SuppressWarnings("unused")
	public PointWithRSSI lookup () {
		//first fo a five times average to just get the values..
		DesiredFunctionality temp = new DesiredFunctionality();		//the co-ordinate part of this object is meaningless
		int i=0;
		while (i<1) {
			if (lookupAverage(temp)) {
				i++;
			}
		}

		ArrayList<PointWithRSSI> matches = new ArrayList<PointWithRSSI> ();
		ArrayList<PointWithRSSI> compare= df.getPoints();
		HashMap<String, AccessPoint> res = new HashMap<String, AccessPoint> ();
		Set<String> keys= temp.getCurrentPoint().WeightedaccessPoints.keySet();
		for (String s: keys) {
			if (temp.getCurrentPoint().getWeightedaccessPoints().get(s).getWeight()>=0) {
				res.put(s, temp.getCurrentPoint().getWeightedaccessPoints().get(s));
				//temp.getCurrentPoint().getWeightedaccessPoints().remove(s);
				//keys.remove(s);
			}
		}
		String as2 = temp.lookupOutput();
		temp.getCurrentPoint().WeightedaccessPoints = res;
		String as1 = temp.lookupOutput();
		keys = temp.getCurrentPoint().WeightedaccessPoints.keySet();
		//Set<String> keys = (Set<String>) temp.getPoints().get(0).getWeightedaccessPoints().keySet();		//this is from the real-time data
		//check containment using keys - never works
		/*
		for (PointWithRSSI p : compare) {
			containment = true;
			Set<String> c = p.getAccessPoints().keySet();
			//if (c.size() == keys.size()) {
			for (String s: keys) {
				if (!c.contains(s)) {
					containment = false;		//s is all the bssid's, which is unique for all the APs
				}
			}
			if (containment == true ) {		//containment probably not being used
				matches.add(p);
			}

			//}
		}	//end for
		
		*/
		int x;
		int y;

		PointWithRSSI minPoint = null;
		//dot product choose the minimum value
		if (matches.size() == 1) {		//never going to happen, it seems (in ENS at least)
			Point2D currPosition = matches.get(0).point;
			x = currPosition.getX();
			y = currPosition.getY();
		}
		else {
			//do some logic - try a simple difference for now
			/*
				float minDiff = Float.MAX_VALUE;
				int minIndex = 0;
				for (int i =0 ; i< matches.size(); i++) {
					float currDiff = 0;
					HashMap<String, AccessPoint> currValues = temp.getPoints().get(0).getWeightedaccessPoints();
					HashMap<String, AccessPoint> pValues = matches.get(i).WeightedaccessPoints;
					for (String s: currValues.keySet()) {
						currDiff += (currValues.get(s).getRssi() + pValues.get(s).getRssi());
					}
					if (currDiff < minDiff) {
						minDiff = currDiff;
						minIndex = i;
					}
				}
				//get the point at matches.i
				matches.get(minIndex).point.getX();
				matches.get(minIndex).point.getY();		*/

			float minDot = Float.MAX_VALUE;
			//apply logic from above else loop here also
			for (PointWithRSSI p : compare) {
				float currDot = 0;
				Set<String> c = p.getAccessPoints().keySet();
				//if (c.size() == keys.size()) {
				for (String s: keys) {
					if (c.contains(s)) {
						currDot += p.getAccessPoints().get(s)*p.getAccessPoints().get(s) ;
						currDot -= (Math.abs(temp.getCurrentPoint().getWeightedaccessPoints().get(s).getRssi()) * Math.abs(p.getAccessPoints().get(s)));		//add to the dot product
					}
					else {		//the penalty for non-containment
						currDot += (temp.getCurrentPoint().getWeightedaccessPoints().get(s).getRssi()*temp.getCurrentPoint().getWeightedaccessPoints().get(s).getRssi());
					}
				}
				currDot = Math.abs(currDot);
				if (currDot < minDot) {
					minDot = currDot;
					minPoint = p;
				}

				//}
			}	//end for

		}	//end else


		//TODO: find the smallest dot product
		/*
		if (matches.size() == 0) {
			float minDot = Float.MAX_VALUE;
			//apply logic from above else loop here also
			for (PointWithRSSI p : compare) {
				float currDot = 0;
				Set<String> c = p.getAccessPoints().keySet();
				//if (c.size() == keys.size()) {
				for (String s: keys) {
					if (c.contains(s)) {
						currDot += p.getAccessPoints().get(s)*p.getAccessPoints().get(s) ;
						currDot -= (Math.abs(temp.getCurrentPoint().getWeightedaccessPoints().get(s).getRssi()) * Math.abs(p.getAccessPoints().get(s)));		//add to the dot product
					}
				}
				if (currDot < minDot) {
					minDot = currDot;
					minPoint = p;
				}

				//}
			}	//end for
		}	//end if

		 */

//		System.out.println(minPoint.getPoint().getY() + minPoint.getPoint().getX());
		return minPoint;
	}
	
	public boolean lookupAverage(DesiredFunctionality curr) {
		ConnectivityManager myConnManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		NetworkInfo myNetworkInfo = myConnManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		WifiManager myWifiManager = (WifiManager)getSystemService(Context.WIFI_SERVICE);
		WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
		//myWifiManager.

		mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		receiverWifi = new WifiReceiver();
		registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
		mainWifi.startScan();

		boolean check = false;
		if (myNetworkInfo.isConnected()){
			//myWifiManager.startScan();			//breaks code for some reason
			//while (!myWifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals("android.net.wifi.SCAN_RESULTS"));


			List<ScanResult> accessPoints= myWifiManager.getScanResults();	//use this to get all access points' information.
			//StringBuffer str = new StringBuffer();
			for (ScanResult sr: accessPoints) {
				String test = sr.BSSID;
				if (curr.getCurrentPoint().getWeightedaccessPoints().containsKey(test)) {
					AccessPoint ap = curr.getCurrentPoint().getWeightedaccessPoints().get(test); 
					//float check = ap.getRssi()*(float)ap.getWeight()+(float)sr.level;
					float a = ap.getRssi();
					float c = (float)sr.level;
					if (a != c ){
						check = true;
					}
				}
				else {
					check = true;
				}
			}
			if (check == true) {
				for (ScanResult sr: accessPoints) {
					//str.append (sr.BSSID + '\t' + sr.level + '\n');
					String test = sr.BSSID;
					//curr.getCurrentPoint().getWeightedaccessPoints().clear();		//empty hash map -- probably not needed
					if (curr.getCurrentPoint().getWeightedaccessPoints().containsKey(test)) { //Current Point's HashMap contains current accesspoint's bssid
						AccessPoint ap = curr.getCurrentPoint().getWeightedaccessPoints().get(test); 
						//float check = ap.getRssi()*(float)ap.getWeight()+(float)sr.level;
						float a = ap.getRssi();
						float b = sr.level;
						ap.setRssi((ap.getRssi()*(float)ap.getWeight()+(float)sr.level)/((float)(ap.getWeight()+1.0)));		//add to running average
						ap.setWeight (ap.getWeight()+1);
					}
					else {
						curr.getCurrentPoint().getWeightedaccessPoints().put(sr.BSSID, new AccessPoint((float)sr.level, sr.BSSID, 1));
					}
				}
			}
		}
		return check;
	}
	
	public void readData(String readString) {
			
			/*
			FileInputStream fIn = openFileInput("samplefile.txt");
			InputStreamReader isr = new InputStreamReader(fIn);
			char[] inputBuffer = new char[100000];
			CharBuffer c = null;
			// Fill the Buffer with data from the file
			isr.read(inputBuffer);
			// Transform the chars to a String
			int index = 0;
			for (int i =0; i< inputBuffer.length; i++) {
				char test = inputBuffer[i];
				if (test == '\u0000') {
					index = i;
					break;
				}
			}
			
			*/
			//String readString = new String(inputBuffer, 0, index);
			
			//				if (readString.equals(df.Output())) {
			//					//
			//					System.out.println("It works!!");
			//				}

			df = DesiredFunctionality.Read(readString);		//lesser (absolute) decibel value means im closer to the access point..
			String s = df.newOutput();
			if (readString.equals(df.newOutput())) {
				System.out.println("It works !!");
			}
	}
	
	class WifiReceiver extends BroadcastReceiver {
		public void onReceive(Context c, Intent intent) {
			// mainText.setText(sb);
		}
	}

}
