package com.abbyy.task01.view;


import android.os.Bundle;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abbyy.task01.AbbyyApp;
import com.abbyy.task01.R;
import com.abbyy.task01.Storage;
import com.abbyy.task01.adapter.HistoryAdapter;
import com.abbyy.task01.databinding.ActivityHistoryBinding;

import java.util.ArrayList;

import javax.inject.Inject;

public class HistoryActivity extends BaseActivity<ActivityHistoryBinding> {

    @Inject Storage storage;

    private HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_history);

        AbbyyApp.getInjector().inject(this);

        ArrayList<String> history = storage.getHistory();

        if (history == null || history.isEmpty()) {
            setEmptyHistoryState(true);
        } else {
            setEmptyHistoryState(false);
            binding.rvHistory.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true));
            historyAdapter = new HistoryAdapter(storage.getHistory());
            binding.rvHistory.setAdapter(historyAdapter);
        }

        binding.btnClear.setOnClickListener(v -> {
            storage.clearHistory();
            if (historyAdapter != null) {
                historyAdapter.clearHistory();
                setEmptyHistoryState(true);
            }
        });
    }

    private void setEmptyHistoryState(boolean state) {
        binding.txtHistoryEmpty.setVisibility(state ? View.VISIBLE : View.GONE);
        binding.rvHistory.setVisibility(state ? View.GONE : View.VISIBLE);
    }
}
