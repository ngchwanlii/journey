import 'dart:async';
import 'dart:convert';

import 'package:http/http.dart' as http;
import 'package:journey/model/search_model.dart';

class SearchDao {
  static Future<SearchModel> fetch(String url, String text) async {
    final response = await http.get(url);

    if (response.statusCode == 200) {
      Utf8Decoder utf8decoder = Utf8Decoder();
      var result = json.decode(utf8decoder.convert(response.bodyBytes));
      SearchModel model = SearchModel.fromJson(result);
      model.keyword = text;
      return model;
    } else {
      throw Exception('Failed to load search_page.json');
    }
  }
}
