package com.faw.hongqi.fragment;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.faw.hongqi.R;
import com.faw.hongqi.adaptar.PtrrvAdapter;
import com.faw.hongqi.dbutil.DBUtil;
import com.faw.hongqi.event.BaseEvent;
import com.faw.hongqi.event.HideKeyboardEvent;
import com.faw.hongqi.holder.ContentHolder;
import com.faw.hongqi.holder.SearchHolder;
import com.faw.hongqi.model.CategoryModel;
import com.faw.hongqi.model.NewsListModel;
import com.faw.hongqi.model.NewsModel;
import com.faw.hongqi.util.LogUtil;
import com.raizlabs.android.dbflow.runtime.transaction.BaseTransaction;
import com.raizlabs.android.dbflow.runtime.transaction.TransactionListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchFragment extends BaseFragment {
    EditText search_edit;
    View delete_btn;
    RecyclerView recyclerView;
    public PtrrvAdapter mAdapter;
    List<NewsModel> newsList = new ArrayList<>();
    public static String WORD = "";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_c229_search;
    }

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
    }

    @Override
    protected void initView(View view) {
        delete_btn = view.findViewById(R.id.delete_btn);
        search_edit = view.findViewById(R.id.search_edit);
        recyclerView = view.findViewById(R.id.ptrrv);
        mAdapter = new PtrrvAdapter(mContext, R.layout.item_search, SearchHolder.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setAdapter(mAdapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    @Override
    protected void initWidgetActions() {
        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_edit.setText("");
            }
        });
        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && !"".equals(s.toString())) {
                    delete_btn.setVisibility(View.VISIBLE);
                    search(s.toString());

                } else {
                    delete_btn.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    List<CategoryModel> list;

    private void search(String word) {
        WORD = word;
        list = new ArrayList<>();
        DBUtil.searchByWord(word, new TransactionListener() {
            @Override
            public void onResultReceived(Object result) {

            }

            @Override
            public boolean onReady(BaseTransaction transaction) {
                return false;
            }

            @Override
            public boolean hasResult(BaseTransaction transaction, Object result) {
                if (result != null)
                    list = (List<CategoryModel>) result;
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.refreshData(list);
                    }
                });

                LogUtil.logError("list size = " + list.size());
                return false;
            }
        });
    }

    public void HideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

        }
    }

    @Subscribe
    public void onEvent(BaseEvent event) {
        if (event instanceof HideKeyboardEvent) {
            HideKeyboard(search_edit);
        }

    }

    @Override
    public void refreshData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
