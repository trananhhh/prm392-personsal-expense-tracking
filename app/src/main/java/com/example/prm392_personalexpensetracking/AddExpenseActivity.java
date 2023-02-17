package com.example.prm392_personalexpensetracking;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.google.android.material.textfield.TextInputEditText;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {
    Button dateButton;
    Calendar cal = Calendar.getInstance();
    int day, month, year;
    Spinner expenseTypeSpinner, categorySpinner;
    TextInputEditText amount, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add new expense");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        amount = findViewById(R.id.textAddExpenseAmount);
        description = findViewById(R.id.textAddExpenseDescription);

//      Spinner setup
        expenseTypeSpinner = findViewById(R.id.expenseTypeSpinner);
        ExpenseTypeAdapter expenseTypeAdapter = new ExpenseTypeAdapter(this, R.layout.expense_type_spinner_adapter, ExpenseType.getExpenseTypeList());
        expenseTypeSpinner.setAdapter(expenseTypeAdapter);

        categorySpinner = findViewById(R.id.categorySpinner);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, R.layout.category_spinner_adapter, Category.getCategoryList());
        categorySpinner.setAdapter(categoryAdapter);

//      DatePicker setup

        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        dateButton = findViewById(R.id.btnDatePicker);
        dateButton.setText(dateToString(day, month+1, year));
        dateButton.setOnClickListener(view -> {
            showDatePickerDialog(day, month, year);
        });

//      SaveBtn
        Button saveBtn = findViewById(R.id.saveExpenseBtn);
        saveBtn.setOnClickListener(view -> {
            saveExpenseHandled();
        });
    }

    private void saveExpenseHandled(){
        ExpenseType currentExpenseType = ExpenseType.getExpenseTypeList().get(expenseTypeSpinner.getSelectedItemPosition());
        Category currentCategory = Category.getCategoryList().get(categorySpinner.getSelectedItemPosition());

        if(amount.getText().length() == 0){
            showErrorAmountRequiredAlert();
            return;
        }

//        expensesList.add(new Expense(1, 6, "CMC Global tháng 2", 15000000, new Date()));

//        String date_string = "26-09-1989";
//        //Instantiating the SimpleDateFormat class
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
//        //Parsing the given String to Date object
//        Date date = formatter.parse(date_string);
        Calendar c = Calendar.getInstance();
        c.set(year, month-1, day);

        Expense.addExpense(new Expense(
                UUID.randomUUID().toString(),
                currentCategory.getCate_id(),
                description.getText().toString(),
                Integer.parseInt(amount.getText().toString()),
                c.getTime()
        ));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showErrorAmountRequiredAlert(){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Amount is required!");

        alertDialog.setMessage("The amount must not be empty!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());

        alertDialog.show();
    }

    private String dateToString(int day, int month, int year){
        return MessageFormat.format("{0}/{1}/{2}", String.valueOf(day), String.valueOf(month), String.valueOf(year));
    }
    private void showDatePickerDialog(int day, int month, int year){
        DatePickerDialog dialog = new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
            dateButton.setText(dateToString(i2, i1+1, i));
            this.day = i2;
            this.month = i1;
            this.year = i;
        }, year, month, day);

        dialog.show();
    }
}