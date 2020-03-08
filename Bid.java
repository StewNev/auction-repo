package com.nev.auction;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Represents a bid by a user.
 */
public final class Bid {

    private final UUID userId;
    private final BigDecimal bidAmount;

    /**
     * Constructs a Bid object.
     * @param userId
     * @param bidAmount
     * @throws IllegalArgumentException if either parameter is null.
     */
    public Bid(UUID userId, BigDecimal bidAmount) {
        if (userId == null || bidAmount == null) {
            throw new IllegalArgumentException("Parameter cannot be null");
        }
        this.userId = userId;
        this.bidAmount = bidAmount;
    }

    /**
     * Returns the user UUID.
     * @return
     */
    public UUID getUserId() {
        return userId;
    }

    /**
     * Returns the amount bid.
     * @return
     */
    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    @Override
    public String toString() {
        return "Bid userId: " + userId + " Bid amount: " + bidAmount;
    }
}
