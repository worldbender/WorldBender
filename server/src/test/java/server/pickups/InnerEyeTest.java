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


class InnerEyeTest {
	
	private InnerEye innerEye;
	private Player player;
	private User user;
	private GameController gameController;
	
	@BeforeEach
	void setUp() {
		gameController = mock(GameController.class);
		user = mock(User.class);
		Properties.createConfigFileForTesting();
		player = new Player(user,gameController);
		
		innerEye = new InnerEye(1,1);
	}
	
	
	@Test
	void checkIfWeaponTypeHasBeenChangedToTriple() {
		player.setWeaponType("normal");
		innerEye.modifyPlayer(player);
		assertThat(player.getWeaponType()).isEqualTo("Triple");
		
	}
	
	
	@Test
	void checkIfShootSpeedModificatorHasBeenChangedTo2L() {
		innerEye.modifyPlayer(player);
		assertThat(player.getShootSpeedModificator()).isEqualTo(2L);
		
	}
	
	@Test
	void checkIf() {
		int before = player.getCollectedItems().size();
		innerEye.modifyPlayer(player);
		int after = player.getCollectedItems().size();
		assertThat(after).isEqualTo(before+1);
		
	}
	
	@AfterEach
	void tearDown() {
		innerEye = null;
		player = null;
		user = null;
		gameController = null;
	}
}