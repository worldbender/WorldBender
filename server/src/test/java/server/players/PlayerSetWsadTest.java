package server.players;

import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.User;
import server.helpers.MockingConfigFileCreation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class PlayerSetWsadTest {
	private User user;
	private Player player;
	private JSONObject wsad;
	
	
	
	@BeforeEach
	void setUp() {
		user = mock(User.class);
		MockingConfigFileCreation.mockingConfigFileCreation(user);
		player = new Player(user);
		wsad = prepareJsonObject();
	}
	
	@Test
	void checkIfWIsTrue(){
		updatePreparedJsonObject(wsad,"w", true);
		player.setWSAD(wsad);
		assertThat(player.KEY_W).isEqualTo(true);
	}
	
	@Test
	void checkIfSIsTrue(){
		updatePreparedJsonObject(wsad,"s", true);
		player.setWSAD(wsad);
		assertThat(player.KEY_S).isEqualTo(true);
	}
	
	@Test
	void checkIfAIsTrue(){
		updatePreparedJsonObject(wsad,"a", true);
		player.setWSAD(wsad);
		assertThat(player.KEY_A).isEqualTo(true);
	}
	
	@Test
	void checkIfDIsTrue(){
		updatePreparedJsonObject(wsad,"d", true);
		player.setWSAD(wsad);
		assertThat(player.KEY_D).isEqualTo(true);
	}
	
	@Test
	void checkIfUpIsTrue(){
		updatePreparedJsonObject(wsad,"up", true);
		player.setWSAD(wsad);
		assertThat(player.UP_ARROW).isEqualTo(true);
	}
	
	@Test
	void checkIfDownIsTrue(){
		updatePreparedJsonObject(wsad,"down", true);
		player.setWSAD(wsad);
		assertThat(player.DOWN_ARROW).isEqualTo(true);
	}
	
	@Test
	void checkIfLeftIsTrue(){
		updatePreparedJsonObject(wsad,"left", true);
		player.setWSAD(wsad);
		assertThat(player.LEFT_ARROW).isEqualTo(true);
	}
	
	@Test
	void checkIfRightIsTrue(){
		updatePreparedJsonObject(wsad,"right", true);
		player.setWSAD(wsad);
		assertThat(player.RIGHT_ARROW).isEqualTo(true);
	}
	
	//helpers
	
	private JSONObject prepareJsonObject() {
		JSONObject wsad = new JSONObject()
				.put("w", false)
				.put("s", false)
				.put("a", false)
				.put("d", false)
				.put("up", false)
				.put("down", false)
				.put("left", false)
				.put("right", false);
		return wsad;
	}
	
	private void updatePreparedJsonObject(JSONObject jsonObject, String key, boolean value){
		jsonObject.put(key,value);
	}
	
	@AfterEach
	void tearDown() {
		user = null;
		player = null;
		wsad = null;
	}
}