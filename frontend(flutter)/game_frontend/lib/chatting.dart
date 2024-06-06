import 'dart:convert';
import 'package:flutter/material.dart';
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
  final double direction_x;
  final double direction_y;
  final double ball_x;
  final double ball_y;
  final double player1_x;
  final double player1_y;
  final double player2_x;
  final double player2_y;
  final int score1;
  final int score2;
  final int time;

  Game({
    required this.gameId,
    required this.gameStatus,
    required this.direction_x,
    required this.direction_y,
    required this.ball_x,
    required this.ball_y,
    required this.player1_x,
    required this.player1_y,
    required this.player2_x,
    required this.player2_y,
    required this.score1,
    required this.score2,
    required this.time,
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
      home: ChattingPage(chattingId: '1', myUuid: '1'),
    );
  }
}

class ChattingPage extends StatefulWidget {
  final String chattingId;
  final String myUuid;

  ChattingPage({required this.chattingId, required this.myUuid});

  @override
  State<ChattingPage> createState() => _ChattingPageState();
}

class _ChattingPageState extends State<ChattingPage> {
  StompClient? stompClient;
  final TextEditingController _textController = TextEditingController();
  final List<Msg> list = [];
  final List<Game> gameList = [];
  final socketUrl = 'http://localhost:8080/chatting';

  void onConnect(StompFrame frame) {
    stompClient!.subscribe(
      destination: '/topic/message/${widget.chattingId}',
      callback: (frame) {
        if (frame.body != null) {
          Map<String, dynamic> obj = json.decode(frame.body!);
          Msg message = Msg(
              chattingId: obj['chattingId'],
              content: obj['content'],
              uuid: obj['uuid']);
          setState(() {
            list.add(message);
          });
        }
      },
    );

    stompClient!.subscribe(
      destination: '/topic/game/${widget.chattingId}',
      callback: (frame) {
        if (frame.body != null) {
          Map<String, dynamic> obj = json.decode(frame.body!);
          Game game = Game(
            gameId: obj['gameId'],
            gameStatus: obj['gameStatus'],
            direction_x: obj['direction_x'],
            direction_y: obj['direction_y'],
            ball_x: obj['ball_x'],
            ball_y: obj['ball_y'],
            player1_x: obj['player1_x'],
            player1_y: obj['player1_y'],
            player2_x: obj['player2_x'],
            player2_y: obj['player2_y'],
            score1: obj['score1'],
            score2: obj['score2'],
            time: obj['time'],
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
        destination: '/app/message',
        body: json.encode({
          "chattingId": widget.chattingId,
          "content": _textController.text,
          "uuid": widget.myUuid
        }),
      );
      _textController.clear();
    }
  }

  @override
  void initState() {
    super.initState();
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
      body: Center(
        child: Column(
          children: [
            Container(
              decoration: BoxDecoration(
                border: Border.all(
                  width: 1,
                  color: Colors.grey,
                ),
                color: Colors.grey[200],
              ),
              height: 500,
              width: 500,
              child: ListView.builder(
                shrinkWrap: true,
                itemCount: list.length,
                itemBuilder: (context, position) {
                  return GestureDetector(
                    child: Card(
                      child: Container(
                        color: list[position].uuid == widget.myUuid
                            ? Color.fromARGB(255, 234, 250, 9)
                            : Color.fromARGB(255, 0, 0, 0),
                        width: 200,
                        child: Text(
                          list[position].content,
                          textAlign: list[position].uuid == widget.myUuid
                              ? TextAlign.right
                              : TextAlign.left,
                          style: TextStyle(
                            color: list[position].uuid == widget.myUuid
                                ? Color.fromARGB(255, 0, 0, 0)
                                : Color.fromARGB(255, 255, 255, 255),
                          ),
                        ),
                      ),
                    ),
                  );
                },
              ),
            ),
            Container(
              width: 500,
              height: 100,
              decoration: BoxDecoration(
                border: Border.all(
                  width: 1,
                  color: Colors.grey,
                ),
              ),
              child: Row(
                children: <Widget>[
                  const SizedBox(width: 50),
                  SizedBox(
                    width: 350,
                    height: 100,
                    child: TextField(
                      controller: _textController,
                      style: TextStyle(color: Colors.black),
                      keyboardType: TextInputType.text,
                      decoration: InputDecoration(hintText: "Send Message"),
                    ),
                  ),
                  SizedBox(
                    width: 50,
                    height: 50,
                    child: ElevatedButton(
                      onPressed: sendMessage,
                      child: Icon(Icons.send),
                    ),
                  ),
                ],
              ),
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

    // 페인트 객체를 만듭니다.
    Paint paint = Paint()
      ..color = Colors.red
      ..style = PaintingStyle.fill;

    // 볼을 그립니다.
    canvas.drawCircle(Offset(ballX, ballY), 10, paint);
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) {
    // 항상 다시 그리도록 설정합니다.
    return true;
  }
}
