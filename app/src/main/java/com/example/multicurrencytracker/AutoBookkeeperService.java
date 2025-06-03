package com.example.multicurrencytracker;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutoBookkeeperService extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        // 检查通知是否来自支付应用
        String packageName = sbn.getPackageName();
        if (isPaymentApp(packageName)) {
            // 解析通知内容
            String title = sbn.getNotification().extras.getString("android.title");
            String text = sbn.getNotification().extras.getString("android.text");

            if (title != null && text != null) {
                parsePaymentNotification(title, text);
            }
        }
    }

    private boolean isPaymentApp(String packageName) {
        // 检查是否来自支付宝、微信、银行等支付应用
        return packageName.contains("alipay") ||
                packageName.contains("tencent.mm") ||
                packageName.contains("bank");
    }

    private void parsePaymentNotification(String title, String text) {
        // 解析支付宝通知
        if (title.contains("支付宝") || title.contains("Alipay")) {
            parseAlipayNotification(text);
        }
        // 解析微信支付通知
        else if (title.contains("微信支付")) {
            parseWechatNotification(text);
        }
        // 解析银行通知
        else if (title.contains("银行") || title.contains("Bank")) {
            parseBankNotification(text);
        }
    }

    private void parseAlipayNotification(String text) {
        String amount = extractAmount(text, "支出(\\d+\\.?\\d*)元");
        String merchant = extractMerchant(text);

        if (amount != null) {
            try {
                // 创建并保存交易记录
                Transaction transaction = createTransaction(
                        merchant != null ? "支付宝 - " + merchant : "支付宝支付",
                        "支付宝",
                        amount,
                        Transaction.TYPE_EXPENSE,
                        text
                );

                Toast.makeText(this, "记录支付宝支出: ¥" + amount, Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Log.e("AutoBookkeeper", "金额格式错误: " + amount);
            }
        }
    }

    private void parseWechatNotification(String text) {
        String amount = extractAmount(text, "微信支付(\\d+\\.?\\d*)元");
        if (amount != null) {
            try {
                // 创建并保存交易记录
                Transaction transaction = createTransaction(
                        "微信支付",
                        "微信钱包",
                        amount,
                        Transaction.TYPE_EXPENSE,
                        text
                );

                Toast.makeText(this, "记录微信支付: ¥" + amount, Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Log.e("AutoBookkeeper", "金额格式错误: " + amount);
            }
        }
    }

    private void parseBankNotification(String text) {
        String amount = extractAmount(text, "支出(\\d+\\.?\\d*)元");
        if (amount != null) {
            try {
                // 提取银行名称
                String bankName = extractBankName(text);

                // 创建并保存交易记录
                Transaction transaction = createTransaction(
                        bankName,
                        "银行卡",
                        amount,
                        Transaction.TYPE_EXPENSE,
                        text
                );

                Toast.makeText(this, "记录银行支出: ¥" + amount, Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Log.e("AutoBookkeeper", "金额格式错误: " + amount);
            }
        }
    }

    private String extractAmount(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1).replace(",", "");
        }
        return null;
    }

    private String extractMerchant(String text) {
        // 商家提取逻辑
        Pattern pattern = Pattern.compile("给(.+?)[付支]款");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private String extractBankName(String text) {
        // 尝试提取银行名称
        Pattern pattern = Pattern.compile("(工商银行|建设银行|招商银行|中国银行|农业银行)");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "银行交易";
    }

    private Transaction createTransaction(String title, String account, String amountStr,
                                          int type, String note) {
        // 创建交易记录对象
        BigDecimal amount = new BigDecimal(amountStr);
        String currency = "CNY";

        // 获取当前日期时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String date = sdf.format(new Date());

        return new Transaction(
                title,          // 交易标题
                account,        // 账户名
                amount,         // 交易金额
                currency,       // 货币类型
                date,           // 交易日期
                type,           // 交易类型（收入/支出）
                note            // 备注信息
        );
    }

    private void saveTransaction(Transaction transaction) {
        // 实际应用中应保存到数据库
        Log.d("AutoBookkeeper", "保存交易记录: " + transaction.getTitle() +
                " - ¥" + transaction.getAmount().toPlainString());

        // 在这里添加数据库保存代码：
        // TransactionDao dao = AppDatabase.getInstance(this).transactionDao();
        // dao.insert(transaction);
    }
}