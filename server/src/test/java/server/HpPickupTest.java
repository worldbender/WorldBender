package server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.connection.GameController;
import server.pickups.HpPickup;
import server.players.Player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class HpPickupTest {
	
	private HpPickup hpPickup;
	private Player player;
	private User user;
	private GameController gameController;
	
	@BeforeEach
	void setUp() {
		gameController = mock(GameController.class);
		user = mock(User.class);
		hpPickup = new HpPickup(1,1);
		Properties.createConfigFileForTesting();
		player = new Player(user,gameController);
	}
	
	@Test
	void checkIfHpHasIcreasedBy10() {
		player.setHp(10);
		int before = player.getHp();
		hpPickup.modifyPlayer(player);
		int after = player.getHp();
		assertThat(after).isEqualTo(before+10);
	}
	
	@Test
	void checkIfHpHasntBeenIcreasedWhenItHasMaxValue() {
		player.setHp(100);
		int before = player.getHp();
		hpPickup.modifyPlayer(player);
		int after = player.getHp();
		assertThat(after).isEqualTo(before);
		
	}
	
	@AfterEach
	void tearDown() {
		hpPickup = null;
		player = null;
		user = null;
		gameController = null;
	}
	
}