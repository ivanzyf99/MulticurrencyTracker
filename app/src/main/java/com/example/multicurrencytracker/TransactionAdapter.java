package com.example.multicurrencytracker;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private final List<Transaction> transactions;

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        Transaction transaction = transactions.get(position);
        holder.bind(transaction);
    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    static class TransactionViewHolder extends RecyclerView.ViewHolder {
        private final TextView title;
        private final TextView account;
        private final TextView amount;
        private final TextView date;
        private final TextView note;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.transactionTitle);
            account = itemView.findViewById(R.id.transactionAccount);
            amount = itemView.findViewById(R.id.transactionAmount);
            date = itemView.findViewById(R.id.transactionDate);
            note = itemView.findViewById(R.id.transactionNote);
        }

        public void bind(Transaction transaction) {
            title.setText(transaction.getTitle());
            account.setText(transaction.getAccount());
            date.setText(transaction.getDate());
            note.setText(transaction.getNote());

            // 格式化金额显示
            NumberFormat format = NumberFormat.getCurrencyInstance(Locale.CHINA);
            format.setCurrency(java.util.Currency.getInstance(transaction.getCurrency()));
            amount.setText(format.format(transaction.getAmount()));

            // 根据交易类型设置颜色
            if (transaction.getType() == Transaction.TYPE_EXPENSE) {
                amount.setTextColor(Color.parseColor("#FF6B6B")); // 红色
            } else if (transaction.getType() == Transaction.TYPE_INCOME) {
                amount.setTextColor(Color.parseColor("#5CDD73")); // 绿色
            } else {
                amount.setTextColor(Color.parseColor("#FFFFFF")); // 白色
            }
        }
    }
}