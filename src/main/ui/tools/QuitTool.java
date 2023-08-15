package ui.tools;

import ui.GuiDrawing;

import javax.swing.*;
import java.awt.event.*;

public class QuitTool extends Tool {
    private final String buttonLabel = "Quit";
    private int actionState = 0;

    public QuitTool(GuiDrawing guiDrawing, JComponent parent) {
        super(guiDrawing, parent);
    }

    @Override
    public void activateAction() {
        super.activateAction();
        this.window.addMessageLine("All unsaved progress will be lost.  Quit anyways?");
        this.window.addMessageLine("Enter \"y\" to confirm quit, or anything else to cancel: ");
        this.guiDrawing.addPopUp(this.window);
        this.actionState = 1;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if (this.actionState == 1) {
            this.guiDrawing.confirmQuit(keyCode);
            this.actionState = 0;
        }
        this.window.clearMessages();
        this.guiDrawing.removePopUp(this.window);
        this.guiDrawing.setActiveTool(null);
    }

    @Override
    protected void createButton(JComponent parent) {
        this.button = new JButton(this.buttonLabel);
        this.button = customizeButton(button);
        addToParent(parent);
    }

    @Override
    protected void addListener() {
        this.button.addActionListener(new QuitToolClickHandler());
    }

    private class QuitToolClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            guiDrawing.setActiveTool(QuitTool.this);
        }
    }
}
