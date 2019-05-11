package server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.bullets.BulletFactory;
import server.connection.GameController;
import server.opponents.*;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class OpponentFactoryTest {
	
	private GameController gameController;
	
	
	@BeforeEach
	void setUp() {
		gameController = mock(GameController.class);
	}
	
	@Test
	void checkIfOpponentIsSchopenhauer() {
		AOpponent resultOpponent = OpponentFactory.createOpponent("Schopenhauer", gameController);
		assertThat(resultOpponent).isInstanceOfAny(Schopenhauer.class);
		
	}
	
	@Test
	void checkIfOpponentIsNietzsche() {
		AOpponent resultOpponent = OpponentFactory.createOpponent("Nietzsche", gameController);
		assertThat(resultOpponent).isInstanceOfAny(Nietzsche.class);
		
	}
	
	@Test
	void checkIfOpponentIsPoe() {
		AOpponent resultOpponent = OpponentFactory.createOpponent("Poe", gameController);
		assertThat(resultOpponent).isInstanceOfAny(Poe.class);
		
	}
	
	@Test
	void checkIfIllegalArgumentExceptionExceptionIsThrown(){
		assertThatIllegalArgumentException().isThrownBy(() ->{
			OpponentFactory.createOpponent("Neron", gameController);
		}).withMessage("There is no such opponent!");
	}
	
	@Test
	void checkIfNullPointerExceptionIsThrown() {
		assertThatNullPointerException().isThrownBy(() -> {
			OpponentFactory.createOpponent(null, gameController);
		});
	}
	
	
	@AfterEach
	void tearDown() {
		gameController = null;
	}
	

}