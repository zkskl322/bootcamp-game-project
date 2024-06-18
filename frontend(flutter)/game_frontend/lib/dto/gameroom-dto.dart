
class GameRoomsDTO {
  final String roomPassword;
  final String roomName;
  final int roomSize;
  final int roomGoal;

  GameRoomsDTO({
    required this.roomPassword, 
    required this.roomName, 
    required this.roomSize, 
    required this.roomGoal});
}
