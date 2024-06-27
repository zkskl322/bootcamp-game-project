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
      ..color = const Color.fromARGB(255, 0, 0, 0)
      ..style = PaintingStyle.fill;

    Paint paintPlayer1 = Paint()
      ..color = Colors.blue
      ..style = PaintingStyle.fill;

    Paint paintPlayer2 = Paint()
      ..color = Color.fromARGB(255, 253, 2, 2)
      ..style = PaintingStyle.fill;
    
    Paint fieldPaint = Paint()
      ..color = Colors.green
      ..style = PaintingStyle.fill;

    Paint paintLine = Paint()
      ..color = Color.fromARGB(255, 255, 255, 255)
      ..style = PaintingStyle.stroke
      ..strokeWidth = 2;
    
    Paint paintfieldLine = Paint()
      ..color = Color.fromARGB(255, 0, 0, 0)
      ..style = PaintingStyle.stroke
      ..strokeWidth = 2;

    Paint paintGoalline = Paint()
      ..color = Colors.red
      ..style = PaintingStyle.stroke
      ..strokeWidth = 1;

    TextStyle textStyle = const TextStyle(
      color: Colors.white,
      fontSize: 12,
    );

    canvas.drawRect(Offset.zero & size, fieldPaint);

    canvas.drawLine(
        const Offset(550, 0), const Offset(550, 700), paintLine); // 중앙선
    canvas.drawCircle(const Offset(550, 350), 90, paintLine); // 중앙원
    canvas.drawCircle(const Offset(550, 350), 2, paintLine); // 중앙원 중심

    // 경기장 테두리
    canvas.drawLine(const Offset(1, 1), const Offset(1099, 1), paintfieldLine);
    canvas.drawLine(const Offset(1, 699), const Offset(1099, 699), paintfieldLine);
    canvas.drawLine(const Offset(1, 1), const Offset(1, 699), paintfieldLine);
    canvas.drawLine(const Offset(1099, 1), const Offset(1099, 699), paintfieldLine);


    // 패널티 에어리어
    canvas.drawLine(const Offset(0, 150), const Offset(170, 150), paintLine);
    canvas.drawLine(const Offset(0, 550), const Offset(170, 550), paintLine);
    canvas.drawLine(const Offset(170, 150), const Offset(170, 550), paintLine);

    canvas.drawLine(
        const Offset(930, 150), const Offset(1100, 150), paintLine);
    canvas.drawLine(
        const Offset(930, 550), const Offset(1100, 550), paintLine);
    canvas.drawLine(const Offset(930, 150), const Offset(930, 550), paintLine);


    // 골킥 에어리어
    canvas.drawLine(
        const Offset(0, 260), const Offset(50, 260), paintLine); // 왼쪽 상단 선
    canvas.drawLine(
        const Offset(0, 440), const Offset(50, 440), paintLine); // 왼쪽 하단 선
    canvas.drawLine(
        const Offset(50, 260), const Offset(50, 440), paintLine); // 왼쪽 선

    canvas.drawLine(
        const Offset(1050, 260), const Offset(1100, 260), paintLine); // 오른쪽 상단 선
    canvas.drawLine(
        const Offset(1050, 440), const Offset(1100, 440), paintLine); // 오른쪽 하단 선
    canvas.drawLine(
        const Offset(1050, 260), const Offset(1050, 440), paintLine); // 오른쪽 선

    // 패널티 포인트
    canvas.drawCircle(const Offset(110, 350), 2, paintLine); // 왼쪽 패널티 포인트
    canvas.drawCircle(const Offset(990, 350), 2, paintLine); // 오른쪽 패널티 포인트

    // 패널티 아크 : 패널티 포인트에서 9m 떨어진 지점을 가까운 골대 반대로 패널티 에어리어 닿기 전까지 그림
    canvas.drawArc(Rect.fromCircle(center: const Offset(110, 350), radius: 90), 0, 0.80, false, paintLine);
    canvas.drawArc(Rect.fromCircle(center: const Offset(110, 350), radius: 90), 5.45, 0.80, false, paintLine);
    canvas.drawArc(Rect.fromCircle(center: const Offset(990, 350), radius: 90), 3.14, 0.80, false, paintLine);
    canvas.drawArc(Rect.fromCircle(center: const Offset(990, 350), radius: 90), 2.36, 0.80, false, paintLine);

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


    canvas.drawCircle(Offset(ballX, ballY), 10, paintBall);
    canvas.drawCircle(Offset(player1X, player1Y), 10, paintPlayer1);
    canvas.drawCircle(Offset(player2_X, player2_Y), 10, paintPlayer2);

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
