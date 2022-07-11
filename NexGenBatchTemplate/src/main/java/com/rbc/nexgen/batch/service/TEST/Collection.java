package com.rbc.nexgen.batch.service.TEST;

import java.util.List;

import org.hibernate.cache.spi.support.AbstractReadWriteAccess.Item;

public class Collection {

    private List<Item> items;

    public Collection(){}

    public Collection(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }
}