package ui.tools;

import ui.GuiDrawing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class ClearTool extends Tool {
    private final String buttonLabel = "Clear";
    private int actionState = 0;
    private String objects = "";

    public ClearTool(GuiDrawing guiDrawing, JComponent parent) {
        super(guiDrawing, parent);
    }

    @Override
    public void activateAction() {
        super.activateAction();
        this.window.addMessageLine("Choose which objects to clear (1 for lines, 2 for text, 3 for all).");
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
                this.guiDrawing.confirmClearDrawing(keyCode, this.objects);
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
            if (keyCode == KeyEvent.VK_1) {
                this.objects = "Lines";
                this.window.addMessageLine("Are you sure you want to clear all lines?");
            } else if (keyCode == KeyEvent.VK_2) {
                this.objects = "Text";
                this.window.addMessageLine("Are you sure you want to clear all text objects?");
            } else if (keyCode == KeyEvent.VK_3) {
                this.objects = "All";
                this.window.addMessageLine("Are you sure you want to clear everything?");
            }
            this.window.addMessageLine("Enter \"y\" to confirm clear, or anything else to cancel: ");
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
        this.button.addActionListener(new ClearToolClickHandler());
    }

    private class ClearToolClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            guiDrawing.setActiveTool(ClearTool.this);
        }
    }
}
