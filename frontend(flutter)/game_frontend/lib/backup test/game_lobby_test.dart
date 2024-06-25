import 'package:dio/dio.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:game_frontend/backup/ingame_lobby.dart';
import 'package:game_frontend/backup/signed_main_page.dart';
import 'package:game_frontend/backup/unsigned_main_page.dart';
import 'package:game_frontend/dto/gameroom-dto.dart';
import 'dart:html';

class Game_Lobby_Test extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData.dark().copyWith(
        scaffoldBackgroundColor: const Color.fromARGB(255, 18, 32, 47),
      ),
      home: GameRoom(),
    );
  }
}

class GameRoom extends StatefulWidget {
  const GameRoom({super.key});

  @override
  _GameRoomState createState() => _GameRoomState();
}

class _GameRoomState extends State<GameRoom> {
  String myUuid = '';
  String myNickname = '';
  String myTier = '';
  String myWinScore = '';
  String myLoseScore = '';
  String myDrawScore = '';
  List<GameRoomsDTO> _gamerooms = [];

  final Dio dio = Dio();
  final Storage _storage = window.localStorage;
  final TextEditingController _roomPasswordController = TextEditingController();

  @override
  void initState() {
    super.initState();
    fetchGameRooms();
  }

  Future<void> _handleLogoutButton(BuildContext context) async {
    final dio = Dio();
    final String? accessToken = window.localStorage['token'];

    if (accessToken == null) {
      if (kDebugMode) {
        print('access token null');
      }
      return;
    }

    try {
      final Response response = await dio.post(
        'http://localhost:8080/user/logout',
        options: Options(
          headers: {
            'Authorization': 'Bearer $accessToken', // accessToken 변수 사용
          },
        ),
      );
      if (response.statusCode == 200) {
        if (kDebugMode) {
          print("logout successfully");
        }
        Navigator.push(
            context,
            MaterialPageRoute(
                builder: (context) => const UnsignedMainPage())); // 로그인 페이지로 이동
      } else {
        if (kDebugMode) {
          print("logout fail: ${response.statusCode}");
        }
      }
    } catch (e) {
      if (kDebugMode) {
        print("error: $e");
      }
    }
  }

  Future<void> fetchGameRooms() async {
    final String? accessToken = window.localStorage['token'];

    if (accessToken == null) {
      if (kDebugMode) {
        print('No access token');
      }
      return;
    }
    try {
      final Response response =
          await dio.get('http://localhost:8080/user/whoAmI',
              options: Options(
                headers: {
                  'access': accessToken,
                },
                extra: {
                  'withCredentials': true,
                },
              ));

      // Dio로 데이터를 받은 후의 처리
      if (response.statusCode == 200) {
        // JSON 데이터를 Map<String, dynamic>으로 변환
        Map<String, dynamic> jsonData = response.data;

        // Map<String, dynamic>을 Map<String, String>으로 변환
        Map<String, String> data = {
          'nickname': jsonData['nickname'],
          'winScore': jsonData['winScore'].toString(),
          'loseScore': jsonData['loseScore'].toString(),
          'drawScore': jsonData['drawScore'].toString(),
          'tier': jsonData['tier'],
          'uuid': jsonData['uuid']
        };

        myNickname = data['nickname']!;
        myWinScore = data['winScore']!;
        myLoseScore = data['loseScore']!;
        myDrawScore = data['drawScore']!;
        myTier = data['tier']!;
        myUuid = data['uuid']!;
      } else {
        print('Error: ${response.statusCode}');
      }
    } catch (e) {
      print('Error: $e');
    }
    try {
      final Response response =
          await dio.get('http://localhost:8080/page/main');
      if (response.statusCode == 200) {
        Map<String, dynamic> data = response.data;
        List<dynamic> gameRooms = data['gameRooms'];
        List<GameRoomsDTO> gameRooms_instance = gameRooms.map((roomData) {
          return GameRoomsDTO(
            roomId: roomData['id'],
            roomPassword: roomData['room_password'],
            roomOwner: roomData['room_owner'],
            roomName: roomData['room_name'],
            roomSize: roomData['room_size'],
            roomGoal: roomData['room_goal'],
          );
        }).toList();
        setState(() {
          _gamerooms = gameRooms_instance;
        });
      } else {
        setState(() {
          print('Error: ${response.statusCode}');
        });
      }
    } catch (e) {
      setState(() {
        print('Error: $e');
      });
    }
  }

