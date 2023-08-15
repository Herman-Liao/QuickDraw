package ui;

import model.*;
import model.Event;
import persistence.*;
import ui.tools.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import org.json.JSONException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiDrawing extends JFrame {
    public static final int WINDOWWIDTH = 1200;
    public static final int WINDOWHEIGHT = 900;
    public static final int CENTERX = WINDOWWIDTH / 2;
    public static final int CENTERY = WINDOWHEIGHT / 2;

    private Display display;
    private boolean autosave;

    private List<Tool> toolList;
    private Tool activeTool;

    private static final String SAVE_DIRECTORY1 = "./data/savedDrawing1.json";
    private static final String SAVE_DIRECTORY2 = "./data/savedDrawing2.json";
    private static final String SAVE_DIRECTORY3 = "./data/savedDrawing3.json";
    private JsonWriter jsonWriter = new JsonWriter(SAVE_DIRECTORY1);
    private JsonReader jsonReader = new JsonReader(SAVE_DIRECTORY1);

    public GuiDrawing() {
        super("Quick Draw!");
        initFields();
        initInteractions();
        initGraphics();
    }

    public void initFields() {
        this.display = new Display();
        this.toolList = new ArrayList<>();
        this.autosave = false;
        this.activeTool = null;
    }

    public void initGraphics() {
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(WINDOWWIDTH, WINDOWHEIGHT));
        createTools();
        add(this.display, BorderLayout.CENTER);
        validate();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initInteractions() {
        DrawingMouseListener dml = new DrawingMouseListener();
        KeyHandler kl = new KeyHandler();
        addKeyListener(kl);
        addMouseListener(dml);
        addMouseMotionListener(dml);
    }

    public void addLine(Line line) {
        this.display.getDrawing().addLine(line);
        if (this.autosave) {
            saveDrawing();
        }
    }

    public void addText(Text text) {
        this.display.getDrawing().addText(text);
        if (this.autosave) {
            saveDrawing();
        }
    }

    public void addPopUp(PopUpWindow popUpWindow) {
        this.display.addPopUp(popUpWindow);
    }

    public List<Layerable> getLayerableList() {
        return this.display.getDrawing().getLayerableList();
    }

    public List<Line> getLineList() {
        return this.display.getDrawing().getLineList();
    }

    public List<Text> getTextList() {
        return this.display.getDrawing().getTextList();
    }

    public boolean removeLine(Line line) {
        boolean removed = this.display.getDrawing().removeLine(line);
        if (removed) {
            if (this.autosave) {
                saveDrawing();
            }
        }
        return removed;
    }

    public boolean removeText(Text text) {
        boolean removed = this.display.getDrawing().removeText(text);
        if (removed) {
            if (this.autosave) {
                saveDrawing();
            }
        }
        return removed;
    }

    public boolean removePopUp(PopUpWindow popUpWindow) {
        return this.display.removePopUp(popUpWindow);
    }

    public void clear(String objects) {
        switch (objects) {
            case "Lines":
                this.display.getDrawing().clearLines();
                break;
            case "Text":
                this.display.getDrawing().clearText();
                break;
            case "All":
                this.display.getDrawing().clearAll();
                break;
        }
        if (this.autosave) {
            saveDrawing();
        }
    }

    public void confirmClearDrawing(int confirmation, String objects) {
        PopUpWindow window = new PopUpWindow(200,100);
        addPopUp(window);
        if (confirmation == KeyEvent.VK_Y) {
            window.addMessageLine("Clearing...");
            clear(objects);
            window.clearMessages();
            window.addMessageLine("Clear successful!");
        } else {
            window.addMessageLine("Cancelled clear.");
        }
        removePopUp(window);
    }

    public void clearPopUps() {
        this.display.clearPopUps();
    }

    public boolean chooseDirectory(int directoryNumber) {
        switch (directoryNumber) {
            case KeyEvent.VK_1:
                this.jsonWriter.setDestination(SAVE_DIRECTORY1);
                this.jsonReader.setSource(SAVE_DIRECTORY1);
                return true;
            case KeyEvent.VK_2:
                this.jsonWriter.setDestination(SAVE_DIRECTORY2);
                this.jsonReader.setSource(SAVE_DIRECTORY2);
                return true;
            case KeyEvent.VK_3:
                this.jsonWriter.setDestination(SAVE_DIRECTORY3);
                this.jsonReader.setSource(SAVE_DIRECTORY3);
                return true;
            default:
                List<String> errorMessage = new ArrayList<>();
                errorMessage.add("No directory chosen.  Action cancelled.");
                displayWindow(errorMessage);
                return false;
        }
    }

    public void confirmSaveDrawing(int confirmation) {
        PopUpWindow window = new PopUpWindow(200,100);
        addPopUp(window);
        if (confirmation == KeyEvent.VK_Y) {
            window.addMessageLine("Saving...");
            saveDrawing();
            window.clearMessages();
            window.addMessageLine("Save successful!");
        } else {
            window.addMessageLine("Cancelled save.");
        }
        removePopUp(window);
    }

    private void saveDrawing() {
        try {
            this.jsonWriter.openWriter();
            this.jsonWriter.write(this.display.getDrawing());
            this.jsonWriter.closeWriter();
        } catch (FileNotFoundException e) {
            List<String> errorMessage = new ArrayList<>();
            errorMessage.add("Error when writing to file!");
            displayWindow(errorMessage);
        }
    }

    public void confirmAutosaveDrawing(int confirmation) {
        PopUpWindow window = new PopUpWindow(200,100);
        addPopUp(window);
        if (confirmation == KeyEvent.VK_Y) {
            window.addMessageLine("Turning on Autosave...");
            this.autosave = true;
            window.clearMessages();
            window.addMessageLine("Autosave is now on!");
        } else {
            window.addMessageLine("Turning off Autosave...");
            this.autosave = false;
            window.clearMessages();
            window.addMessageLine("Autosave is now off!");
        }
        removePopUp(window);
    }

    public void confirmLoadDrawing(int confirmation) {
        PopUpWindow window = new PopUpWindow(200,100);
        addPopUp(window);
        if (confirmation == KeyEvent.VK_Y) {
            window.addMessageLine("Loading...");
            loadDrawing();
            window.clearMessages();
            window.addMessageLine("Load successful!");
        } else {
            window.addMessageLine("Cancelled load.");
        }
        removePopUp(window);
    }

    private void loadDrawing() {
        try {
            this.display.setDrawing(this.jsonReader.read());
        } catch (IOException e) {
            List<String> errorMessage = new ArrayList<>();
            errorMessage.add("Error when reading from file!");
            displayWindow(errorMessage);
        } catch (JSONException e) {
            List<String> errorMessage = new ArrayList<>();
            errorMessage.add("That file is not JSON-readable!");
            errorMessage.add("1. Make sure that file is a .json file.");
            errorMessage.add("2. Save any drawing to that file.");
            errorMessage.add("3. Try loading that file again.");
            displayWindow(errorMessage);
        }
    }

    public void confirmQuit(int confirmation) {
        PopUpWindow window = new PopUpWindow(200,100);
        addPopUp(window);
        if (confirmation == KeyEvent.VK_Y) {
            window.addMessageLine("Quitting...");
            quit();
            window.clearMessages();
            window.addMessageLine("Quit successful!  This should not be visible.");
        } else {
            window.addMessageLine("Cancelled quit.");
        }
        removePopUp(window);
    }

    public void quit() {
        for (Event nextEvent : EventLog.getInstance()) {
            //System.out.println(nextEvent.toString());
            System.out.println(nextEvent.getDate() + ": " + nextEvent.getDescription());
        }
        System.exit(0);
    }

    private void displayWindow(List<String> messageLines) {
        PopUpWindow window = new PopUpWindow(200,100);
        for (String line : messageLines) {
            window.addMessageLine(line);
        }
        addPopUp(window);
        // Do something here to decide when to remove the window
        removePopUp(window);
    }

    public void setActiveTool(Tool newActiveTool) {
        if (activeTool != null) {
            activeTool.setActive(false);
        }
        if (newActiveTool != null) {
            newActiveTool.setActive(true);
        }
        this.activeTool = newActiveTool;
    }

    private void handleMousePressed(MouseEvent e) {
        if (this.activeTool != null) {
            this.activeTool.mousePressedInDrawingArea(e);
        }
        repaint();
    }

    private void handleMouseReleased(MouseEvent e) {
        if (this.activeTool != null) {
            this.activeTool.mouseReleasedInDrawingArea(e);
        }
        repaint();
    }

    private void handleMouseClicked(MouseEvent e) {
        if (this.activeTool != null) {
            this.activeTool.mouseClickedInDrawingArea(e);
        }
        repaint();
    }

    private void handleMouseDragged(MouseEvent e) {
        if (this.activeTool != null) {
            this.activeTool.mouseDraggedInDrawingArea(e);
        }
        repaint();
    }

    private void handleKeyPressed(KeyEvent keyEvent) {
        if (this.activeTool != null) {
            this.activeTool.keyPressed(keyEvent);
        }
        repaint();
    }

    private void createTools() {
        JPanel toolArea = new JPanel();
        toolArea.setLayout(new GridLayout(3,3));
        toolArea.setSize(0,0);
        add(toolArea, BorderLayout.SOUTH);

        createEditingTools(toolArea);

        SaveTool saveTool = new SaveTool(this,toolArea);
        toolList.add(saveTool);

        AutosaveTool autosaveTool = new AutosaveTool(this,toolArea);
        toolList.add(autosaveTool);

        LoadTool loadTool = new LoadTool(this,toolArea);
        toolList.add(loadTool);

        QuitTool quitTool = new QuitTool(this,toolArea);
        toolList.add(quitTool);

        setActiveTool(null);
    }

    private void createEditingTools(JPanel toolArea) {
        LineTool lineTool = new LineTool(this, toolArea);
        toolList.add(lineTool);

        PencilTool pencilTool = new PencilTool(this,toolArea);
        toolList.add(pencilTool);

        TextTool textTool = new TextTool(this, toolArea);
        toolList.add(textTool);

        EraserTool eraserTool = new EraserTool(this,toolArea);
        toolList.add(eraserTool);

        ClearTool clearTool = new ClearTool(this,toolArea);
        toolList.add(clearTool);
    }

    private class DrawingMouseListener extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            handleMousePressed(translateEvent(e));
        }

        public void mouseReleased(MouseEvent e) {
            handleMouseReleased(translateEvent(e));
        }

        public void mouseClicked(MouseEvent e) {
            handleMouseClicked(translateEvent(e));
        }

        public void mouseDragged(MouseEvent e) {
            handleMouseDragged(translateEvent(e));
        }

        private MouseEvent translateEvent(MouseEvent e) {
            return SwingUtilities.convertMouseEvent(e.getComponent(), e, display);
        }
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            handleKeyPressed(e);
        }
    }

    public static void main(String[] args) {
        new GuiDrawing();
    }

    /*

    private void drawingLoop() throws IOException {
        do {
            render();
            autosave();
            System.out.println("Commands:\n- \"line\": add a line\n- \"text\": add text"
                    + "\n- \"erase\": erase an object\n- \"clear\": clear objects of a type\n- \"save\" to save drawing"
                    + "\n- \"load\" to load drawing\n- \"autosave\" to set autosave setting\n- \"quit\" to quit");
            this.userInputString = this.reader.next();
        } while (runUserCommand(this.userInputString));
    }

    // This is a list of commands, so I don't think that there's a way to stop the method length from exceeding 25,
    // unless there is a way to store functions to run into a list.
    private boolean runUserCommand(String input) {
        switch (input) {
            case "quit":
                return quit();
            case "save":
                confirmSaveDrawing();
                break;
            case "autosave":
                setAutosave();
                break;
            case "load":
                loadDrawing();
                break;
            case "line":
                inputLine();
                break;
            case "text":
                inputText();
                break;
            case "erase":
                erase();
                break;
            case "clear":
                clear();
                break;
        }
        return true;
    }

    private void inputText() {
        Colour userColour;

        try {
            System.out.print("Enter the text's starting position, separating the x and y coordinates by a space: ");
            Point userStartPoint = new Point(this.reader.nextInt(), this.reader.nextInt());
            System.out.println("Enter the text's colour, separating the r, g, and b values with spaces: ");
            do {
                System.out.print("Remember that all RGB values must be between 0 and 255! ");
                userColour = new Colour(this.reader.nextInt(), this.reader.nextInt(), this.reader.nextInt());
            } while (!userColour.validateColour());
            System.out.print("What would you like the text to say?  Enter what it will say: ");
            this.reader.nextLine();
            String userString = this.reader.nextLine();

            Text userText = new Text(userStartPoint, userColour, userString);
            this.drawing.addText(userText);
            System.out.println("Text added!");
        } catch (Exception e) {
            System.out.println("Invalid Syntax!  One of the coordinate values you entered was not a number!");
        }
    }

    private void clear() {
        System.out.println("What would you like to clear?"
                + "\nClear all lines: \"lines\""
                + "\nClear all text: \"text\""
                + "\nClear everything: \"all\"\n");
        this.userInputString = reader.next();
        switch (this.userInputString) {
            case "lines":
                this.drawing.clearLines();
                System.out.println("All lines have been cleared!");
                break;
            case "text":
                this.drawing.clearText();
                System.out.println("All text has been cleared!");
                break;
            case "all":
                this.drawing.clearAll();
                System.out.println("Everything has been cleared!");
                break;
            default:
                System.out.println("The input did not specify what should have been cleared, so nothing was cleared.");
                break;
        }
    }

    private void setAutosave() {
        System.out.println("Would you like to turn on autosave?");
        System.out.println("Enter \"y\" to turn on autosave, \"n\" to turn off autosave, or anything else to keep"
                + " all previous autosave settings.");
        this.userInputString = this.reader.next();
        switch (this.userInputString) {
            case "y":
                System.out.println();
                if (chooseDirectory("autosave")) {
                    this.isAutosave = true;
                } else {
                    System.out.println("Current autosave settings have not changed.");
                }
                System.out.println("Every edit will be autosaved to the chosen directory!");
                break;
            case "n":
                this.isAutosave = false;
                System.out.println("Autosave has been disabled!");
                break;
            default:
                System.out.println("Current autosave settings have not changed.");
                break;
        }
    }

    private void autosave() {
        if (isAutosave) {
            saveDrawing();
        }
    }

    private boolean quit() {
        System.out.println("Would you like to save your drawing before quitting?  All unsaved progress will be lost.");
        System.out.println("Enter \"y\" to save, \"n\" to quit without saving, or anything else to cancel: ");
        this.userInputString = this.reader.next();
        switch (this.userInputString) {
            case "y":
                confirmSaveDrawing();
                System.out.println("Quitting...");
                return false;
            case "n":
                System.out.println("Quitting without saving...");
                return false;
            default:
                System.out.println("Quit cancelled.");
                return true;
        }
    }

    private void drawPoint(Point point, TextColor colour, String character) {
        TextGraphics text = screen.newTextGraphics();
        text.setForegroundColor(colour);
        text.putString(point.getXpos(), point.getYpos(), character);
    }



     */
}