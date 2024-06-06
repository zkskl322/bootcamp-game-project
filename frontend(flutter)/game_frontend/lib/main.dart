import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
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

class GamePlayer {
  final double player_x;
  final double player_y;
  final int? player_direction;

  GamePlayer({
    required this.player_x,
    required this.player_y,
    required this.player_direction,
  });

  factory GamePlayer.fromJson(Map<String, dynamic> json) {
    return GamePlayer(
      player_x: json['player_x'].toDouble(),
      player_y: json['player_y'].toDouble(),
      player_direction: json['player_direction'],
    );
  }
}

class GameSoccerTeam {
  List<GamePlayer> players;

  GameSoccerTeam({required this.players});

  factory GameSoccerTeam.fromJson(Map<String, dynamic> json) {
    var playersFromJson = json['players'] as List;
    List<GamePlayer> playersList =
        playersFromJson.map((i) => GamePlayer.fromJson(i)).toList();
    return GameSoccerTeam(players: playersList);
  }
}

class Game {
  final String gameId;
  final String gameStatus;
  final int score1;
  final int score2;
  final int time;
  final int who_has_ball;

  final double ball_direction_x;
  final double ball_direction_y;
  final double ball_x;
  final double ball_y;

  final int player1_control_player;
  final double player1_x;
  final double player1_y;
  final int player1_direction;
  final bool player1_possession;
  final GameSoccerTeam player1_players;

  final int player2_control_player;
  final double player2_x;
  final double player2_y;
  final int player2_direction;
  final bool player2_possession;
  final GameSoccerTeam player2_players;

  Game({
    required this.gameId,
    required this.gameStatus,
    required this.score1,
    required this.time,
    required this.score2,
    required this.who_has_ball,
    required this.ball_direction_x,
    required this.ball_direction_y,
    required this.ball_x,
    required this.ball_y,
    required this.player1_control_player,
    required this.player1_x,
    required this.player1_y,
    required this.player1_direction,
    required this.player1_possession,
    required this.player1_players,
    required this.player2_control_player,
    required this.player2_x,
    required this.player2_y,
    required this.player2_direction,
    required this.player2_possession,
    required this.player2_players,
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
      title: 'Soccer Game',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: LobbyPage(),
    );
  }
}

class LobbyPage extends StatefulWidget {
  @override
  State<LobbyPage> createState() => _LobbyPageState();
}

class _LobbyPageState extends State<LobbyPage> {
  final TextEditingController _uuidController = TextEditingController();
  final TextEditingController _gameIdController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Soccer Game'),
      ),
      body: Column(
        children: [
          Container(
            child: TextField(
              controller: _uuidController,
              decoration: InputDecoration(
                border: OutlineInputBorder(),
                labelText: 'Game ID',
              ),
            ),
          ),
          Container(
            child: TextField(
              controller: _gameIdController,
              decoration: InputDecoration(
                border: OutlineInputBorder(),
                labelText: 'UUID',
              ),
            ),
          ),
          ElevatedButton(
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => GameRoomPage(
                    GameId: _uuidController.text,
                    myUuid: _gameIdController.text,
                  ),
                ),
              );
            },
            child: Text('Join Game'),
          ),
        ],
      ),
    );
  }
}

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
  final socketUrl = 'http://localhost:8080/game';

  void onConnect(StompFrame frame) {
    stompClient!.subscribe(
      destination: '/topic/game/${widget.GameId}',
      callback: (frame) {
        if (frame.body != null) {
          Map<String, dynamic> obj = json.decode(frame.body!);
          Game game = Game(
            gameId: obj['gameId'],
            gameStatus: obj['gameStatus'],
            score1: obj['score1'],
            score2: obj['score2'],
            time: obj['time'],
            who_has_ball: obj['who_has_ball'],
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
            // -------------------------------------- //
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

    // 컨테이너 크기와 볼 위치를 계산합니다. --- //
    double scale = 1000 / 10;
    double ballX = currentGame.ball_x * scale;
    double ballY = currentGame.ball_y * scale;
    // -------------------------------------- //

    // player1 ------------------------------ //
    double player1_X = currentGame.player1_x * scale;
    double player1_Y = currentGame.player1_y * scale;
    int player1_control_player = currentGame.player1_control_player;
    // -------------------------------------- //

    // player2 ------------------------------ //
    double player2_X = currentGame.player2_x * scale;
    double player2_Y = currentGame.player2_y * scale;
    int player2_control_player = currentGame.player2_control_player;
    // -------------------------------------- //

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

    int i = -1;
    for (var player in currentGame.player1_players.players) {
      i++;
      if (i == player1_control_player) continue;
      double player1_X = player.player_x * scale;
      double player1_Y = player.player_y * scale;
      canvas.drawCircle(Offset(player1_X, player1_Y), 10, paint_player1);
    }
    i = -1;
    for (var player in currentGame.player2_players.players) {
      i++;
      if (i == player2_control_player) continue;
      double player2_X = player.player_x * scale;
      double player2_Y = player.player_y * scale;
      canvas.drawCircle(Offset(player2_X, player2_Y), 10, paint_player2);
    }
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) {
    // 항상 다시 그리도록 설정합니다.
    return true;
  }
}
