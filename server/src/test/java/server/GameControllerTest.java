package server;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import server.bullets.ABullet;
import server.bullets.BulletList;
import server.bullets.Tear;
import server.connection.GameController;
import server.logicmap.LogicMapHandler;
import server.opponents.AOpponent;
import server.opponents.OpponentList;
import server.opponents.Poe;
import server.players.Player;
import server.rooms.Room;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameControllerTest {
	
	private Room room;
	private GameController gameController;
	private LogicMapHandler logicMapHandler;
	private BulletList bulletList;
	private CopyOnWriteArrayList<ABullet> bullets;
	private OpponentList opponentList;
	private CopyOnWriteArrayList<AOpponent> opponents;
	private CopyOnWriteArrayList<User> users;
	private Player player;
	
	@BeforeEach
	void setUp() {
		room = mock(Room.class);
		logicMapHandler = mock(LogicMapHandler.class);
		bulletList = mock(BulletList.class);
		bullets = getPreparedBullets();
		users = getPreparedPlayers();
		player = mock(Player.class);
		
		when(room.getBulletList()).thenReturn(bulletList);
		when(bulletList.getBullets()).thenReturn(bullets);
		
		opponentList = mock(OpponentList.class);
		opponents = getPreparedOpponenets();
		
		when(room.getOpponentList()).thenReturn(opponentList);
		when(opponentList.getOpponents()).thenReturn(opponents);
		
		when(room.getUsersInRoom()).thenReturn(users);
		
		gameController = new GameController(room, logicMapHandler);
	}
	
	@Test
	void checkBulletsData() {
		JSONAssert.assertEquals(getPreparedBulletsJSON(), gameController.getBulletsData(), JSONCompareMode.STRICT);
	}
	
	@Test
	void checkIfJSONArrayOfBulletsIsNotEmpty() {
		JSONArray bulletsData = gameController.getBulletsData();
		
		assertFalse(bulletsData.isEmpty());
	}
	
	@Test
	void checkIfJSONArrayHasTheSameSizeLikeListOfBullets() {
		assertEquals(getPreparedBullets().size(), gameController.getBulletsData().length());
	}
	
	@Test
	void checkOpponentsData() {
		JSONAssert.assertEquals(getPreparedOpponentsJSON(), gameController.getOpponentsData(), JSONCompareMode.STRICT);
	}
	
	@Test
	void checkIfJSONArrayOfOpponentsIsNotEmpty() {
		JSONArray opponentsData = gameController.getOpponentsData();
		
		assertFalse(opponentsData.isEmpty());
	}
	
	@Test
	void checkIfJSONArrayHasTheSameSizeLikeListOfPlayers() {
		assertEquals(getPreparedPlayers().size(), gameController.getPlayersData().length());
	}
	@Test
	void checkPlayersData() {
		JSONAssert.assertEquals(getPreparedPlayersJSON(), gameController.getPlayersData(), JSONCompareMode.STRICT);
	}
	
	@Test
	void checkIfJSONArrayOfPlayersIsNotEmpty() {
		JSONArray playersData = gameController.getPlayersData();
		
		assertFalse(playersData.isEmpty());
	}
	
	@Test
	void checkIfJSONArrayHasTheSameSizeLikeListOfOpponents() {
		assertEquals(getPreparedBullets().size(), gameController.getBulletsData().length());
	}
	
	private CopyOnWriteArrayList<User> getPreparedPlayers() {
		CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();
		for (int i = 0; i < 3; i++) {
			User user = mock(User.class);
			Player player = mock(Player.class);
			when(user.getPlayer()).thenReturn(player);
			when(user.getName()).thenReturn("player");
			when(player.getX()).thenReturn((double) i);
			when(player.getY()).thenReturn((double) i);
			when(player.getHp()).thenReturn(i);
			when(player.getActiveMovementKey()).thenReturn("DOWN");
			when(player.getHeadDirection()).thenReturn("DOWN");
			when(player.isMoving()).thenReturn(true);
			users.add(user);
		}
		return users;
	}
	
	
	
	private CopyOnWriteArrayList<AOpponent> getPreparedOpponenets() {
		CopyOnWriteArrayList<AOpponent> opponents = new CopyOnWriteArrayList<>();
		for (int i = 0; i < 3; i++) {
			Poe poe = mock(Poe.class);
			when(poe.getId()).thenReturn(i);
			when(poe.getX()).thenReturn((double) i);
			when(poe.getY()).thenReturn((double) i);
			when(poe.getHp()).thenReturn(i);
			when(poe.getType()).thenReturn("Poe");
			opponents.add(poe);
		}
		return opponents;
	}
	
	
	private CopyOnWriteArrayList<ABullet> getPreparedBullets() {
		CopyOnWriteArrayList<ABullet> bullets = new CopyOnWriteArrayList<>();
		for (int i = 0; i < 3; i++) {
			Tear tear = mock(Tear.class);
			when(tear.getId()).thenReturn(i);
			when(tear.getX()).thenReturn((double) i);
			when(tear.getY()).thenReturn((double) i);
			bullets.add(tear);
		}
		return bullets;
	}
	
	private String getPreparedBulletsJSON() {
		JSONArray bulletsList = new JSONArray();
		
		for (ABullet bullet : this.getPreparedBullets()) {
			JSONObject bulletData = new JSONObject()
					.put("id", bullet.getId())
					.put("x", bullet.getX())
					.put("y", bullet.getY());
			
			bulletsList.put(bulletData);
		}
		return bulletsList.toString();
	}
	
	private String getPreparedOpponentsJSON() {
		JSONArray opponentsList = new JSONArray();
		
		for (AOpponent opponent : this.getPreparedOpponenets()) {
			JSONObject opponentData = new JSONObject()
					.put("id", opponent.getId())
					.put("x", opponent.getX())
					.put("y", opponent.getY())
					.put("hp", opponent.getHp())
					.put("type", opponent.getType());
			
			opponentsList.put(opponentData);
		}
		
		return opponentsList.toString();
	}
	
	public JSONArray getPreparedPlayersJSON() {
		
		JSONArray playersList = new JSONArray();
		
		for (User user : getPreparedPlayers()) {
			JSONObject playerData = new JSONObject()
					.put("name", user.getName())
					.put("x", user.getPlayer().getX())
					.put("y", user.getPlayer().getY())
					.put("hp", user.getPlayer().getHp())
					.put("activeMovementKey", user.getPlayer().getActiveMovementKey())
					.put("headDirection", user.getPlayer().getHeadDirection())
					.put("isMoving", user.getPlayer().isMoving());
			
			playersList.put(playerData);
		}
		
		return playersList;
	}
	
	@AfterEach
	void tearDown(){
		gameController = null;
		logicMapHandler = null;
		bulletList = null;
		bullets = null;
		opponentList = null;
		opponents = null;
	}
}