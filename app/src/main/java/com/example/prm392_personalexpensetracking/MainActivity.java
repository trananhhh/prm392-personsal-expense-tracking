package com.example.prm392_personalexpensetracking;
import android.os.Bundle;

import com.example.prm392_personalexpensetracking.model.Category;
import com.example.prm392_personalexpensetracking.model.Expense;
import com.example.prm392_personalexpensetracking.model.ExpenseType;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.prm392_personalexpensetracking.databinding.ActivityMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    public static String intToMoneyFormat(int amount){
        return String.format("%,d", amount) + " đ";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ExpenseType.initExpenseType();
        Category.initCategory();
        Expense.initExpenseList();

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

}