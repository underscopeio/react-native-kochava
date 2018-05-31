package com.aquto.rn.kochava;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.lang.Object;
import java.lang.Exception;
import org.json.JSONObject;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.kochava.android.tracker.Feature;

class RNKochavaModule extends ReactContextBaseJavaModule {
    private Context context;
    private static Feature kTracker;

    public RNKochavaModule(ReactApplicationContext reactContext, String appId, Boolean enableDebug) {
        super(reactContext);

        if (enableDebug) {
            Feature.enableDebug(true);
        }

        HashMap<String, Object> datamap = new HashMap<String, Object>();
        datamap.put(Feature.INPUTITEMS.KOCHAVA_APP_ID , appId);
        datamap.put(Feature.INPUTITEMS.REQUEST_ATTRIBUTION, true);

        kTracker = new Feature(reactContext , datamap);
    }


    @Override
    public String getName() {
        return "RNKochava";
    }

    @ReactMethod
    public void identityLink(ReadableMap userInfo, Promise promise) {
        try {
            Map<String, String> data = toMap(userInfo);
            kTracker.linkIdentity(data);

            promise.resolve("Done");
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    @ReactMethod
    public void sendEvent(String name, ReadableMap eventInfo, Promise promise) {
        try {
            Map<String, String> data = toMap(eventInfo);
            JSONObject json = new JSONObject(data);
            kTracker.event(name, json.toString());

            promise.resolve("Done");
        } catch (Exception e) {
            promise.reject(e);
        }
    }

    private HashMap<String, String> toMap(ReadableMap map) {
      ReadableMapKeySetIterator iterator = map.keySetIterator();
      HashMap<String, String> hashMap = new HashMap<>();

      while (iterator.hasNextKey()) {
        String key = iterator.nextKey();
        hashMap.put(key, map.getString(key));
      }
      return hashMap;
    }
}
