package com.example.prm392_personalexpensetracking.model;

import com.example.prm392_personalexpensetracking.R;

import java.util.ArrayList;

public class Category {
    public static ArrayList<Category> categoriesList = new ArrayList<>();
    private int cateId;
    private String cateName;
    private int parentCateId;
    private String iconName;
    private int type;
    /*
        1. Income
        2. Outcome
        3. Loan / Debt
     */

    public Category() {
        initCategory();
    }

    public Category(String cateName) {
        this.cateName = cateName;
    }

    public Category(int cateId, String cateName, int parentCateId, int type) {
        this.cateId = cateId;
        this.cateName = cateName;
        this.parentCateId = parentCateId;
        this.type = type;;
        this.iconName = iconName;
//        this.child_cate = child_cate;
    }

    public int getCateId() {
        return cateId;
    }

    public void setCateId(int cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public int getParentCateId() {
        return parentCateId;
    }

    public void setParentCateId(int parentCateId) {
        this.parentCateId = parentCateId;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static Category getCategoryById(int id){
        return categoriesList.stream().filter((item) -> item.getCateId() == id).findFirst().get();
    }

    public static void initCategory(){
        categoriesList.clear();
        if(categoriesList.isEmpty()){
            categoriesList.add(new Category(1, "Shopping", -1, 2));
            categoriesList.add(new Category(2, "Food & Beverage", -1, 2));
            categoriesList.add(new Category(3, "Transport", -1, 2));
            categoriesList.add(new Category(4, "Bills & Utilities", -1, 2));
            categoriesList.add(new Category(5, "Other", -1, 2));
            categoriesList.add(new Category(6, "Salary", -1, 1));
            categoriesList.add(new Category(7, "Cafe", -1, 2));
            categoriesList.add(new Category(8, "Donate", -1, 2));
            categoriesList.add(new Category(9, "Education", -1, 2));
            categoriesList.add(new Category(10, "Electronics", -1, 2));
            categoriesList.add(new Category(11, "Fuel", -1, 2));
            categoriesList.add(new Category(12, "Gifts", -1, 1));
            categoriesList.add(new Category(13, "Health", -1, 2));
            categoriesList.add(new Category(14, "Maintenance", -1, 2));
            categoriesList.add(new Category(15, "Party", -1, 2));
            categoriesList.add(new Category(16, "Self Development", -1, 2));
            categoriesList.add(new Category(17, "Sport", -1, 2));
            categoriesList.add(new Category(18, "Savings", -1, 2));
        }
    }

    public int getImage(){
        switch (getCateId()){
            case 1:
                return R.drawable.groceries;
            case 2:
                return R.drawable.restaurant;
            case 3:
                return R.drawable.transportation;
            case 4:
                return R.drawable.laundry;
            case 5:
                return R.drawable.money;
            case 6:
                return R.drawable.institute;
            case 7:
                return R.drawable.cafe;
            case 8:
                return R.drawable.donate;
            case 9:
                return R.drawable.education;
            case 10:
                return R.drawable.electronics;
            case 11:
                return R.drawable.fuel;
            case 12:
                return R.drawable.gifts;
            case 13:
                return R.drawable.health;
            case 14:
                return R.drawable.maintenance;
            case 15:
                return R.drawable.party;
            case 16:
                return R.drawable.self_development;
            case 17:
                return R.drawable.sport;
            case 18:
                return R.drawable.savings;
        }
//        return R.drawable.groceries;
        return 0;
    }
    public static ArrayList<Category> getCategoryList(){
        return categoriesList;
    }
}
