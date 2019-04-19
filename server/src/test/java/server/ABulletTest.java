package server;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.bullets.ABullet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class ABulletTest {

    ABullet bulletExample1;

    @BeforeEach
    void setUp() {
        bulletExample1 = new ABullet(1,1,30.0);
    }

    @AfterEach
    void tearDown() {
        bulletExample1 = null;
    }

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

    @Test
    void getAngle() {
    }

    @Test
    void setAngle() {
    }

    @Test
    void getY() {
    }

    @Test
    void setY() {
    }

    @Test
    void getX() {
    }

    @Test
    void setX() {
    }

    @Test
    void getId() {
    }

    @Test
    void setId() {
    }

    @Test
    void getRange() {
    }

    @Test
    void setRange() {
    }

    @Test
    void getType() {
    }

    @Test
    void setType() {
    }

    @Test
    void getAttack() {
    }

    @Test
    void setAttack() {
    }

    @Test
    void isHostile() {
    }

    @Test
    void setHostile() {
    }
}