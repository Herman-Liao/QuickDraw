package persistence;

import org.json.JSONObject;

// Entire interface taken from persistence.Writable in https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public interface Writable {
    // EFFECT: Returns this as a JSON object.
    JSONObject toJson();
}
