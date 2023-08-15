package test;

import model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PointTest {
    Point testPoint;
    int testXPos;
    int testYPos;

    @BeforeEach
    void runBefore() {
        this.testXPos = 24;
        this.testYPos = -42;
        this.testPoint = new Point(this.testXPos, this.testYPos);
    }

    @Test
    void testConstructor() {
        assertEquals(this.testXPos, this.testPoint.getXpos());
        assertEquals(this.testYPos, this.testPoint.getYpos());
    }

    @Test
    void testSetPos() {
        this.testPoint.setPos(80, 23);
        assertEquals(80, this.testPoint.getXpos());
        assertEquals(23, this.testPoint.getYpos());
    }
}