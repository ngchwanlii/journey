package com.onehub.flutter.plugin.asr;

import android.content.Context;
import android.util.Log;

import com.baidu.speech.EventManager;
import com.baidu.speech.EventManagerFactory;
import com.baidu.speech.asr.SpeechConstant;

import org.json.JSONObject;

import java.util.Map;

public class AsrManager {
    private static final String TAG = "AsrManager";
    /**
     * SDK core EventManager type
     */
    private EventManager asr;

    // SDK core event invoke type, use to implement our own voice recognise logic
    private RecogEventAdapter eventListener;

    private static volatile boolean isInited = false;

    /**
     * Init & provide Context & RecogEventAdapter for EventManagerFactory
     *
     * @param context
     * @param listener callback to voice recognise status and result
     */
    public AsrManager(Context context, OnAsrListener listener) {
        if (isInited) {
            Log.e(TAG, "Haven't invoke release()，please create a new object");
            throw new RuntimeException("Haven't invoke release()，please create a new object");
        }
        isInited = true;
        // SDK integration process: init asr' EventManager，possible get multiple object, we only need to choose one to use
        asr = EventManagerFactory.create(context, "asr");
        // SDK integration process: setup callback event， voice recognisation engine
        // will invoke this object to inform important status and result
        this.eventListener = new RecogEventAdapter(listener);
        asr.registerListener(eventListener);
    }

    /**
     * @param params
     */
    public void start(Map<String, Object> params) {
        // SDK integration process: receive params
        if (!isInited) {
            throw new RuntimeException("release() was called");
        }
        String json = new JSONObject(params).toString();
        Log.i(TAG + ".Debug", "on start params: " + json);
        asr.send(SpeechConstant.ASR_START, json, null, 0, 0);
    }


    /**
     * Stop voice recording and waiting for voice recognization result
     */
    public void stop() {
        Log.i(TAG, "stop voice recording");
        // SDK integration process: stop voice recording
        if (!isInited) {
            throw new RuntimeException("release() was called");
        }
        asr.send(SpeechConstant.ASR_STOP, "{}", null, 0, 0);
    }

    /**
     * Cancel voice recognization process, if cancel, we won't return any result
     *
     */
    public void cancel() {
        Log.i(TAG, "cancel voice recording");
        if (!isInited) {
            throw new RuntimeException("release() was called");
        }
        // SDK integration process: cancel voice recording
        asr.send(SpeechConstant.ASR_CANCEL, "{}", null, 0, 0);
    }

    public void release() {
        if (asr == null) {
            return;
        }
        cancel();
        // SDK integration process: unregister event listener
        asr.unregisterListener(eventListener);
        asr = null;
        isInited = false;
    }
}