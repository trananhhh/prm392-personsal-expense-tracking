package com.example.prm392_personalexpensetracking.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_personalexpensetracking.AddExpenseActivity;
import com.example.prm392_personalexpensetracking.MainActivity;
import com.example.prm392_personalexpensetracking.adapter.ExpensesDayAdapter;
import com.example.prm392_personalexpensetracking.databinding.FragmentHomeBinding;
import com.example.prm392_personalexpensetracking.model.Category;
import com.example.prm392_personalexpensetracking.model.Expense;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private RecyclerView mRecyclerView;
    private ExpensesDayAdapter mExpenseAdapter ;
    private int totalExpenses = 0;
    private int currentBalance = 0;
    private int totalIncome = 0;
    private TextView expenseStat;
    private TextView balanceStat;
    private TextView incomeStat;
    private ExtendedFloatingActionButton addExpenseFab;

    private void setStats(FragmentHomeBinding binding){
        totalExpenses = 0;
        totalIncome = 0;

        expenseStat = binding.expenseStat;
        balanceStat = binding.balanceStat;
        incomeStat = binding.incomeStat;

        Expense.getExpensesList().forEach((item) -> {
            int catType = Category.getCategoryById(item.getCate_id()).getType();
            if(catType == 1)
                totalIncome += item.getAmount();
            if(catType == 2)
                totalExpenses += item.getAmount();
        });
        currentBalance = totalIncome - totalExpenses;

        expenseStat.setText( MainActivity.intToMoneyFormat(totalExpenses));
        balanceStat.setText( MainActivity.intToMoneyFormat(currentBalance));
        incomeStat.setText( MainActivity.intToMoneyFormat(totalIncome));
    }

    private void bindingRecyclerView(FragmentHomeBinding binding){
        mRecyclerView = binding.expensesRecyclerView;
        mExpenseAdapter = new ExpensesDayAdapter(Expense.getExpensesList(), getContext());
        mRecyclerView.setAdapter(mExpenseAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        setStats(binding);
        bindingRecyclerView(binding);

        addExpenseFab = binding.addExpenseFab;
        addExpenseFab.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), AddExpenseActivity.class);
            startActivity(intent);
        });

        Log.d("HomeFragment", "onCreateView");

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}