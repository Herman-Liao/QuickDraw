package test;

import model.*;
import model.Point;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TextTest implements WritableTest, LayerableTest {
     Point testPos;
     Color testColour;
     String testString;
     Text testText;

    @BeforeEach
    void runBefore() {
        this.testPos = new Point(50, 30);
        this.testColour = new Color(64, 64, 128);
        this.testString = "Placeholder";

        this.testText = new Text(this.testPos, testColour, testString);
    }

    @Test
    void testConstructor() {
        assertEquals(this.testPos, this.testText.getPos());
        assertEquals(this.testColour, this.testText.getColour());
        assertEquals(this.testString, this.testText.getString());
    }

    @Override
    @Test
    public void testMove() {
        int dx = 30;
        int dy = -54;
        Point correctNewPos = new Point(this.testPos.getXpos() + dx, this.testPos.getYpos() + dy);

        this.testText.move(dx, dy);
        assertEquals(correctNewPos.getXpos(), this.testText.getPos().getXpos());
        assertEquals(correctNewPos.getYpos(), this.testText.getPos().getYpos());
    }

    @Test
    public void testDescribe() {
        String positionString = "Position = (" + this.testPos.getXpos() + ", " + this.testPos.getYpos() + ")";
        String colourString = "Colour = (" + this.testColour.getRed() + ", "
                + this.testColour.getGreen() + ", "
                + this.testColour.getBlue() + ")";
        String textString = "Text = \"" + this.testString + "\"";

        String correctString = "Text: ";
        correctString += positionString + ", ";
        correctString += colourString + ", ";
        correctString += textString;
        assertEquals(correctString, this.testText.describe());
    }
/*
    @Test
    public void testGetDrawingPointsList() {
        List<DrawingPoint> correctList = new ArrayList<>();
        for (int i = 0; i < this.testString.length(); i++) {
            correctList.add(new DrawingPoint(this.testPos.getXpos() + i, this.testPos.getYpos(),
                    this.testColour, String.valueOf(this.testString.charAt(i))));
        }
        List<DrawingPoint> currentList = this.testText.getDrawingPointsList();
        assertEquals(correctList.size(), currentList.size());
        for (int i = 0; i < correctList.size(); i++) {
            DrawingPoint correctDP = correctList.get(i);
            DrawingPoint currentDP = currentList.get(i);
            assertEquals(correctDP.getXpos(), currentDP.getXpos());
            assertEquals(correctDP.getYpos(), currentDP.getYpos());
            assertEquals(correctDP.getColour(), currentDP.getColour());
            assertEquals(correctDP.getCharacter(), currentDP.getCharacter());
        }
    }
*/
    @Override
    @Test
    public void testToJson() {
        JSONObject correctJsonObject = new JSONObject();
        correctJsonObject.put("Type", "Text");
        correctJsonObject.put("Position x", this.testText.getPos().getXpos());
        correctJsonObject.put("Position y", this.testText.getPos().getYpos());
        correctJsonObject.put("Colour r", this.testText.getColour().getRed());
        correctJsonObject.put("Colour g", this.testText.getColour().getGreen());
        correctJsonObject.put("Colour b", this.testText.getColour().getBlue());
        correctJsonObject.put("String", this.testText.getString());

        assertEquals(correctJsonObject.toString(4), this.testText.toJson().toString(4));
    }
}
