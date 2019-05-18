package server.factories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Properties;
import server.User;
import server.connection.GameController;
import server.players.Ground;
import server.players.Player;
import server.players.PlayersFactory;
import server.players.Water;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;


public class PlayersFactoryTest {
	
	private User user;
	private GameController gameController;
	private Player resultPlayer;
	
	
	@BeforeEach
	void setUp() {
		gameController = mock(GameController.class);
		user = mock(User.class);
		Properties.createConfigFileForTesting();
	}
	
	@Test
	void checkIfPlayerIsGround() {
		resultPlayer = PlayersFactory.createPlayer("Ground", user, gameController);
		assertThat(resultPlayer).isInstanceOfAny(Ground.class);
	}
	
	@Test
	void checkIfPlayerIsWater() {
		resultPlayer = PlayersFactory.createPlayer("Water", user, gameController);
		assertThat(resultPlayer).isInstanceOfAny(Water.class);
	}
	
	@Test
	void checkIfIllegalArgumentExceptionExceptionIsThrown(){
		assertThatIllegalArgumentException().isThrownBy(() ->{
			resultPlayer = PlayersFactory.createPlayer("geek", user, gameController);
		}).withMessage("There is no such type of the Player!");
	}
	
	@Test
	void checkIfNullPointerExceptionIsThrown() {
		assertThatNullPointerException().isThrownBy(() -> {
			resultPlayer = PlayersFactory.createPlayer(null, user, gameController);
		});
	}
	
	@AfterEach
	void tearDown() {
		user = null;
		gameController = null;
		resultPlayer = null;
	}
	
	
}