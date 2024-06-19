package springboot.profpilot.model.logSystem;


//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoDatabase;
//import org.bson.Document;
//import org.springframework.stereotype.Service;
//import springboot.profpilot.model.Game.GameState;
//
//import java.util.List;
//import java.util.stream.Collectors;


//@Service
//public class SimulationRawDataService {
//
//
//    public void saveByGameState(GameState gameState) {
//        String uri = "mongodb://localhost:27017";
//        MongoClient mongoClient = MongoClients.create(uri);
//        MongoDatabase database = mongoClient.getDatabase("soccer-game-database");
//        MongoCollection<Document> collection = database.getCollection("match-log");
//
//        List<Document> team1Players =  gameState.getPlayer1_players().getPlayers().stream()
//                .map(player -> new Document(
//                                "player_x", player.getPlayer_x())
//                        .append("player_y", player.getPlayer_y())
//                        .append("player_direction", player.getPlayer_direction())
//                        .append("player_x_speed", player.getPlayer_x_speed())
//                        .append("player_y_speed", player.getPlayer_y_speed())
//                        .append("player_action", "null")) // "null" is a placeholder for "player_action
//                .collect(Collectors.toList());
//
//        List<Document> team2Players =  gameState.getPlayer2_players().getPlayers().stream()
//                .map(player -> new Document(
//                                "player_x", player.getPlayer_x())
//                        .append("player_y", player.getPlayer_y())
//                        .append("player_direction", player.getPlayer_direction())
//                        .append("player_x_speed", player.getPlayer_x_speed())
//                        .append("player_y_speed", player.getPlayer_y_speed())
//                        .append("player_action", "action: null")) // "null" is a placeholder for "player_action
//                .collect(Collectors.toList());
//
//        Document document = new Document("game_id", gameState.getGameId())
//                .append("game_datetime", gameState.getTick())
//                .append("score", gameState.getScore1() + "-" + gameState.getScore2())
//                .append("half", gameState.getIsFirstHalf() == 1 ? "first" : "second")
//                .append("event", new Document(
//                                "action", "The action that occurred (e.g., tackle)"
//                        ).append("actor", "The entity performing the action (e.g., player1_offender1)"
//                        ).append("target", "The entity affected by the action (e.g., player2_offender1)")
//                )
//                .append("players", new Document("team1", team1Players)).append("team2", team2Players)
//                .append("ball", new Document("position", new Document("x", gameState.getBall_x())
//                        .append("y", gameState.getBall_y()))
//                        .append("velocity", new Document("x", gameState.getBall_direction_x())
//                                .append("y", gameState.getBall_direction_y())));
//
//
//        collection.insertOne(document);
//
//        System.out.println("Connected to the database successfully");
//    }
//}


//                .append("events", gameState.
//        double team1_offender1_x = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_x();
//        double team1_offender1_y = gameState.getPlayer1_players().getPlayers().get(0).getPlayer_y();
//        double team1_offender2_x = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_x();
//        double team1_offender2_y = gameState.getPlayer1_players().getPlayers().get(1).getPlayer_y();
//        double team1_defender3_x = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_x();
//        double team1_defender3_y = gameState.getPlayer1_players().getPlayers().get(2).getPlayer_y();
//        double team1_defender4_x = gameState.getPlayer1_players().getPlayers().get(3).getPlayer_x();
//        double team1_defender4_y = gameState.getPlayer1_players().getPlayers().get(3).getPlayer_y();
//        double team1_goalkeeper_x = gameState.getPlayer1_players().getPlayers().get(4).getPlayer_x();
//        double team1_goalkeeper_y = gameState.getPlayer1_players().getPlayers().get(4).getPlayer_y();
//
//        double team2_offender1_x = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_x();
//        double team2_offender1_y = gameState.getPlayer2_players().getPlayers().get(0).getPlayer_y();
//        double team2_offender2_x = gameState.getPlayer2_players().getPlayers().get(1).getPlayer_x();
//        double team2_offender2_y = gameState.getPlayer2_players().getPlayers().get(1).getPlayer_y();
//        double team2_defender3_x = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_x();
//        double team2_defender3_y = gameState.getPlayer2_players().getPlayers().get(2).getPlayer_y();
//        double team2_defender4_x = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_x();
//        double team2_defender4_y = gameState.getPlayer2_players().getPlayers().get(3).getPlayer_y();
//        double team2_goalkeeper_x = gameState.getPlayer2_players().getPlayers().get(4).getPlayer_x();
//        double team2_goalkeeper_y = gameState.getPlayer2_players().getPlayers().get(4).getPlayer_y();
