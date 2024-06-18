// [{room_password: $2a$10$7vo6BVhZxamcoumL4WMFl..cqqW1B3hJSkvZvVh64FUPLpa2qxAFC,
//room_name: a,
//room_size: 1,
//room_goal: 1},

class GameRoomsDTO {
  final int roomId;
  final String roomOwner;
  final String roomPassword;
  final String roomName;
  final int roomSize;
  final int roomGoal;

  GameRoomsDTO(
      {required this.roomId,
      required this.roomOwner,
      required this.roomPassword,
      required this.roomName,
      required this.roomSize,
      required this.roomGoal});
}
