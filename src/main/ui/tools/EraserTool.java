package ui.tools;

import model.Line;
import model.Text;
import ui.GuiDrawing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class EraserTool extends Tool {
    private final String buttonLabel = "Eraser";
    private final double maxTextEraseDistance = 5;

    public EraserTool(GuiDrawing guiDrawing, JComponent parent) {
        super(guiDrawing, parent);
    }

    @Override
    public void mouseDraggedInDrawingArea(MouseEvent e) {
        Point mousePos = e.getPoint();

        List<Line> removeLineList = new ArrayList<>();
        List<Text> removeTextList = new ArrayList<>();
        markLinesForRemoval(removeLineList, mousePos);
        markTextForRemoval(removeTextList,mousePos);
        removeLines(removeLineList);
        removeTexts(removeTextList);
    }

    private void markLinesForRemoval(List<Line> markedList, Point mousePos) {
        for (Line l : this.guiDrawing.getLineList()) {
            if (Point.distance(mousePos.x,mousePos.y,l.getStart().getXpos(),l.getStart().getYpos())
                    < this.maxTextEraseDistance
                    || Point.distance(mousePos.x,mousePos.y,l.getEnd().getXpos(),l.getEnd().getYpos())
                    < this.maxTextEraseDistance) {
                markedList.add(l);
            }
        }
    }

    private void markTextForRemoval(List<Text> markedList, Point mousePos) {
        for (Text t : this.guiDrawing.getTextList()) {
            if (Point.distance(mousePos.x,mousePos.y,t.getPos().getXpos(),t.getPos().getYpos())
                    < this.maxTextEraseDistance) {
                markedList.add(t);
            }
        }
    }

    private void removeLines(List<Line> removeList) {
        for (Line l : removeList) {
            this.guiDrawing.removeLine(l);
        }
    }

    private void removeTexts(List<Text> removeList) {
        for (Text t : removeList) {
            this.guiDrawing.removeText(t);
        }
    }

    @Override
    protected void createButton(JComponent parent) {
        this.button = new JButton(this.buttonLabel);
        this.button = customizeButton(button);
        addToParent(parent);
    }

    @Override
    protected void addListener() {
        this.button.addActionListener(new EraserToolClickHandler());
    }

    private class EraserToolClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            guiDrawing.setActiveTool(EraserTool.this);
        }
    }
}
