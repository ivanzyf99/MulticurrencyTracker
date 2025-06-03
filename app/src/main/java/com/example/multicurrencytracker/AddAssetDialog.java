package com.example.multicurrencytracker;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

public class AddAssetDialog extends Dialog {

    public AddAssetDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_asset);

        // 设置币种下拉列表
        Spinner currencySpinner = findViewById(R.id.currencySpinner);
        List<String> currencies = Arrays.asList("CNY", "USD", "EUR", "GBP", "JPY", "BTC", "ETH");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, currencies);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        currencySpinner.setAdapter(adapter);

        // 设置保存按钮事件
        Button btnSave = findViewById(R.id.btnSaveAsset);
        btnSave.setOnClickListener(v -> {
            // 获取用户输入
            EditText etName = findViewById(R.id.etAssetName);
            EditText etBalance = findViewById(R.id.etInitialBalance);

            String name = etName.getText().toString();
            String balanceStr = etBalance.getText().toString();
            String currency = currencySpinner.getSelectedItem().toString();

            // 在实际应用中，这里会保存新资产到数据库
            dismiss();
        });

        // 设置取消按钮
        Button btnCancel = findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(v -> dismiss());
    }
}