package com.example.prm392_personalexpensetracking.model;

import com.example.prm392_personalexpensetracking.R;

import java.util.ArrayList;

public class ExpenseType {
    private static ArrayList<ExpenseType> expenseTypeList = new ArrayList<>();
    private int expense_type_id;
    private String expense_type_title;

    public ExpenseType(int expense_type_id, String expense_type_title) {
        this.expense_type_id = expense_type_id;
        this.expense_type_title = expense_type_title;
    }

    public int getExpense_type_id() {
        return expense_type_id;
    }

    public void setExpense_type_id(int expense_type_id) {
        this.expense_type_id = expense_type_id;
    }

    public String getExpense_type_title() {
        return expense_type_title;
    }

    public void setExpense_type_title(String expense_type_title) {
        this.expense_type_title = expense_type_title;
    }

    public static void initExpenseType(){
        expenseTypeList.add(new ExpenseType(1, "Income"));
        expenseTypeList.add(new ExpenseType(1, "Outcome"));
        expenseTypeList.add(new ExpenseType(1, "Loan / Debt"));
    }

    public static ArrayList<ExpenseType> getExpenseTypeList(){
        return expenseTypeList;
    }
}
