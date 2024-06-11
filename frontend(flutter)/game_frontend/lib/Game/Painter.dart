import 'package:flutter/material.dart';
import 'package:game_frontend/Game/game_instance.dart';

// 경기장
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
    double player1X = currentGame.player1_x * scale;
    double player1Y = currentGame.player1_y * scale;
    int player1ControlPlayer = currentGame.player1_control_player;
    // -------------------------------------- //

    // player2 ------------------------------ //
    double player2_X = currentGame.player2_x * scale;
    double player2_Y = currentGame.player2_y * scale;
    int player2ControlPlayer = currentGame.player2_control_player;
    // -------------------------------------- //

    Paint paintBall = Paint()
      ..color = Colors.red
      ..style = PaintingStyle.fill;

    Paint paintPlayer1 = Paint()
      ..color = Colors.blue
      ..style = PaintingStyle.fill;

    Paint paintPlayer2 = Paint()
      ..color = Colors.green
      ..style = PaintingStyle.fill;

    Paint paintLine = Paint()
      ..color = const Color.fromARGB(255, 139, 139, 139)
      ..style = PaintingStyle.stroke
      ..strokeWidth = 1;

    Paint paintGoalline = Paint()
      ..color = Colors.red
      ..style = PaintingStyle.stroke
      ..strokeWidth = 1;

    TextStyle textStyle = const TextStyle(
      color: Colors.white,
      fontSize: 12,
    );

    canvas.drawCircle(Offset(ballX, ballY), 10, paintBall);
    canvas.drawCircle(Offset(player1X, player1Y), 10, paintPlayer1);
    canvas.drawCircle(Offset(player2_X, player2_Y), 10, paintPlayer2);

    canvas.drawLine(
        const Offset(550, 0), const Offset(550, 700), paintLine); // 중앙선
    canvas.drawCircle(const Offset(550, 350), 90, paintLine); // 중앙원
    canvas.drawCircle(const Offset(550, 350), 2, paintLine); // 중앙원 중심

    canvas.drawLine(
        const Offset(0, 0), const Offset(0, 700), paintLine); // 왼쪽 선
    canvas.drawLine(
        const Offset(1100, 0), const Offset(1100, 700), paintLine); // 오른쪽 선
    canvas.drawLine(
        const Offset(0, 0), const Offset(1100, 0), paintLine); // 위쪽 선
    canvas.drawLine(
        const Offset(0, 700), const Offset(1100, 700), paintLine); // 아래쪽 선

    canvas.drawLine(
        const Offset(0, 0), const Offset(0, 300), paintLine); // 왼쪽 상단 선
    canvas.drawLine(
        const Offset(0, 400), const Offset(0, 700), paintLine); // 왼쪽 하단 선
    canvas.drawLine(
        const Offset(1100, 0), const Offset(1100, 300), paintLine); // 오른쪽 상단 선
    canvas.drawLine(const Offset(1100, 400), const Offset(1100, 700),
        paintLine); // 오른쪽 하단 선

    canvas.drawLine(
        const Offset(0, 300), const Offset(0, 400), paintGoalline); // 골대
    canvas.drawLine(
        const Offset(1100, 300), const Offset(1100, 400), paintGoalline); // 골대

    int i = -1;
    for (var player in currentGame.player1_players.players) {
      i++;
      if (i == player1ControlPlayer) continue;
      double player1X = player.player_x * scale;
      double player1Y = player.player_y * scale;
      canvas.drawCircle(Offset(player1X, player1Y), 10, paintPlayer1);

      // 번호 작성
      final textSpan = TextSpan(
        text: '$i',
        style: textStyle,
      );
      final textPainter = TextPainter(
        text: textSpan,
        textAlign: TextAlign.center,
        textDirection: TextDirection.ltr,
      );
      textPainter.layout(minWidth: 0, maxWidth: size.width);
      textPainter.paint(
          canvas,
          Offset(player1X - textPainter.width / 2,
              player1Y - textPainter.height / 2));
    }
    i = -1;
    for (var player in currentGame.player2_players.players) {
      i++;
      if (i == player2ControlPlayer) continue;
      double player2X = player.player_x * scale;
      double player2Y = player.player_y * scale;
      // 위에 1~5번호 작성
      canvas.drawCircle(Offset(player2X, player2Y), 10, paintPlayer2);

      // 번호 작성
      final textSpan = TextSpan(
        text: '$i',
        style: textStyle,
      );
      final textPainter = TextPainter(
        text: textSpan,
        textAlign: TextAlign.center,
        textDirection: TextDirection.ltr,
      );
      textPainter.layout(minWidth: 0, maxWidth: size.width);
      textPainter.paint(
          canvas,
          Offset(player2X - textPainter.width / 2,
              player2Y - textPainter.height / 2));
    }
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) {
    // 항상 다시 그리도록 설정합니다.
    return true;
  }
}

// 경기장 밖에 구역
class MyPainter2 extends CustomPainter {
  @override
  void paint(Canvas canvas, Size size) {
    Paint paintLine = Paint()
      ..color = const Color.fromARGB(255, 139, 139, 139)
      ..style = PaintingStyle.stroke
      ..strokeWidth = 1;

    canvas.drawLine(const Offset(50, 300), const Offset(100, 300), paintLine);
    canvas.drawLine(const Offset(50, 400), const Offset(100, 400), paintLine);
    canvas.drawLine(const Offset(50, 300), const Offset(50, 400), paintLine);
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) {
    return true;
  }
}

// 경기장 밖에 구역 (오른쪽)
class MyPainter3 extends CustomPainter {
  @override
  void paint(Canvas canvas, Size size) {
    Paint paintLine = Paint()
      ..color = const Color.fromARGB(255, 139, 139, 139)
      ..style = PaintingStyle.stroke
      ..strokeWidth = 1;

    canvas.drawLine(const Offset(0, 300), const Offset(50, 300), paintLine);
    canvas.drawLine(const Offset(0, 400), const Offset(50, 400), paintLine);
    canvas.drawLine(const Offset(50, 300), const Offset(50, 400), paintLine);
  }

  @override
  bool shouldRepaint(CustomPainter oldDelegate) {
    return true;
  }
}
