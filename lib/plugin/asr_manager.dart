import 'package:flutter/services.dart';

class AsrManager {
  static const MethodChannel _channel = const MethodChannel('asr_plugin');

  // start voice recording
  static Future<String> start({Map params}) async {
    print("parms: ------------ $params");
    return await _channel.invokeMethod('start', params ?? {});
  }

  // stop voice recording
  static Future<String> stop() async {
    return await _channel.invokeMethod('stop');
  }

  // cancel voice recording
  static Future<String> cancel() async {
    return await _channel.invokeMethod('cancel');
  }
}
