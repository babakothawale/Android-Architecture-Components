package com.androidlearning.androidarchitecturecomponents.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.androidlearning.androidarchitecturecomponents.entity.Shop;

import java.util.List;

@Dao
public interface ShopDao {

    @Query("SELECT * FROM shop")
    List<Shop> getShops();

    @Insert
    long insertShop(Shop shop);

    @Insert
    void insertShops(List<Shop> shops);

    @Delete
    int deleteShop(Shop shop);

    @Query("DELETE from shop")
    int deleteAll();

    @Update
    int updateShop(Shop shop);
}