  Future<void> checkPassword(int roomId, String password) async {
    final String? accessToken = window.localStorage['token'];
    if (accessToken == null) {
      return;
    }
    try {
      final Response response =
          await dio.post('http://localhost:8080/game/room/checkPassword',
              options: Options(
                headers: {
                  'access': accessToken,
                },
                extra: {
                  'withCredentials': true,
                },
              ),
              data: {
            'roomId': roomId,
            'password': password,
          });

      if (response.statusCode == 200) {
        Map<String, dynamic> data = response.data;
        print("data: $data");
        if (data['result'] == 'success') {
          Navigator.push(
            context,
            MaterialPageRoute(
              builder: (context) => IngameLobby2(
                  GameId: roomId, myRealUuid: myUuid, method: "join"),
            ),
          );
        } else {
          showAboutDialog(
              context: context,
              applicationName: "Join Room",
              children: [
                Column(
                  children: [
                    Text("Password is incorrect"),
                    ElevatedButton(
                      onPressed: () {
                        Navigator.pop(context);
                      },
                      child: Text('OK'),
                    ),
                  ],
                ),
              ]);
        }
      } else {
        showAboutDialog(
            context: context,
            applicationName: "Join Room",
            children: [
              Column(
                children: [
                  Text("Error: ${response.statusCode}"),
                  ElevatedButton(
                    onPressed: () {
                      Navigator.pop(context);
                    },
                    child: Text('OK'),
                  ),
                ],
              ),
            ]);
      }
    } catch (e) {
      print('Error: $e');
    }
  }

  Future<void> joinGameRoombtn(int roomId) async {
    showAboutDialog(context: context, applicationName: "Join Room", children: [
      Column(
        children: [
          Text("Room ID: $roomId"),
          TextField(
            controller: _roomPasswordController,
            decoration: const InputDecoration(
              labelText: 'Password',
            ),
          ),
          ElevatedButton(
            onPressed: () {
              checkPassword(roomId, _roomPasswordController.text);
            },
            child: Text('Join'),
          ),
        ],
      ),
    ]);
  }

