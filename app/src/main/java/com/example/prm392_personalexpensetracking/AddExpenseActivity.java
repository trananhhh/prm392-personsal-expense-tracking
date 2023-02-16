package com.example.prm392_personalexpensetracking;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.prm392_personalexpensetracking.adapter.CategoryAdapter;
import com.example.prm392_personalexpensetracking.adapter.ExpenseTypeAdapter;
import com.example.prm392_personalexpensetracking.model.Category;
import com.example.prm392_personalexpensetracking.model.Expense;
import com.example.prm392_personalexpensetracking.model.ExpenseType;

import java.text.MessageFormat;
import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity {
    Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add new expense");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//      Spinner setup
        Spinner expenseTypeSpinner = findViewById(R.id.expenseTypeSpinner);
        ExpenseTypeAdapter expenseTypeAdapter = new ExpenseTypeAdapter(this, R.layout.expense_type_spinner_adapter, ExpenseType.getExpenseTypeList());
        expenseTypeSpinner.setAdapter(expenseTypeAdapter);

        Spinner categorySpinner = findViewById(R.id.categorySpinner);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, R.layout.category_spinner_adapter, Category.getCategoryList());
        categorySpinner.setAdapter(categoryAdapter);

//      DatePicker setup
        Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        dateButton = findViewById(R.id.btnDatePicker);
        dateButton.setText(dateToString(day, month+1, year));
        dateButton.setOnClickListener(view -> {
            showDatePickerDialog(day, month, year);
        });
    }

    private String dateToString(int day, int month, int year){
        return MessageFormat.format("{0}/{1}/{2}", String.valueOf(day), String.valueOf(month), String.valueOf(year));
    }
    private void showDatePickerDialog(int day, int month, int year){
        DatePickerDialog dialog = new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
            dateButton.setText(dateToString(i2, i1+1, i));
        }, year, month, day);

        dialog.show();
    }
}