# Journey

## Introduction
- Journey is an travel application made in Flutter

## Mocking data / assets
-   Majority of the assets such as icon, image, url link is obtained from ```m.ctrip.com``` mobile version.
    The reason behind this is because by getting mock data / assets from ctrip server, we can greatly simplify the process to setup a real backend server / api gateway
    to serve these assets, hence, I can focus more on learning how to build app with Flutter framework.


##  Search function with Voice Recognition 
-   Setup Android Native SDK
    -   [Download Baidu's Android 离在线融合 SDK](https://ai.baidu.com/sdk#asr)
    -   Read [Baidu's Speech Recognition Documentation](http://ai.baidu.com/docs#/ASR-Android-SDK/top) for setup details   
    -   Download the sdk, and unzip it.
    -   Open your android folder under Android Editor mode, create new module and package name     
    -   Copy the ```sdk/core/libs/bdasr_V3_xxxx_xxx.jar``` to your Flutter android's 
        ```android/<your-plugin>/libs/bdasr_V3_xxxx_xxx.jar```             
    -   Copy the directory from ```sdk/core/src/main/jniLibs```to your Flutter androids's  ```android/src/main/<jniLibs>```
    -   Add ```-keep class com.baidu.speech.**{*;}``` to your Flutter android's ```proguard-rules.pro``` file
    -   Copy ```AndroidMainfest.xml``` settings from [Baidu's Speech Recognition Documentation](http://ai.baidu.com/docs#/ASR-Android-SDK/top)
        to your android's ```AndroidMainfest.xml```
    -   In Baidu's Cloud Baidu's Speech Recogniztion, create an application and follow the on screen instruction.
        -   After create the application, you can setup your APP_ID, APP_KEY, APP_SECRET in your ```AndroidMainfest.xml```. 
            Find the instruction in [Baidu's Speech Recognition Documentation](http://ai.baidu.com/docs#/ASR-Android-SDK/top)
            for details.
    -   To setup speech recognition service, copy paste 
        ```xml
        <service android:name="com.baidu.speech.VoiceRecognitionService" android:exported="false" />
        ``` 
        to your ```AndroidMainfest.xml``` within your 
        ```xml
        <application></application>
        ```  
    -   Configure dependencies between your ```app``` and ```your_speech_plugin``` in ```build.gradle``` (Module: app)
    -   Under ```android/asr_plugin/java/<your-package-name>```, implement your plugin function
    -   Setup ```ndk``` framework that flutter supports in ```app```'s ```build_gradle```
    -   Setup your dependencies between Android's & Flutter by configure ```asr_plugin```'s ```build.gradle``` accordingly. 
    -   **Remember** to configure your ```app```'s ```build_gradle``` to resolve dependencies conflicts between ```app``` & ```asr_plugin``` 
        & ```libflutter.so```.     
        - ```app``` depend on ```libflutter.so``` & ```asr_plugin```
        - ```asr_plugin``` depend on ```libflutter.so```  
       
-   Setup iOS Native SDK
    -   [Download Baidu's iOS 离在线融合 SDK](https://ai.baidu.com/sdk#asr)
    -   Read [Baidu's Speech Recognition Documentation](http://ai.baidu.com/docs#/ASR-iOS-SDK/top) for setup details
    -   Download the sdk, and unzip it.
    -   Open your xcode workspace under ```<your-project>/ios/Runnerxcworkspace```
    -   Create a new group such as ```plugin``` under ```Runner```    
    -   Copy the ```sdk/BSDClientLib``` to your Flutter iOS's ```plugin/ASRPlugin``` group directory you just created.with ```create groups``` mode.
    -   Add the ```sdk/BDSClientResource/ASR/BDSClientResources``` directory to your Flutter's iOS ```plugin/ASRPlugin``` group directory with the ```create folder references``` mode. 
    -   Add the ```sdk/BDSClientResource/ASR/BDSClientEASRResources``` directory to your Flutter's iOS ```plugin/ASRPlugin``` group directory with the ```create groups``` mode.
    -   Rename your     
    -   Setup / linked necessary frameworks and libraries in iOS's workspace ```Runner -> General``` tab
        - Add ```libz.1.2.5.tbd``` framework
        - Add ```libc++.tbd``` framework
        - Add ```CoreTelephony``` framework
        - Add ```libsqlite3.0.tbd``` framework
        - Add ```libiconv.2.4.0.tbd``` framework
        - Add ```libBaiduSpeechSDK.a``` found in downloaded ```sdk/BDSClientLib```
        - Adjust your ```Library Search Paths``` in ```Build Settings``` tab if library not found
    -   Setup microphone permission usage
        - Under ```Runner/Info.plist```, add key = ```Privacy - Microphone Usage Description```, value = ```<any description you like>```
    -   Under your ```Runner/plugin/ASRPlugin```, create files & implement your function. Ex: ```AsrManager.h```, ```AsrManager.m```, ```AsrPlugin.h``` & ```AsrPlugin.m```
        to integrate with Flutter's client through MessageChannel
    -   Register your ```AsrPlugin.h``` in ```Runner/App.delegate.m```         
    
-   Setup plugin & communication channel in Flutter
    -   Create a ```plugin``` directory under your Flutter's ```lib``` directory. 
    -   Implement ```asr_manager.dart``` to invoke ```MethodChannel``` and functions so that we can communicate with native Android & iOS

                    
      
    
        
     
        
        
          





