package com.androidlearning.androidarchitecturecomponents.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidlearning.androidarchitecturecomponents.R;
import com.androidlearning.androidarchitecturecomponents.entity.Shop;
import com.androidlearning.androidarchitecturecomponents.repository.Repository;
import com.androidlearning.androidarchitecturecomponents.ui.Adapter.ShopAdapter;
import com.androidlearning.androidarchitecturecomponents.viewmodel.MainViewModel;
import com.androidlearning.androidarchitecturecomponents.viewmodel.MainViewModelParams;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private MainViewModelParams mMainViewModel;

    private ShopAdapter.OnItemClickListener mItemClickListener = new ShopAdapter.OnItemClickListener() {
        @Override
        public void onClick(Shop shop) {
            Toast.makeText(getApplicationContext(), shop.getName(),
                    Toast.LENGTH_LONG).show();
        }

        @Override
        public void onDeleteClick(Shop shop, int position) {
            mMainViewModel.deleteShop(shop);
        }

        @Override
        public void onEditClick(Shop shop, int position) {
            showEditDialog(shop, position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.list);
        mRecyclerView.setAdapter(new ShopAdapter(new ArrayList<>(), mItemClickListener));

        //mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        mMainViewModel = ViewModelProviders.of(this, new MainViewModelParams.Factory(Repository.getInstance())).get(MainViewModelParams.class);

        mMainViewModel.getShops().observe(this, this::updateShops);
    }

    private void updateShops(List<Shop> shops) {
        ((ShopAdapter)mRecyclerView.getAdapter()).setShops(shops);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_delete_shops:
                mMainViewModel.deleteAllShops();
                break;

            case R.id.button_insert_shop:
                mMainViewModel.insertShops();
                break;
        }
    }

    private void showEditDialog(Shop shop, int position) {
        final View view = getLayoutInflater().inflate(R.layout.layout_edit_shop, null);
        EditText etName = view.findViewById(R.id.edittext_name);
        etName.setText(shop.getName());
        EditText etAddress = view.findViewById(R.id.edittext_address);
        etAddress.setText(shop.getAddress());
        EditText etOwner = view.findViewById(R.id.edittext_owner);
        etOwner.setText(String.valueOf(shop.getOwnerId()));

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setPositiveButton("Update", (dialog, which) -> {
                    shop.setName(String.valueOf(etName.getText()));
                    shop.setAddress(String.valueOf(etAddress.getText()));
                    mMainViewModel.updateShop(shop);
                    mRecyclerView.getAdapter().notifyItemChanged(position);
                })
                .setView(view);
        builder.show();
    }
}
