import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/material.dart';
import 'package:game_frontend/Game/game_room.dart';
import 'package:game_frontend/backup/game_lobby.dart';
import 'package:game_frontend/backup/game_result.dart';
import 'package:game_frontend/backup/ingame_lobby.dart';
import 'package:game_frontend/backup/unsigned_main_page.dart';
import 'package:game_frontend/firebase_options.dart';
import 'package:kakao_flutter_sdk_user/kakao_flutter_sdk_user.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
  KakaoSdk.init(nativeAppKey: '8afe56d45b7b2c0a4d1be6f2bcba8514');
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
      // home: GameResultPage(playerId: "2", score1: 1, score2: 1, gameId: int.parse("1")),
      // home: GameRoomPage(myUuid: '1', GameId: 11),
      // home: IngameLobby2(myUuid: '1', GameId: 11, method: "1"),
      // home: Game_Lobby(),
      home: UnsignedMainPage(),
    );
  }
}
