import 'package:flutter/material.dart';
import 'package:flutter_app/main2/storeconnector_weight.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';

import 'CountState.dart';

class FirstPage extends StatelessWidget {
  String title;
  final Store<CountState> store;

  FirstPage(this.title, this.store) : super();

  @override
  Widget build(BuildContext context) {
    return StoreProvider<CountState>(
      store: store,
      child: MaterialApp(
          title: "Zero",
          theme: new ThemeData.light(),
          home: Container(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: <Widget>[
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Text(
                    title,
                    maxLines: 1,
                    textDirection: TextDirection.ltr,
                    style: TextStyle(
                        color: Colors.deepOrange,
                        fontSize: 30,
                        fontWeight: FontWeight.bold),
                  ),
                ),
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: StoreConnectorTextWidget(),
                ),
                StoreConnectorAddButtonWidget("加一", 1),
                StoreConnectorAddButtonWidget("加二", 2)
              ],
            ),
          )),
    );
  }
}
