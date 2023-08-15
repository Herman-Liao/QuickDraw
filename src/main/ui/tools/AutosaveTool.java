package ui.tools;

import ui.GuiDrawing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class AutosaveTool extends Tool {
    private final String buttonLabel = "Autosave";
    private int actionState = 0;

    public AutosaveTool(GuiDrawing guiDrawing, JComponent parent) {
        super(guiDrawing, parent);
    }

    @Override
    public void activateAction() {
        super.activateAction();
        this.window.addMessageLine("Choose a directory to autosave to (1, 2, 3).");
        this.guiDrawing.addPopUp(this.window);
        this.actionState = 1;
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        switch (this.actionState) {
            case 1:
                inputDirectory(keyCode);
                break;
            case 2:
                this.guiDrawing.confirmAutosaveDrawing(keyCode);
                this.guiDrawing.removePopUp(this.window);
                this.window.clearMessages();
                this.guiDrawing.setActiveTool(null);
                break;
            default:
                this.guiDrawing.removePopUp(this.window);
                this.guiDrawing.setActiveTool(null);
        }
    }

    private void inputDirectory(int keyCode) {
        if (this.guiDrawing.chooseDirectory(keyCode)) {
            this.guiDrawing.removePopUp(this.window);
            this.window.clearMessages();
            this.window.addMessageLine("This will overwrite the contents of the save file.  Autosave anyways?");
            this.window.addMessageLine("Enter \"y\" to confirm autosave, or anything else to cancel: ");
            this.guiDrawing.addPopUp(this.window);
            this.actionState = 2;
        } else {
            this.actionState = 0;
            this.guiDrawing.removePopUp(this.window);
            this.window.clearMessages();
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
        this.button.addActionListener(new AutosaveToolClickHandler());
    }

    private class AutosaveToolClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            guiDrawing.setActiveTool(AutosaveTool.this);
        }
    }
}
