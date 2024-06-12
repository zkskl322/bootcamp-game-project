import 'package:flutter/material.dart';
import 'package:dio/dio.dart';
import 'package:game_frontend/dto/gameroom-dto.dart';

void main() {
  runApp(const FigmaToCodeApp());
}

// Generated by: https://www.figma.com/community/plugin/842128343887142055/
class FigmaToCodeApp extends StatelessWidget {
  const FigmaToCodeApp({super.key});

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

class _CreateRoomState extends State<CreateRoom>{
  final Dio dio = Dio();
  List<GameroomDTO> _gamerooms = [];

  final TextEditingController roomNameController = TextEditingController();
  final TextEditingController roomPasswordController = TextEditingController();
  final TextEditingController roomSizeController = TextEditingController();
  final TextEditingController targetGoalController = TextEditingController();

  @override
  void initState() {
    super.initState();
    // fetchGameRooms();
  }

  // Future<void> fetchGameRooms() async { 
  //   try {
  //     final Response response = await dio.get('http://localhost:8080/page/main');
  //     if (response.statusCode == 200) {
  //       List<dynamic> data = response.data;
  //       setState(() {
  //         _gamerooms = data.map((json) => GameroomDTO.fromJson(json)).toList();
  //       });
  //     } else {
  //       setState(() {
  //         print('Error: ${response.statusCode}');
  //       });
  //     }
  //   } catch (e) {
  //     setState(() {
  //       print('Error: $e');
  //     });
  //   }
  // }
//  private String room_password;
//     private String room_name;
//     private Long room_size;
//     private Long room_goal;

//     public GameRoomDTO(String room_password, String room_name, Long room_size, Long room_goal) {
//         this.room_password = room_password;
//         this.room_name = room_name;
//         this.room_size = room_size;
//         this.room_goal = room_goal;
//     }

  Future<void> createRoombtn() async {
    try {
      final response = await dio.post(
        'http://localhost:8080/game/room/create',
        options: Options(headers: {
          'Content-Type': 'application/json',
        }),
        data: {
          'room_password': roomPasswordController.text,
          'room_name': roomNameController.text,
          'room_size': int.parse(roomSizeController.text),
          'room_goal': int.parse(targetGoalController.text),
        },
      );

      if (response.statusCode == 200) {
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
              Positioned( //top btn section
                left: 50,
                top: 20,
                child: SizedBox(
                  width: 1500,
                  height: 60,
                  child: Stack(
                    children: [
                      Positioned(
                        left: 1310,
                        top: 0,
                        child: SizedBox(
                          width: 190,
                          height: 60,
                          child: Stack(
                            children: [
                              Positioned(
                                left: 0,
                                top: 0,
                                child: Container(
                                  width: 190,
                                  height: 60,
                                  decoration: ShapeDecoration(
                                    color: Color(0xFF758CFF),
                                    shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(16),
                                    ),
                                  ),
                                ),
                              ),
                              const Positioned(
                                left: 18,
                                top: 15,
                                child: SizedBox(
                                  width: 153,
                                  height: 29,
                                  child: Text(
                                    'LOGOUT',
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
                      Positioned(
                        left: 0,
                        top: 0,
                        child: Container(
                          width: 190,
                          height: 60,
                          child: Stack(
                            children: [
                              Positioned(
                                left: 0,
                                top: 0,
                                child: Container(
                                  width: 190,
                                  height: 60,
                                  decoration: ShapeDecoration(
                                    color: Color(0xFF758CFF),
                                    shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(16),
                                    ),
                                  ),
                                ),
                              ),
                              const Positioned(
                                left: 22,
                                top: 18,
                                child: SizedBox(
                                  width: 146,
                                  height: 23,
                                  child: Text(
                                    'HOME',
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
              Positioned( //middle setting section
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
                              const Positioned( //gamerooom text
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
                              Positioned( //roomname setting
                                left: 85,
                                top: 142,
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
                                        padding: const EdgeInsets.symmetric(horizontal: 16.0),
                                        child: TextField(
                                          controller: roomNameController,
                                          style: const TextStyle(color: Colors.black),
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
                              Positioned( //roompassword setting
                                left: 735,
                                top: 142,
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
                                        padding: const EdgeInsets.symmetric(horizontal: 16.0),
                                        child: TextField(
                                          controller: roomPasswordController,
                                          style: const TextStyle(color: Colors.black),
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
                              Positioned( //roomsize setting
                                left: 85,
                                top: 397,
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
                                        padding: const EdgeInsets.symmetric(horizontal: 16.0),
                                        child: TextField(
                                          controller: roomSizeController,
                                          style: const TextStyle(color: Colors.black),
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
                              Positioned( //targetgoal setting
                                left: 735,
                                top: 397,
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
                                        padding: const EdgeInsets.symmetric(horizontal: 16.0),
                                        child: TextField(
                                          controller: targetGoalController,
                                          style: const TextStyle(color: Colors.black),
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
              Positioned( //bottom btn section
                left: 50,
                top: 862,
                child: SizedBox(
                  width: 1500,
                  height: 70,
                  child: Stack(
                    children: [
                      Positioned( //back home btn
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
                                  child: Text(
                                    'BACK',
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
                      Positioned( //create room btn
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
                                    color: Color(0xFFC8C5C2),
                                    shape: RoundedRectangleBorder(
                                      borderRadius: BorderRadius.circular(16),
                                    ),
                                  ),
                                ),
                              ),
                              Positioned(
                                left: 25,
                                top: 17,
                                child: SizedBox(
                                  width: 274,
                                  height: 36,
                                  child: TextButton(
                                    onPressed: createRoombtn,
                                    child: const Text(
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