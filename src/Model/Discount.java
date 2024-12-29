package Model;

import java.io.Serializable;

/**
 * Represents a discount that can be applied to a game.
 * Discounts are represented as percentages.
 */

public class Discount implements HasId, Serializable {
    private Integer discountId;
    private float discountPercentage;

    /**
     * Constructs a discount with the specified discount ID and percentage.
     *
     * @param discountId         The unique identifier for the discount.
     * @param discountPercentage The percentage of the discount.
     */

    public Discount(Integer discountId, float discountPercentage) {
        this.discountId = discountId;
        this.discountPercentage = discountPercentage;
    }

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public float getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(float discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "discountId=" + discountId +
                ", discountPercentage=" + discountPercentage +
                '}';
    }

    @Override
    public Integer getId() {
        return discountId;
    }
}

