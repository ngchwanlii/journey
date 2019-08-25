package com.onehub.flutter.plugin.asr;

/**
 * The returned params between SDK is in RecogEventAdapter
 */

public interface OnAsrListener {

    /**
     * CALLBACK_EVENT_ASR_READY
     * After ASR_START voice input, invoke this method, engine is ready
     */
    void onAsrReady();

    /**
     * CALLBACK_EVENT_ASR_BEGIN
     * After onAsrReady, check user and see if user start speaking
     */
    void onAsrBegin();

    /**
     * CALLBACK_EVENT_ASR_END
     * Check until user stop speaking, or invoke after ASR_STOP event
     */
    void onAsrEnd();

    /**
     * CALLBACK_EVENT_ASR_PARTIAL resultType=partial_result
     * After onAsrBegin, return temporary result while user speaking
     *
     * @param results     possible return many result, read the first one
     * @param recogResult complete result
     */
    void onAsrPartialResult(String[] results, RecogResult recogResult);

    /**
     * Online voice's semantic result
     * <p>
     * CALLBACK_EVENT_ASR_PARTIAL resultType=nlu_result
     *
     * @param nluResult
     */
    void onAsrOnlineNluResult(String nluResult);

    /**
     * CALLBACK_EVENT_ASR_PARTIAL resultType=final_result
     * The final voice recognisation result
     *
     * @param results     possible return many result, read the first one
     * @param recogResult complete result
     */
    void onAsrFinalResult(String[] results, RecogResult recogResult);

    /**
     * CALLBACK_EVENT_ASR_FINISH
     *
     * @param recogResult complete result
     */
    void onAsrFinish(RecogResult recogResult);

    /**
     * CALLBACK_EVENT_ASR_FINISH error!=0
     *
     * @param errorCode
     * @param subErrorCode
     * @param descMessage
     * @param recogResult
     */
    void onAsrFinishError(int errorCode, int subErrorCode, String descMessage, RecogResult recogResult);

    /**
     * Long voice recognisation finish
     */
    void onAsrLongFinish();

    /**
     * CALLBACK_EVENT_ASR_VOLUME
     * Adjust volume
     *
     * @param volumePercent in 0-100 %
     * @param volume
     */
    void onAsrVolume(int volumePercent, int volume);

    /**
     * CALLBACK_EVENT_ASR_AUDIO
     *
     * @param data   pcm format，16bits 16000 sample rate
     * @param offset
     * @param length
     */
    void onAsrAudio(byte[] data, int offset, int length);

    /**
     * CALLBACK_EVENT_ASR_EXIT
     * Engine complete entire voice recognisation, idle
     */
    void onAsrExit();

    /**
     * CALLBACK_EVENT_ASR_LOADED
     * Offline resource loaded successfully
     */
    void onOfflineLoaded();

    /**
     * CALLBACK_EVENT_ASR_UNLOADED
     * Offline resource unloaded successfully
     */
    void onOfflineUnLoaded();
}

