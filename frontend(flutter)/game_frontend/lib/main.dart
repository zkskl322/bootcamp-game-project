import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:game_frontend/backup/game_lobby.dart';
import 'package:game_frontend/backup/unsigned_main_page.dart';
import 'package:game_frontend/firebase_options.dart';
import 'package:game_frontend/lobbytest.dart';
import 'package:kakao_flutter_sdk_user/kakao_flutter_sdk_user.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  // await Firebase.initializeApp(
  //   // options: DefaultFirebaseOptions.currentPlatform,
  // );
  // KakaoSdk.init(nativeAppKey: '8afe56d45b7b2c0a4d1be6f2bcba8514');
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Unsigned start page',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const UnsignedMainPage(),
    );
  }
}





                  // Positioned(
                  //   left: 1106.77,
                  //   top: -39,
                  //   child: Transform(
                  //     transform: Matrix4.identity()
                  //       ..translate(0.0, 0.0)
                  //       ..rotateZ(0.26),
                  //     child: Container(
                  //       width: 751.85,
                  //       height: 1019.13,
                  //       decoration: ShapeDecoration(
                  //         image: const DecorationImage(
                  //           image: NetworkImage(
                  //               "https://via.placeholder.com/752x1019"),
                  //           fit: BoxFit.fill,
                  //         ),
                  //         shape: RoundedRectangleBorder(
                  //           borderRadius: BorderRadius.circular(30),
                  //         ),
                  //       ),
                  //     ),
                  //   ),
                  // ),