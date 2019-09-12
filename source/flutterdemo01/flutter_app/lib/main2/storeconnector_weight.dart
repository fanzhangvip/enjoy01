import 'package:flutter/material.dart';
import 'package:flutter_redux/flutter_redux.dart';
import 'package:redux/redux.dart';

import 'CountState.dart';
import 'action_reducer.dart';

class StoreConnectorTextWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    ///第六步 获取store的state
    return StoreConnector<CountState, int>(
      converter: (store) => store.state.count,
      builder: (context, count) {
        return Text(
          "Count: $count",
          textDirection: TextDirection.ltr,
        );
      },
    );
  }
}

class StoreConnectorAddButtonWidget extends StatelessWidget {
  String text;
  int value;
  StoreConnectorAddButtonWidget(this.text, this.value) : super();

  @override
  Widget build(BuildContext context) {
    return StoreConnector<CountState, VoidCallback>(
      converter: (Store<CountState> store) {
        return () => store.dispatch(IncreAction(value));
      },
      builder: (context, callback) {
        return RaisedButton(
          child: Text(text, textDirection: TextDirection.ltr),
          onPressed: callback,
        );
      },
    );
  }
}

class DecreStoreConnectorButtonWidget extends StatelessWidget {
  String text;
  int value;
  DecreStoreConnectorButtonWidget(this.text, this.value) : super();

  @override
  Widget build(BuildContext context) {
    return StoreConnector<CountState, VoidCallback>(
      builder: (store, callback) {
        return Center(
          child: RaisedButton(
            child: Text(
              text,
              textDirection: TextDirection.ltr,
            ),
            onPressed: callback,
          ),
        );
      },
      converter: (Store<CountState> store) {
        return () => store.dispatch(DecreAction(value));
      },
    );
  }
}
