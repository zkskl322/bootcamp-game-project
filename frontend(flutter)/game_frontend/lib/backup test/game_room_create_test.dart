import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import 'dart:html' as html;
import 'package:game_frontend/backup/game_lobby.dart';
import 'package:game_frontend/backup/ingame_lobby.dart';
import 'package:game_frontend/backup/login_page.dart';

class CreateRoomTest extends StatefulWidget {
  @override
  _CreateRoomState createState() => _CreateRoomState();
}

class _CreateRoomState extends State<CreateRoomTest> {
  final Dio dio = Dio();
  final String? accessToken = html.window.localStorage['token'];
  final TextEditingController roomNameController = TextEditingController();
  final TextEditingController roomPasswordController = TextEditingController();
  final TextEditingController roomSizeController = TextEditingController();
  final TextEditingController targetGoalController = TextEditingController();

  Future<void> createRoombtn() async {
    if (accessToken == null) {
      html.window.alert('로그인이 필요합니다.');
      Navigator.push(
          context, MaterialPageRoute(builder: (context) => const Login_Page()));
      return;
    }
    try {
      final response = await dio.post(
        'http://localhost:8080/game/room/create',
        options: Options(headers: {
          'Content-Type': 'application/json',
          'access': accessToken,
        }),
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
          MaterialPageRoute(
              builder: (context) => IngameLobby2(
                  GameId: response.data, myRealUuid: '1', method: 'create')),
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
    return Scaffold(
      body: Stack(
        children: [
          Container(
            width: 1600,
            height: 960,
            color: Colors.white,
          ),
          Positioned(
            left: 0,
            top: 0,
            child: Container(
              width: 1600,
              height: 960,
              child: Stack(
                children: [
                  RoomSettingSection(
                    roomNameController: roomNameController,
                    roomPasswordController: roomPasswordController,
                    roomSizeController: roomSizeController,
                    targetGoalController: targetGoalController,
                  ),
                  Positioned(
                    left: 50,
                    top: 862,
                    child: Container(
                      width: 1500,
                      height: 70,
                      child: Row(
                        mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        children: [
                          ElevatedButton(
                            onPressed: () {
                              Navigator.push(
                                  context,
                                  MaterialPageRoute(
                                      builder: (context) => Game_Lobby()));
                            },
                            style: ElevatedButton.styleFrom(
                              foregroundColor: Colors.black,
                              backgroundColor: const Color(0xFFC8C5C2),
                              fixedSize: const Size(220, 70),
                              textStyle: const TextStyle(
                                fontSize: 24,
                                fontFamily: 'Press Start 2P',
                                fontWeight: FontWeight.w400,
                              ),
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(16),
                              ),
                            ),
                            child: const Text('BACK'),
                          ),
                          ElevatedButton(
                            onPressed: createRoombtn,
                            style: ElevatedButton.styleFrom(
                              foregroundColor: Colors.black,
                              backgroundColor: const Color(0xFFC8C5C2),
                              fixedSize: const Size(325, 70),
                              textStyle: const TextStyle(
                                fontSize: 24,
                                fontFamily: 'Press Start 2P',
                                fontWeight: FontWeight.w400,
                              ),
                              shape: RoundedRectangleBorder(
                                borderRadius: BorderRadius.circular(16),
                              ),
                            ),
                            child: const Text('CREATE ROOM'),
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
    );
  }
}

class RoomSettingSection extends StatelessWidget {
  final TextEditingController roomNameController;
  final TextEditingController roomPasswordController;
  final TextEditingController roomSizeController;
  final TextEditingController targetGoalController;

  const RoomSettingSection({
    Key? key,
    required this.roomNameController,
    required this.roomPasswordController,
    required this.roomSizeController,
    required this.targetGoalController,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Positioned(
      left: 50,
      top: 94,
      child: Container(
        width: 1500,
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
                width: 1431.80,
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
                    _buildSettingField(
                        'ROOM NAME', roomNameController, 85, 142),
                    _buildSettingField(
                        'ROOM PASSWORD', roomPasswordController, 735, 142),
                    _buildSettingField('ROOM SIZE', roomSizeController, 85, 397,
                        TextInputType.number),
                    _buildSettingField('TARGET GOAL', targetGoalController, 735,
                        397, TextInputType.number),
                  ],
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildSettingField(
      String label, TextEditingController controller, double left, double top,
      [TextInputType? keyboardType]) {
    return Positioned(
      left: left,
      top: top,
      child: Container(
        width: 612,
        height: 200,
        clipBehavior: Clip.antiAlias,
        decoration: ShapeDecoration(
          color: Colors.white,
          shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(8)),
        ),
        child: Column(
          children: [
            Padding(
              padding: const EdgeInsets.all(16.0),
              child: Text(
                label,
                style: const TextStyle(
                  color: Colors.black,
                  fontSize: 20,
                  fontFamily: 'Press Start 2P',
                  fontWeight: FontWeight.w400,
                  letterSpacing: 0.80,
                ),
              ),
            ),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 16.0),
              child: Material(
                color: Colors.transparent,
                child: TextField(
                  controller: controller,
                  style: const TextStyle(color: Colors.black),
                  decoration: InputDecoration(
                    border: const OutlineInputBorder(),
                    labelText: 'Enter $label',
                  ),
                  keyboardType: keyboardType,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
