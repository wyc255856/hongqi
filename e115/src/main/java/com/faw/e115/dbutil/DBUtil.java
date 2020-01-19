package com.faw.e115.dbutil;

import android.content.Context;

import com.faw.e115.R;
import com.faw.e115.model.CategoryListModel;
import com.faw.e115.model.CategoryModel;
import com.faw.e115.model.CategoryModel_Table;
import com.faw.e115.model.NewsListModel;
import com.faw.e115.model.NewsModel;
import com.faw.e115.model.NewsModel_Table;
import com.faw.e115.util.LogUtil;
import com.faw.e115.util.SharedpreferencesUtil;
import com.faw.e115.util.TestUtil;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.runtime.TransactionManager;
import com.raizlabs.android.dbflow.runtime.transaction.TransactionListener;
import com.raizlabs.android.dbflow.runtime.transaction.process.ProcessModelInfo;
import com.raizlabs.android.dbflow.runtime.transaction.process.SaveModelTransaction;
import com.raizlabs.android.dbflow.sql.language.SQLite;

public class DBUtil {
    public static void initData(final Context context) {
        LogUtil.logDebug("=====");
        if (SharedpreferencesUtil.isUploadBD(context)) {
            new Thread() {
                @Override
                public void run() {
                    super.run();
//                    Delete.table(NewsModel.class);
//                    Delete.table(CategoryModel.class);
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
    public static void getCategory(TransactionListener transactionListener) {
        SQLite.select()
                .from(CategoryModel.class)
                .where()
                .async().queryList(transactionListener);
    }

    public static void getNewsListById(int id, TransactionListener transactionListener) {
        LogUtil.logError("fast id = " + id);
        SQLite.select()
                .from(NewsModel.class)
                .where(NewsModel_Table.caid.eq(10077))
                .and(NewsModel_Table.id.eq(1064))
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

    public static void getNewsListByCatId(int catid, TransactionListener transactionListener) {
        LogUtil.logError("fast catid = " + catid);
        SQLite.select()
                .from(NewsModel.class)
                .where(NewsModel_Table.caid.eq(catid))
//                .where()
                .async().queryList(transactionListener);
    }

    public static void searchByWord(String word, TransactionListener transactionListener) {
        SQLite.select()
                .from(NewsModel.class)
                .where(NewsModel_Table.title.like("%" + word + "%"))
                .async().queryList(transactionListener);


//        SQLite.select(NewsModel_Table.caid, CategoryModel_Table.catid)
//                .from(NewsModel.class)
//                .leftOuterJoin(CategoryModel.class)
//                .on(NewsModel_Table.caid.withTable().eq(CategoryModel_Table.catid.withTable())).where(CategoryModel_Table.parentid.eq(1855)).and(NewsModel_Table.title.like(word))
//                .async().queryList(transactionListener);
    }

    public static NewsModel getTestModel(Context context) {
        String json = TestUtil.readTextFileFromRawResourceId(context, R.raw.test);
        NewsModel menuListModel = new Gson().fromJson(json, NewsModel.class);
        if (menuListModel != null) {
            return menuListModel;
        }
        return null;
    }
}
