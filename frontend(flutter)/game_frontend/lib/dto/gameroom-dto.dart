class GameroomDTO {
  final int id;
  final String gameroomName;
  final String ownerName;

  GameroomDTO({
    required this.id,
    required this.gameroomName,
    required this.ownerName,
  });

  factory GameroomDTO.fromJson(Map<String, dynamic> json) {
    return GameroomDTO(
      id: json['id'],
      gameroomName: json['gameroomName'],
      ownerName: json['ownerName'],
    );
  }
}