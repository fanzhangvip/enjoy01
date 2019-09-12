import 'package:redux/redux.dart';

import 'CountState.dart';

enum Actions {
  increment,
  decrement
}

class Action {
  final Actions type;
  Action(this.type);
}

class IncreAction extends Action{
  int value;
  IncreAction(this.value):super(Actions.increment);
}

CountState increReducer(CountState preState, dynamic action) {

  switch(action.type) {
    case Actions.increment:
      return CountState(preState.count + action.value);
    default:
      return preState;

  }

}

class DecreAction extends Action{
  int value;
  DecreAction(this.value):super(Actions.decrement);
}

CountState decreReducer(CountState preState, dynamic action) {

  switch(action.type) {
    case Actions.decrement:
      return CountState(preState.count + action.value);
    default:
      return preState;

  }

}
final reducers = combineReducers([
  increReducer,
  decreReducer
]);
