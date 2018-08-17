package com.androidlearning.androidarchitecturecomponents.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;

import com.androidlearning.androidarchitecturecomponents.entity.Shop;
import com.androidlearning.androidarchitecturecomponents.repository.Repository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MainViewModelParams extends ViewModel {
    private final Repository mRepository;
    private CompositeDisposable mDisposables;
    private MutableLiveData<List<Shop>> mShops;

    public MainViewModelParams(Repository repository) {
        mRepository = repository;
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
        mDisposables.add(mRepository.deleteAllShops().subscribe(aBoolean -> {refreshShops();}));
    }

    public void insertShops() {
        mDisposables.add(mRepository.insertShopData().subscribe(success -> {
            Log.d("MainViewModelParams", "accept: insert success");
            refreshShops();
        }));
    }

    public void deleteShop(Shop shop) {
        mDisposables.add(mRepository.deleteShop(shop).subscribe(() -> refreshShops()));
    }

    public void updateShop(Shop shop) {
        mDisposables.add(mRepository.updateShop(shop).subscribe());
    }

    @Override
    protected void onCleared() {
        mDisposables.clear();
        mShops = null;
        super.onCleared();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Repository mRepository;

        public Factory(@NonNull Repository repository) {
            mRepository = repository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new MainViewModelParams(mRepository);
        }
    }
}
