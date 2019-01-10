package model;

public enum Payment {
    on_delivery("on delivery"), bank_transfer("bank transfer");
    private String caption;
    Payment(String s) {
        caption = s;
    }
    @Override
    public String toString() {
        return caption;
    }
    public static Payment getByValue(String val) {
        for (Payment payment : Payment.values()) {
            if (payment.caption.equals(val)) {
                return payment;
            }
        }
        return null;
    }

}
