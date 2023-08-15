package model;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Display extends JPanel {

    private Drawing drawing;
    private List<PopUpWindow> popUpList;
    private Color backGroundColour;

    // MODIFIES: this
    // EFFECTS: Creates an empty drawing
    public Display() {
        super();
        this.drawing = new Drawing();
        this.popUpList = new ArrayList<>();
        this.backGroundColour = new Color(255,255,255);
        setBackground(this.backGroundColour);
    }

    public Drawing getDrawing() {
        return this.drawing;
    }

    public List<PopUpWindow> getPopUpList() {
        return this.popUpList;
    }

    public Color getBackGroundColour() {
        return this.backGroundColour;
    }

    public void setDrawing(Drawing drawing) {
        this.drawing = drawing;
    }

    // MODIFIES: this
    // EFFECTS: Adds the given text object to the end of textList
    public void addPopUp(PopUpWindow popUpWindow) {
        this.popUpList.add(popUpWindow);
    }

    // MODIFIES: this
    // EFFECTS: Removes the given line from lineList
    public boolean removePopUp(PopUpWindow chosenPopUp) {
        return this.popUpList.remove(chosenPopUp);
    }

    // MODIFIES: this
    // EFFECTS: Removes all pop-up windows from the drawing.
    public void clearPopUps() {
        this.popUpList.clear();
    }

    public void setBackGroundColour(Color backGroundColour) {
        this.backGroundColour = backGroundColour;
        EventLog.getInstance().logEvent(new Event("Background Colour set to: (" + this.backGroundColour.getRed() + ", "
                + this.backGroundColour.getGreen() + ", "
                + this.backGroundColour.getBlue() + ")"));
    }

    // EFFECTS: Renders all objects in the list of layerables.
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderDrawing(g);
        renderPopups(g);
    }

    public void renderDrawing(Graphics g) {
        this.drawing.render(g);
    }

    // EFFECTS: Renders all popup windows.
    public void renderPopups(Graphics g) {
        for (PopUpWindow p : this.popUpList) {
            p.render(g);
        }
    }
}
