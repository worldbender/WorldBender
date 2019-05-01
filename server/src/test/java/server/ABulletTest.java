package server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.bullets.ABullet;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;


class ABulletTest {

    ABullet bulletExample1;

    @BeforeEach
    void setUp() {
        bulletExample1 = new ABullet(500,500,30.0);
    }

    @AfterEach
    void tearDown() {
        bulletExample1 = null;
    }
    
    
/************************************************************Testing Fields**********************************************************/
    @Test
   void testIfThereIsXField() {
        assertThat(bulletExample1).hasFieldOrProperty("x");
    }

    @Test
    void testIfThereIsYField() {
        
        assertThat(bulletExample1).hasFieldOrProperty("y");
    }

   @Test
   void testIfThereIsAngleField() {
        assertThat(bulletExample1).hasFieldOrProperty("angle");
    }

   @Test
   void testIfThereIsIdField() {
        
        assertThat(bulletExample1).hasFieldOrProperty("id");
    }

   @Test
   void testIfThereIsRangeField() {
        
        assertThat(bulletExample1).hasFieldOrProperty("range");
   }

   @Test
   void testIfThereIsWidthFieldWithProperValue() {
        
        assertThat(bulletExample1).hasFieldOrPropertyWithValue("width", 15);
    }

    @Test
    void testIfThereIsHightFieldWithProperValue() {
        
        assertThat(bulletExample1).hasFieldOrPropertyWithValue("height", 28);
     }

     @Test
     void testIfThereIsTypeField() {
        
        assertThat(bulletExample1).hasFieldOrProperty("type");
      }
    
    @Test
    void testIfThereIsBulletSpeedFieldWithProperValue() {
        
        assertThat(bulletExample1).hasFieldOrPropertyWithValue("bulletSpeed",1.0 );
    }
    
    @Test
    void testIfThereIsIsDeadFieldWithProperValue() {
        
        assertThat(bulletExample1).hasFieldOrPropertyWithValue("isDead",false );
    }
    
    @Test
    void testIfThereIsAttackFieldWithProperValue() {
        
        assertThat(bulletExample1).hasFieldOrPropertyWithValue("attack",1 );
    }
    
    @Test
    void testIfThereIsHostileField() {
        
        assertThat(bulletExample1).hasFieldOrProperty("hostile");
    }
    
    /*********************************************Testing getters and setters***************************************************************/

    @Test
    void testGetAngle() {
        double expected = bulletExample1.getAngle();
        double actual = 30.0;
        assertThat(actual, is(equalTo(expected)));
    }
    
    @Test
    void testSetAngle() {
        bulletExample1.setAngle(50);
        double expected = bulletExample1.getAngle();
        double actual = 50.0;
        assertThat(actual, is(equalTo(expected)));
    }
    
    @Test
    void testGetY() {
        double expected = bulletExample1.getY();
        double actual = 500;
        assertThat(actual, is(equalTo(expected)));
    }
    
    @Test
    void testSetY() {
        bulletExample1.setY(0);
        double expected = bulletExample1.getY();
        double actual = 0;
        assertThat(actual, is(equalTo(expected)));
    }
    
    @Test
    void testGetX() {
        double expected = bulletExample1.getX();
        double actual = 500;
        assertThat(actual, is(equalTo(expected)));
    }
    
    @Test
    void testSetX() {
        bulletExample1.setX(0);
        double expected = bulletExample1.getX();
        double actual = 0;
        assertThat(actual, is(equalTo(expected)));
    }
    
    @Test
    void testSetAndGetId() {
        bulletExample1.setId(1);
        double expected = bulletExample1.getId();
        double actual = 1;
        assertThat(actual, is(equalTo(expected)));
    }
    
    @Test
    void testGetAndSetRange() {
        bulletExample1.setRange(200);
        double expected = bulletExample1.getRange();
        double actual = 200;
        assertThat(actual, is(equalTo(expected)));
    }
    
    
    @Test
    void testSetAndGetType() {
        bulletExample1.setType("Ring");
        String expected = bulletExample1.getType();
        String actual = "Ring";
        assertThat(actual, is(equalTo(expected)));
        
    }
    
    @Test
    void testGetAttack() {
        int expected = bulletExample1.getAttack();
        int actual = 1;
        assertThat(actual, is(equalTo(expected)));
    }
    
    @Test
    void testSetAttack() {
        bulletExample1.setAttack(10);
        int expected = bulletExample1.getAttack();
        int actual = 10;
        assertThat(actual, is(equalTo(expected)));
    }
    
    @Test
    void testIsAndSetHostile() {
        bulletExample1.setHostile(true);
        boolean expected = bulletExample1.isHostile();
        boolean actual = true;
        assertThat(actual, is(equalTo(expected)));
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Test
    void update() {
    }

    @Test
    void handleAllBulletCollisions() {
    }

    @Test
    void handleBulletCollisionWithMap() {
    }

    @Test
    void handleBulletCollisionWithOpponents() {
    }

    @Test
    void handleBulletCollisionWithPlayers() {
    }

    @Test
    void handleBulletShift() {
    }

}