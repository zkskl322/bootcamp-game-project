import 'dart:convert';
import "dart:html";
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:game_frontend/Game/lobby.dart';
import 'package:game_frontend/backup/game_lobby.dart';
import 'package:game_frontend/backup/login_page.dart';
import 'package:stomp_dart_client/stomp_dart_client.dart';

class Msg {
  final String chattingId;
  final String content;
  final String uuid;

  Msg({
    required this.chattingId,
    required this.content, 
    required this.uuid});
}

class IngameLobby2 extends StatefulWidget {
  final int GameId;
  final String myUuid;
  final String method; 

  const IngameLobby2({Key? key, required this.GameId, required this.myUuid, required this.method} ) : super(key: key);

  @override
  State<IngameLobby2> createState() => _IngameLobby2State();
}

class _IngameLobby2State extends State<IngameLobby2> {
  StompClient? stompClient;
  bool player1IsReady = false;
  bool player2IsReady = false;
  String myUuid = '1';
  final TextEditingController _textController = TextEditingController();
  final List<Msg> list = [];
  final socketUrl = 'http://localhost:8080/chatting';
  
  Future<void> joinRoom() async {
    final Dio dio = Dio();

    final String? accessToken = window.localStorage['token'];

    if (accessToken == null) {
      window.alert('로그인이 필요합니다.');
      Navigator.push(
          context, MaterialPageRoute(builder: (context) => const Login_Page()));
    }

    final response = await dio.post(
      'http://localhost:8080/game/room/join',
      options: Options(
        headers: {
          'access': accessToken,
        },
        extra: {
          'withCredentials': true,
        },
      ),
      data: {
        'id': widget.GameId,
      },
      
    );

    if (response.statusCode == 200) {
      if (response.data == 'Join GameRoom') {
        print('Join room success');
        myUuid = '2';
      } else if (response.data == 'You already in room1') {
        print('You already in room');
      } else if (response.data == 'You already in room2') {
        myUuid = '2';
        print('You already in room');
      }
      else {
        Navigator.push(
            context, MaterialPageRoute(builder: (context) => LobbyPage()));
      }
    } else {
      print('Join room failed');
    }
  }
  
  Future<void> deleteRoom() async {
    final Dio dio = Dio();

    final String? accessToken = window.localStorage['token'];

    if (accessToken == null) {
      window.alert('로그인이 필요합니다.');
      Navigator.push(
          context, MaterialPageRoute(builder: (context) => const Login_Page()));
    }

    final response = await dio.post(
      'http://localhost:8080/game/room/delete',
      options: Options(
        headers: {
          'access': accessToken,
        },
        extra: {
          'withCredentials': true,
        },
      ),
      data: {
        'id': widget.GameId,
      },
      
    );
    //     @PostMapping("/game/room/delete")
    // public String leaveOrDeleteRoom(@RequestBody GameRoomIdDTO gameRoomId, Principal principal) {
    //     GameRoom gameRoom = gameRoomRepository.findById(gameRoomId.getId()).orElseThrow(() -> new RuntimeException("GameRoom not found"));
    //     List<Gamer> gamers = gameRoom.getGamers();

    //     if (gamers.get(0).getNickname().equals(principal.getName())) {
    //         gameRoomService.delete(gameRoomId.getId());
    //         return "Leave GameRoom";
    //     } else if (gamers.get(1).getNickname().equals(principal.getName())) {
    //         gamers.remove(1);
    //         gameRoom.setGamers(gamers);
    //         gameRoomRepository.save(gameRoom);
    //         return "Leave GameRoom";
    //     }
    //     return "You can't leave room";
    // }

    if (response.statusCode == 200) {
      if (response.data == 'Leave GameRoom') {
        print('Leave room success');
      } else {
        Navigator.push(
            context, MaterialPageRoute(builder: (context) => Game_Lobby()));
      }
    } else {
      print('Leave room failed');
    }
  }


