package com.abbyy.task01.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.ViewDataBinding;

import com.google.android.material.snackbar.Snackbar;

public abstract class BaseActivity<T extends ViewDataBinding> extends AppCompatActivity {
    public T binding;

    public void showMessage(int ID) {
        Snackbar.make(binding.getRoot(), getString(ID), Snackbar.LENGTH_SHORT).show();
    }

    public void showMessage(String string) {
        Snackbar.make(binding.getRoot(), string, Snackbar.LENGTH_SHORT).show();
    }
}
