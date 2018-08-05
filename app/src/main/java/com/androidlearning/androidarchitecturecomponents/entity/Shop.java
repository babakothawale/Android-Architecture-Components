package com.androidlearning.androidarchitecturecomponents.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "shop")
public class Shop {
    @PrimaryKey(autoGenerate = true)
    long id;
    @ColumnInfo(name = "shop_name")
    String name;
    @ColumnInfo(name = "shop_address")
    String address;
    @ColumnInfo(name = "owner_id")
    private long ownerId;

    public Shop(){}

    @Ignore
    public Shop(String name, String address, long ownerId) {
        this.name = name;
        this.address = address;
        this.ownerId = ownerId;
    }

    @Override
    @Ignore
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", ownerId=" + ownerId +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public long getOwnerId() {
        return ownerId;
    }
}
