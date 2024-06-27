import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:game_frontend/Game/Painter.dart';
import 'package:game_frontend/Game/game_instance.dart';
import 'package:game_frontend/backup/ingame_result.dart';
import 'package:game_frontend/backup/ingame_win.dart';
import 'package:stomp_dart_client/stomp_dart_client.dart';

class GameRoomPage extends StatefulWidget {
  final int GameId;
  final String myUuid;

  GameRoomPage({required this.GameId, required this.myUuid});

  @override
  State<GameRoomPage> createState() => _GamePageState();
}

class Msg {
  final String content;
  final String uuid;

  Msg({required this.content, required this.uuid});
}

class _GamePageState extends State<GameRoomPage> {
  StompClient? stompClient;
  final TextEditingController _textController = TextEditingController();
  late final FocusNode _focusNode;
  final List<Game> gameList = [];
  final socketUrl = 'http://localhost:8080/game';


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
            player1Nickname: obj['player1Nickname'],
            player2Nickname: obj['player2Nickname'],
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

          if (game.gameStatus == 'END') {
            Navigator.push(
              context,
              MaterialPageRoute(
                builder: (context) => GameResultPage(
                  playerId: widget.myUuid,
                  gameId: int.parse(game.gameId),
                  score1: game.score1,
                  score2: game.score2,
                ),
              ),
            );
          }
        }
      },
    );
  }

  void sendAction() {
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
    sendAction();
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
              case LogicalKeyboardKey.keyW:
                _textController.text = 'KEY_W';
                sendAction();
                _textController.clear();
                break;

              case LogicalKeyboardKey.keyD:
                _textController.text = 'KEY_D';
                sendAction();
                _textController.clear();
                break;
              case LogicalKeyboardKey.keyS:
                _textController.text = 'KEY_S';
                sendAction();
                _textController.clear();
                break;
              case LogicalKeyboardKey.keyA:
                _textController.text = 'KEY_A';
                sendAction();
                _textController.clear();
                break;
            }
          } else {
            switch (e.logicalKey) {
              case LogicalKeyboardKey.arrowDown:
                _textController.text = 'DOWN';
                sendAction();
                _textController.clear();
                break;
              case LogicalKeyboardKey.arrowUp:
                _textController.text = 'UP';
                sendAction();
                _textController.clear();
                break;
              case LogicalKeyboardKey.arrowLeft:
                _textController.text = 'LEFT';
                sendAction();
                _textController.clear();
                break;
              case LogicalKeyboardKey.arrowRight:
                _textController.text = 'RIGHT';
                sendAction();
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
              const SizedBox(height: 100),
              
              Row(
                children: [

                    // 300 + 100 + 10 + 100 + 300 = 810
                    SizedBox(width: screenSize.width/2 - 410),
                    Container(
                      width: 300,
                      height: 100,
                      decoration: const BoxDecoration(
                        color: Color.fromARGB(228, 13, 29, 99),
                      ),
                      child:Center(
                        child: 
                          Text(
                            gameList.isNotEmpty ? gameList.last.player1Nickname : 'Player1',
                            style: const TextStyle(
                              fontSize: 40,
                              color: Colors.white,
                              fontFamily: 'Press Start 2P',
                            ),
                          ),
                      ),
                    ),
                    Container(
                      width: 100,
                      height: 100,
                      decoration: const BoxDecoration(
                        color: Color.fromARGB(255, 109, 255, 117),
                      ),
                      child:Center(
                        child: 
                          Text(
                            gameList.isNotEmpty ? gameList.last.score1.toString() : '0',
                            style: const TextStyle(
                              fontSize: 40,
                              color: Colors.white,
                              fontFamily: 'Press Start 2P',
                            ),
                          ),
                      ),
                    ),
                    const SizedBox(width: 10),
                    Container(
                      width: 100,
                      height: 100,
                      decoration: const BoxDecoration(
                        color: Color.fromARGB(255, 109, 255, 117),
                      ),
                      child:Center(
                        child: 
                          Text(
                            gameList.isNotEmpty ? gameList.last.score2.toString() : '0',
                            style: const TextStyle(
                              fontSize: 40,
                              color: Colors.white,
                              fontFamily: 'Press Start 2P',
                            ),
                          ),
                      ),
                    ),
                    Container(
                      width: 300,
                      height: 100,
                      decoration: const BoxDecoration(
                        color: Color.fromARGB(228, 13, 29, 99),
                      ),
                      child:Center(
                        child: 
                          Text(
                            gameList.isNotEmpty ? gameList.last.player2Nickname : 'Player2',
                            style: const TextStyle(
                              fontSize: 40,
                              color: Colors.white,
                              fontFamily: 'Press Start 2P',
                            ),
                          ),
                      ),
                    ),
                    const SizedBox(width: 10),
                    Container(
                      width: 100,
                      height: 100,
                      decoration: const BoxDecoration(
                        color: Color.fromARGB(255, 228, 135, 28),
                      ),
                      child:Center(
                        child: 
                          Text(
                            gameList.isNotEmpty ? gameList.last.time.floor().toString() : '0',
                            style: const TextStyle(
                              fontSize: 40,
                              color: Colors.white,
                              fontFamily: 'Press Start 2P',
                            ),
                          ),
                      ),
                    ),
                    const SizedBox(width: 30),
                    Container(
                      width: 100,
                      height: 100,
                      child: ElevatedButton(
                        onPressed: () => {
                          sendStartMessage(),
                        },
                        child: const Text('Start'),
                      ),
                  ),
                  ],
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
                      decoration: const BoxDecoration(
                        color: Color.fromARGB(1, 20, 189, 48),
                      ),
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
