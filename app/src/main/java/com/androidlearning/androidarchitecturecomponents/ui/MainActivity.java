package com.androidlearning.androidarchitecturecomponents.ui;

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

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CompositeDisposable mDisposables = new CompositeDisposable();
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = findViewById(R.id.list);

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_delete_shops:
                Repository.getInstance().deleteAllShops().subscribe(aBoolean -> loadShops());
                break;

            case R.id.button_insert_shop:
                Repository.getInstance().insertShopData().subscribe(success -> {
                    Log.d("MainActivity", "accept: insert success");
                    loadShops();

                });

                break;
        }
    }

    private void loadShops()  {
       mDisposables.add(Repository.getInstance().getShops().subscribe(shops -> setShopAdapter(shops)));
    }

    private void setShopAdapter(List<Shop> shops) {
        ShopAdapter adapter = new ShopAdapter(shops,
                new ShopAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(Shop shop) {
                        Toast.makeText(getApplicationContext(), shop.getName(),
                                Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onDeleteClick(Shop shop, int position) {
                        Repository.getInstance().deleteShop(shop).subscribe();
                    }

                    @Override
                    public void onEditClick(Shop shop, int position) {
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
                                    Repository.getInstance().updateShop(shop).subscribe();
                                    mRecyclerView.getAdapter().notifyItemChanged(position);
                                })
                                .setView(view);
                        builder.show();
                    }
                });

        mRecyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposables.clear();
    }
}
