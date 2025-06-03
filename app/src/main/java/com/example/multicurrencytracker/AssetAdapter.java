package com.example.multicurrencytracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class AssetAdapter extends RecyclerView.Adapter<AssetAdapter.AssetViewHolder> {

    private final List<Asset> assets;

    public AssetAdapter(List<Asset> assets) {
        this.assets = assets;
    }

    @NonNull
    @Override
    public AssetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_asset, parent, false);
        return new AssetViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssetViewHolder holder, int position) {
        Asset asset = assets.get(position);
        holder.bind(asset);
    }

    @Override
    public int getItemCount() {
        return assets.size();
    }

    static class AssetViewHolder extends RecyclerView.ViewHolder {
        private final ImageView icon;
        private final TextView name;
        private final TextView balance;
        private final TextView currency;

        public AssetViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.assetIcon);
            name = itemView.findViewById(R.id.assetName);
            balance = itemView.findViewById(R.id.assetBalance);
            currency = itemView.findViewById(R.id.assetCurrency);
        }

        public void bind(Asset asset) {
            icon.setImageResource(asset.getIcon());
            name.setText(asset.getName());

            // 格式化金额显示
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
            format.setCurrency(java.util.Currency.getInstance(asset.getCurrency()));
            balance.setText(format.format(asset.getBalance()));

            // 显示币种
            currency.setText(asset.getCurrency());
        }
    }
}