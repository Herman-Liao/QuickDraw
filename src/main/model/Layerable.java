package model;

import persistence.Writable;

public interface Layerable extends Writable {
    // MODIFIES: this
    // EFFECTS: Moves all positions related to the object by dx and dy.
    void move(int dx, int dy);

    // EFFECTS: Returns a string describing the properties of the object.
    String describe();
}
