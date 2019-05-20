package server.factories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.User;
import server.connection.GameController;
import server.helpers.MockingConfigFileCreation;
import server.players.Player;
import server.powers.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.mockito.Mockito.mock;


public class PowerFactoryTest {
	
	private Player player;
	private GameController gameController;
	private IPower resultPower;
	private User user;
	
	
	@BeforeEach
	void setUp() {
		gameController = mock(GameController.class);
		user = mock(User.class);
		MockingConfigFileCreation.mockingConfigFileCreation(user);
		player = new Player(user);
	}
	
	@Test
	void checkIfPowerIsAggroTaker(){
		resultPower = PowerFactory.createPower("AggroTaker", gameController, player);
		assertThat(resultPower).isInstanceOfAny(AggroTaker.class);
	}
	
	@Test
	void checkIfPowerIsHealer(){
		resultPower = PowerFactory.createPower("Healer", gameController, player);
		assertThat(resultPower).isInstanceOfAny(Healer.class);
	}
	
	@Test
	void checkIfPowerIsEmptyPower(){
		resultPower = PowerFactory.createPower("MotherOfDragons", gameController, player);
		assertThat(resultPower).isInstanceOfAny(EmptyPower.class);
	}
	
	@Test
	void checkIfNullPointerExceptionIsThrown() {
		assertThatNullPointerException().isThrownBy(() -> {
			resultPower = PowerFactory.createPower(null, gameController, player);
		});
	}
	
	
	
	@AfterEach
	void tearDown() {
		player = null;
		gameController = null;
		resultPower = null;
	}
}