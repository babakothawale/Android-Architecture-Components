package com.androidlearning.androidarchitecturecomponents.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.androidlearning.androidarchitecturecomponents.entity.Shop;
import com.androidlearning.androidarchitecturecomponents.repository.Repository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MainViewModel extends ViewModel {
    private CompositeDisposable mDisposables;
    private MutableLiveData<List<Shop>> mShops;

    public MainViewModel() {
        mDisposables = new CompositeDisposable();
        if (mShops == null) {
            mShops = new MutableLiveData<>();
            refreshShops();//load shops
        }
    }

    public MutableLiveData<List<Shop>> getShops() {
        return mShops;
    }

    private void refreshShops()  {
        mDisposables.add(Repository.getInstance().getShops().subscribe(shops -> mShops.setValue(shops)));
    }

    public void deleteAllShops() {
        mDisposables.add(Repository.getInstance().deleteAllShops().subscribe(aBoolean -> {refreshShops();}));
    }

    public void insertShops() {
        mDisposables.add(Repository.getInstance().insertShopData().subscribe(success -> {
            Log.d("MainViewModel", "accept: insert success");
            refreshShops();
        }));
    }

    public void deleteShop(Shop shop) {
        mDisposables.add(Repository.getInstance().deleteShop(shop).subscribe(() -> refreshShops()));
    }

    public void updateShop(Shop shop) {
        mDisposables.add(Repository.getInstance().updateShop(shop).subscribe());
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
        mShops = null;
        super.onCleared();
    }
}
