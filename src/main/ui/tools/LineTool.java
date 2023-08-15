package ui.tools;

import model.Line;
import ui.GuiDrawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LineTool extends Tool {
    private Line line;
    private final String buttonLabel = "Line";

    public LineTool(GuiDrawing guiDrawing, JComponent parent) {
        super(guiDrawing, parent);
    }

    @Override
    public void mousePressedInDrawingArea(MouseEvent e) {
        Point mousePos = e.getPoint();
        model.Point lineStart = new model.Point(mousePos.x, mousePos.y);
        model.Point lineEnd = new model.Point(mousePos.x, mousePos.y);
        this.line = new Line(lineStart, lineEnd, new Color(0,0,0));
        this.line.getEnd().setSelected(true);
        this.guiDrawing.addLine(this.line);
    }

    @Override
    public void mouseReleasedInDrawingArea(MouseEvent e) {
        this.line.getEnd().setSelected(false);
    }

    @Override
    public void mouseDraggedInDrawingArea(MouseEvent e) {
        Point mousePos = e.getPoint();
        this.line.getEnd().setPos(mousePos.x, mousePos.y);
    }

    @Override
    protected void createButton(JComponent parent) {
        this.button = new JButton(this.buttonLabel);
        this.button = customizeButton(button);
        addToParent(parent);
    }

    @Override
    protected void addListener() {
        this.button.addActionListener(new LineToolClickHandler());
    }

    private class LineToolClickHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            guiDrawing.setActiveTool(LineTool.this);
        }
    }
}
