package com.nev.auction;

import java.util.*;

/**
 * Implementation of AuctionBidTracker.
 */
public final class AuctionBidTrackerImplementation implements AuctionBidTracker {

    private final Map<UUID, Item> itemHashMap;
    private Map<UUID, List<UUID>> itemsWithUserBidMap;

    /**
     * Returns an instance of an implementation of AuctionBidTracker.
     *
     * @param itemIdList
     * @return AuctionBidTracker
     * @throws IllegalArgumentException if itemIdList is null.
     */
    public static AuctionBidTracker getInstance(List<UUID> itemIdList) {
        if (itemIdList == null) {
            throw new IllegalArgumentException("Item list cannot be null");
        }

        return new AuctionBidTrackerImplementation(itemIdList);
    }

    /**
     * Constructs an AuctionBidTrackerImplementation object.
     * @param itemIdList
     * @throws IllegalArgumentException if an item uuid is null.
     */
    private AuctionBidTrackerImplementation(List<UUID> itemIdList) {
        itemHashMap = new HashMap<>();

        for (UUID itemId : itemIdList) {
            if (itemId == null) {
                throw new IllegalArgumentException("itemId cannot be null");
            }
            itemHashMap.put(itemId, new Item(itemId));
        }
    }

    @Override
    public synchronized boolean recordUserBid(Bid bid, UUID itemId) {

        if (bid == null) {
            throw new IllegalArgumentException("Bid cannot be null");
        }

        Item item = itemHashMap.get(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Item does not exist");
        }

        boolean result = item.addBid(bid);
        if (result) {
            if (itemsWithUserBidMap == null) {
                itemsWithUserBidMap = new HashMap<>();
            }

            List<UUID> itemsWithUserBidList = itemsWithUserBidMap.get(bid.getUserId());
            if (itemsWithUserBidList == null) {
                itemsWithUserBidList = new ArrayList<>();
            }

            itemsWithUserBidList.add(item.getUuid());
            itemsWithUserBidMap.put(bid.getUserId(), itemsWithUserBidList);
        }
        return result;
    }

    @Override
    public synchronized Bid getItemWinningBid(UUID itemId) {
        Item item = itemHashMap.get(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Item does not exist");
        }
        return item.getWinningBid();
    }

    @Override
    public synchronized List<Bid> getBidsForItem(UUID itemId) {
        Item item = itemHashMap.get(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Item does not exist");
        }
        return item.getAllBids();
    }

    @Override
    public synchronized List<UUID> getItemsWithUserBid(UUID userId) {
        if (itemsWithUserBidMap == null) {
            return null;
        }
        return new ArrayList<>(itemsWithUserBidMap.get(userId));
    }

    /**
     * Class to associate bids with an item.
     */
    private final class Item {

        private final UUID uuid;
        private List<Bid> bidList;

        /**
         * Constructs an Item object.
         * @param uuid the id of the item.
         * @throws IllegalArgumentException if the uuid is null.
         */
        public Item(UUID uuid) {
            if (uuid == null) {
                throw new IllegalArgumentException("Parameter cannot be null");
            }
            this.uuid = uuid;
        }

        /**
         * Adds a bid if it's the first one or is higher than the current highest bid.
         * @param bid the bid to add.
         * @return true if bid successful, false otherwise.
         * @throws IllegalArgumentException if the bid is null.
         */
        public boolean addBid(Bid bid) {
            if (bid == null) {
                throw new IllegalArgumentException("Bid cannot be null");
            }

            if (bidList == null) {
                bidList = new ArrayList<>();
                bidList.add(bid);
            } else {
                if (bid.getBidAmount().compareTo(bidList.get(bidList.size()-1).getBidAmount()) > 0) {
                    bidList.add(bid);
                } else {
                    return false;
                }
            }
            return true;
        }

        /**
         * Returns the winning bid or null if there are none.
         * @return
         */
        public Bid getWinningBid() {
            if(bidList == null) {
                return null;
            }

            return bidList.get(bidList.size()-1);
        }

        /**
         * Returns a list of all bids or null if there are none.
         * @return
         */
        public List<Bid> getAllBids() {
            if(bidList == null) {
                return null;
            }

            return new ArrayList<>(bidList);
        }

        /**
         * Returns the id of the item.
         * @return
         */
        public UUID getUuid() {
            return uuid;
        }

        @Override
        public String toString() {
            return "Item Id: " + uuid;
        }
    }
}
