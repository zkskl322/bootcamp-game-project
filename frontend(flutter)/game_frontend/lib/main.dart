import 'package:flutter/material.dart';
import 'package:game_frontend/Game/lobby.dart';

class Msg {
  final String GameId;
  final String content;
  final String uuid;

  Msg({required this.GameId, required this.content, required this.uuid});
}

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Soccer Game',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: LobbyPage(),
    );
  }
}

