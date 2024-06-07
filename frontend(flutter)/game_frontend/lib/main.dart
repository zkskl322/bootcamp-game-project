import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:game_frontend/Game/game_instance.dart';
import 'package:game_frontend/Game/lobby.dart';
import 'package:stomp_dart_client/stomp_dart_client.dart';

class Msg {
  final String GameId;
  final String content;
  final String uuid;

  Msg({required this.GameId, required this.content, required this.uuid});
}

// who_has_ball:                    0: no one, 1: player1, 2: player2
// player_control_player:           1. player_offender1, 2. player_offender2, 3. player_defender1, 4. player_defender2
// player_direction:                0: stop, 1: up, 2: down 3: left, 4: right


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

