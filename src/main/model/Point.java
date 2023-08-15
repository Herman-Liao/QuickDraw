package model;

public class Point {
    private double xpos;
    private double ypos;
    private boolean selected;

    public Point(double x, double y) {
        this.xpos = x;
        this.ypos = y;
        this.selected = false;
    }

    public double getXpos() {
        return this.xpos;
    }

    public double getYpos() {
        return this.ypos;
    }

    public boolean getSelected() {
        return this.selected;
    }

    public void setXpos(double newXpos) {
        this.xpos = newXpos;
    }

    public void setYpos(double newYpos) {
        this.ypos = newYpos;
    }

    public void setSelected(boolean newSelected) {
        this.selected = newSelected;
    }

    // MODIFIES: this
    // EFFECTS: Sets the x and y coordinates to the new inputted coordinates.
    public void setPos(double newXpos, double newYpos) {
        setXpos(newXpos);
        setYpos(newYpos);
    }

    public static double distance(Point p1, Point p2) {
        return Math.sqrt((p1.xpos - p2.xpos) * (p1.xpos - p2.xpos) + (p1.ypos - p2.ypos) * (p1.ypos - p2.ypos));
    }
}