  void onConnect(StompFrame frame) {
    stompClient!.subscribe(
      destination: '/topic/message/${widget.GameId}',
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
  }
  
  void sendMessage() {
    if (_textController.text.isNotEmpty) {
      stompClient!.send(
        destination: '/app/message',
        body: json.encode({
          "chattingId": widget.GameId,
          "content": _textController.text, 
          "uuid": widget.myUuid}),
      );
      _textController.clear();
    }
  }

  @override
  void initState() {
    super.initState();
    print(widget.GameId);
    joinRoom();
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

  void startGame() {
    if (player1IsReady && player2IsReady && widget.myUuid == '1') {
      _textController.text = 'START_GAME';
      sendMessage();
      _textController.clear();
    } else if (widget.myUuid == '2') {
      showAboutDialog(context: context, children: const <Widget>[
        Text('You are not the host!'),
      ]);
    } else if (!player1IsReady || !player2IsReady) {
      showAboutDialog(context: context, children: const <Widget>[
        Text('Both players must be ready to start the game!'),
      ]);
    } else {
      showAboutDialog(context: context, children: const <Widget>[
        Text('An error occurred!'),
      ]);
    }
  }
  
  @override
  Widget build(BuildContext context) {
    final Size screenSize = MediaQuery.of(context).size;
    return Scaffold(
      body: 
        Column(
        children: [
          Container(
            width: screenSize.width,
            height: screenSize.height,
            clipBehavior: Clip.antiAlias,
            decoration: const BoxDecoration(color: Color(0xFFF2F2F2)),
            child: Column(
              children: [
                const SizedBox(height: 50),
                Row(
                  children: [
                    const SizedBox(width: 100),
                    Positioned( // 왼쪽 정보 : 게임 설정
                      child: Container(
                        width: 700,
                        height: 900,
                        clipBehavior: Clip.antiAlias,
                        decoration: ShapeDecoration(
                          color: Color(0xFF1F0707),
                          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
                        ),
                        child: const Column(
                          children: [
                            SizedBox(height: 100),
                            Positioned(
                              left: 39,
                              top: 54,
                              child: SizedBox(
                                width: 612,
                                height: 47,
                                child: Text(
                                  'GAME SETTING',
                                  textAlign: TextAlign.center,
                                  style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 40,
                                    fontFamily: 'Press Start 2P',
                                    fontWeight: FontWeight.w400,
                                    height: 0.02,
                                    letterSpacing: 1.60,
                                  ),
                                ),
                              ),
                            ),
                            SizedBox(height: 100),
                            Row(
                              mainAxisSize: MainAxisSize.min,
                              mainAxisAlignment: MainAxisAlignment.start,
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  'TARGET GOAL',
                                  style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 20,
                                    fontFamily: 'Press Start 2P',
                                    fontWeight: FontWeight.w400,
                                    height: 0.09,
                                    letterSpacing: 0.80,
                                  ),
                                ),
                                SizedBox(width: 230),
                                Text(
                                  '10',
                                  style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 20,
                                    fontFamily: 'Press Start 2P',
                                    fontWeight: FontWeight.w400,
                                    height: 0.09,
                                    letterSpacing: 0.80,
                                  ),
                                ),
                              ],
                            ),
                            SizedBox(height: 100),
                            Row(
                                  mainAxisSize: MainAxisSize.min,
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children: [
                                    Text(
                                      'LIMITED TIME',
                                      style: TextStyle(
                                        color: Colors.white,
                                        fontSize: 20,
                                        fontFamily: 'Press Start 2P',
                                        fontWeight: FontWeight.w400,
                                        height: 0.09,
                                        letterSpacing: 0.80,
                                      ),
                                    ),
                                    SizedBox(width: 167),
                                    Text(
                                      '5 MIN',
                                      style: TextStyle(
                                        color: Colors.white,
                                        fontSize: 20,
                                        fontFamily: 'Press Start 2P',
                                        fontWeight: FontWeight.w400,
                                        height: 0.09,
                                        letterSpacing: 0.80,
                                      ),
                                    ),
                                  ],
                            ),
                            SizedBox(height: 100),
                            Row(
                              mainAxisSize: MainAxisSize.min,
                              mainAxisAlignment: MainAxisAlignment.start,
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  'ROOM PASSWORD',
                                  style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 20,
                                    fontFamily: 'Press Start 2P',
                                    fontWeight: FontWeight.w400,
                                    height: 0.09,
                                    letterSpacing: 0.80,
                                  ),
                                ),
                                SizedBox(width: 162),
                                Text(
                                  '1234',
                                  style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 20,
                                    fontFamily: 'Press Start 2P',
                                    fontWeight: FontWeight.w400,
                                    height: 0.09,
                                    letterSpacing: 0.80,
                                  ),
                                ),
                              ],
                            ),
                            SizedBox(height: 100),
                            Row(
                              mainAxisSize: MainAxisSize.min,
                              mainAxisAlignment: MainAxisAlignment.start,
                              crossAxisAlignment: CrossAxisAlignment.start,
                              children: [
                                Text(
                                  'LIMITED SIZE',
                                  style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 20,
                                    fontFamily: 'Press Start 2P',
                                    fontWeight: FontWeight.w400,
                                    height: 0.09,
                                    letterSpacing: 0.80,
                                  ),
                                ),
                                SizedBox(width: 251),
                                Text(
                                  '2',
                                  style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 20,
                                    fontFamily: 'Press Start 2P',
                                    fontWeight: FontWeight.w400,
                                    height: 0.09,
                                    letterSpacing: 0.80,
                                  ),
                                ),
                              ],
                            ),
                            SizedBox(height: 100),
                          ],
                        ),
                      ),
                    ),
                    const SizedBox(width: 100),
                    Column( // 오른쪽 정보 : 게임 유저 정보, 채팅
                      children: [
                        SizedBox( // 게임 유저 정보
                          width: 765,
                          height: 432,
                          child: Stack(
                            children: [
                              Positioned(
                                left: 0,
                                top: 0,
                                child: Column (children: [
                                  Container(
                                    width: 355,
                                    height: 270,
                                    decoration: const BoxDecoration(color: Color(0xFFD9D9D9)),
                                  ),
                                  Container(
                                    width: 355,
                                    height: 80,
                                    // player가 준비를 했는지 안했는지에 따라 색깔이 달라짐
                                    decoration: BoxDecoration(
                                      color: player1IsReady ? Color.fromARGB(255, 214, 39, 16) : Color.fromARGB(255, 0, 0, 0),
                                    ),
                                    child: Center(  // Center 위젯을 추가하여 텍스트를 가운데로 배치합니다.
                                      child: Text(
                                        player1IsReady ? 'READY' : 'NOT READY',
                                        style: const TextStyle(
                                          color: Color.fromARGB(255, 255, 255, 255),
                                          fontSize: 24,
                                          fontFamily: 'Press Start 2P',
                                          fontWeight: FontWeight.w400,
                                          height: 0.07,
                                          letterSpacing: 0.96,
                                        ),
                                      ),
                                    ),
                                  )
  
                                ],)
                              ),
                              Positioned(
                                left: 410,
                                top: 0,
                                child: Column (children: [
                                  Container(
                                    width: 355,
                                    height: 270,
                                    decoration: const BoxDecoration(color: Color(0xFFD9D9D9)),
                                  ),
                                  Container(
                                    width: 355,
                                    height: 80,
                                    // player가 준비를 했는지 안했는지에 따라 색깔이 달라짐
                                    decoration: BoxDecoration(
                                      color: player2IsReady ? Color.fromARGB(255, 41, 44, 187) : Color.fromARGB(255, 0, 0, 0),
                                    ),
                                    child: Center(  // Center 위젯을 추가하여 텍스트를 가운데로 배치합니다.
                                      child: Text(
                                        player2IsReady ? 'READY' : 'NOT READY',
                                        style: const TextStyle(
                                          color: Color.fromARGB(255, 255, 255, 255),
                                          fontSize: 24,
                                          fontFamily: 'Press Start 2P',
                                          fontWeight: FontWeight.w400,
                                          height: 0.07,
                                          letterSpacing: 0.96,
                                        ),
                                      ),
                                    ),
                                  ),
                                ],)
                              ),
                              const Positioned(
                                left: 115,
                                top: 394,
                                child: Text(
                                  'MARIO',
                                  style: TextStyle(
                                    color: Colors.black,
                                    fontSize: 24,
                                    fontFamily: 'Press Start 2P',
                                    fontWeight: FontWeight.w400,
                                    height: 0.07,
                                    letterSpacing: 0.96,
                                  ),
                                ),
                              ),
                              const Positioned(
                                left: 522,
                                top: 394,
                                child: Text(
                                  'KOOPA',
                                  style: TextStyle(
                                    color: Colors.black,
                                    fontSize: 24,
                                    fontFamily: 'Press Start 2P',
                                    fontWeight: FontWeight.w400,
                                    height: 0.07,
                                    letterSpacing: 0.96,
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ),
                        const SizedBox(height: 55),
                        Column(
                          children: [
                            Container(
                              decoration: BoxDecoration(
                                border: Border.all(
                                  width: 1,
                                  color: Colors.grey,
                                ),
                                color: Colors.grey[200],
                              ),
                              height: 350,
                              width: 750,
                                child: ListView.builder(
                                  shrinkWrap: true,
                                  itemCount: list.length,
                                  itemBuilder: (context, position) {
                                    return GestureDetector(
                                      child: Card(
                                        
                                        child: Container(
                                          color: list[position].uuid == widget.myUuid ? Color.fromARGB(255, 214, 168, 16) : Color.fromARGB(255, 0, 0, 0),
                                          width: 200,
                                          child: Text(
                                            list[position].content,
                                            textAlign: list[position].uuid == widget.myUuid ? TextAlign.right : TextAlign.left,
                                            style: TextStyle(
                                              color: list[position].uuid == widget.myUuid ? Color.fromARGB(255, 0, 0, 0) : Color.fromARGB(255, 255, 255, 255),
                                            ),
                                          ),
                                        ),
                                      ),
                                    );
                                  },
                                ),
                            ),
                            Container(
                              width: 750,
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
                                    width: 550,
                                    height: 100,
                                    child: TextField(
                                      controller: _textController,
                                      style: TextStyle(color: Colors.black),
                                      keyboardType: TextInputType.text,
                                      decoration: InputDecoration(hintText: "Send Message"),
                                    ),
                                    
                                  ),
                                  SizedBox(width: 10),
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
                            SizedBox(height: 50),
                            Row(
                              children: [
                                ElevatedButton(
                                  onPressed: ( ) => {
                                    deleteRoom(),
                                  }, 
                                  child: const Text('Delete Room'),
                                ),
                                const SizedBox(width: 50),
                                ElevatedButton(
                                  onPressed: () => {
                                    setState(() {
                                      if (widget.myUuid == '1') {
                                        player1IsReady = !player1IsReady;
                                      } else {
                                        player2IsReady = !player2IsReady;
                                      }
                                    }),
                                  }, 
                                  child: const Text('READY'),
                                  
                                ),
                                const SizedBox(width: 50),
                                ElevatedButton(
                                  onPressed: () => {
                                    startGame(),
                                  }, 
                                  child: const Text('START'),
                                ),
                              ],
                            )
                          ],
                        )
                      ],
                    )
                  ],
                ),
              ],
            ),
          ),
        ],
      )
    );
  }
}
