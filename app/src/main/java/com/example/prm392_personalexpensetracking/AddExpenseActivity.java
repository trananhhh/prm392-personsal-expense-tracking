package com.example.prm392_personalexpensetracking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.prm392_personalexpensetracking.adapter.CategoryAdapter;
import com.example.prm392_personalexpensetracking.adapter.ExpenseTypeAdapter;
import com.example.prm392_personalexpensetracking.model.Category;
import com.example.prm392_personalexpensetracking.model.ExpenseType;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.MessageFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddExpenseActivity extends AppCompatActivity {
    Button dateButton;
    Calendar cal = Calendar.getInstance();
    int day, month, year;
    Spinner expenseTypeSpinner, categorySpinner;
    TextInputEditText amount, description;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    FirebaseUser fUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Add new expense");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        Firebase
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        amount = findViewById(R.id.textAddExpenseAmount);
        description = findViewById(R.id.textAddExpenseDescription);

        spinnerSetup();
        datePickerSetup();
        saveBtnSetup();


    }

    private void spinnerSetup(){
        expenseTypeSpinner = findViewById(R.id.expenseTypeSpinner);
        ExpenseTypeAdapter expenseTypeAdapter = new ExpenseTypeAdapter(this, R.layout.expense_type_spinner_adapter, ExpenseType.getExpenseTypeList());
        expenseTypeSpinner.setAdapter(expenseTypeAdapter);

        categorySpinner = findViewById(R.id.categorySpinner);
        CategoryAdapter categoryAdapter = new CategoryAdapter(this, R.layout.category_spinner_adapter, Category.getCategoryList());
        categorySpinner.setAdapter(categoryAdapter);
    }

    private void datePickerSetup(){
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);

        dateButton = findViewById(R.id.btnDatePicker);
        dateButton.setText(dateToString(day, month+1, year));
        dateButton.setOnClickListener(view -> {
            showDatePickerDialog(day, month, year);
        });
    }

    private void saveBtnSetup(){
        Button saveBtn = findViewById(R.id.saveExpenseBtn);
        saveBtn.setOnClickListener(view -> {
            saveExpenseHandled();
        });
    }

    private void saveExpenseHandled(){
//        ExpenseType currentExpenseType = ExpenseType.getExpenseTypeList().get(expenseTypeSpinner.getSelectedItemPosition());
        Category currentCategory = Category.getCategoryList().get(categorySpinner.getSelectedItemPosition());

        if(amount.getText().length() == 0){
            showErrorAmountRequiredAlert();
            return;
        }
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        String id = UUID.randomUUID().toString();
        Map<String, Object> transaction = new HashMap<>();
        transaction.put("expenseId", id);
        transaction.put("cateId", currentCategory.getCateId());
        transaction.put("description", description.getText().toString());
        transaction.put("amount", Integer.parseInt(amount.getText().toString()));
        transaction.put("createAt", c.getTime());

        fStore.collection("Data").document(fAuth.getUid()).collection("Expenses").document(id).set(transaction).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddExpenseActivity.this, "Added", Toast.LENGTH_SHORT).show();
                description.setText("");
                amount.setText("");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddExpenseActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

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