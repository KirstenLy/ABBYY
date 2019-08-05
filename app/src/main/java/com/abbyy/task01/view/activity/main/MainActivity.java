package com.abbyy.task01.view.activity.main;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.abbyy.task01.AbbyyApp;
import com.abbyy.task01.R;
import com.abbyy.task01.di.ViewModelFactory;
import com.abbyy.task01.view.activity.BaseActivity;
import com.abbyy.task01.view.activity.history.HistoryActivity;
import com.abbyy.task01.view.adapter.TranslationAdapter;
import com.abbyy.task01.view.dialog.ErrorDialog;
import com.abbyy.task01.view.model.ArticleModel;
import com.abbyy.task01.view.model.ArticleNode;
import com.abbyy.task01.databinding.ActivityMainBinding;
import com.abbyy.task01.AbbyyUtils;
import com.example.sdk.models.AbbyyError;
import com.example.sdk.utils.KeyboardUtils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import static com.abbyy.task01.AbbyyUtils.fillTopParagraphLayout;
import static com.abbyy.task01.AbbyyUtils.isNodeBelongToType;
import static com.example.sdk.utils.ValidationUtils.isAlpha;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Inject ViewModelFactory factoryVM;

    private MainActivityViewModel viewModel;
    private TranslationAdapter translationAdapter = new TranslationAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        AbbyyApp.getInjector().inject(this);
        viewModel = ViewModelProviders.of(this, factoryVM).get(MainActivityViewModel.class);
        viewModel.getBearedToken(getString(R.string.basic_api_key));

        binding.rvTranslation.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.rvTranslation.setAdapter(translationAdapter);

        binding.btnSend.setOnClickListener(v -> {
            String inputWord = binding.inputWord.getText().toString();
            if (wordValidation(inputWord)) {
                viewModel.translate(inputWord);
            }
            KeyboardUtils.hideSoftKeyboard(this);
        });

        viewModel.loadingStateLiveData.observe(this, this::setProgressState);
        viewModel.nothingFoundStateLiveData.observe(this, this::setNothingFoundedState);
        viewModel.responseDataLiveData.observe(this, this::onDataReady);
        viewModel.responseErrorLiveData.observe(this, this::showOnErrorDialog);
    }

    public void setProgressState(boolean state) {
        binding.progress.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    public void setNothingFoundedState(boolean state) {
        binding.txtEmptyStub.setVisibility(View.GONE);

        binding.txtNothingFound.setVisibility(state ? View.VISIBLE : View.GONE);
        binding.lytContent.setVisibility(state ? View.GONE : View.VISIBLE);
    }

    public void showOnErrorDialog(AbbyyError error) {
        String errorMessage;

        switch (error.getErrorType()) {
            case INTERNET_NO_AVIALABLE:
                errorMessage = getString(R.string.error_network_no_avialable);
                break;
            case SERVER_NO_RESPONSE:
                errorMessage = getString(R.string.error_network_server_no_response);
                break;
            default:
                errorMessage = "Unknown";
        }

        ErrorDialog errorDialog = ErrorDialog.getInstance(errorMessage);
        errorDialog.show(getSupportFragmentManager(), "errorTag");
    }

    public void onDataReady(ArticleModel modelList) {
        binding.txtEmptyStub.setVisibility(View.GONE);

        binding.txtTitle.setText(modelList.getTitle());

        ArrayList<ArticleNode> paragraphNodes = new ArrayList<>();
        ArticleNode listNode = null;

        //Finding all Paragraph type nodes inside on top level of Body
        //to fill top layout(lytParagraph) with transcriptions, sounds icons, e.t.c
        //At the same time we looking for node with List type. I suggest it's only one inside body.
        for (ArticleNode node : modelList.getBody()) {
            if (isNodeBelongToType(node, AbbyyUtils.TYPE_PARAGRAPH)) {
                paragraphNodes.add(node);
            }
            if (isNodeBelongToType(node, AbbyyUtils.TYPE_LIST)) {
                listNode = node;
            }
        }

        if (!paragraphNodes.isEmpty()) {
            binding.lytParagraph.setVisibility(View.VISIBLE);
            fillTopParagraphLayout(this, binding.lytParagraph, paragraphNodes);
        } else {
            binding.lytParagraph.setVisibility(View.GONE);
        }

        if (listNode != null && listNode.getItems() != null && !listNode.getItems().isEmpty()) {
            translationAdapter.setData((ArrayList<ArticleNode>) listNode.getItems());
        } else {
            translationAdapter.clear();
        }
    }

    private boolean wordValidation(String word) {
        if (word == null || word.isEmpty()) {
            showMessage(R.string.error_input_empty);
            return false;
        }

        if (!isAlpha(word)) {
            showMessage(R.string.error_input_alpha);
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (item.getItemId() == R.id.menu_history) {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
