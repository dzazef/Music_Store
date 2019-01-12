package model;

public enum Status {
    in_progress("in progress"), prepared_for_sending("prepared for sending"),sent("sent"),
    delivered("delivered"), returned("returned"), cancelled("cancelled");

    private String name;
    Status(String s) {
        name = s;
    }
    @Override
    public String toString() {
        return name;
    }
}
