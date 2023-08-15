package model;

import org.json.JSONObject;

import java.awt.*;

import static model.Line.round;

public class Text implements Layerable {
    private Point pos;
    private Color colour;
    private String string;

    public Text(Point pos, Color colour, String string) {
        this.pos = pos;
        this.colour = colour;
        this.string = string;
    }

    public Point getPos() {
        return this.pos;
    }

    public void setPos(Point newPos) {
        this.pos = newPos;
    }

    public Color getColour() {
        return this.colour;
    }

    public void setColour(Color newColour) {
        this.colour = newColour;
    }

    public String getString() {
        return this.string;
    }

    public void setString(String newString) {
        this.string = newString;
    }
/*
    // EFFECTS: Returns a list of DrawingPoint for rendering the all the characters onto the GUI
    //@Override
    public List<DrawingPoint> getDrawingPointsList() {
        List<DrawingPoint> drawingPointList = new ArrayList<>();
        Point point;
        String character;
        DrawingPoint drawingPoint;
        for (int i = 0; i < this.string.length(); i++) {
            point = new Point(this.pos.getXpos() + i, this.pos.getYpos());
            character = Character.toString(this.string.charAt(i));
            drawingPoint = new DrawingPoint(point.getXpos(), point.getYpos(), this.colour, character);
            drawingPointList.add(drawingPoint);
        }
        return drawingPointList;
    }*/

    public void render(Graphics g) {
        g.setColor(this.colour);
        g.drawChars(this.string.toCharArray(),0,this.string.length(),
                round(this.pos.getXpos()),round(this.pos.getYpos()));
    }

    // MODIFIES: this
    // EFFECTS: Translates the starting point (and therefore all the text) by dx and dy.
    @Override
    public void move(int dx, int dy) {
        setPos(new Point(this.pos.getXpos() + dx, this.pos.getYpos() + dy));
    }

    // EFFECTS: Returns a string describing the text object's properties.
    @Override
    public String describe() {
        String positionString = "Position = (" + this.pos.getXpos() + ", " + this.pos.getYpos() + ")";
        String colourString = "Colour = (" + this.colour.getRed() + ", "
                + this.colour.getGreen() + ", "
                + this.colour.getBlue() + ")";
        String textString = "Text = \"" + this.string + "\"";
        return "Text: " + positionString + ", " + colourString + ", " + textString;
    }

    // EFFECTS: Returns a JSON representation of the line.
    // NOTE: This method was modelled off of model.Thingy.toJson() from
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Type", "Text");
        jsonObject.put("Position x", this.pos.getXpos());
        jsonObject.put("Position y", this.pos.getYpos());
        jsonObject.put("Colour r", this.colour.getRed());
        jsonObject.put("Colour g", this.colour.getGreen());
        jsonObject.put("Colour b", this.colour.getBlue());
        jsonObject.put("String", this.string);
        return jsonObject;
    }
}
