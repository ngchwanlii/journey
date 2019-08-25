# Journey

## Introduction
- Journey is an online travel application
- The purpose of building this application is to learn how to use Flutter framework.
For simplicity, I have choose to clone one of the famous OTA in China - ctrip.com. Converting 
their mobile h5 UI to Flutter UI

## Demonstration
<img src="https://github.com/ngchwanlii/journey/blob/master/demo_images/home.jpg" width="200"/>
<img src="https://github.com/ngchwanlii/journey/blob/master/demo_images/search_input.jpg" width="200"/>
<img src="https://github.com/ngchwanlii/journey/blob/master/demo_images/search_speak.jpg" width="200"/>
<img src="https://github.com/ngchwanlii/journey/blob/master/demo_images/search_web.jpg" width="200"/>
<img src="https://github.com/ngchwanlii/journey/blob/master/demo_images/web1.jpg" width="200"/>
<img src="https://github.com/ngchwanlii/journey/blob/master/demo_images/travel.jpg" width="200"/>
<img src="https://github.com/ngchwanlii/journey/blob/master/demo_images/travel_detail.jpg" width="200"/>
<img src="https://github.com/ngchwanlii/journey/blob/master/demo_images/my.jpg" width="200"/>

## Run the apps
1. First, follow this [tutorial](https://flutter.dev/docs/get-started/install) to setup Flutter
2. Clone [journey](https://github.com/ngchwanlii/journey), then ```cd``` to the project
3. In Terminal, run ```flutter packages get```
4. Start your app by running ```flutter run```


## Implemented UI
- [x] Designed for iOS & Android UI
    - [x] Home Page (首页)    
    - [x] Search Page (搜索)
    - [x] Travel Page (旅拍)
    - [x] My Page (我的)

## Implemented functionalities & pattern
- [x] Caching images locally
- [x] Used staggered grid layout for travel page
- [x] Getting data/assets from GET / POST request from ctrip h5 mobile server
- [x] Lazy loading for more travel pages when user slide up
- [x] Handle refresh when user slide down
- [x] Clicking on specific icon/image/post can link back to h5 mobile side and displayed as in Flutter UI using WebView
- [x] Implemented Baidu's voice recognition for search
- [x] Implement Baidu's voice recognition for both iOS & Android by integrating native and flutter code using Message Channel
- [x] User can manually search using keyword
            
##  Note on implement Search function with Voice Recognition 
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

                    
# App Deployment
- Android
    - [Preparing an Android app for release](https://flutter.dev/docs/deployment/android)
    - If there is a ```Gradle task assembleRelease failed with exit code 1``` issues, 
        in terminal type ```flutter clean``` and rebuild your apk ```flutter build apk```.      
- iOS
    - [Preparing an iOS app for release](https://flutter.dev/docs/deployment/ios)    
        
     
        
        
          





