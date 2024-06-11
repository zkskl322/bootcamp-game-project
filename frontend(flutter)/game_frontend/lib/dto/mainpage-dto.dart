class GameroomDTO {
  final String gameroomName;

  GameroomDTO({
    required this.gameroomName,
  });

  factory GameroomDTO.fromJson(Map<String, dynamic> json) {
    return GameroomDTO(
      gameroomName: json['gameroomName'],
    );
  }
}