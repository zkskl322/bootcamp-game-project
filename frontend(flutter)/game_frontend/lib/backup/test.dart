import 'dart:convert';
import 'dart:html' as html;
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import 'package:game_frontend/Game/lobby.dart';
import 'package:game_frontend/backup%20test/game_room_create_test.dart';
import 'package:game_frontend/backup/game_room_create.dart';
import 'package:game_frontend/backup/ingame_lobby.dart';
import 'package:game_frontend/backup/signed_main_page.dart';
import 'package:game_frontend/backup/unsigned_main_page.dart';
import 'package:game_frontend/dto/gameroom-dto.dart';

class Game_Lobby extends StatelessWidget {
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
  const GameRoom({Key? key}) : super(key: key);

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
  final TextEditingController _roomPasswordController = TextEditingController();

  @override
  void initState() {
    super.initState();
    fetchGameRooms();
  }

  void _handleLogoutButton(BuildContext context) {
    _logout().then((_) {
      Navigator.push(
        context,
        MaterialPageRoute(builder: (context) => const UnsignedMainPage()),
      );
    }).catchError((error) {
      print("Logout error: $error");
    });
  }

  Future<void> _logout() async {
    final String? accessToken = html.window.localStorage['token'];

    if (accessToken == null) {
      print('access token null');
      return;
    }

    try {
      final Response response = await dio.post(
        'http://localhost:8080/user/logout',
        options: Options(
          headers: {
            'Authorization': 'Bearer $accessToken',
          },
        ),
      );
      if (response.statusCode == 200) {
        print("logout successfully");
      } else {
        print("logout fail: ${response.statusCode}");
      }
    } catch (e) {
      print("error: $e");
      throw e;
    }
  }

  Future<void> fetchGameRooms() async {
    final String? accessToken = html.window.localStorage['token'];

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

      if (response.statusCode == 200) {
        Map<String, dynamic> jsonData = response.data;

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
    final String? accessToken = html.window.localStorage['token'];
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
    final String? accessToken = html.window.localStorage['token'];
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

  Widget _buildButton(String text, VoidCallback onPressed,
      {bool isTop = false}) {
    return ElevatedButton(
      onPressed: onPressed,
      style: ElevatedButton.styleFrom(
        backgroundColor:
            isTop ? const Color(0xFF758CFF) : const Color(0xFFC8C5C2),
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(16)),
        minimumSize: Size(isTop ? 190 : 325, isTop ? 60 : 70),
      ),
      child: Text(
        text,
        style: const TextStyle(
          color: Colors.black,
          fontSize: 24,
          fontFamily: 'Press Start 2P',
          fontWeight: FontWeight.w400,
        ),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        color: const Color(0xFFF2F2F2),
        child: Stack(
          children: [
            // 상단 버튼
            Positioned(
              top: 20,
              left: 20,
              right: 20,
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  _buildButton('HOME', () {
                    Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => Main_Signed_Page()));
                  }),
                  _buildButton('LOGOUT', () => _handleLogoutButton(context)),
                ],
              ),
            ),
            // 메인 콘텐츠
            Positioned(
              top: 100,
              left: 20,
              right: 20,
              bottom: 100,
              child: Row(
                children: [
                  // 사용자 정보
                  Expanded(
                    flex: 1,
                    child: Container(
                      decoration: BoxDecoration(
                        color: Colors.black,
                        borderRadius: BorderRadius.circular(16),
                      ),
                      child: Column(
                        children: [
                          Container(
                            height: 356,
                            color: Colors.grey,
                          ),
                          const SizedBox(height: 20),
                          Text('USERNAME : $myNickname',
                              style: const TextStyle(color: Colors.white)),
                          Text('WIN : $myWinScore',
                              style: const TextStyle(color: Colors.white)),
                          Text('LOSE : $myLoseScore',
                              style: const TextStyle(color: Colors.white)),
                          Text('DRAW : $myDrawScore',
                              style: const TextStyle(color: Colors.white)),
                          const Text('Win Rate : 0%',
                              style: TextStyle(color: Colors.white)),
                          Text('TIER : $myTier',
                              style: const TextStyle(color: Colors.white)),
                        ],
                      ),
                    ),
                  ),
                  const SizedBox(width: 20),
                  // 게임룸 목록
                  Expanded(
                    flex: 2,
                    child: Container(
                      decoration: BoxDecoration(
                        color: Colors.black,
                        borderRadius: BorderRadius.circular(16),
                      ),
                      child: Column(
                        children: [
                          // 게임룸 헤더
                          const Padding(
                            padding: EdgeInsets.all(8.0),
                            child: Row(
                              children: [
                                Expanded(
                                    child: Text('No.',
                                        style: TextStyle(color: Colors.white))),
                                Expanded(
                                    flex: 2,
                                    child: Text('Room name',
                                        style: TextStyle(color: Colors.white))),
                                Expanded(
                                    child: Text('name',
                                        style: TextStyle(color: Colors.white))),
                              ],
                            ),
                          ),
                          // 게임룸 목록
                          Expanded(
                            child: ListView.builder(
                              itemCount: _gamerooms.length,
                              itemBuilder: (context, index) {
                                final room = _gamerooms[index];
                                return Container(
                                  color: Colors.white,
                                  margin: const EdgeInsets.symmetric(
                                      vertical: 4, horizontal: 8),
                                  child: Row(
                                    children: [
                                      Expanded(child: Text('${index + 1}')),
                                      Expanded(
                                          flex: 2, child: Text(room.roomName)),
                                      Expanded(child: Text(room.roomOwner)),
                                      ElevatedButton(
                                        onPressed: () =>
                                            joinGameRoombtn(room.roomId),
                                        style: ElevatedButton.styleFrom(
                                            backgroundColor: Colors.grey[800]),
                                        child: const Text('JOIN'),
                                      ),
                                    ],
                                  ),
                                );
                              },
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ],
              ),
            ),
            // 하단 버튼
            Positioned(
              bottom: 20,
              left: 20,
              right: 20,
              child: Row(
                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                children: [
                  _buildButton('RANKING', showRankingModal),
                  _buildButton('CREATE ROOM', () {
                    Navigator.push(
                        context,
                        MaterialPageRoute(
                            builder: (context) => CreateRoomTest()));
                  }),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }
}
