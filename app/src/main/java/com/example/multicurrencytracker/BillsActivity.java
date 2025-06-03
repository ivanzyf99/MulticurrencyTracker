package com.example.multicurrencytracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BillsActivity extends AppCompatActivity {

    private RecyclerView recyclerTransactions;
    private TransactionAdapter transactionAdapter;
    private TextView txtMonth, txtExpense, txtIncome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills);

        // 初始化视图
        recyclerTransactions = findViewById(R.id.recyclerTransactions);
        txtMonth = findViewById(R.id.txtMonth);
        txtExpense = findViewById(R.id.txtExpense);
        txtIncome = findViewById(R.id.txtIncome);

        // 设置当前月份
        Calendar calendar = Calendar.getInstance();
        txtMonth.setText(String.format("%d年%d月", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1));

        // 设置交易列表
        setupTransactionsList();

        // 设置底部导航
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigation);
        bottomNav.setSelectedItemId(R.id.nav_bills);
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                finish();
                return true;
            } else if (id == R.id.nav_reports) {
                startActivity(new Intent(this, ReportsActivity.class));
                return true;
            }
            return false;
        });
    }

    private void setupTransactionsList() {
        // 创建交易数据
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("内部转账", "汇丰银行借记卡", new BigDecimal("40000.00"), "CNY",
                "2025-06-02", Transaction.TYPE_INTERNAL, "【资产初始化】初期余额40000.0"));

        transactions.add(new Transaction("内部转账", "中国银行借记卡", new BigDecimal("10000.00"), "CNY",
                "2025-06-02", Transaction.TYPE_INTERNAL, "【资产初始化】初期余额10000.0"));

        transactions.add(new Transaction("内部转账", "招商银行借记卡", new BigDecimal("69928.47"), "CNY",
                "2025-06-02", Transaction.TYPE_INTERNAL, "【资产初始化】初期余额69928.47"));

        transactions.add(new Transaction("内部转账", "微信钱包", new BigDecimal("40.26"), "CNY",
                "2025-06-02", Transaction.TYPE_INTERNAL, "【资产初始化】初期余额40.26"));

        // 设置适配器
        transactionAdapter = new TransactionAdapter(transactions);
        recyclerTransactions.setLayoutManager(new LinearLayoutManager(this));
        recyclerTransactions.setAdapter(transactionAdapter);

        // 计算总支出和总收入
        calculateTotals(transactions);
    }

    private void calculateTotals(List<Transaction> transactions) {
        BigDecimal totalExpense = BigDecimal.ZERO;
        BigDecimal totalIncome = BigDecimal.ZERO;

        for (Transaction transaction : transactions) {
            if (transaction.getType() == Transaction.TYPE_EXPENSE) {
                totalExpense = totalExpense.add(transaction.getAmount());
            } else if (transaction.getType() == Transaction.TYPE_INCOME) {
                totalIncome = totalIncome.add(transaction.getAmount());
            }
        }

        txtExpense.setText("¥" + totalExpense.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
        txtIncome.setText("¥" + totalIncome.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
    }
}