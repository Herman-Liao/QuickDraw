package persistence;

import model.Drawing;
import java.io.*;

// Entire class (except for setDestination) taken from persistence.JSONWriter in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriter {
    private static final int TAB = 4; // One tab is TAB spaces
    private String destination;
    private PrintWriter writer;

    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // EFFECTS: Returns the destination directory of the writer.
    public String getDestination() {
        return this.destination;
    }

    // MODIFIES: this
    // EFFECTS: Sets the destination directory to the given destination.
    public void setDestination(String destination) {
        this.destination = destination;
    }

    // EFFECTS: Returns the number of spaces in one tab.
    public int getTab() {
        return this.TAB;
    }

    // EFFECTS: Returns one tab string.
    public String getTabString() {
        String outputString = "";
        for (int i = 0; i < this.TAB; i++) {
            outputString += " ";
        }
        return outputString;
    }

    // MODIFIES: this
    // EFFECTS: Opens the writer so that it can write to the file.
    public void openWriter() throws FileNotFoundException {
        this.writer = new PrintWriter(this.destination);
    }

    // MODIFIES: this
    // EFFECTS: Writes the JSON representation of the inputted drawing to the file.
    public void write(Drawing drawing) {
        this.writer.print(drawing.toJson().toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: Closes the writer so that it cannot write to the file.
    public void closeWriter() {
        writer.close();
    }
}
