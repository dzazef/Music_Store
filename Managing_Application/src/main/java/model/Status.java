package model;

public enum Status {
    waiting_for_payment("waiting for payment"), in_progress("in progress"), prepared_for_sending("prepared for sending"),sent("sent"),
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
