package model;

import ui.GuiDrawing;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PopUpWindow extends JPanel {
    private int width;
    private int height;
    private List<String> messages;
    private Color messageColour;
    private static final int LEFTMARGIN = 15;

    public PopUpWindow(int width, int height) {
        this.width = width;
        this.height = height;
        this.messages = new ArrayList<>();
        this.messageColour = new Color(0,0,0);
    }

    public PopUpWindow(int width, int height, List<String> messages) {
        this.width = width;
        this.height = height;
        this.messages = messages;
        this.messageColour = new Color(0,0,0);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public Color getMessageColour() {
        return this.messageColour;
    }

    public void setMessageColour(Color messageColour) {
        this.messageColour = messageColour;
//        EventLog.getInstance().logEvent(new Event("Set Message Colour to: (" + this.messageColour.getRed() + ", "
//                + this.messageColour.getGreen() + ", "
//                + this.messageColour.getBlue() + ")"));
    }

    public void addMessageLine(String str) {
        this.messages.add(str);
//        EventLog.getInstance().logEvent(new Event("Added line to message: \"" + str + "\""));
    }

    public void clearMessages() {
        this.messages.clear();
//        EventLog.getInstance().logEvent(new Event("Cleared all lines from message"));
    }

    // EFFECTS: Returns a string that describes all the layerables in the drawing.
    public String describe() {
        String outputString = "";
        outputString += "Width: " + this.width + ", ";
        outputString += "Height: " + this.height + ", ";
        outputString += "Message Colour: (" + this.messageColour.getRed() + ", "
                + this.messageColour.getGreen() + ", "
                + this.messageColour.getBlue() + "), ";
        outputString += "Lines:";
        for (int i = 1; i <= this.messages.size(); i++) {
            outputString += "\n\t" + i + ". \"" + this.messages.get(i - 1) + "\"";
        }
        return outputString;
    }

    public void render(Graphics g) {
        g.setColor(new Color(255,255,255));
        g.fillRect(GuiDrawing.CENTERX - this.width / 2, GuiDrawing.CENTERY - this.height / 2, this.width, this.height);
        g.setColor(this.messageColour);
        g.drawRect(GuiDrawing.CENTERX - this.width / 2, GuiDrawing.CENTERY - this.height / 2, this.width, this.height);

        String str;
        for (int i = 0; i < messages.size(); i++) {
            str = messages.get(i);
            g.drawChars(str.toCharArray(),0,str.length(),
                    GuiDrawing.CENTERX - this.width / 2 + this.LEFTMARGIN,GuiDrawing.CENTERY + 10 * i);
        }
    }
}