  Future<void> showRankingModal() async {
    final String? accessToken = window.localStorage['token'];
    if (accessToken == null) {
      return;
    }

    final Response response = await dio.get(
      'http://localhost:8080/user/ranking',
      options: Options(
        headers: {
          'access': accessToken,
        },
        extra: {
          'withCredentials': true,
        },
      ),
    );

    print(response.data);
    // [{nickname: aaa, win: 0, lose: 0, draw: 0, rankPoint: 1100, tier: Bronze}, {nickname: bbb, win: 0, lose: 0, draw: 0, rankPoint: 900, tier: Bronze}]

    List<dynamic> rankingData = response.data;

    showDialog(
      context: context,
      builder: (BuildContext context) {
        return Dialog(
          child: Container(
            height: 620,
            width: 500,
            padding: const EdgeInsets.all(16),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                const Text(
                  'GAME RANKING',
                  style: TextStyle(
                    fontSize: 24,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                const SizedBox(height: 16),
                Expanded(
                  child: ListView.builder(
                    shrinkWrap: true,
                    itemCount: rankingData.length,
                    itemBuilder: (BuildContext context, int index) {
                      return ListTile(
                        leading: Text(
                          '${index + 1}',
                          style: const TextStyle(
                            fontSize: 16,
                            fontWeight: FontWeight.bold,
                          ),
                        ),
                        title: Row(
                          mainAxisAlignment: MainAxisAlignment.spaceBetween,
                          children: [
                            Text(rankingData[index]['nickname']),
                            Text('${rankingData[index]['rankPoint']} PT.'),
                          ],
                        ),
                      );
                    },
                  ),
                ),
                const SizedBox(height: 24),
                SizedBox(
                  width: double.infinity,
                  child: ElevatedButton(
                    onPressed: () {
                      Navigator.pop(context);
                    },
                    style: ElevatedButton.styleFrom(
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(12),
                      ),
                    ),
                    child: const Text('CLOSE'),
                  ),
                ),
              ],
            ),
          ),
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      children: [
        Container(
          width: 1600,
          height: 960,
          clipBehavior: Clip.antiAlias,
          decoration: const BoxDecoration(color: Color(0xFFF2F2F2)),
          child: Stack(
            children: [
              Row(
                //top btn
                children: [
                  InkWell(
                    onTap: () {
                      Navigator.push(
                          context,
                          MaterialPageRoute(
                              builder: (context) => Main_Signed_Page()));
                      print('Home 버튼이 눌렸습니다.');
                    },
                    borderRadius: BorderRadius.circular(16),
                    child: Container(
                      width: 190,
                      height: 60,
                      decoration: ShapeDecoration(
                        color: Color(0xFF758CFF),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(16),
                        ),
                      ),
                      child: const Center(
                        child: Text(
                          'HOME',
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
                    ),
                  ),
                  InkWell(
                    onTap: () => _handleLogoutButton(context),
                    child: Container(
                      width: 190,
                      height: 60,
                      decoration: ShapeDecoration(
                        color: const Color(0xFF758CFF),
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(16),
                        ),
                      ),
                      child: Container(
                        width: 190,
                        height: 60,
                        decoration: ShapeDecoration(
                          color: Color(0xFF758CFF),
                          shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(16),
                          ),
                        ),
                        child: const Center(
                          child: Text(
                            'LOGOUT',
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
                      ),
                    ),
                  ),
                ],
              ),
              Positioned(
                // middle section
                left: 52,
                top: 94,
                child: SizedBox(
                  width: 1501,
                  height: 748,
                  child: Stack(
                    children: [
                      Positioned(
                        left: 452,
                        top: 0,
                        child: Container(
                          width: 1049,
                          height: 748,
                          clipBehavior: Clip.antiAlias,
                          decoration: ShapeDecoration(
                            color: const Color(0xFF080808),
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(16),
                            ),
                            shadows: const [
                              BoxShadow(
                                color: Color(0x7FFFFFFF),
                                blurRadius: 2.83,
                                offset: Offset(0, 2.83),
                                spreadRadius: 0,
                              )
                            ],
                          ),
                          child: Stack(
                            children: [
                              Positioned(
                                left: 34,
                                top: 34,
                                child: Container(
                                  width: 980.80,
                                  height: 680.17,
                                  clipBehavior: Clip.antiAlias,
                                  decoration: ShapeDecoration(
                                    color: const Color(0xFF1B1B1B),
                                    shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(2.83),
                                    ),
                                  ),
                                  child: Stack(
                                    children: [
                                      Positioned(
                                        left: 11,
                                        top: 64,
                                        child: Container(
                                          width: 959,
                                          height: 73,
                                          clipBehavior: Clip.antiAlias,
                                          decoration: ShapeDecoration(
                                            color: Colors.white,
                                            shape: RoundedRectangleBorder(
                                              borderRadius:
                                                  BorderRadius.circular(16),
                                            ),
                                          ),
                                          child: Stack(
                                            children: [
                                              Positioned(
                                                left: 829.10,
                                                top: 7,
                                                child: Container(
                                                  width: 122.95,
                                                  height: 59,
                                                  padding: const EdgeInsets
                                                      .symmetric(
                                                      horizontal: 42.48,
                                                      vertical: 28.32),
                                                  decoration: ShapeDecoration(
                                                    color:
                                                        const Color(0xFF393434),
                                                    shape:
                                                        RoundedRectangleBorder(
                                                      borderRadius:
                                                          BorderRadius.circular(
                                                              16),
                                                    ),
                                                  ),
                                                  child: const Row(
                                                    mainAxisSize:
                                                        MainAxisSize.min,
                                                    mainAxisAlignment:
                                                        MainAxisAlignment
                                                            .center,
                                                    crossAxisAlignment:
                                                        CrossAxisAlignment
                                                            .center,
                                                    children: [
                                                      Text(
                                                        'JOIN',
                                                        style: TextStyle(
                                                          color: Colors.white,
                                                          fontSize: 24,
                                                          fontFamily:
                                                              'Press Start 2P',
                                                          fontWeight:
                                                              FontWeight.w400,
                                                          height: 0.07,
                                                          letterSpacing: -0.60,
                                                        ),
                                                      ),
                                                    ],
                                                  ),
                                                ),
                                              ),
                                              const Positioned(
                                                left: 28,
                                                top: 24,
                                                child: SizedBox(
                                                  width: 774.77,
                                                  height: 32,
                                                  child: Stack(
                                                    children: [
                                                      Positioned(
                                                        left: 582.61,
                                                        top: 0,
                                                        child: SizedBox(
                                                          width: 192.16,
                                                          height: 32,
                                                          child: Text(
                                                            'name',
                                                            style: TextStyle(
                                                              color:
                                                                  Colors.black,
                                                              fontSize: 24,
                                                              fontFamily:
                                                                  'Press Start 2P',
                                                              fontWeight:
                                                                  FontWeight
                                                                      .w400,
                                                              height: 0,
                                                              letterSpacing:
                                                                  -0.60,
                                                            ),
                                                          ),
                                                        ),
                                                      ),
                                                      Positioned(
                                                        left: 116.52,
                                                        top: 0,
                                                        child: SizedBox(
                                                          width: 454.85,
                                                          height: 32,
                                                          child: Text(
                                                            'Room name',
                                                            style: TextStyle(
                                                              color:
                                                                  Colors.black,
                                                              fontSize: 24,
                                                              fontFamily:
                                                                  'Press Start 2P',
                                                              fontWeight:
                                                                  FontWeight
                                                                      .w400,
                                                              height: 0,
                                                              letterSpacing:
                                                                  -0.60,
                                                            ),
                                                          ),
                                                        ),
                                                      ),
                                                      Positioned(
                                                        left: 0,
                                                        top: 0,
                                                        child: SizedBox(
                                                          width: 87.75,
                                                          height: 32,
                                                          child: Text(
                                                            'No.',
                                                            style: TextStyle(
                                                              color:
                                                                  Colors.black,
                                                              fontSize: 24,
                                                              fontFamily:
                                                                  'Press Start 2P',
                                                              fontWeight:
                                                                  FontWeight
                                                                      .w400,
                                                              height: 0,
                                                              letterSpacing:
                                                                  -0.60,
                                                            ),
                                                          ),
                                                        ),
                                                      ),
                                                    ],
                                                  ),
                                                ),
                                              ),
                                            ],
                                          ),
                                        ),
                                      ),
                                      const Positioned(
                                        left: 40,
                                        top: 7,
                                        child: SizedBox(
                                          width: 848.09,
                                          height: 33,
                                          child: Stack(
                                            children: [
                                              Positioned(
                                                left: 575.11,
                                                top: 0,
                                                child: SizedBox(
                                                  width: 272.98,
                                                  height: 32,
                                                  child: Text(
                                                    'name',
                                                    style: TextStyle(
                                                      color: Colors.white,
                                                      fontSize: 24,
                                                      fontFamily:
                                                          'Press Start 2P',
                                                      fontWeight:
                                                          FontWeight.w400,
                                                      height: 0,
                                                      letterSpacing: -0.60,
                                                    ),
                                                  ),
                                                ),
                                              ),
                                              Positioned(
                                                left: 119.62,
                                                top: 1,
                                                child: SizedBox(
                                                  width: 466.22,
                                                  height: 32,
                                                  child: Text(
                                                    'Room name',
                                                    textAlign: TextAlign.center,
                                                    style: TextStyle(
                                                      color: Colors.white,
                                                      fontSize: 24,
                                                      fontFamily:
                                                          'Press Start 2P',
                                                      fontWeight:
                                                          FontWeight.w400,
                                                      height: 0,
                                                      letterSpacing: -0.60,
                                                    ),
                                                  ),
                                                ),
                                              ),
                                              Positioned(
                                                left: 0,
                                                top: 1,
                                                child: SizedBox(
                                                  width: 84.86,
                                                  height: 32,
                                                  child: Text(
                                                    'No.',
                                                    style: TextStyle(
                                                      color: Colors.white,
                                                      fontSize: 24,
                                                      fontFamily:
                                                          'Press Start 2P',
                                                      fontWeight:
                                                          FontWeight.w400,
                                                      height: 0,
                                                      letterSpacing: -0.60,
                                                    ),
                                                  ),
                                                ),
                                              ),
                                            ],
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                      Positioned(
                        left: 0,
                        top: 0,
                        child: Container(
                          width: 424,
                          height: 748,
                          clipBehavior: Clip.antiAlias,
                          decoration: ShapeDecoration(
                            color: const Color(0xFF080808),
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(16.97),
                            ),
                            shadows: const [
                              BoxShadow(
                                color: Color(0x7FFFFFFF),
                                blurRadius: 2.83,
                                offset: Offset(0, 2.83),
                                spreadRadius: 0,
                              )
                            ],
                          ),
                          child: Stack(
                            children: [
                              Positioned(
                                left: 34,
                                top: 34,
                                child: Container(
                                  width: 355.80,
                                  height: 680.17,
                                  clipBehavior: Clip.antiAlias,
                                  decoration: ShapeDecoration(
                                    color: const Color(0xFF1B1B1B),
                                    shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(2.83),
                                    ),
                                  ),
                                  child: Stack(
                                    children: [
                                      const Positioned(
                                        left: -8,
                                        top: 393,
                                        child: SizedBox(
                                          width: 369,
                                          height: 248,
                                          child: Stack(
                                            children: [
                                              Positioned(
                                                left: 242,
                                                top: 102,
                                                child: SizedBox(
                                                  width: 120,
                                                  height: 52,
                                                  child: Stack(
                                                    children: [
                                                      Positioned(
                                                        left: 0,
                                                        top: 32,
                                                        child: SizedBox(
                                                          width: 120,
                                                          height: 20,
                                                          child: Text(
                                                            '0',
                                                            textAlign: TextAlign
                                                                .center,
                                                            style: TextStyle(
                                                              color:
                                                                  Colors.white,
                                                              fontSize: 24,
                                                              fontFamily:
                                                                  'Press Start 2P',
                                                              fontWeight:
                                                                  FontWeight
                                                                      .w400,
                                                              height: 0.07,
                                                              letterSpacing:
                                                                  0.96,
                                                            ),
                                                          ),
                                                        ),
                                                      ),
                                                      Positioned(
                                                        left: 0,
                                                        top: 0,
                                                        child: SizedBox(
                                                          width: 120,
                                                          height: 20,
                                                          child: Text(
                                                            'DRAW',
                                                            textAlign: TextAlign
                                                                .center,
                                                            style: TextStyle(
                                                              color:
                                                                  Colors.white,
                                                              fontSize: 24,
                                                              fontFamily:
                                                                  'Press Start 2P',
                                                              fontWeight:
                                                                  FontWeight
                                                                      .w400,
                                                              height: 0.07,
                                                              letterSpacing:
                                                                  0.96,
                                                            ),
                                                          ),
                                                        ),
                                                      ),
                                                    ],
                                                  ),
                                                ),
                                              ),
                                              Positioned(
                                                left: 120,
                                                top: 102,
                                                child: SizedBox(
                                                  width: 120,
                                                  height: 52,
                                                  child: Stack(
                                                    children: [
                                                      Positioned(
                                                        left: 0,
                                                        top: 32,
                                                        child: SizedBox(
                                                          width: 120,
                                                          height: 20,
                                                          child: Text(
                                                            '0',
                                                            textAlign: TextAlign
                                                                .center,
                                                            style: TextStyle(
                                                              color:
                                                                  Colors.white,
                                                              fontSize: 24,
                                                              fontFamily:
                                                                  'Press Start 2P',
                                                              fontWeight:
                                                                  FontWeight
                                                                      .w400,
                                                              height: 0.07,
                                                              letterSpacing:
                                                                  0.96,
                                                            ),
                                                          ),
                                                        ),
                                                      ),
                                                      Positioned(
                                                        left: 0,
                                                        top: 0,
                                                        child: SizedBox(
                                                          width: 120,
                                                          height: 20,
                                                          child: Text(
                                                            'LOSE',
                                                            textAlign: TextAlign
                                                                .center,
                                                            style: TextStyle(
                                                              color:
                                                                  Colors.white,
                                                              fontSize: 24,
                                                              fontFamily:
                                                                  'Press Start 2P',
                                                              fontWeight:
                                                                  FontWeight
                                                                      .w400,
                                                              height: 0.07,
                                                              letterSpacing:
                                                                  0.96,
                                                            ),
                                                          ),
                                                        ),
                                                      ),
                                                    ],
                                                  ),
                                                ),
                                              ),
                                              Positioned(
                                                left: 0,
                                                top: 102,
                                                child: SizedBox(
                                                  width: 120,
                                                  height: 52,
                                                  child: Stack(
                                                    children: [
                                                      Positioned(
                                                        left: 0,
                                                        top: 0,
                                                        child: SizedBox(
                                                          width: 120,
                                                          height: 20,
                                                          child: Text(
                                                            'WIN',
                                                            textAlign: TextAlign
                                                                .center,
                                                            style: TextStyle(
                                                              color:
                                                                  Colors.white,
                                                              fontSize: 24,
                                                              fontFamily:
                                                                  'Press Start 2P',
                                                              fontWeight:
                                                                  FontWeight
                                                                      .w400,
                                                              height: 0.07,
                                                              letterSpacing:
                                                                  0.96,
                                                            ),
                                                          ),
                                                        ),
                                                      ),
                                                      Positioned(
                                                        left: 0,
                                                        top: 32,
                                                        child: SizedBox(
                                                          width: 120,
                                                          height: 20,
                                                          child: Text(
                                                            '0',
                                                            textAlign: TextAlign
                                                                .center,
                                                            style: TextStyle(
                                                              color:
                                                                  Colors.white,
                                                              fontSize: 24,
                                                              fontFamily:
                                                                  'Press Start 2P',
                                                              fontWeight:
                                                                  FontWeight
                                                                      .w400,
                                                              height: 0.07,
                                                              letterSpacing:
                                                                  0.96,
                                                            ),
                                                          ),
                                                        ),
                                                      ),
                                                    ],
                                                  ),
                                                ),
                                              ),
                                              Positioned(
                                                left: 8,
                                                top: 0,
                                                child: SizedBox(
                                                  width: 230,
                                                  height: 52,
                                                  child: Stack(
                                                    children: [
                                                      Positioned(
                                                        left: 16,
                                                        top: 32,
                                                        child: SizedBox(
                                                          width: 214,
                                                          height: 20,
                                                          child: Text(
                                                            'MARIO',
                                                            textAlign: TextAlign
                                                                .center,
                                                            style: TextStyle(
                                                              color:
                                                                  Colors.white,
                                                              fontSize: 24,
                                                              fontFamily:
                                                                  'Press Start 2P',
                                                              fontWeight:
                                                                  FontWeight
                                                                      .w400,
                                                              height: 0.07,
                                                              letterSpacing:
                                                                  0.96,
                                                            ),
                                                          ),
                                                        ),
                                                      ),
                                                      Positioned(
                                                        left: 0,
                                                        top: 0,
                                                        child: SizedBox(
                                                          width: 230,
                                                          height: 20,
                                                          child: Text(
                                                            'USERNAME',
                                                            textAlign: TextAlign
                                                                .center,
                                                            style: TextStyle(
                                                              color:
                                                                  Colors.white,
                                                              fontSize: 24,
                                                              fontFamily:
                                                                  'Press Start 2P',
                                                              fontWeight:
                                                                  FontWeight
                                                                      .w400,
                                                              height: 0.07,
                                                              letterSpacing:
                                                                  0.96,
                                                            ),
                                                          ),
                                                        ),
                                                      ),
                                                    ],
                                                  ),
                                                ),
                                              ),
                                              Positioned(
                                                left: 8,
                                                top: 196,
                                                child: SizedBox(
                                                  width: 230,
                                                  height: 52,
                                                  child: Stack(
                                                    children: [
                                                      Positioned(
                                                        left: 0,
                                                        top: 32,
                                                        child: SizedBox(
                                                          width: 230,
                                                          height: 20,
                                                          child: Text(
                                                            '0 %',
                                                            textAlign: TextAlign
                                                                .center,
                                                            style: TextStyle(
                                                              color:
                                                                  Colors.white,
                                                              fontSize: 24,
                                                              fontFamily:
                                                                  'Press Start 2P',
                                                              fontWeight:
                                                                  FontWeight
                                                                      .w400,
                                                              height: 0.07,
                                                              letterSpacing:
                                                                  -2.40,
                                                            ),
                                                          ),
                                                        ),
                                                      ),
                                                      Positioned(
                                                        left: 0,
                                                        top: 0,
                                                        child: SizedBox(
                                                          width: 230,
                                                          height: 20,
                                                          child: Text(
                                                            'WIN RATE',
                                                            textAlign: TextAlign
                                                                .center,
                                                            style: TextStyle(
                                                              color:
                                                                  Colors.white,
                                                              fontSize: 24,
                                                              fontFamily:
                                                                  'Press Start 2P',
                                                              fontWeight:
                                                                  FontWeight
                                                                      .w400,
                                                              height: 0.07,
                                                              letterSpacing:
                                                                  0.96,
                                                            ),
                                                          ),
                                                        ),
                                                      ),
                                                    ],
                                                  ),
                                                ),
                                              ),
                                              Positioned(
                                                left: 238,
                                                top: 196,
                                                child: SizedBox(
                                                  width: 131,
                                                  height: 52,
                                                  child: Stack(
                                                    children: [
                                                      Positioned(
                                                        left: 0,
                                                        top: 32,
                                                        child: SizedBox(
                                                          width: 131,
                                                          height: 20,
                                                          child: Text(
                                                            'TIER',
                                                            textAlign: TextAlign
                                                                .center,
                                                            style: TextStyle(
                                                              color:
                                                                  Colors.white,
                                                              fontSize: 24,
                                                              fontFamily:
                                                                  'Press Start 2P',
                                                              fontWeight:
                                                                  FontWeight
                                                                      .w400,
                                                              height: 0.07,
                                                              letterSpacing:
                                                                  0.96,
                                                            ),
                                                          ),
                                                        ),
                                                      ),
                                                      Positioned(
                                                        left: 0,
                                                        top: 0,
                                                        child: SizedBox(
                                                          width: 131,
                                                          height: 20,
                                                          child: Text(
                                                            'RANK',
                                                            textAlign: TextAlign
                                                                .center,
                                                            style: TextStyle(
                                                              color:
                                                                  Colors.white,
                                                              fontSize: 24,
                                                              fontFamily:
                                                                  'Press Start 2P',
                                                              fontWeight:
                                                                  FontWeight
                                                                      .w400,
                                                              height: 0.07,
                                                              letterSpacing:
                                                                  0.96,
                                                            ),
                                                          ),
                                                        ),
                                                      ),
                                                    ],
                                                  ),
                                                ),
                                              ),
                                            ],
                                          ),
                                        ),
                                      ),
                                      Positioned(
                                        left: 0,
                                        top: 0,
                                        child: Container(
                                          width: 356,
                                          height: 356,
                                          decoration: ShapeDecoration(
                                            color: const Color(0xFFD9D9D9),
                                            shape: RoundedRectangleBorder(
                                                borderRadius:
                                                    BorderRadius.circular(8)),
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              Positioned(
                // bottom btn section
                left: 50,
                top: 862,
                child: SizedBox(
                  width: 1500,
                  height: 70,
                  child: Stack(
                    children: [
                      Positioned(
                        // ranking btn
                        left: 0,
                        top: 0,
                        child: SizedBox(
                          width: 220,
                          height: 70,
                          child: Stack(
                            children: [
                              Positioned(
                                left: 0,
                                top: 0,
                                child: Container(
                                  width: 220,
                                  height: 70,
                                  decoration: ShapeDecoration(
                                    color: const Color(0xFFC8C5C2),
                                    shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(16),
                                    ),
                                  ),
                                ),
                              ),
                              const Positioned(
                                left: 22,
                                top: 17,
                                child: SizedBox(
                                  width: 176,
                                  height: 36,
                                  child: Text(
                                    'RANKING',
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
                              ),
                            ],
                          ),
                        ),
                      ),
                      Positioned(
                        // createroom btn
                        left: 1175,
                        top: 0,
                        child: SizedBox(
                          width: 325,
                          height: 70,
                          child: Stack(
                            children: [
                              Positioned(
                                left: 0,
                                top: 0,
                                child: Container(
                                  width: 325,
                                  height: 70,
                                  decoration: ShapeDecoration(
                                    color: const Color(0xFFC8C5C2),
                                    shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(16),
                                    ),
                                  ),
                                ),
                              ),
                              const Positioned(
                                left: 12,
                                top: 17,
                                child: SizedBox(
                                  width: 300,
                                  height: 36,
                                  child: Text(
                                    'CREATE ROOM',
                                    textAlign: TextAlign.center,
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
                              ),
                            ],
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
            ],
          ),
        ),
      ],
    );
  }
}
