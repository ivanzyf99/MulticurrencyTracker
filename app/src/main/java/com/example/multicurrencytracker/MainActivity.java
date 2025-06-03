package com.example.multicurrencytracker;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 恢复系统状态栏（不再隐藏）
        restoreSystemBars();

        // 初始化视图
        Button btnAddAsset = findViewById(R.id.btnAddAsset);
        TextView txtNetAssets = findViewById(R.id.txtNetAssets);
        TextView txtTotalAssets = findViewById(R.id.txtTotalAssets);
        TextView txtTotalLiabilities = findViewById(R.id.txtTotalLiabilities);

        // 设置初始值（根据截图）
        txtNetAssets.setText("¥119,968.75");
        txtTotalAssets.setText("¥119,968.75");
        txtTotalLiabilities.setText("¥0.00");

        // 设置添加资产按钮点击事件
        btnAddAsset.setOnClickListener(v -> {
            // 添加资产的逻辑
        });

        // 设置资产卡片点击事件
        setupAssetCardClicks();
    }

    private void restoreSystemBars() {
        // 1. 禁用自定义状态栏效果
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        // 2. 设置状态栏背景为半透明黑色（适配深色主题）
        getWindow().setStatusBarColor(Color.TRANSPARENT);

        // 3. 确保状态栏文字颜色为白色
        WindowInsetsControllerCompat controller = WindowCompat.getInsetsController(
                getWindow(),
                decorView
        );
        if (controller != null) {
            controller.setAppearanceLightStatusBars(false); // 深色模式时文字用白色
        }
    }

    private void setupAssetCardClicks() {
        // 微信钱包卡片
        findViewById(R.id.cardWechat).setOnClickListener(v -> {
            // 打开微信钱包详情
        });

        // 美股账户卡片
        findViewById(R.id.cardStock).setOnClickListener(v -> {
            // 打开美股账户详情
        });

        // 比特币钱包卡片
        findViewById(R.id.cardBitcoin).setOnClickListener(v -> {
            // 打开比特币钱包详情
        });
    }
}