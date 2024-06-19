import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import 'package:game_frontend/backup/game_lobby.dart';
import 'package:game_frontend/backup/ingame_lobby.dart';
import 'package:game_frontend/backup/login_page.dart';
import 'package:game_frontend/backup/signed_main_page.dart';
import 'package:game_frontend/dto/gameroom-dto.dart';
import "dart:html";


class Create_Room extends StatelessWidget {
  const Create_Room({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData.dark().copyWith(
        scaffoldBackgroundColor: const Color.fromARGB(255, 18, 32, 47),
      ),
      home: Scaffold(
        body: ListView(children: [
          CreateRoom(),
        ]),
      ),
    );
  }
}

class CreateRoom extends StatefulWidget {
  @override
  _CreateRoomState createState() => _CreateRoomState();
}

class _CreateRoomState extends State<CreateRoom> {
  final Dio dio = Dio();
  List<GameRoomsDTO> _gamerooms = [];

  final String? accessToken = window.localStorage['token'];
  final TextEditingController roomNameController = TextEditingController();
  final TextEditingController roomPasswordController = TextEditingController();
  final TextEditingController roomSizeController = TextEditingController();
  final TextEditingController targetGoalController = TextEditingController();

  @override
  void initState() {
    super.initState();
  }





  Future<void> createRoombtn() async {
    if (accessToken == null) {
      window.alert('로그인이 필요합니다.');
      Navigator.push(
          context, MaterialPageRoute(builder: (context) => const Login_Page()));
    }
    try {
      final response = await dio.post(
        'http://localhost:8080/game/room/create',
        options: Options(
          headers: {
            'Content-Type': 'application/json',
            'access': accessToken,
          }
        ),
        data: {
          'room_password': roomPasswordController.text,
          'room_name': roomNameController.text,
          'room_size': int.parse(roomSizeController.text),
          'room_goal': int.parse(targetGoalController.text),
        },
      );

      if (response.statusCode == 200) {
        Navigator.push(
          context,
          MaterialPageRoute(builder: (context) => IngameLobby2(GameId: response.data, myRealUuid: '1', method: 'create')),
        );
        print('Room created successfully');
      } else {
        print('Failed to create room: ${response.statusCode}');
      }
    } catch (e) {
      print('Error: $e');
    }
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
              Positioned(
                //middle setting section
                left: 50,
                top: 94,
                child: Container(
                  width: 1500,
                  height: 748,
                  clipBehavior: Clip.antiAlias,
                  decoration: ShapeDecoration(
                    color: Color(0xFF080808),
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
                          width: 1431.80,
                          height: 680.17,
                          clipBehavior: Clip.antiAlias,
                          decoration: ShapeDecoration(
                            color: Color(0xFF1B1B1B),
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(2.83),
                            ),
                          ),
                          child: Stack(
                            children: [
                              const Positioned(
                                //gamerooom text
                                left: 516,
                                top: 20,
                                child: Text(
                                  'ROOM SETTING',
                                  style: TextStyle(
                                    color: Colors.white,
                                    fontSize: 32,
                                    fontFamily: 'Press Start 2P',
                                    fontWeight: FontWeight.w400,
                                    height: 0.04,
                                    letterSpacing: 1.28,
                                  ),
                                ),
                              ),
                              Positioned(
                                //roomname setting
                                left: 85,
                                top: 142,
                                child: Container(
                                  width: 612,
                                  height: 200,
                                  clipBehavior: Clip.antiAlias,
                                  decoration: ShapeDecoration(
                                    color: Colors.white,
                                    shape: RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(8)),
                                  ),
                                  child: Column(
                                    children: [
                                      const Padding(
                                        padding: EdgeInsets.all(16.0),
                                        child: Text(
                                          'ROOM NAME',
                                          style: TextStyle(
                                            color: Colors.black,
                                            fontSize: 20,
                                            fontFamily: 'Press Start 2P',
                                            fontWeight: FontWeight.w400,
                                            letterSpacing: 0.80,
                                          ),
                                        ),
                                      ),
                                      Padding(
                                        padding: const EdgeInsets.symmetric(
                                            horizontal: 16.0),
                                        child: TextField(
                                          controller: roomNameController,
                                          style: const TextStyle(
                                              color: Colors.black),
                                          decoration: const InputDecoration(
                                            border: OutlineInputBorder(),
                                            labelText: 'Enter Room Name',
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                              Positioned(
                                //roompassword setting
                                left: 735,
                                top: 142,
                                child: Container(
                                  width: 612,
                                  height: 200,
                                  clipBehavior: Clip.antiAlias,
                                  decoration: ShapeDecoration(
                                    color: Colors.white,
                                    shape: RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(8)),
                                  ),
                                  child: Column(
                                    children: [
                                      const Padding(
                                        padding: EdgeInsets.all(16.0),
                                        child: Text(
                                          'ROOM PASSWORD',
                                          style: TextStyle(
                                            color: Colors.black,
                                            fontSize: 20,
                                            fontFamily: 'Press Start 2P',
                                            fontWeight: FontWeight.w400,
                                            letterSpacing: 0.80,
                                          ),
                                        ),
                                      ),
                                      Padding(
                                        padding: const EdgeInsets.symmetric(
                                            horizontal: 16.0),
                                        child: TextField(
                                          controller: roomPasswordController,
                                          style: const TextStyle(
                                              color: Colors.black),
                                          decoration: const InputDecoration(
                                            border: OutlineInputBorder(),
                                            labelText: 'Enter Room Password',
                                          ),
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                              Positioned(
                                //roomsize setting
                                left: 85,
                                top: 397,
                                child: Container(
                                  width: 612,
                                  height: 200,
                                  clipBehavior: Clip.antiAlias,
                                  decoration: ShapeDecoration(
                                    color: Colors.white,
                                    shape: RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(8)),
                                  ),
                                  child: Column(
                                    children: [
                                      const Padding(
                                        padding: EdgeInsets.all(16.0),
                                        child: Text(
                                          'ROOM SIZE',
                                          style: TextStyle(
                                            color: Colors.black,
                                            fontSize: 20,
                                            fontFamily: 'Press Start 2P',
                                            fontWeight: FontWeight.w400,
                                            letterSpacing: 0.80,
                                          ),
                                        ),
                                      ),
                                      Padding(
                                        padding: const EdgeInsets.symmetric(
                                            horizontal: 16.0),
                                        child: TextField(
                                          controller: roomSizeController,
                                          style: const TextStyle(
                                              color: Colors.black),
                                          decoration: const InputDecoration(
                                            border: OutlineInputBorder(),
                                            labelText: 'Enter Room Size',
                                          ),
                                          keyboardType: TextInputType.number,
                                        ),
                                      ),
                                    ],
                                  ),
                                ),
                              ),
                              Positioned(
                                //targetgoal setting
                                left: 735,
                                top: 397,
                                child: Container(
                                  width: 612,
                                  height: 200,
                                  clipBehavior: Clip.antiAlias,
                                  decoration: ShapeDecoration(
                                    color: Colors.white,
                                    shape: RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(8)),
                                  ),
                                  child: Column(
                                    children: [
                                      const Padding(
                                        padding: EdgeInsets.all(16.0),
                                        child: Text(
                                          'TARGET GOAL',
                                          style: TextStyle(
                                            color: Colors.black,
                                            fontSize: 20,
                                            fontFamily: 'Press Start 2P',
                                            fontWeight: FontWeight.w400,
                                            letterSpacing: 0.80,
                                          ),
                                        ),
                                      ),
                                      Padding(
                                        padding: const EdgeInsets.symmetric(
                                            horizontal: 16.0),
                                        child: TextField(
                                          controller: targetGoalController,
                                          style: const TextStyle(
                                              color: Colors.black),
                                          decoration: const InputDecoration(
                                            border: OutlineInputBorder(),
                                            labelText: 'Enter Target Goal',
                                          ),
                                          keyboardType: TextInputType.number,
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
                //bottom btn section
                left: 50,
                top: 862,
                child: SizedBox(
                  width: 1500,
                  height: 70,
                  child: Stack(
                    children: [
                      Positioned(
                        // back home btn
                        left: 0,
                        top: 0,
                        child: InkWell(
                          onTap: () {
                            Navigator.push(
                                context,
                                MaterialPageRoute(
                                    builder: (context) => Game_Lobby()));
                            print('Back(Game Lobby)');
                          },
                          borderRadius: BorderRadius.circular(16),
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
                                      color: Color(0xFFC8C5C2),
                                      shape: RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(16),
                                      ),
                                    ),
                                  ),
                                ),
                                const Positioned(
                                  left: 60,
                                  top: 17,
                                  child: SizedBox(
                                    width: 100,
                                    height: 36,
                                    child: const Center(
                                      child: Text(
                                        'BACK',
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
                              ],
                            ),
                          ),
                        ),
                      ),
                      Positioned(
                        left: 1175,
                        top: 0,
                        child: SizedBox(
                          width: 325,
                          height: 70,
                          child: InkWell(
                            onTap: createRoombtn,
                            borderRadius: BorderRadius.circular(16),
                            child: Container(
                              width: 325,
                              height: 70,
                              decoration: BoxDecoration(
                                color: Color(0xFFC8C5C2),
                                borderRadius: BorderRadius.circular(16),
                              ),
                              child: const Center(
                                child: Text(
                                  'CREATE ROOM',
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
