import 'package:flutter/material.dart';
import 'package:game_frontend/backup/ingame_lobby.dart';

class GameResultPage extends StatefulWidget {
  final int gameId;
  final String playerId;
  final int score1;
  final int score2;

  const GameResultPage({
    required this.gameId,
    required this.playerId,
    required this.score1,
    required this.score2,
  });

  @override
  State<GameResultPage> createState() => _GameResultPage();
}



class _GameResultPage extends State<GameResultPage> {
  String GameReultString = "";
  
  @override
  void initState() {
    if (widget.score1 > widget.score2) {
      if (widget.playerId == '1') {
        GameReultString = "You Win!";
      } else {
        GameReultString = "You Lose!";
      }
    } else if (widget.score1 < widget.score2) {
      if (widget.playerId == '2') {
        GameReultString = "You Win!";
      } else {
        GameReultString = "You Lose!";
      } 
    } else {
      GameReultString = "Draw!";
    }
  }


  @override
  Widget build(BuildContext context) {
    final Size screenSize = MediaQuery.of(context).size;
    return Scaffold(
      body: 
        Column(
        children: [
          Container(
            width: screenSize.width,
            height: screenSize.height,
            decoration: const BoxDecoration(color: Colors.white),
            child: Row(
              mainAxisSize: MainAxisSize.min,
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                Column(
                  mainAxisSize: MainAxisSize.min,
                  mainAxisAlignment: MainAxisAlignment.start,
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    SizedBox(
                      height: 143,
                      child: Text(
                        GameReultString,
                        textAlign: TextAlign.center,
                        style: const TextStyle(
                          color: Color(0xFFEA4B4B),
                          fontSize: 96,
                          fontFamily: 'Literata',
                          fontWeight: FontWeight.w500,
                          height: 0,
                          letterSpacing: -1.92,
                        ),
                      ),
                    ),
                    ElevatedButton(
                      onPressed:   
                        () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) => IngameLobby2(
                                GameId: widget.gameId,
                                myRealUuid: widget.playerId,
                                method: "replay",
                              ),
                            ),
                          );
                        },
                      child: const Text('Play Again'),
                    ),
                    
                  ],
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }
}