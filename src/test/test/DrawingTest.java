package test;

import model.*;
import model.Point;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DrawingTest implements WritableTest {
    private Color testColour1;
    private Color testColour2;
    private Color testColour3;

    private Point testPoint1;
    private Point testPoint2;
    private Point testPoint3;
    private Point testPoint4;
    private Point testPoint5;
    private Point testPoint6;

    private Line testLine1;
    private Line testLine2;
    private Line testLine3;

    private Text testText1;
    private Text testText2;
    private Text testText3;

    private Drawing testDrawing;

    @BeforeEach
    void runBefore() {
        this.testDrawing = new Drawing();

        // Variables beyond this point are only setups for later tests.
        this.testColour1 = new Color(128, 255, 64);
        this.testColour2 = new Color(255, 0, 0);
        this.testColour3 = new Color(255, 255, 255);

        this.testPoint1 = new Point(24, 72);
        this.testPoint2 = new Point(50, 100);
        this.testPoint3 = new Point(300, 500);
        this.testPoint4 = new Point(200, 100);
        this.testPoint5 = new Point(10000, 0);
        this.testPoint6 = new Point(0, 10000);

        this.testLine1 = new Line(this.testPoint1, this.testPoint2, this.testColour1);
        this.testLine2 = new Line(this.testPoint3, this.testPoint4, this.testColour2);
        this.testLine3 = new Line(this.testPoint5, this.testPoint6, this.testColour3);

        this.testText1 = new Text(this.testPoint1, this.testColour1, "Placeholder 1");
        this.testText2 = new Text(this.testPoint2, this.testColour2, "Placeholder 2");
        this.testText3 = new Text(this.testPoint3, this.testColour3, "Placeholder 3");
    }

    @Test
    void testConstructor() {
        assertTrue(this.testDrawing.getLayerableList().isEmpty());
        assertTrue(this.testDrawing.getLineList().isEmpty());
        assertTrue(this.testDrawing.getTextList().isEmpty());
    }

    @Test
    void testAddLineOne() {
        List<Layerable> correctLayerList = new ArrayList<>();
        List<Line> correctLineList = new ArrayList<>();

        this.testDrawing.addLine(this.testLine1);
        correctLayerList.add(this.testLine1);
        correctLineList.add(this.testLine1);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctLineList, this.testDrawing.getLineList());
    }

    @Test
    void testAddLineMany() {
        List<Layerable> correctLayerList = new ArrayList<>();
        List<Line> correctLineList = new ArrayList<>();

        this.testDrawing.addLine(this.testLine1);
        correctLayerList.add(this.testLine1);
        correctLineList.add(this.testLine1);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctLineList, this.testDrawing.getLineList());

        this.testDrawing.addLine(this.testLine2);
        correctLayerList.add(this.testLine2);
        correctLineList.add(this.testLine2);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctLineList, this.testDrawing.getLineList());

        this.testDrawing.addLine(this.testLine3);
        correctLayerList.add(this.testLine3);
        correctLineList.add(this.testLine3);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctLineList, this.testDrawing.getLineList());
    }

    @Test
    void testRemoveLineOne() {
        List<Layerable> correctLayerList = new ArrayList<>();
        List<Line> correctLineList = new ArrayList<>();

        this.testDrawing.addLine(this.testLine1);
        correctLayerList.add(this.testLine1);
        correctLineList.add(this.testLine1);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctLineList, this.testDrawing.getLineList());

        assertFalse(this.testDrawing.removeLine(this.testLine2));

        assertTrue(this.testDrawing.removeLine(this.testLine1));
        correctLayerList.remove(this.testLine1);
        correctLineList.remove(this.testLine1);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctLineList, this.testDrawing.getLineList());
    }

    @Test
    void testRemoveLineMany() {
        List<Layerable> correctLayerList = new ArrayList<>();
        List<Line> correctLineList = new ArrayList<>();
        correctLayerList.add(this.testLine1);
        correctLayerList.add(this.testLine2);
        correctLayerList.add(this.testLine3);
        correctLineList.add(this.testLine1);
        correctLineList.add(this.testLine2);
        correctLineList.add(this.testLine3);
        this.testDrawing.addLine(this.testLine1);
        this.testDrawing.addLine(this.testLine2);
        this.testDrawing.addLine(this.testLine3);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctLineList, this.testDrawing.getLineList());

        this.testDrawing.removeLine(this.testLine2);
        correctLayerList.remove(this.testLine2);
        correctLineList.remove(this.testLine2);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctLineList, this.testDrawing.getLineList());

        this.testDrawing.removeLine(this.testLine1);
        correctLayerList.remove(this.testLine1);
        correctLineList.remove(this.testLine1);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctLineList, this.testDrawing.getLineList());

        assertFalse(this.testDrawing.removeLine(testLine1));
        assertFalse(this.testDrawing.removeLine(testLine2));

        this.testDrawing.removeLine(this.testLine3);
        correctLayerList.remove(this.testLine3);
        correctLineList.remove(this.testLine3);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctLineList, this.testDrawing.getLineList());
    }

    @Test
    void testClearLinesOne() {
        List<Layerable> correctLayerList = new ArrayList<>();
        List<Line> correctLineList = new ArrayList<>();

        correctLayerList.add(this.testLine1);
        correctLineList.add(this.testLine1);
        this.testDrawing.addLine(this.testLine1);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctLineList, this.testDrawing.getLineList());

        correctLayerList.clear();
        correctLineList.clear();
        this.testDrawing.clearLines();
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctLineList, this.testDrawing.getLineList());
    }

    @Test
    void testClearLinesMany() {
        List<Layerable> correctLayerList = new ArrayList<>();
        List<Line> correctLineList = new ArrayList<>();
        correctLayerList.add(this.testLine1);
        correctLayerList.add(this.testLine2);
        correctLayerList.add(this.testLine3);
        correctLineList.add(this.testLine1);
        correctLineList.add(this.testLine2);
        correctLineList.add(this.testLine3);
        this.testDrawing.addLine(this.testLine1);
        this.testDrawing.addLine(this.testLine2);
        this.testDrawing.addLine(this.testLine3);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctLineList, this.testDrawing.getLineList());

        correctLayerList.clear();
        correctLineList.clear();
        this.testDrawing.clearLines();
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctLineList, this.testDrawing.getLineList());
    }

    @Test
    void testAddTextOne() {
        List<Layerable> correctLayerList = new ArrayList<>();
        List<Text> correctTextList = new ArrayList<>();

        this.testDrawing.addText(this.testText1);
        correctLayerList.add(this.testText1);
        correctTextList.add(this.testText1);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctTextList, this.testDrawing.getTextList());
    }

    @Test
    void testAddTextMany() {
        List<Layerable> correctLayerList = new ArrayList<>();
        List<Text> correctTextList = new ArrayList<>();

        this.testDrawing.addText(this.testText1);
        correctLayerList.add(this.testText1);
        correctTextList.add(this.testText1);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctTextList, this.testDrawing.getTextList());

        this.testDrawing.addText(this.testText2);
        correctLayerList.add(this.testText2);
        correctTextList.add(this.testText2);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctTextList, this.testDrawing.getTextList());

        this.testDrawing.addText(this.testText3);
        correctLayerList.add(this.testText3);
        correctTextList.add(this.testText3);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctTextList, this.testDrawing.getTextList());
    }

    @Test
    void testRemoveTextOne() {
        List<Layerable> correctLayerList = new ArrayList<>();
        List<Text> correctTextList = new ArrayList<>();

        this.testDrawing.addText(this.testText1);
        correctLayerList.add(this.testText1);
        correctTextList.add(this.testText1);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctTextList, this.testDrawing.getTextList());

        assertFalse(this.testDrawing.removeText(this.testText2));

        assertTrue(this.testDrawing.removeText(this.testText1));
        correctLayerList.remove(this.testText1);
        correctTextList.remove(this.testText1);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctTextList, this.testDrawing.getTextList());
    }

    @Test
    void testRemoveTextMany() {
        List<Layerable> correctLayerList = new ArrayList<>();
        List<Text> correctTextList = new ArrayList<>();
        correctLayerList.add(this.testText1);
        correctLayerList.add(this.testText2);
        correctLayerList.add(this.testText3);
        correctTextList.add(this.testText1);
        correctTextList.add(this.testText2);
        correctTextList.add(this.testText3);
        this.testDrawing.addText(this.testText1);
        this.testDrawing.addText(this.testText2);
        this.testDrawing.addText(this.testText3);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctTextList, this.testDrawing.getTextList());

        this.testDrawing.removeText(this.testText2);
        correctLayerList.remove(this.testText2);
        correctTextList.remove(this.testText2);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctTextList, this.testDrawing.getTextList());

        this.testDrawing.removeText(this.testText1);
        correctLayerList.remove(this.testText1);
        correctTextList.remove(this.testText1);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctTextList, this.testDrawing.getTextList());

        assertFalse(this.testDrawing.removeText(testText1));
        assertFalse(this.testDrawing.removeText(testText2));

        this.testDrawing.removeText(this.testText3);
        correctLayerList.remove(this.testText3);
        correctTextList.remove(this.testText3);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctTextList, this.testDrawing.getTextList());
    }

    @Test
    void testClearTextsOne() {
        List<Layerable> correctLayerList = new ArrayList<>();
        List<Text> correctTextList = new ArrayList<>();

        correctLayerList.add(this.testText1);
        correctTextList.add(this.testText1);
        this.testDrawing.addText(this.testText1);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctTextList, this.testDrawing.getTextList());

        correctLayerList.clear();
        correctTextList.clear();
        this.testDrawing.clearText();
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctTextList, this.testDrawing.getTextList());
    }

    @Test
    void testClearTextsMany() {
        List<Layerable> correctLayerList = new ArrayList<>();
        List<Text> correctTextList = new ArrayList<>();
        correctLayerList.add(this.testText1);
        correctLayerList.add(this.testText2);
        correctLayerList.add(this.testText3);
        correctTextList.add(this.testText1);
        correctTextList.add(this.testText2);
        correctTextList.add(this.testText3);
        this.testDrawing.addText(this.testText1);
        this.testDrawing.addText(this.testText2);
        this.testDrawing.addText(this.testText3);
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctTextList, this.testDrawing.getTextList());

        correctLayerList.clear();
        correctTextList.clear();
        this.testDrawing.clearText();
        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctTextList, this.testDrawing.getTextList());
    }

    @Test
    void testClearAll() {
        List<Layerable> correctLayerList = new ArrayList<>();
        List<Layerable> correctLineList = new ArrayList<>();
        List<Layerable> correctTextList = new ArrayList<>();
        this.testDrawing.addLine(this.testLine2);
        this.testDrawing.addText(this.testText3);
        correctLayerList.add(this.testLine2);
        correctLayerList.add(this.testText3);
        correctLineList.add(this.testLine2);
        correctTextList.add(this.testText3);

        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctLineList, this.testDrawing.getLineList());
        assertEquals(correctTextList, this.testDrawing.getTextList());

        this.testDrawing.clearAll();
        correctLayerList.clear();
        correctLineList.clear();
        correctTextList.clear();

        assertEquals(correctLayerList, this.testDrawing.getLayerableList());
        assertEquals(correctLineList, this.testDrawing.getLineList());
        assertEquals(correctTextList, this.testDrawing.getTextList());
    }

    @Test
    void testDescriptionEmpty() {
        assertEquals("Layers:", this.testDrawing.describe());
    }

    @Test
    void testDescriptionVariousObjects() {
        this.testDrawing.addLine(this.testLine1);
        this.testDrawing.addText(this.testText2);
        this.testDrawing.addText(this.testText3);

        assertEquals("Layers:\n1. " + this.testLine1.describe()
                + "\n2. " + this.testText2.describe()
                + "\n3. " + this.testText3.describe(), this.testDrawing.describe());
    }

    @Override
    @Test
    public void testToJson() {
        testToJsonEmpty();
        testToJsonVariousObjects();
    }

    @Test
    public void testToJsonEmpty() {
        JSONObject correctJsonObject = new JSONObject();
        JSONArray correctJsonLayers = new JSONArray();
        for (Layerable l : this.testDrawing.getLayerableList()) {
            correctJsonLayers.put(l.toJson());
        }
        correctJsonObject.put("Layers", correctJsonLayers);
        assertEquals(correctJsonObject.toString(4), this.testDrawing.toJson().toString(4));
    }

    @Test
    public void testToJsonVariousObjects() {
        this.testDrawing.addText(this.testText2);
        this.testDrawing.addLine(this.testLine1);
        this.testDrawing.addText(this.testText1);
        this.testDrawing.addLine(this.testLine3);
        JSONObject correctJsonObject = new JSONObject();
        JSONArray correctJsonLayers = new JSONArray();
        for (Layerable l : this.testDrawing.getLayerableList()) {
            correctJsonLayers.put(l.toJson());
        }
        correctJsonObject.put("Layers", correctJsonLayers);
        assertEquals(correctJsonObject.toString(4), this.testDrawing.toJson().toString(4));
    }
}