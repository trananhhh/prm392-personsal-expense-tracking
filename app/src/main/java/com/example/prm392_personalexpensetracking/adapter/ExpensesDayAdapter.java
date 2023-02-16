package com.example.prm392_personalexpensetracking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_personalexpensetracking.MainActivity;
import com.example.prm392_personalexpensetracking.R;
import com.example.prm392_personalexpensetracking.model.Category;
import com.example.prm392_personalexpensetracking.model.Expense;

import java.util.ArrayList;

public class ExpensesDayAdapter extends RecyclerView.Adapter<ExpensesDayAdapter.ViewHolder> {

    private final ArrayList<Expense> expenseList;
    private Context context;

    public ExpensesDayAdapter(ArrayList<Expense> expenseList, Context context) {
        this.expenseList = expenseList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(context);
        View mExpensesItemView = mInflater.inflate(R.layout.dashboard_expense_item, parent, false);
        return new ViewHolder(mExpensesItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Expense expense = expenseList.get(position);
        Category category = Category.getCategoryById(expense.getCate_id());

        holder.catTitle.setText(category.getCate_name());
        holder.description.setText(expense.getDescription());
        holder.price.setText(MainActivity.intToMoneyFormat(expense.getAmount()));

        if(category.getType() == 1)
            holder.price.setTextColor(ContextCompat.getColor(context, R.color.green_income));
        else
            if(category.getType() == 2) {
                holder.price.setTextColor(ContextCompat.getColor(context, R.color.red_expense));
            }
    }

    @Override
    public int getItemCount() {
        return expenseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ExpensesDayAdapter mAdapter;
        private ImageView catIcon;
        private TextView catTitle;
        private TextView description;
        private TextView price;
        public ViewHolder(@NonNull View itemView, ExpensesDayAdapter expensesDayAdapter) {
            super(itemView);
            this.mAdapter = expensesDayAdapter;
            catIcon = itemView.findViewById(R.id.expenseCatIcon);
            catTitle = itemView.findViewById(R.id.expenseCatTitle);
            description = itemView.findViewById(R.id.expenseDes);
            price = itemView.findViewById(R.id.expensePrice);
        }
    }
}
