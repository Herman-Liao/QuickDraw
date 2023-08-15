package ui.tools;

import model.Line;
import ui.GuiDrawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class PencilTool extends Tool {
    protected Line line;
    private final String buttonLabel = "Pencil";

    public PencilTool(GuiDrawing guiDrawing, JComponent parent) {
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
        this.line.getEnd().setSelected(false);
        model.Point lineStart = new model.Point(mousePos.x, mousePos.y);
        model.Point lineEnd = new model.Point(mousePos.x, mousePos.y);
        this.line = new Line(lineStart, lineEnd, new Color(0,0,0));
        this.line.getEnd().setSelected(true);
        this.guiDrawing.addLine(this.line);
    }

    @Override
    protected void createButton(JComponent parent) {
        this.button = new JButton(this.buttonLabel);
        this.button = customizeButton(button);
        addToParent(parent);
    }

    @Override
    protected void addListener() {
        this.button.addActionListener(new PencilToolClickHandler());
    }

    private class PencilToolClickHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            guiDrawing.setActiveTool(PencilTool.this);
        }
    }
}
