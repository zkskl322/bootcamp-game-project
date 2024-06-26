import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:game_frontend/Game/lobby.dart';
import 'package:game_frontend/backup%20test/game_lobby_test.dart';
import 'package:game_frontend/backup/game_lobby.dart';
import 'package:game_frontend/backup/ingame_result.dart';
import 'package:game_frontend/backup/ingame_lobby.dart';
import 'package:game_frontend/backup%20test/login_page_test.dart';
import 'package:game_frontend/backup/unsigned_main_page.dart';
import 'package:game_frontend/firebase_options.dart';
import 'package:kakao_flutter_sdk_user/kakao_flutter_sdk_user.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
  KakaoSdk.init(javaScriptAppKey: '038930029f4c94d4bf024f6553f1ba23');
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
      home: UnsignedMainPage(),
      // home: Game_Lobby(),
    );
  }
}
