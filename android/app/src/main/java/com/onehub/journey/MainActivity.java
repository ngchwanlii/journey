package com.onehub.journey;

import android.os.Bundle;

import com.onehub.flutter.plugin.asr.AsrPlugin;

import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;


public class MainActivity extends FlutterActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GeneratedPluginRegistrant.registerWith(this);
        registerSelfPlugin();
    }

    // register our own plugin
    private void registerSelfPlugin() {
        AsrPlugin.registerWith(registrarFor("com.onehub.flutter.plugin.asr.AsrPlugin"));
    }

}
