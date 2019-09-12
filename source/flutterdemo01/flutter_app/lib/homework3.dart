import 'dart:async';

class EventBus {
  static EventBus _instanse;
  StreamController _streamController;

  factory EventBus.getDefault() {
    return _instanse ??= EventBus._instanse;
  }

  EventBus._internal() {
    _streamController = StreamController.broadcast();
  }

  StreamSubscription<T> regist<T>(void onData(T event)) {
    if (T is dynamic) {
      return _streamController.stream.listen(onData);
    } else {
      Stream<T> stream =
          _streamController.stream.where((type) => type is T).cast<T>();
      return stream.listen(onData);
    }
  }

  void post(event) {
    _streamController.add(event);
  }

  void destory() {
    _streamController.close();
  }
}
