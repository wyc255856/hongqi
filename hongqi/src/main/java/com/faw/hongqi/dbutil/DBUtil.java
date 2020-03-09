package com.faw.hongqi.dbutil;

import android.content.Context;

import com.faw.hongqi.R;
import com.faw.hongqi.model.CategoryListModel;
import com.faw.hongqi.model.CategoryModel;
import com.faw.hongqi.model.CategoryModel_Table;
import com.faw.hongqi.model.HotWord_Table;
import com.faw.hongqi.model.HotWord;
import com.faw.hongqi.model.NewsListModel;
import com.faw.hongqi.model.NewsModel;
import com.faw.hongqi.model.NewsModel_Table;
import com.faw.hongqi.util.Constant;
import com.faw.hongqi.util.LogUtil;
import com.faw.hongqi.util.SharedpreferencesUtil;
import com.faw.hongqi.util.TestUtil;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.TransactionListener;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Join;
import com.raizlabs.android.dbflow.sql.language.NameAlias;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

import java.util.List;

public class DBUtil {

    public static void initData(final Context context) {
        if (SharedpreferencesUtil.isUploadBD(context)) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
//                    Delete.table(NewsModel.class);
//                    Delete.table(CategoryModel.class);
                    Constant.initHotWord();
                    //TODO 插入category
                    insertCategory(context);
                    //TODO 插入news
                    insertNews(context);
                    SharedpreferencesUtil.setUpLoadBD(context, false);

                }
            }.start();
        }
    }

    /**
     * 获取w
     *
     * @return
     */
    private static NewsListModel getList(Context context) {
        String json = TestUtil.readTextFileFromRawResourceId(context, R.raw.zy_news);
        NewsListModel menuListModel = new Gson().fromJson(json, NewsListModel.class);
        if (menuListModel != null) {
            LogUtil.logError("数据长度" + menuListModel.getRECORDS().size());
            return menuListModel;
        }
        return null;
    }


    private static CategoryListModel getCategoryList(Context context) {
        String json = TestUtil.readTextFileFromRawResourceId(context, R.raw.zy_category);
        CategoryListModel menuListModel = new Gson().fromJson(json, CategoryListModel.class);
        if (menuListModel != null) {
            LogUtil.logError("数据长度" + menuListModel.getRECORDS().size());
            return menuListModel;
        }
        return null;
    }

    private static void insertNews(Context context) {
        long startTime = System.currentTimeMillis();
        LogUtil.logError("开始入库news表");
        TransactionManager.getInstance().addTransaction(new SaveModelTransaction<>(ProcessModelInfo.withModels(getList(context).getRECORDS())));
        LogUtil.logError("news表入库结束");
        LogUtil.logError("消耗了" + (System.currentTimeMillis() - startTime) + "毫秒");
    }

    private static void insertCategory(Context context) {
        long startTime = System.currentTimeMillis();
        LogUtil.logError("开始入库Category表");
        TransactionManager.getInstance().addTransaction(new SaveModelTransaction<>(ProcessModelInfo.withModels(getCategoryList(context).getRECORDS())));
        LogUtil.logError("Category表入库结束");
        LogUtil.logError("消耗了" + (System.currentTimeMillis() - startTime) + "毫秒");
    }

    public static void getAllNews(TransactionListener transactionListener) {
        SQLite.select()
                .from(NewsModel.class)
                .where()
                .async().queryList(transactionListener);
    }

    public static void getNewsListById(Context context, int id, TransactionListener transactionListener) {
        LogUtil.logError("fast id = " + id);
        SQLite.select()
                .from(NewsModel.class)
                .where(NewsModel_Table.id.eq(1064))
                .and(Constant.getCurrentIntProperty(context).eq(1))
                .async().queryList(transactionListener);
    }

    public static void getFastCategoryList(TransactionListener transactionListener) {
        SQLite.select()
                .from(CategoryModel.class)
                .where(CategoryModel_Table.parentid.eq(1869))
//                .where()
                .async().queryList(transactionListener);
    }

    public static void getManuaCategoryList(TransactionListener transactionListener) {
        SQLite.select()
                .from(CategoryModel.class)
                .where(CategoryModel_Table.parentid.eq(1855))
//                .where()

                .async().queryList(transactionListener);
    }

    public static void getNewsListByCatId(Context context, int catid, TransactionListener transactionListener) {
        LogUtil.logError("fast catid = " + catid);
        SQLite.select()
                .from(NewsModel.class)
                .where(NewsModel_Table.catid.eq(catid))
//                .where()
                .and(Constant.getCurrentIntProperty(context).eq(1))
                .async().queryList(transactionListener);
    }

    public static void searchByWord(Context context, String word, TransactionListener transactionListener) {
        SQLite.select()
                .from(NewsModel.class)
                .where(NewsModel_Table.title.like("%" + word + "%"))
                .and(Constant.getCurrentIntProperty(context).eq(1))
                .async().queryList(transactionListener);


//        SQLite.select(NewsModel_Table.caid, CategoryModel_Table.catid)
//                .from(NewsModel.class)
//                .leftOuterJoin(CategoryModel.class)
//                .on(NewsModel_Table.caid.withTable().eq(CategoryModel_Table.catid.withTable())).where(CategoryModel_Table.parentid.eq(1855)).and(NewsModel_Table.title.like(word))
//                .async().queryList(transactionListener);
    }

    public static void getHotWordList(Context context, TransactionListener transactionListener) {
        SQLite.select()
                .from(HotWord.class)
                .where()
                .orderBy(HotWord_Table.id, false)
                .limit(4)
                .async().queryList(transactionListener);
    }

    public static NewsModel getTestModel(Context context) {
        String json = TestUtil.readTextFileFromRawResourceId(context, R.raw.test);
        NewsModel menuListModel = new Gson().fromJson(json, NewsModel.class);
        if (menuListModel != null) {
            return menuListModel;
        }
        return null;
    }

    public static void insertHotWord(String word) {
        HotWord hotWord = new HotWord();
        hotWord.setWord(word);
        hotWord.save();
    }
}
