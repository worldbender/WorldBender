package server.players;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.User;
import server.helpers.MockingConfigFileCreation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class PlayerTest {
	private User user;
	private Player player;
	
	
	@BeforeEach
	void setUp() {
		user = mock(User.class);
		MockingConfigFileCreation.mockingConfigFileCreation(user);
		player = new Player(user);
	}
	
	@Test
	void checkIfDemageIsProper(){
		player.setHp(50);
		int before = player.getHp();
		player.doDamage(10);
		int after  = player.getHp();
		assertThat(after+10).isEqualTo(before);
	}
	
	@AfterEach
	void tearDown() {
		user = null;
		player = null;
	}
}
