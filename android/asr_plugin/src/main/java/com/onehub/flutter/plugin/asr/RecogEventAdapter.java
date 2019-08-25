package com.onehub.flutter.plugin.asr;

import android.util.Log;

import com.baidu.speech.EventListener;
import com.baidu.speech.asr.SpeechConstant;

import org.json.JSONException;
import org.json.JSONObject;

public class RecogEventAdapter implements EventListener {
    private OnAsrListener listener;

    private static final String TAG = "RecogEventAdapter";

    public RecogEventAdapter(OnAsrListener listener) {
        this.listener = listener;
    }


    // Invoke callback
    @Override
    public void onEvent(String name, String params, byte[] data, int offset, int length) {
        String currentJson = params;
        String logMessage = "name:" + name + "; params:" + params;

        // logcat search RecogEventAdapter
        Log.i(TAG, logMessage);
        if (false) { // can debug
            return;
        }
        if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_LOADED)) {
            listener.onOfflineLoaded();
        } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_UNLOADED)) {
            listener.onOfflineUnLoaded();
        } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_READY)) {
            // engine is ready, can start voice recording
            listener.onAsrReady();
        } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_BEGIN)) {
            // check and see if user is start speaking
            listener.onAsrBegin();

        } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_END)) {
            // check and see if user stop speaking
            listener.onAsrEnd();

        } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL)) {
            RecogResult recogResult = RecogResult.parseJson(params);
            // temporary recognize result, long voice recording mode will need to get this result
            String[] results = recogResult.getResultsRecognition();
            if (recogResult.isFinalResult()) {
                listener.onAsrFinalResult(results, recogResult);
            } else if (recogResult.isPartialResult()) {
                listener.onAsrPartialResult(results, recogResult);
            } else if (recogResult.isNluResult()) {
                listener.onAsrOnlineNluResult(new String(data, offset, length));
            }

        } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_FINISH)) {
            // voice recognization finish, the final result will probably has error, so we need to check it
            RecogResult recogResult = RecogResult.parseJson(params);
            if (recogResult.hasError()) {
                int errorCode = recogResult.getError();
                int subErrorCode = recogResult.getSubError();
                Log.e(TAG, "asr error:" + params);
                listener.onAsrFinishError(errorCode, subErrorCode, recogResult.getDesc(), recogResult);
            } else {
                listener.onAsrFinish(recogResult);
            }
        } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_LONG_SPEECH)) { // long voice recording
            listener.onAsrLongFinish();
        } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_EXIT)) {
            listener.onAsrExit();
        } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_VOLUME)) {
            // Logger.info(TAG, "asr volume event:" + params);
            Volume vol = parseVolumeJson(params);
            listener.onAsrVolume(vol.volumePercent, vol.volume);
        } else if (name.equals(SpeechConstant.CALLBACK_EVENT_ASR_AUDIO)) {
            if (data.length != length) {
                Log.e(TAG, "internal error: asr.audio callback data length is not equal to length param");
            }
            listener.onAsrAudio(data, offset, length);
        }
    }

    private Volume parseVolumeJson(String jsonStr) {
        Volume vol = new Volume();
        vol.origalJson = jsonStr;
        try {
            JSONObject json = new JSONObject(jsonStr);
            vol.volumePercent = json.getInt("volume-percent");
            vol.volume = json.getInt("volume");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return vol;
    }

    private class Volume {
        private int volumePercent = -1;
        private int volume = -1;
        private String origalJson;
    }
}
