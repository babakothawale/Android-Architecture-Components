package com.androidlearning.androidarchitecturecomponents.repository;

import com.androidlearning.androidarchitecturecomponents.db.LocalDataSource;
import com.androidlearning.androidarchitecturecomponents.entity.Shop;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Repository {
    private static Repository INSTANCE;

    public static Repository getInstance() {
        if (INSTANCE == null) {
            synchronized (Repository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Repository();
                }
            }
        }
        return INSTANCE;
    }

    public Observable<Boolean> insertShopData() {
        return Observable.fromCallable(() -> {
            List<Shop> shops = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                shops.add(new Shop("Shop " + i, "Address" + i, i));
            }
            LocalDataSource.getInstance().insertShops(shops);
            return true;
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    public Completable deleteShop(Shop shop) {
        return Completable.fromAction(() -> LocalDataSource.getInstance().deleteShop(shop))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable updateShop(Shop shop) {
        return Completable.fromAction(() -> LocalDataSource.getInstance().updateShop(shop))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Boolean> deleteAllShops() {
        return Observable.fromCallable(() -> LocalDataSource.getInstance().deleteAll() > 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }


    public Observable<List<Shop>> getShops() {
        return Observable.fromCallable(() -> LocalDataSource.getInstance().getShops())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}