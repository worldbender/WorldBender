import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.logicmap.EventBlock;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Testowanko {
    EventBlock block;

    @BeforeEach
    public void setUp(){
        block = new EventBlock(2, 5, "napis", "napis2");
    }

    @Test
    public void testowanko(){
        assertEquals(2, block.getX());
    }

    @Test
    public void testowanko2(){
        assertEquals(5, block.getY());
    }

    @AfterEach
    public void tearDown(){
        block = null;
    }
}
