import 'package:flutter/material.dart';
import 'package:journey/model/common_model.dart';
import 'package:journey/util/navigator_util.dart';
import 'package:journey/widget/webview.dart';

class SubNav extends StatelessWidget {
  final List<CommonModel> subNavList;

  const SubNav({Key key, @required this.subNavList}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        color: Colors.white,
        borderRadius: BorderRadius.circular(6),
      ),
      child: Padding(
        padding: EdgeInsets.all(7),
        child: _items(context),
      ),
    );
  }

  _items(BuildContext context) {
    if (subNavList == null) return null;
    List<Widget> items = [];
    subNavList.forEach((model) => {items.add(_item(context, model))});

    int separate = (subNavList.length / 2 + 0.5).toInt();

    return Column(
      children: <Widget>[
        Row(
          children: items.sublist(0, separate),
          mainAxisAlignment: MainAxisAlignment.spaceBetween,
        ),
        Padding(
          padding: EdgeInsets.only(top: 10),
          child: Row(
            children: items.sublist(separate, subNavList.length),
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
          ),
        ),
      ],
    );
  }

  _item(BuildContext context, CommonModel model) {
    return Expanded(
      flex: 1,
      child: GestureDetector(
        onTap: () {
          NavigatorUtil.push(
              context,
              WebView(
                url: model.url,
                statusBarColor: model.statusBarColor,
                hideAppBar: model.hideAppBar,
              ));
        },
        child: Column(
          children: <Widget>[
            Image.network(model.icon, width: 18, height: 18),
            Padding(
              padding: EdgeInsets.only(top: 3),
              child: Text(
                model.title,
                style: TextStyle(fontSize: 12),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
