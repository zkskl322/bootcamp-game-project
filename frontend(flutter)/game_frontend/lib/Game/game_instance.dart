class GamePlayer {
  final double player_x;
  final double player_y;
  final int? player_direction;

  GamePlayer({
    required this.player_x,
    required this.player_y,
    required this.player_direction,
  });

  factory GamePlayer.fromJson(Map<String, dynamic> json) {
    return GamePlayer(
      player_x: json['player_x'].toDouble(),
      player_y: json['player_y'].toDouble(),
      player_direction: json['player_direction'],
    );
  }
}

class GameSoccerTeam {
  List<GamePlayer> players;

  GameSoccerTeam({required this.players});

  factory GameSoccerTeam.fromJson(Map<String, dynamic> json) {
    var playersFromJson = json['players'] as List;
    List<GamePlayer> playersList =
        playersFromJson.map((i) => GamePlayer.fromJson(i)).toList();
    return GameSoccerTeam(players: playersList);
  }
}

class Game {
  final String gameId;
  final String gameStatus;
  final int score1;
  final int score2;
  final int who_has_ball;

  // final String start_time;
  final double time;
  final double max_time;
  final int   isFirstHalf;

  final double ball_direction_x;
  final double ball_direction_y;
  final double ball_x;
  final double ball_y;

  final int player1_control_player;
  final double player1_x;
  final double player1_y;
  final int player1_direction;
  final bool player1_possession;
  final GameSoccerTeam player1_players;

  final int player2_control_player;
  final double player2_x;
  final double player2_y;
  final int player2_direction;
  final bool player2_possession;
  final GameSoccerTeam player2_players;

  Game({
    required this.gameId,
    required this.gameStatus,
    required this.score1,
    required this.who_has_ball,
    // required this.start_time,
    required this.time,
    required this.max_time,
    required this.isFirstHalf,
    required this.score2,
    required this.ball_direction_x,
    required this.ball_direction_y,
    required this.ball_x,
    required this.ball_y,
    required this.player1_control_player,
    required this.player1_x,
    required this.player1_y,
    required this.player1_direction,
    required this.player1_possession,
    required this.player1_players,
    required this.player2_control_player,
    required this.player2_x,
    required this.player2_y,
    required this.player2_direction,
    required this.player2_possession,
    required this.player2_players,
  });
}
