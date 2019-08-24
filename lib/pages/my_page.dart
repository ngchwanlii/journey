import 'package:flutter/material.dart';
import 'package:journey/widget/webview.dart';

const MY_URL = 'https://m.ctrip.com/webapp/myctrip/';

class MyPage extends StatefulWidget {
  @override
  _MyPageState createState() => _MyPageState();
}

class _MyPageState extends State<MyPage> {

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        body: Center(
          child: WebView(
            url: MY_URL,
            hideAppBar: true,
            backForbid: true,
            statusBarColor: '4c5bca',
          ),
        )
    );
  }
}
