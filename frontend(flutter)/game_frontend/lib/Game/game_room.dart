import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:game_frontend/Game/Painter.dart';
import 'package:game_frontend/Game/game_instance.dart';
import 'package:stomp_dart_client/stomp_dart_client.dart';

class GameRoomPage extends StatefulWidget {
  final String GameId;
  final String myUuid;

  GameRoomPage({required this.GameId, required this.myUuid});

  @override
  State<GameRoomPage> createState() => _GamePageState();
}

class _GamePageState extends State<GameRoomPage> {
  StompClient? stompClient;
  final TextEditingController _textController = TextEditingController();
  late final FocusNode _focusNode;
  final List<Game> gameList = [];
<<<<<<< HEAD
  final socketUrl = 'http://192.168.3.6:8080/game';
=======
  final socketUrl = 'http://192.168.3.4:8080/game';
>>>>>>> main

  void onConnect(StompFrame frame) {
    stompClient!.subscribe(
      destination: '/topic/game/${widget.GameId}',
      callback: (frame) {
        if (frame.body != null) {
          Map<String, dynamic> obj = json.decode(frame.body!);
          Game game = Game(
            // game ------------------------------ //
            gameId: obj['gameId'],
            gameStatus: obj['gameStatus'],
            score1: obj['score1'],
            score2: obj['score2'],
            who_has_ball: obj['who_has_ball'],
            // time ------------------------------ //
            // start_time: obj['start_time'],
            time: obj['time'],
            max_time: obj['max_time'],
            isFirstHalf: obj['isFirstHalf'],
            ball_direction_x: obj['ball_direction_x'],
            ball_direction_y: obj['ball_direction_y'],
            ball_x: obj['ball_x'],
            ball_y: obj['ball_y'],
            // player1 ------------------------------ //
            player1_control_player: obj['player1_control_player'],
            player1_x: obj['player1_x'],
            player1_y: obj['player1_y'],
            player1_direction: obj['player1_direction'],
            player1_possession: obj['player1_possession'],
            player1_players: GameSoccerTeam.fromJson(obj['player1_players']),
            // player2 ------------------------------ //
            player2_control_player: obj['player2_control_player'],
            player2_x: obj['player2_x'],
            player2_y: obj['player2_y'],
            player2_direction: obj['player2_direction'],
            player2_possession: obj['player2_possession'],
            player2_players: GameSoccerTeam.fromJson(obj['player2_players']),
            // -------------------------------------- //
          );

          setState(() {
            gameList.add(game);
          });
        }
      },
    );
  }

  void sendMessage() {
    if (_textController.text.isNotEmpty) {
      stompClient!.send(
        destination: '/app/action',
        body: json.encode({
          'gameId': widget.GameId,
          'playerId': widget.myUuid,
          'action': _textController.text,
          'isDone': 'false',
        }),
      );
      _textController.clear();
    }
  }

  void sendStartMessage() {
    _textController.text = 'START_GAME';
    sendMessage();
    _textController.clear();
  }

  @override
  void initState() {
    super.initState();
    _focusNode = FocusNode();
    if (stompClient == null) {
      stompClient = StompClient(
        config: StompConfig.sockJS(
          url: socketUrl,
          onConnect: onConnect,
          onWebSocketError: (dynamic error) => print(error.toString()),
        ),
      );
      stompClient!.activate();
    }
  }

  @override
  void dispose() {
    stompClient?.deactivate();
    _textController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    final Size screenSize = MediaQuery.of(context).size;

    return Scaffold(
      appBar: AppBar(
        title: Text('Game : ${widget.GameId}'),
      ),
      body: KeyboardListener(
        focusNode: _focusNode,
        autofocus: true,
        onKeyEvent: (e) async {
          if (e is KeyUpEvent) {
            switch (e.logicalKey) {
              case LogicalKeyboardKey.keyD:
                _textController.text = 'KEY_D';
                sendMessage();
                _textController.clear();
                break;
              case LogicalKeyboardKey.keyS:
                _textController.text = 'KEY_S';
                sendMessage();
                _textController.clear();
                break;
              case LogicalKeyboardKey.keyA:
                _textController.text = 'KEY_A';
                sendMessage();
                _textController.clear();
                break;
            }
          } else {
            switch (e.logicalKey) {
              case LogicalKeyboardKey.arrowDown:
                _textController.text = 'DOWN';
                sendMessage();
                _textController.clear();
                break;
              case LogicalKeyboardKey.arrowUp:
                _textController.text = 'UP';
                sendMessage();
                _textController.clear();
                break;
              case LogicalKeyboardKey.arrowLeft:
                _textController.text = 'LEFT';
                sendMessage();
                _textController.clear();
                break;
              case LogicalKeyboardKey.arrowRight:
                _textController.text = 'RIGHT';
                sendMessage();
                _textController.clear();
                break;
              default:
                break;
            }
          }
        },
        child: Center(
          child: Column(
            children: [
              Text(
                "Time: ${gameList.isNotEmpty ? gameList.last.time.floor() : 0} / ${gameList.isNotEmpty ? gameList.last.max_time : 0}",
                style: const TextStyle(fontSize: 20),
              ),
              Text(
                "Score: ${gameList.isNotEmpty ? gameList.last.score1 : 0} : ${gameList.isNotEmpty ? gameList.last.score2 : 0}",
                style: const TextStyle(fontSize: 20),
              ),
              ElevatedButton(
                onPressed: () => {
                  sendStartMessage(),
                },
                child: const Icon(Icons.start),
              ),
              ElevatedButton(
                onPressed: () => {
                  _textController.text = 'END_GAME',
                  sendMessage(),
                  _textController.clear(),
                },
                child: const Icon(Icons.stop),
              ),
              SizedBox(
                width: 1300,
                height: 800,
                child: Row(
                  children: [
                    SizedBox(
                        width: 100,
                        height: 700,
                        child: CustomPaint(
                          painter: MyPainter2(),
                        )),
                    Container(
                      width: 1100,
                      height: 700,
                      decoration: const BoxDecoration(),
                      child: CustomPaint(
                        painter: MyPainter(gameList),
                      ),
                    ),
                    SizedBox(
                        width: 100,
                        height: 700,
                        child: CustomPaint(
                          painter: MyPainter3(),
                        )),
                  ],
                ),
              )
            ],
          ),
        ),
      ),
    );
  }
}
