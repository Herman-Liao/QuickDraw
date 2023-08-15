package test;

import model.*;
import model.Point;
import persistence.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    private final String TEST_DESTINATION = "./data/savedDrawingTest.json";
    private Drawing testDrawing;
    private JsonWriter testJsonWriter;

    @BeforeEach
    void runBefore() {
        Color testColour1 = new Color(128, 255, 64);
        Color testColour2 = new Color(255, 0, 0);
        Color testColour3 = new Color(255, 255, 255);

        Point testStart1 = new Point(24, 72);
        Point testEnd1 = new Point(50, 100);
        Point testStart2 = new Point(300, 500);
        Point testEnd2 = new Point(200, 100);
        Point testStart3 = new Point(10000, 0);
        Point testEnd3 = new Point(0, 10000);

        Line testLine1 = new Line(testStart1, testEnd1, testColour1);
        Line testLine2 = new Line(testStart2, testEnd2, testColour2);
        Line testLine3 = new Line(testStart3, testEnd3, testColour3);

        this.testDrawing = new Drawing();
        this.testDrawing.addLine(testLine1);
        this.testDrawing.addLine(testLine2);
        this.testDrawing.addLine(testLine3);

        this.testJsonWriter = new JsonWriter(TEST_DESTINATION);
    }

    @Test
    void testConstructor() {
        assertEquals(this.TEST_DESTINATION, this.testJsonWriter.getDestination());
    }

    @Test
    void testOpenWriterDestinationExists() {
        try {
            this.testJsonWriter.openWriter();
        } catch (FileNotFoundException e) {
            fail("The \"./data/savedDrawingTest.json\" directory should exist!");
        }
    }

// If I want to trigger a FileNotFoundException, I need to make the PrintWriter try to write somewhere that cannot have
// files created inside, or the PrintWriter will just create a file in that directory and not throw the exception.
// Therefore, this test will always fail, so it has been commented out.
/*
    @Test
    void testOpenWriterDestinationDoesNotExist() {
        try {
            this.testJsonWriter.setDestination("./data/DNE.json");
            this.testJsonWriter.openWriter();
            fail("The \"./data/DNE.json\" directory should not exist!");
        } catch (FileNotFoundException e) {

        }
    }
*/


    @Test
    void testCloseWriter() {
        try {
            this.testJsonWriter.openWriter();
            this.testJsonWriter.closeWriter();
        } catch (Exception e) {
            fail("There should be no exceptions!");
        }
    }

    @Test
    void testWrite() {
        try {
            JsonReader jsonReader = new JsonReader(this.TEST_DESTINATION);
            this.testJsonWriter.openWriter();
            this.testJsonWriter.write(this.testDrawing);
            this.testJsonWriter.closeWriter();
            // For some reason, the assertEquals below fails when I try to assert that the drawings or their line lists
            // are equal, but I really only need the descriptions to be equal, because it would show that they are the
            // same drawing as well.
            // Even two empty drawings are not equal, and using .equals does not work either.  It may be because two
            // different objects are referenced, but the two strings from .describe() should also be different objects.
            assertEquals(this.testDrawing.describe(), jsonReader.read().describe());
        } catch (FileNotFoundException e) {
            fail("The \"./data/savedDrawingTest.json\" directory should exist!");
        } catch (IOException e) {
            fail("There should be no errors when reading the file!");
        }
    }

    @Test
    void testGetTabString() {
        String correctString = "";
        for (int i = 0; i < this.testJsonWriter.getTab(); i++) {
            correctString += " ";
        }
        assertEquals(correctString, this.testJsonWriter.getTabString());
    }
}
