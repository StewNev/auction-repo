package com.nev.auction;

import java.util.List;
import java.util.UUID;

public interface AuctionBidTracker {

    /**
     * Records a bid for an item if it is higher than the current highest bid.
     *
     * @param bid bid to record
     * @param itemId uuid of the item
     * @return true if bid successful, false otherwise
     * @throws IllegalArgumentException if bid is null or item does not exist
     */
    boolean recordUserBid(Bid bid, UUID itemId);

    /**
     * Returns the winning bid for an item.
     *
     * @param itemId uuid of the item
     * @return the winning bid, or null if there are no bids
     * @throws IllegalArgumentException if item does not exist
     */
    Bid getItemWinningBid(UUID itemId);

    /**
     * Returns all bids for an item.
     *
     * @param itemId uuid of the item
     * @return list of bids, or null if there are none
     * @throws IllegalArgumentException if item does not exist
     */
    List<Bid> getBidsForItem(UUID itemId);

    /**
     * Returns items on which a user has bid.
     *
     * @param userId uuid of the user
     * @return list of items, or null if there are none.
     */
    List<UUID> getItemsWithUserBid(UUID userId);
}
