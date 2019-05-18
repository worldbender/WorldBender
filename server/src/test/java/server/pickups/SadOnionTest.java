package server.pickups;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Properties;
import server.User;
import server.connection.GameController;
import server.players.Player;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class SadOnionTest {
	
	private SadOnion sadOnion;
	private Player player;
	private User user;
	private GameController gameController;
	
	@BeforeEach
	void setUp() {
		gameController = mock(GameController.class);
		user = mock(User.class);
		Properties.createConfigFileForTesting();
		player = new Player(user,gameController);
		
		sadOnion = new SadOnion(1,1);
	}
	
	
	@Test
	void checkIfWeaponTypeHasBeenChangedToSadOnion() {
		player.setWeaponType("normal");
		sadOnion.modifyPlayer(player);
		assertThat(player.getWeaponType()).isEqualTo("SadOnion");
		
	}
	
	
	@Test
	void checkIfShootSpeedModificatorHasBeenChangedTo2L() {
		sadOnion.modifyPlayer(player);
		assertThat(player.getShootSpeedModificator()).isEqualTo(2L);
		
	}
	
	@Test
	void checkIfTripleHasBeenAddedToItemsCollection() {
		int before = player.getCollectedItems().size();
		sadOnion.modifyPlayer(player);
		int after = player.getCollectedItems().size();
		assertThat(after).isEqualTo(before+1);
		
	}
	
	@AfterEach
	void tearDown() {
		sadOnion = null;
		player = null;
		user = null;
		gameController = null;
	}
}
