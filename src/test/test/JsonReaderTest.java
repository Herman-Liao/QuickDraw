package test;


import model.*;
import model.Point;
import org.json.JSONArray;
import org.json.JSONObject;
import persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    private Color testColour1 = new Color(128, 255, 64);
    private Color testColour2 = new Color(255, 0, 0);
    private Color testColour3 = new Color(255, 255, 255);

    private Point testStart1 = new Point(24, 72);
    private Point testEnd1 = new Point(50, 100);
    private Point testStart2 = new Point(300, 500);
    private Point testEnd2 = new Point(200, 100);
    private Point testStart3 = new Point(10000, 0);
    private Point testEnd3 = new Point(0, 10000);

    private Line testLine1 = new Line(testStart1, testEnd1, testColour1);
    private Line testLine2 = new Line(testStart2, testEnd2, testColour2);
    private Line testLine3 = new Line(testStart3, testEnd3, testColour3);

    private String TEST_SOURCE = "./data/savedDrawingTest.json";
    private Drawing testDrawing;
    private JsonReader testJsonReader;

    @BeforeEach
    void runBefore() {
        this.testDrawing = new Drawing();
        this.testDrawing.addLine(testLine1);

        this.testJsonReader = new JsonReader(this.TEST_SOURCE);
    }

    @Test
    void testConstructor() {
        assertEquals(this.TEST_SOURCE, this.testJsonReader.getSource());
    }

    @Test
    void testAddLine() {
        Drawing correctDrawing = new Drawing();
        correctDrawing.addLine(this.testLine1);
        correctDrawing.addLine(this.testLine2);

        this.testJsonReader.addLine(this.testDrawing, this.testLine2.toJson());
        assertEquals(correctDrawing.describe(), this.testDrawing.describe());

        correctDrawing.addLine(this.testLine3);
        this.testJsonReader.addLine(this.testDrawing, this.testLine3.toJson());
        assertEquals(correctDrawing.describe(), this.testDrawing.describe());
    }

    @Test
    void testAddLines() {
        Drawing correctDrawing = new Drawing();
        correctDrawing.addLine(this.testLine1);
        correctDrawing.addLine(this.testLine2);
        correctDrawing.addLine(this.testLine3);

        JSONObject jsonLine2 = this.testLine2.toJson();
        JSONObject jsonLine3 = this.testLine3.toJson();
        JSONArray jsonNewLinesArray = new JSONArray();
        jsonNewLinesArray.put(jsonLine2);
        jsonNewLinesArray.put(jsonLine3);
        JSONObject jsonNewLinesObject = new JSONObject();
        jsonNewLinesObject.put("Layers", jsonNewLinesArray);
        this.testJsonReader.addLayers(this.testDrawing, jsonNewLinesObject);

        assertEquals(correctDrawing.describe(), this.testDrawing.describe());
    }

    @Test
    void testParseDrawing() {
        Drawing correctDrawing = new Drawing();
        correctDrawing.addLine(this.testLine1);

        JSONObject correctDrawingJson = correctDrawing.toJson();
        JSONObject testDrawingJson = this.testDrawing.toJson();

        assertEquals(this.testJsonReader.parseDrawing(correctDrawingJson).describe(),
                this.testJsonReader.parseDrawing(testDrawingJson).describe());
    }

    @Test
    void testReadEmptyFile() {
        this.testDrawing.clearLines();
        try {
            JsonWriter jsonWriter = new JsonWriter(TEST_SOURCE);
            jsonWriter.openWriter();
            jsonWriter.write(this.testDrawing);
            jsonWriter.closeWriter();

            assertEquals("{\"Layers\": []}", this.testJsonReader.readFile());
        } catch (FileNotFoundException e) {
            fail("The directory of the source should exist!");
        } catch (IOException e) {
            fail("There should be no errors in reading the file!");
        }
    }

    @Test
    void testReadCurrentFile() {
        try {
            JsonWriter jsonWriter = new JsonWriter(TEST_SOURCE);
            jsonWriter.openWriter();
            jsonWriter.write(this.testDrawing);
            jsonWriter.closeWriter();

            String TAB = jsonWriter.getTabString();
            String expectedString = "{\"Layers\": [{" +
                    TAB + "\"Type\": \"Line\"," +
                    TAB + "\"Colour g\": 255," +
                    TAB + "\"Start y\": 72," +
                    TAB + "\"Start x\": 24," +
                    TAB + "\"End y\": 100," +
                    TAB + "\"End x\": 50," +
                    TAB + "\"Colour r\": 128," +
                    TAB + "\"Colour b\": 64" +
                    "}]}";
            assertEquals(expectedString, this.testJsonReader.readFile());
        } catch (FileNotFoundException e) {
            fail("The directory of the source should exist!");
        } catch (IOException e) {
            fail("There should be no errors in reading the file!");
        }
    }

    @Test
    void testReadEmpty() {
        this.testDrawing.clearLines();
        try {
            JsonWriter jsonWriter = new JsonWriter(TEST_SOURCE);
            jsonWriter.openWriter();
            jsonWriter.write(this.testDrawing);
            jsonWriter.closeWriter();

            assertEquals(this.testDrawing.describe(), this.testJsonReader.read().describe());
        } catch (FileNotFoundException e) {
            fail("The directory of the source should exist!");
        } catch (IOException e) {
            fail("There should be no errors in reading the file!");
        }
    }

    @Test
    void testRead3Lines() {
        this.testDrawing.addLine(this.testLine2);
        this.testDrawing.addLine(this.testLine3);

        try {
            JsonWriter jsonWriter = new JsonWriter(TEST_SOURCE);
            jsonWriter.openWriter();
            jsonWriter.write(this.testDrawing);
            jsonWriter.closeWriter();

            assertEquals(this.testDrawing.describe(), this.testJsonReader.read().describe());
        } catch (FileNotFoundException e) {
            fail("The directory of the source should exist!");
        } catch (IOException e) {
            fail("There should be no errors in reading the file!");
        }
    }
}
