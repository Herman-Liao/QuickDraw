package ui.tools;

import model.PopUpWindow;
import ui.*;

import javax.swing.*;
import java.awt.event.*;

// Entire class (except for setDestination) taken from ui.tools.Tool in
// https://github.students.cs.ubc.ca/CPSC210/SimpleDrawingPlayer-Complete
public abstract class Tool {
    protected JButton button;
    protected PopUpWindow window = new PopUpWindow(400, 75);
    protected GuiDrawing guiDrawing;
    private boolean active;

    public Tool(GuiDrawing guiDrawing, JComponent parent) {
        this.guiDrawing = guiDrawing;
        createButton(parent);
        addToParent(parent);
        this.active = false;
        addListener();
    }

    protected JButton customizeButton(JButton button) {
        button.setBorderPainted(true);
        button.setFocusPainted(true);
        button.setContentAreaFilled(true);
        return button;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean newActive) {
        this.active = newActive;
        if (this.active) {
            activateAction();
        }
    }

    protected abstract void createButton(JComponent parent);

    protected abstract void addListener();

    public void addToParent(JComponent parent) {
        parent.add(this.button);
    }

    public void activateAction() {
        this.guiDrawing.setFocusable(true);
        this.guiDrawing.requestFocus();
        this.window.clearMessages();
        this.guiDrawing.clearPopUps();
    }

    public void mousePressedInDrawingArea(MouseEvent e) {}

    public void mouseReleasedInDrawingArea(MouseEvent e) {}

    public void mouseClickedInDrawingArea(MouseEvent e) {}

    public void mouseDraggedInDrawingArea(MouseEvent e) {}

    public void keyPressed(KeyEvent keyEvent) {}
}
