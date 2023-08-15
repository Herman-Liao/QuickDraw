package ui.tools;

import model.Text;
import ui.GuiDrawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextTool extends Tool {
    private Text text;
    private final String buttonLabel = "Text";

    public TextTool(GuiDrawing guiDrawing, JComponent parent) {
        super(guiDrawing, parent);
    }

    @Override
    public void mouseReleasedInDrawingArea(MouseEvent e) {
        Point mousePos = e.getPoint();
        model.Point textPoint = new model.Point(mousePos.x, mousePos.y);
        this.text = new Text(textPoint, new Color(0,0,0), "");
        this.guiDrawing.addText(text);
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        char keyChar = keyEvent.getKeyChar();
        this.text.setString(this.text.getString() + keyChar);
    }

    @Override
    protected void createButton(JComponent parent) {
        this.button = new JButton(this.buttonLabel);
        this.button = customizeButton(button);
        addToParent(parent);
    }

    @Override
    protected void addListener() {
        this.button.addActionListener(new TextToolClickHandler());
    }

    private class TextToolClickHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            guiDrawing.setActiveTool(TextTool.this);
        }
    }
}
