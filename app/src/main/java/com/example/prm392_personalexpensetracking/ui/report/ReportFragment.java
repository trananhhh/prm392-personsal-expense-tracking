package com.example.prm392_personalexpensetracking.ui.report;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_personalexpensetracking.MainActivity;
import com.example.prm392_personalexpensetracking.adapter.CategoryReportAdapter;
import com.example.prm392_personalexpensetracking.databinding.FragmentReportBinding;
import com.example.prm392_personalexpensetracking.model.Category;
import com.example.prm392_personalexpensetracking.model.CategoryReport;
import com.example.prm392_personalexpensetracking.model.Expense;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ReportFragment extends Fragment {
    private FragmentReportBinding binding;
    private RecyclerView mRecyclerView;
    private CategoryReportAdapter mExpenseAdapter;
    private ImageView prevMonthBtn, nextMonthBtn;
    private TextView expenseStat, balanceStat, incomeStat, monthTitle;
    private Calendar currentMonth;
    private int totalExpenses = 0, currentBalance = 0, totalIncome = 0;
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    private ArrayList<Expense> expenseArrayList;
    private Map<Integer, ArrayList<Expense>> catReportMap;
    private ArrayList<CategoryReport> catRenderList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReportBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        currentMonth = Calendar.getInstance();

        monthTitle = binding.monthTitle;
        monthTitle.setText(MainActivity.getMonthTitle(currentMonth));

        prevMonthBtn = binding.prevMonthBtn;
        nextMonthBtn = binding.nextMonthBtn;

        prevMonthBtn.setOnClickListener(view -> {
            currentMonth.add(Calendar.MONTH, -1);
            monthTitle.setText(MainActivity.getMonthTitle(currentMonth));
            loadData(binding);
        });

        nextMonthBtn.setOnClickListener(view -> {
            currentMonth.add(Calendar.MONTH, 1);
            monthTitle.setText(MainActivity.getMonthTitle(currentMonth));
            loadData(binding);
        });

        return root;
    }

    private void bindingRecyclerView(FragmentReportBinding binding){
        mRecyclerView = binding.categoriesRecyclerView;
        mExpenseAdapter = new CategoryReportAdapter(catRenderList, getContext());
        mRecyclerView.setAdapter(mExpenseAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
    private void loadData(FragmentReportBinding binding){
        Calendar tmpCal = Calendar.getInstance();
        fStore.collection("Data").document(fAuth.getUid()).collection("Expenses").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                catReportMap = new HashMap<>();
                catRenderList = new ArrayList<>();

                catReportMap.clear();
                catRenderList.clear();
                for(DocumentSnapshot ds:task.getResult()){
                    tmpCal.setTime(ds.getDate("createAt"));
                    if (tmpCal.get(Calendar.YEAR) == currentMonth.get(Calendar.YEAR) && tmpCal.get(Calendar.MONTH) == currentMonth.get(Calendar.MONTH)){
                        Expense expense = new Expense(
                            ds.getString("expenseId"),
                            Math.toIntExact(ds.getLong("cateId")),
                            ds.getString("description"),
                            Math.toIntExact(ds.getLong("amount")),
                            ds.getDate("createAt")
                        );
                        if(catReportMap.get(expense.getCateId()) == null)
                            catReportMap.put(expense.getCateId(), new ArrayList<>());
                        catReportMap.get(expense.getCateId()).add(expense);

                        if(Category.getCategoryById(expense.getCateId()).getType() == 1)
                            totalExpenses += Math.toIntExact(ds.getLong("amount"));
                        else
                            totalIncome += Math.toIntExact(ds.getLong("amount"));

                    }
                };
                bindingRecyclerView(binding);

                Set<Integer> set = catReportMap.keySet();
                for (Integer key : set) {
                    ArrayList<Expense> thisExpenseList = catReportMap.get(key);
                    Integer sum = thisExpenseList.stream().map(expense -> expense.getAmount()).reduce(0, Integer::sum);
                    catRenderList.add(new CategoryReport(Category.getCategoryById(key), thisExpenseList.size(), sum, (totalExpenses == 0 ? 1 : sum / totalExpenses * 100)));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        if(fAuth.getCurrentUser() != null)
            loadData(binding);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}