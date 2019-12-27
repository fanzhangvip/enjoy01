import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Container();
  }
}

class ParentWidgetC extends StatefulWidget {
  @override
  _ParentWidgetCState createState() {
    // TODO: implement createState
    return new _ParentWidgetCState();
  }
}

class _ParentWidgetCState extends State<ParentWidgetC> {
  bool _active = false;

  void _handleTapboxChanged(bool newValue) {
    setState(() {
      _active = newValue;
    });
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new TabBobC(
      active: _active,
      onChange: _handleTapboxChanged,
    );
  }
}

class TabBobC extends StatefulWidget {
  final bool active;

  final ValueChanged<bool> onChange;

  TabBobC({Key key, this.active: false, @required this.onChange});

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return new _TabBoxCState();
  }
}

class _TabBoxCState extends State<TabBobC> {
  bool _highlight = false;

  void _handleTabDown(TapDownDetails details) {
    setState(() {
      _highlight = true;
    });
  }

  void _handleTapUp(TapUpDetails details) {
    setState(() {
      _highlight = false;
    });
  }

  void _handleTapCancel() {
    setState(() {
      _highlight = false;
    });
  }

  void _handleTap() {
    widget.onChange(!widget.active);
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return new GestureDetector(
      onTapDown: _handleTabDown,
      onTapUp: _handleTapUp,
      onTap: _handleTap,
      onTapCancel: _handleTapCancel,
      child: new Container(
        child: new Center(
          child: Column(
            children: <Widget>[
              new Text(
                widget.active
                    ? 'Active1111111111111111111111111112321312312'
                    : 'InactiveActive1111111111111111111111111112321312312',
                style: new TextStyle(
                    inherit: false,
                    fontSize: 32.0,
                    color: Colors.white,
                    decoration: TextDecoration.underline,
                    decorationStyle: TextDecorationStyle.dashed),
                textAlign: TextAlign.left,
                maxLines: 1,
                overflow: TextOverflow.ellipsis,
                textScaleFactor: 1.5,
                textDirection: TextDirection.ltr,
              ),
              Text.rich(
                TextSpan(
                  children: [
                    TextSpan(
                      text: 'Home:',
                    ),
                    TextSpan(
                      text: "www.baidu.com",
                      style: TextStyle(color: Colors.blue),
//                       recognizer: _
                    )
                  ],
                ),
                textDirection: TextDirection.ltr,
              ),
            new Directionality(
                textDirection: TextDirection.ltr,
                child:RaisedButton(
                  textTheme: ButtonTextTheme.accent,
                  child: Text(
                    'normal',
                    textDirection: TextDirection.ltr,
                  ),
                  onPressed: () {},
                )
            ),

            ],
            crossAxisAlignment: CrossAxisAlignment.center,
            textDirection: TextDirection.ltr,
            mainAxisAlignment: MainAxisAlignment.center,
          ),
//          child: new Text(
//
//          ),
        ),
        width: 200.0,
        height: 200.0,
        decoration: new BoxDecoration(
          color: widget.active ? Colors.lightGreen[700] : Colors.grey[700],
          border: _highlight
              ? new Border.all(color: Colors.teal[700], width: 10.0)
              : null,
        ),
      ),
    );
  }
}
