package server.factories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.connection.GameController;
import server.opponents.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class OpponentFactoryTest {
	
	private GameController gameController;
	AOpponent resultOpponent;
	
	
	@BeforeEach
	void setUp() {
		gameController = mock(GameController.class);
	}
	
	@Test
	void checkIfOpponentIsSchopenhauer() {
		resultOpponent = OpponentFactory.createOpponent("Schopenhauer", gameController);
		assertThat(resultOpponent).isInstanceOfAny(Schopenhauer.class);
		
	}
	
	@Test
	void checkIfOpponentIsNietzsche() {
		resultOpponent = OpponentFactory.createOpponent("Nietzsche", gameController);
		assertThat(resultOpponent).isInstanceOfAny(Nietzsche.class);
		
	}
	
	@Test
	void checkIfOpponentIsPoe() {
		resultOpponent = OpponentFactory.createOpponent("Poe", gameController);
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
		resultOpponent = null;
	}
	

}