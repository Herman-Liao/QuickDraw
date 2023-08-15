package test;

import model.Line;
import model.Point;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LineTest implements WritableTest, LayerableTest {
     int testStartPointX;
     int testStartPointY;
     int testEndPointX;
     int testEndPointY;
     Point testStartPoint;
     Point testEndPoint;
     Color testColour;
     Line testLine;

    @BeforeEach
    void runBefore() {
        this.testStartPointX = 70;
        this.testStartPointY = 100;
        this.testEndPointX = 90;
        this.testEndPointY = 80;
        this.testStartPoint = new Point(testStartPointX, testStartPointY);
        this.testEndPoint = new Point(testEndPointX, testEndPointY);
        this.testColour = new Color(0, 0, 0);

        this.testLine = new Line(this.testStartPoint, this.testEndPoint, this.testColour);
    }

    @Test
    void testConstructor() {
        assertEquals(this.testStartPoint, this.testLine.getStart());
        assertEquals(this.testEndPoint, this.testLine.getEnd());
        assertEquals(this.testColour, this.testLine.getColour());
    }

    @Test
    void testSetColour() {
        Color testNewColour = new Color(3, 56, 247);
        this.testLine.setColour(testNewColour);
        assertEquals(testNewColour, this.testLine.getColour());
    }

    @Test
    void testIsIntersectingSameLine() {
        Point point1 = new Point(1, 2);
        Point point2 = new Point(5, 10);
        Point point3 = new Point(8, 16);
        Point point4 = new Point(20, 40);
        Color c = new Color(0,0,0);
        assertTrue(Line.isIntersecting(new Line(point1, point3, c), new Line(point2, point4, c)));
        assertTrue(Line.isIntersecting(new Line(point1, point3, c), new Line(point4, point2, c)));
        assertTrue(Line.isIntersecting(new Line(point1, point4, c), new Line(point2, point3, c)));
        assertFalse(Line.isIntersecting(new Line(point1, point2, c), new Line(point3, point4, c)));
    }

    @Test
    void testIsIntersectingSameLineVertical() {
        Point point1 = new Point(5, 2);
        Point point2 = new Point(5, 10);
        Point point3 = new Point(5, 16);
        Point point4 = new Point(5, 40);
        Color c = new Color(0,0,0);
        assertTrue(Line.isIntersecting(new Line(point1, point3, c), new Line(point2, point4, c)));
        assertTrue(Line.isIntersecting(new Line(point1, point3, c), new Line(point4, point2, c)));
        assertTrue(Line.isIntersecting(new Line(point1, point4, c), new Line(point2, point3, c)));
        assertFalse(Line.isIntersecting(new Line(point1, point2, c), new Line(point3, point4, c)));
    }

    @Test
    void testIsIntersectingSameSlope() {
        Point point1 = new Point(3, 4);
        Point point2 = new Point(3, 8);
        Point point3 = new Point(18, 4);
        Point point4 = new Point(18, 8);
        Color c = new Color(0,0,0);
        assertFalse(Line.isIntersecting(new Line(point1, point3, c), new Line(point2, point4, c)));
        assertFalse(Line.isIntersecting(new Line(point1, point2, c), new Line(point3, point4, c)));
    }

    @Test
    void testIsIntersectingOneVertical() {
        Point point1 = new Point(4, 4);
        Point point2 = new Point(4, 8);
        Point point3 = new Point(0, 2);

        Point point4 = new Point(20, 11);
        Point point5 = new Point(20, 12);
        Point point6 = new Point(20, 14);
        Color c = new Color(0,0,0);

        assertFalse(Line.isIntersecting(new Line(point1, point2, c), new Line(point3, point4, c)));
        assertTrue(Line.isIntersecting(new Line(point1, point2, c), new Line(point3, point5, c)));
        assertTrue(Line.isIntersecting(new Line(point1, point2, c), new Line(point3, point6, c)));
        assertFalse(Line.isIntersecting(new Line(point3, point1, c), new Line(point4, point6, c)));
    }

    @Test
    void testIsIntersecting() {
        Point point1 = new Point(10, 10);
        Point point2 = new Point(20, 20);
        Point point3 = new Point(15, 10);
        Point point4 = new Point(17, 22);
        Color c = new Color(0,0,0);

        assertTrue(Line.isIntersecting(new Line(point1, point2, c), new Line(point3, point4, c)));
        assertTrue(Line.isIntersecting(new Line(point3, point4, c), new Line(point1, point2, c)));
        assertFalse(Line.isIntersecting(new Line(point1, point3, c), new Line(point2, point4, c)));
        assertFalse(Line.isIntersecting(new Line(point1, point4, c), new Line(point2, point3, c)));
    }

    @Override
    @Test
    public void testMove() {
        int dx = -24;
        int dy = 66;
        Point correctLineStart = new Point(this.testStartPointX + dx,this.testStartPointY + dy);
        Point correctLineEnd = new Point(this.testEndPointX + dx,this.testEndPointY + dy);
        Line correctLine = new Line(correctLineStart,correctLineEnd,this.testColour);

        this.testLine.move(dx,dy);
        assertEquals(correctLine.getStart().getXpos(), this.testLine.getStart().getXpos());
        assertEquals(correctLine.getStart().getYpos(), this.testLine.getStart().getYpos());
        assertEquals(correctLine.getEnd().getXpos(), this.testLine.getEnd().getXpos());
        assertEquals(correctLine.getEnd().getYpos(), this.testLine.getEnd().getYpos());
    }

    @Test
    public void testDescribe() {
        String startString = "Start = (" + this.testStartPoint.getXpos() + ", " + this.testStartPoint.getYpos() + ")";
        String endString = "End = (" + this.testEndPoint.getXpos() + ", " + this.testEndPoint.getYpos() + ")";
        String colourString = "Colour = (" + this.testColour.getRed() + ", "
                + this.testColour.getGreen() + ", "
                + this.testColour.getBlue() + ")";

        String correctString = "Line: ";
        correctString += startString + ", ";
        correctString += endString + ", ";
        correctString += colourString;
        assertEquals( correctString, this.testLine.describe());
    }

    @Override
    @Test
    public void testToJson() {
        JSONObject correctJsonObject = new JSONObject();
        correctJsonObject.put("Type", "Line");
        correctJsonObject.put("Start x", this.testLine.getStart().getXpos());
        correctJsonObject.put("Start y", this.testLine.getStart().getYpos());
        correctJsonObject.put("End x", this.testLine.getEnd().getXpos());
        correctJsonObject.put("End y", this.testLine.getEnd().getYpos());
        correctJsonObject.put("Colour r", this.testLine.getColour().getRed());
        correctJsonObject.put("Colour g", this.testLine.getColour().getGreen());
        correctJsonObject.put("Colour b", this.testLine.getColour().getBlue());

        assertEquals(correctJsonObject.toString(4), this.testLine.toJson().toString(4));
    }
}