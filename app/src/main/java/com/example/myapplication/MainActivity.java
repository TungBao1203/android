package com.example.myapplication;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private EditText edtA, edtB;
    private TextView tvResult;
    private MaterialButton btnAdd, btnSub, btnMul, btnDiv;

    // Trạng thái: phép toán đang chọn ('\0' = chưa chọn)
    private char operation = '\0';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtA = findViewById(R.id.edtA);
        edtB = findViewById(R.id.edtB);
        tvResult = findViewById(R.id.tvResult);
        btnAdd = findViewById(R.id.btnAdd);
        btnSub = findViewById(R.id.btnSub);
        btnMul = findViewById(R.id.btnMul);
        btnDiv = findViewById(R.id.btnDiv);

        btnAdd.setOnClickListener(v -> chooseOperation('+', btnAdd));
        btnSub.setOnClickListener(v -> chooseOperation('-', btnSub));
        btnMul.setOnClickListener(v -> chooseOperation('*', btnMul));
        btnDiv.setOnClickListener(v -> chooseOperation('/', btnDiv));

        // Gõ số là tính lại ngay
        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int st, int c, int a) {}
            @Override public void onTextChanged(CharSequence s, int st, int b, int c) {}
            @Override public void afterTextChanged(Editable s) { calculate(); }
        };
        edtA.addTextChangedListener(watcher);
        edtB.addTextChangedListener(watcher);
    }

    private void chooseOperation(char op, MaterialButton selected) {
        operation = op;


        btnAdd.setStrokeWidth(0);
        btnSub.setStrokeWidth(0);
        btnMul.setStrokeWidth(0);
        btnDiv.setStrokeWidth(0);
        selected.setStrokeWidth(4);

        calculate();
    }

    private void calculate() {
        String sa = edtA.getText().toString().trim();
        String sb = edtB.getText().toString().trim();

        if (operation == '\0' || sa.isEmpty() || sb.isEmpty()) {
            tvResult.setText("Kết quả: ");
            return;
        }

        double a, b;
        try {
            a = Double.parseDouble(sa);
            b = Double.parseDouble(sb);
        } catch (NumberFormatException e) {
            tvResult.setText("Kết quả: Số không hợp lệ");
            return;
        }

        double result;
        switch (operation) {
            case '+': result = a + b; break;
            case '-': result = a - b; break;
            case '*': result = a * b; break;
            default:
                if (b == 0) {
                    tvResult.setText("Kết quả: Không thể chia cho 0");
                    return;
                }
                result = a / b;
        }

        // 8.0 -> hiện "8"
        String text = (result == (long) result)
                ? String.valueOf((long) result)
                : String.valueOf(result);
        tvResult.setText("Kết quả: " + text);
    }
}