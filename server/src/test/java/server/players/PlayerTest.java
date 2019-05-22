package server.players;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.User;
import server.helpers.MockingConfigFileCreation;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class PlayerTest{
	private User user;
	private Player player;
	
	
	@BeforeEach
	void setUp() {
		user = mock(User.class);
		MockingConfigFileCreation.mockingConfigFileCreation(user);
		player = new Player(user);
	}
	
	@Test
	void checkIfDamageIsProper(){
		player.setHp(50);
		int before = player.getHp();
		player.doDamage(10);
		int after  = player.getHp();
		assertThat(after).isEqualTo(before-10);
	}
	
	
	@Test
	void checkIfPlayerHasCollectedItem(){
		ArrayList <String> collectedItems = new ArrayList <String>();
		collectedItems.add("Banana");
		player.setCollectedItems(collectedItems);
		assertThat(player.hasPlayerItem("Banana")).isEqualTo(true);
	}
	
	@Test
	void checkIfPlayerHasNotCollectedItem(){
		ArrayList <String> collectedItems = new ArrayList <String>();
		player.setCollectedItems(collectedItems);
		assertThat(player.hasPlayerItem("Mango")).isEqualTo(false);
	}
	
	
	
	@AfterEach
	void tearDown() {
		user = null;
		player = null;
	}
}
