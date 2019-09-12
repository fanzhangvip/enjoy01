import 'package:flutter/material.dart';
import 'package:redux/redux.dart';

import 'CountState.dart';
import 'action_reducer.dart';
import 'first_page.dart';

void main() {
  final store = Store<CountState>(reducers,
  initialState: CountState.initState());
  runApp(FirstPage("111",store));
}


