package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Drawing implements Writable {
    private List<Layerable> layerableList;
    private List<Line> lineList;
    private List<Text> textList;

    // MODIFIES: this
    // EFFECTS: Creates an empty drawing
    public Drawing() {
        this.layerableList = new ArrayList<>();
        this.lineList = new ArrayList<>();
        this.textList = new ArrayList<>();
    }

    public List<Layerable> getLayerableList() {
        return this.layerableList;
    }

    public List<Line> getLineList() {
        return this.lineList;
    }

    public List<Text> getTextList() {
        return this.textList;
    }

    // MODIFIES: this
    // EFFECTS: Adds the given line to the end of lineList
    public void addLine(Line newLine) {
        this.layerableList.add(newLine);
        this.lineList.add(newLine);
        EventLog.getInstance().logEvent(new Event("Line added: " + newLine.describe()));
    }

    // MODIFIES: this
    // EFFECTS: Removes the given line from lineList
    public boolean removeLine(Line chosenLine) {
        for (Line line : this.lineList) {
            if (chosenLine.equals(line)) {
                this.layerableList.remove(chosenLine);
                this.lineList.remove(chosenLine);
                EventLog.getInstance().logEvent(new Event("Line successfully removed: " + chosenLine.describe()));
                return true;
            }
        }
        EventLog.getInstance().logEvent(new Event("Line unsuccessfully removed: " + chosenLine.describe()));
        return false;
    }

    // MODIFIES: this
    // EFFECTS: Removes all line objects from the drawing.
    public void clearLines() {
        for (Line line : this.lineList) {
            this.layerableList.remove(line);
        }
        this.lineList.clear();
        EventLog.getInstance().logEvent(new Event("All lines cleared from drawing."));
    }

    // MODIFIES: this
    // EFFECTS: Adds the given text object to the end of textList
    public void addText(Text newText) {
        this.layerableList.add(newText);
        this.textList.add(newText);
        EventLog.getInstance().logEvent(new Event("Text added: " + newText.describe()));
    }

    // MODIFIES: this
    // EFFECTS: Removes the given text object from textList
    public boolean removeText(Text chosenText) {
        for (Text text : this.textList) {
            if (chosenText.equals(text)) {
                this.layerableList.remove(chosenText);
                this.textList.remove(chosenText);
                EventLog.getInstance().logEvent(new Event("Text successfully removed: " + chosenText.describe()));
                return true;
            }
        }
        EventLog.getInstance().logEvent(new Event("Text unsuccessfully removed: " + chosenText.describe()));
        return false;
    }

    // MODIFIES: this
    // EFFECTS: Removes all text objects from the drawing.
    public void clearText() {
        for (Text text : this.textList) {
            this.layerableList.remove(text);
        }
        this.textList.clear();
        EventLog.getInstance().logEvent(new Event("All text objects cleared from drawing."));
    }

    // MODIFIES: this
    // EFFECTS: Removes all objects from the drawing.
    public void clearAll() {
        this.layerableList.clear();
        this.lineList.clear();
        this.textList.clear();
        EventLog.getInstance().logEvent(new Event("All layerable objects cleared from drawing."));
    }

    // EFFECTS: Returns a string that describes all the layerables in the drawing.
    public String describe() {
        String outputString = "Layers:";
        for (int i = 1; i <= this.layerableList.size(); i++) {
            outputString += "\n" + i + ". " + this.layerableList.get(i - 1).describe();
        }
        return outputString;
    }

    // EFFECTS: Renders all objects in the list of layerables.
    public void render(Graphics g) {
        for (Line l : this.lineList) {
            l.render(g);
        }
        for (Text t : this.textList) {
            t.render(g);
        }
    }

    // EFFECTS: Returns the drawing as a JSON object.
    // NOTE: This method was modelled off of model.WorkRoom.thingiesToJson() and model.WorkRoom.toJson() from
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();

        JSONArray jsonLayers = new JSONArray();
        for (Layerable l : this.layerableList) {
            jsonLayers.put(l.toJson());
        }

        jsonObject.put("Layers", jsonLayers);

        return jsonObject;
    }
}