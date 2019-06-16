package com.abbyy.task01.view;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.abbyy.task01.AbbyyApp;
import com.abbyy.task01.R;
import com.abbyy.task01.beans.ArticleModel;
import com.abbyy.task01.beans.ArticleNode;
import com.abbyy.task01.contract.MainActivityContract;
import com.abbyy.task01.databinding.ActivityMainBinding;
import com.abbyy.task01.presenter.MainActivityPresenter;
import com.abbyy.task01.utils.AbbyyUtils;
import com.abbyy.task01.utils.KeyboardUtils;

import javax.inject.Inject;


import static com.abbyy.task01.utils.AbbyyUtils.createParagraphString;
import static com.abbyy.task01.utils.ValidationUtils.isAlpha;

public class MainActivity extends BaseActivity<ActivityMainBinding> implements MainActivityContract {

    @Inject
    MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        AbbyyApp.getInjector().inject(this);
        presenter.setView(this);
        presenter.getBearedToken();

        binding.btnSend.setOnClickListener(v -> {
            String inputWord = binding.inputWord.getText().toString();
            if (wordValidation(inputWord)) {
                presenter.translate(inputWord);
            }
            KeyboardUtils.hideSoftKeyboard(this);
        });
    }

    @Override
    public void setLoadingState(boolean state) {
        binding.progress.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setNothingFoundedState(boolean state) {
        binding.txtEmptyStub.setVisibility(View.GONE);

        binding.txtNothingFound.setVisibility(state ? View.VISIBLE : View.GONE);
        binding.lytContent.setVisibility(state ? View.GONE : View.VISIBLE);
    }

    @Override
    public void onDataReady(ArticleModel modelList) {
        binding.txtEmptyStub.setVisibility(View.GONE);

        binding.txtTitle.setText(modelList.getTitle());
        binding.rvTranslation.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        ArticleNode paragraphNode = null;
        for (ArticleNode node : modelList.getBody()) {
            if (node.getNodeType().equals(AbbyyUtils.TYPE_STRING_PARAGRAPH)) {
                paragraphNode = node;
            }
        }

        if (paragraphNode != null) {
            binding.lytParagraph.setVisibility(View.VISIBLE);
            createParagraphString(this, binding.lytParagraph, paragraphNode);
        } else {
            binding.lytParagraph.setVisibility(View.GONE);
        }


//                modelList.getTitleMarkup()
//        ArrayList<ArticleNode> body = (ArrayList<ArticleNode>) modelList.getBody();
//        ArrayList<ArticleNode> listTypeNodes = getListTypeNodes(body);

//        ArrayList<ArticleNode> listItemNodes = (ArrayList<ArticleNode>) listNode.getItems();

//        binding.rvTranslation.setAdapter(new TranslationAdapter(res));
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
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_history) {
            Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.removeView();
    }
}
