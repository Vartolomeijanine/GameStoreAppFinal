package Model;

import java.io.Serializable;

public class PaymentMethod implements HasId, Serializable {
    private Integer paymentId;
    private String paymentType;

    /**
     * Constructs a PaymentMethod with a specified ID and type.
     *
     * @param paymentId   The unique identifier for the payment method.
     * @param paymentType The type of payment method (e.g., Visa, PayPal).
     */


    public PaymentMethod(Integer paymentId, String paymentType) {
        this.paymentId = paymentId;
        this.paymentType = paymentType;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "paymentId=" + paymentId +
                ", paymentType='" + paymentType + '\'' +
                '}';
    }

    @Override
    public Integer getId() {
        return paymentId;
    }
}
