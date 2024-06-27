import 'package:flutter/material.dart';
import 'package:game_frontend/Game/game_room.dart';

class LobbyPage extends StatefulWidget {
  @override
  State<LobbyPage> createState() => _LobbyPageState();
}

class _LobbyPageState extends State<LobbyPage> {
  final TextEditingController _uuidController = TextEditingController();
  final TextEditingController _gameIdController = TextEditingController();

  @override
  Widget build(BuildContext context) {
    _uuidController.text = '1';
    _gameIdController.text = '1';
    return Scaffold(
      appBar: AppBar(
        title: const Text('Soccer Game'),
      ),
      body: Column(
        children: [
          TextField(
            controller: _gameIdController,
            decoration: const InputDecoration(
              border: OutlineInputBorder(),
              labelText: 'Game ID',
            ),
          ),
          TextField(
            controller: _uuidController,
            decoration: const InputDecoration(
              border: OutlineInputBorder(),
              labelText: 'UUID',
            ),
          ),
          ElevatedButton(
            onPressed: () {
              Navigator.push(
                context,
                MaterialPageRoute(
                  builder: (context) => GameRoomPage(
                    GameId: _gameIdController.text,
                    myUuid: _uuidController.text,
                  ),
                ),
              );
            },
            child: const Text('Join Game'),
          ),
        ],
      ),
    );
  }
}
