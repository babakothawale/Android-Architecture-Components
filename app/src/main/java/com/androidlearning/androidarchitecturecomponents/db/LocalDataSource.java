package com.androidlearning.androidarchitecturecomponents.db;

import com.androidlearning.androidarchitecturecomponents.entity.Shop;

import java.util.List;

public class LocalDataSource {

    private static LocalDataSource INSTANCE;
    private final SampleDatabase mDatabase;

    public static LocalDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (LocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataSource();
                }
            }
        }
        return INSTANCE;
    }

    private LocalDataSource() {
        mDatabase = SampleDatabase.getInstance();
    }

    public long insertShop(Shop shop) {
        return mDatabase.shopDao().insertShop(shop);
    }

    public void insertShops(List<Shop> shopList) {
        mDatabase.shopDao().insertShops(shopList);
    }


    public int updateShop(Shop shop) {
        return mDatabase.shopDao().updateShop(shop);
    }

    public int deleteShop(Shop shop) {
        return mDatabase.shopDao().deleteShop(shop);
    }

    public int deleteAll() {
        return mDatabase.shopDao().deleteAll();
    }

    public List<Shop> getShops() {
        return mDatabase.shopDao().getShops();
    }
}
