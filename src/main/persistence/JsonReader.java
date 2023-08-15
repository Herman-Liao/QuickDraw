package persistence;

import model.*;
import model.Point;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Entire class (except for setSource) taken from persistence.JSONReader in
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: Constructs reader so that it will read from the source directory.
    public JsonReader(String source) {
        this.source = source;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    // EFFECTS: Reads the Drawing JSONObject from source directory file and returns it.
    // Will throw an IOException if there is an error while reading the file.
    public Drawing read() throws IOException {
        String jsonData = readFile();
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseDrawing(jsonObject);
    }

    // EFFECTS: Reads the whole file as a string and returns it.
    // Will throw an IOException if there is an error while reading the file.
    public String readFile() throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(this.source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: Parses Drawing from given jsonObject and returns it.
    public Drawing parseDrawing(JSONObject jsonObject) {
        Drawing drawing = new Drawing();
        addLayers(drawing, jsonObject);
        return drawing;
    }

    // MODIFIES: drawing
    // EFFECTS: Parses all Line objects from jsonObject and adds them to drawing.
    public void addLayers(Drawing drawing, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Layers");
        for (Object json : jsonArray) {
            JSONObject nextLayer = (JSONObject) json;
            String objectType = nextLayer.getString("Type");
            if (objectType.equals("Line")) {
                addLine(drawing, nextLayer);
            } else if (objectType.equals("Text")) {
                addText(drawing, nextLayer);
            }
        }
    }

    // MODIFIES: drawing
    // EFFECTS: Parses jsonObject and adds the resulting line to drawing.
    public void addLine(Drawing drawing, JSONObject jsonLine) {
        int startX = jsonLine.getInt("Start x");
        int startY = jsonLine.getInt("Start y");
        int endX = jsonLine.getInt("End x");
        int endY = jsonLine.getInt("End y");
        int colourR = jsonLine.getInt("Colour r");
        int colourG = jsonLine.getInt("Colour g");
        int colourB = jsonLine.getInt("Colour b");

        Point start = new Point(startX, startY);
        Point end = new Point(endX, endY);
        Color lineColour = new Color(colourR, colourG, colourB);

        drawing.addLine(new Line(start, end, lineColour));
    }

    // MODIFIES: drawing
    // EFFECTS: Parses jsonObject and adds the resulting line to drawing.
    public void addText(Drawing drawing, JSONObject jsonText) {
        int posX = jsonText.getInt("Position x");
        int posY = jsonText.getInt("Position y");
        int colourR = jsonText.getInt("Colour r");
        int colourG = jsonText.getInt("Colour g");
        int colourB = jsonText.getInt("Colour b");
        String string = jsonText.getString("String");

        Point position = new Point(posX, posY);
        Color colour = new Color(colourR, colourG, colourB);

        drawing.addText(new Text(position, colour, string));
    }
}
