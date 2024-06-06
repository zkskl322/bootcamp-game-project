import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:stomp_dart_client/stomp_dart_client.dart';

class Msg {
  final String chattingId;
  final String content;
  final String uuid;

  Msg({required this.chattingId, required this.content, required this.uuid});
}

class Game {
  final String gameId;
  final String gameStatus;
  final int score1;
  final int score2;
  final int time;

  final double direction_x;
  final double direction_y;
  final double ball_x;
  final double ball_y;

  final double player1_x;
  final double player1_y;
  final double player2_x;
  final double player2_y;
  final int player1_direction;
  final int player2_direction;
  final bool player1_possession;
  final bool player2_possession;

  Game({
    required this.gameId,
    required this.gameStatus,
    required this.score1,
    required this.score2,
    required this.time,
    required this.direction_x,
    required this.direction_y,
    required this.ball_x,
    required this.ball_y,
    required this.player1_x,
    required this.player1_y,
    required this.player2_x,
    required this.player2_y,
    required this.player1_direction,
    required this.player2_direction,
    required this.player1_possession,
    required this.player2_possession,
  });
}

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Chatting App',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: GameRoomPage(chattingId: '1', myUuid: '1'),
    );
  }
}

class GameRoomPage extends StatefulWidget {
  final String chattingId;
  final String myUuid;

  GameRoomPage({required this.chattingId, required this.myUuid});

  @override
  State<GameRoomPage> createState() => _ChattingPageState();
}

class _ChattingPageState extends State<GameRoomPage> {
  StompClient? stompClient;
  final TextEditingController _textController = TextEditingController();
  late final FocusNode _focusNode;
  final List<Game> gameList = [];
  final socketUrl = 'http://localhost:8080/game';

  void onConnect(StompFrame frame) {
    stompClient!.subscribe(
      destination: '/topic/game/${widget.chattingId}',
      callback: (frame) {
        if (frame.body != null) {
          Map<String, dynamic> obj = json.decode(frame.body!);
          Game game = Game(
            gameId: obj['gameId'],
            gameStatus: obj['gameStatus'],
            score1: obj['score1'],
            score2: obj['score2'],
            time: obj['time'],
            direction_x: obj['direction_x'],
            direction_y: obj['direction_y'],
            ball_x: obj['ball_x'],
            ball_y: obj['ball_y'],
            player1_x: obj['player1_x'],
            player1_y: obj['player1_y'],
            player2_x: obj['player2_x'],
            player2_y: obj['player2_y'],
            player1_direction: obj['player1_direction'],
            player2_direction: obj['player2_direction'],
            player1_possession: obj['player1_possession'],
            player2_possession: obj['player2_possession'],
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
          'gameId': widget.chattingId,
          'playerId': widget.myUuid,
          'action': _textController.text,
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
        title: Text('Chatting : ${widget.chattingId}'),
      ),
      body: KeyboardListener(
        focusNode: _focusNode,
        autofocus: true,
        onKeyEvent: (e) async {
          if (e is! KeyDownEvent) {
            return;
          }
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
            case LogicalKeyboardKey.space:
              print("space");
              _textController.text = 'SHOOT';
              sendMessage();
              _textController.clear();
              break;
            default:
              print("default");
              break;
          }
        },
        child: Center(
          child: Column(
            children: [
              ElevatedButton(
                onPressed: () => {
                  sendStartMessage(),
                },
                child: Icon(Icons.start),
              ),
              ElevatedButton(
                onPressed: () => {
                  _textController.text = 'END_GAME',
                  sendMessage(),
                  _textController.clear(),
                },
                child: Icon(Icons.stop),
              ),
              Container(
                width: 1000,
                height: 1000,
                decoration: BoxDecoration(
                  border: Border.all(
                    width: 1,
                    color: Colors.grey,
                  ),
                  color: Colors.grey[200],
                ),
                child: Stack(
                  children: [
                    Container(
                      width: 1000,
                      height: 1000,
                      decoration: BoxDecoration(
                        border: Border.all(
                          width: 1,
                          color: Colors.grey,
                        ),
                        color: Colors.grey[200],
                      ),
                      child: CustomPaint(
                        painter: MyPainter(gameList),
                      ),
                    ),
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

class MyPainter extends CustomPainter {
  final List<Game> gameList;

  MyPainter(this.gameList);

  @override
  void paint(Canvas canvas, Size size) {
    if (gameList.isEmpty) return;

    // 가장 최근 게임 데이터를 가져옵니다.
    Game currentGame = gameList.last;
    gameList.clear();

    // 컨테이너 크기와 볼 위치를 계산합니다.
    double scale = 1000 / 10;
    double ballX = currentGame.ball_x * scale;
    double ballY = currentGame.ball_y * scale;
    double player1_X = currentGame.player1_x * scale;
    double player1_Y = currentGame.player1_y * scale;
    double player2_X = currentGame.player2_x * scale;
    double player2_Y = currentGame.player2_y * scale;

    Paint paint_ball = Paint()
      ..color = Colors.red
      ..style = PaintingStyle.fill;

    Paint paint_player1 = Paint()
      ..color = Colors.blue
      ..style = PaintingStyle.fill;

    Paint paint_player2 = Paint()
      ..color = Colors.green
      ..style = PaintingStyle.fill;

    canvas.drawCircle(Offset(ballX, ballY), 10, paint_ball);
    canvas.drawCircle(Offset(player1_X, player1_Y), 10, paint_player1);
    canvas.drawCircle(Offset(player2_X, player2_Y), 10, paint_player2);
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) {
    // 항상 다시 그리도록 설정합니다.
    return true;
  }
}
