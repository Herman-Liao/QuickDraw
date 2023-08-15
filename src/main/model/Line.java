package model;

import org.json.JSONObject;

import java.awt.*;

public class Line implements Layerable {
    private Point start;
    private Point end;
    private Color colour;

    public Line(Point start, Point end, Color colour) {
        this.start = start;
        this.end = end;
        this.colour = colour;
    }

    public Point getStart() {
        return this.start;
    }

    public void setStart(Point newStart) {
        this.start = newStart;
    }

    public Point getEnd() {
        return this.end;
    }

    public void setEnd(Point newEnd) {
        this.end = newEnd;
    }

    public Color getColour() {
        return this.colour;
    }

    public void setColour(Color newColour) {
        this.colour = newColour;
    }

    public void render(Graphics g) {
        g.setColor(this.colour);
        g.drawLine(round(this.start.getXpos()),
                round(this.start.getYpos()),
                round(this.end.getXpos()),
                round(this.end.getYpos()));
    }

    // EFFECTS: Returns true if there is an intersection between the two line segments, and false otherwise.
    public static boolean isIntersecting(Line line1, Line line2) {
        double slope1 = line1.calculateSlope();
        double slope2 = line2.calculateSlope();

        if (slope1 == slope2) {
            return isIntersectingSameSlope(line1,line2);
        } else {
            Point intersection = findIntersection(line1,line2);
            return (isBetween(line1.start.getXpos(), line1.end.getXpos(), intersection.getXpos())
                    && isBetween(line1.start.getYpos(), line1.end.getYpos(), intersection.getYpos())
                    && isBetween(line2.start.getXpos(), line2.end.getXpos(), intersection.getXpos())
                    && isBetween(line2.start.getYpos(), line2.end.getYpos(), intersection.getYpos()));
        }
    }

    // REQUIRES: The slopes of line1 and line2 are equal.
    // EFFECTS: Returns true if there is an intersection between the two line segments, and false otherwise.
    private static boolean isIntersectingSameSlope(Line line1, Line line2) {
        double intercept1 = line1.calculateIntercept();
        double intercept2 = line2.calculateIntercept();
        if (intercept1 == intercept2) {
            if (intercept1 == Double.POSITIVE_INFINITY
                    && line1.getStart().getXpos() != line2.getStart().getXpos()) {
                return false;
            } else if (intercept1 == Double.POSITIVE_INFINITY) {
                return (isBetween(line1.start.getYpos(), line1.end.getYpos(), line2.start.getYpos())
                        || isBetween(line1.start.getYpos(), line1.end.getYpos(), line2.end.getYpos()));
            } else {
                return (isBetween(line1.start.getXpos(), line1.end.getXpos(), line2.start.getXpos())
                        || isBetween(line1.start.getXpos(), line1.end.getXpos(), line2.end.getXpos())
                        || isBetween(line1.start.getYpos(), line1.end.getYpos(), line2.start.getYpos())
                        || isBetween(line1.start.getYpos(), line1.end.getYpos(), line2.end.getYpos()));
            }
        }
        return false;
    }

    // EFFECTS: Returns the y-intercept of this line.  Returns Double.POSITIVE_INFINITY if the slope is infinite.
    private double calculateIntercept() {
        if (Double.isInfinite(calculateSlope())) {
            return Double.POSITIVE_INFINITY;
        } else {
            return calculateSlope() * this.start.getXpos() * (-1) + this.start.getYpos();
        }
    }

    // EFFECTS: Returns the slope of this line.  Returns Double.POSITIVE_INFINITY if delta x is 0.
    private double calculateSlope() {
        double deltaX = this.end.getXpos() - this.start.getXpos();
        double deltaY = this.end.getYpos() - this.start.getYpos();
        if (deltaX == 0) {
            return Double.POSITIVE_INFINITY;
        } else {
            return deltaY / deltaX;
        }
    }

    // EFFECTS: Returns true if value is between boundary1 and boundary2, and false otherwise.
    private static boolean isBetween(double boundary1, double boundary2, double value) {
        return (boundary1 <= value && value <= boundary2) || (boundary1 >= value && value >= boundary2);
    }

    // REQUIRES: There exists an intersection point between the two lines.
    // EFFECTS: Returns the intersection point of the two given lines.
    private static Point findIntersection(Line line1, Line line2) {
        double xpos;
        double ypos;
        double slope1 = line1.calculateSlope();
        double slope2 = line2.calculateSlope();
        double intercept1 = line1.calculateIntercept();
        double intercept2 = line2.calculateIntercept();

        if (Double.isInfinite(slope1)) {
            xpos = line1.start.getXpos();
            ypos = slope2 * xpos + intercept2;
        } else if (Double.isInfinite(slope2)) {
            xpos = line2.start.getXpos();
            ypos = slope1 * xpos + intercept1;
        } else {
            xpos = (intercept2 - intercept1) / (slope1 - slope2);
            ypos = slope1 * xpos + intercept1;
        }

        return new Point(xpos,ypos);
    }

    // EFFECTS: Returns the integer that results from rounding the given double.
    public static int round(double value) {
        if (value % 1 >= 0.5) {
            return (int) (value / 1) + 1;
        } else {
            return (int) (value / 1);
        }
    }

    // MODIFIES: this
    // EFFECTS: Translates the start and end point of the line by dx and dy.
    @Override
    public void move(int dx, int dy) {
        this.setStart(new Point(this.start.getXpos() + dx, this.start.getYpos() + dy));
        this.setEnd(new Point(this.end.getXpos() + dx, this.end.getYpos() + dy));
    }

    // EFFECTS: Returns a string describing the line's properties.
    @Override
    public String describe() {
        String startString = "Start = (" + this.start.getXpos() + ", " + this.start.getYpos() + ")";
        String endString = "End = (" + this.end.getXpos() + ", " + this.end.getYpos() + ")";
        String colourString = "Colour = (" + this.colour.getRed() + ", "
                + this.colour.getGreen() + ", "
                + this.colour.getBlue() + ")";
        return "Line: " + startString + ", " + endString + ", " + colourString;
    }

    // EFFECTS: Returns a JSON representation of the line.
    // NOTE: This method was modelled off of model.Thingy.toJson() from
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.
    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("Type", "Line");
        jsonObject.put("Start x", this.start.getXpos());
        jsonObject.put("Start y", this.start.getYpos());
        jsonObject.put("End x", this.end.getXpos());
        jsonObject.put("End y", this.end.getYpos());
        jsonObject.put("Colour r", this.colour.getRed());
        jsonObject.put("Colour g", this.colour.getGreen());
        jsonObject.put("Colour b", this.colour.getBlue());
        return jsonObject;
    }
}