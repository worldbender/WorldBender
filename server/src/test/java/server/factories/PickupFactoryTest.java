package server.factories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import server.pickups.*;

import static org.assertj.core.api.Assertions.*;

public class PickupFactoryTest {
	
	private APickup resultPickup;
	
	@Test
	void checkIfPickupIsHp() {
		 resultPickup = PickupFactory.createPickup(1,1, "Hp");
		 assertThat(resultPickup).isInstanceOfAny(HpPickup.class);
		 
	}
	
	@Test
	void checkIfPickupIsInnerEye() {
		resultPickup = PickupFactory.createPickup(1,1, "InnerEye");
		assertThat(resultPickup).isInstanceOfAny(InnerEye.class);
		
	}
	
	@Test
	void checkIfPickupIsSadOnion() {
		resultPickup = PickupFactory.createPickup(1,1, "SadOnion");
		assertThat(resultPickup).isInstanceOfAny(SadOnion.class);
		
	}
	
	@Test
	void checkIfPickupIsWarp() {
		resultPickup = PickupFactory.createPickup(1,1, "Warp");
		assertThat(resultPickup).isInstanceOfAny(Warp.class);
		
	}
	
	@Test
	void checkIfPickupIsInvisibleWarp() {
		resultPickup = PickupFactory.createPickup(1,1, "InvisibleWarp");
		assertThat(resultPickup).isInstanceOfAny(InvisibleWarp.class);
		
	}
	
	@Test
	void checkIfIllegalArgumentExceptionExceptionIsThrown(){
		assertThatIllegalArgumentException().isThrownBy(() ->{
			resultPickup = PickupFactory.createPickup(1,1, "Coconut");
		}).withMessage("There is no such pickup!");
	}
	
	@Test
	void checkIfNullPointerExceptionIsThrown() {
		assertThatNullPointerException().isThrownBy(() -> {
			resultPickup = PickupFactory.createPickup(1,1, null);
		});
	}
	
	@AfterEach
	void tearDown() {
		resultPickup = null;
	}
}