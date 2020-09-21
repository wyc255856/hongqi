package com.faw.hongqi.dbutil;

import android.content.Context;
import android.text.TextUtils;
import com.faw.hongqi.R;
import com.faw.hongqi.model.CategoryListModel;
import com.faw.hongqi.model.CategoryModel;
import com.faw.hongqi.model.HotWord_Table;
import com.faw.hongqi.model.HotWord;
import com.faw.hongqi.model.NewsListModel;
import com.faw.hongqi.model.NewsModel;
import com.faw.hongqi.model.NewsModel_Table;
import com.faw.hongqi.util.Constant;
import com.faw.hongqi.util.FileUtil;
import com.faw.hongqi.util.LogUtil;
import com.faw.hongqi.util.TestUtil;
import com.google.gson.Gson;
import com.raizlabs.android.dbflow.runtime.transaction.TransactionListener;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBUtil {
    private static DBUtil instance = new DBUtil();
    public List<NewsModel> newsList=new ArrayList<>();
    public List<CategoryModel> categoryList=new ArrayList<>();
    private DBUtil(){}
    public static DBUtil getInstance(){
        return instance;
    }

    public void initData(final Context context) {

//        String basePath=FileUtil.getDownloadResPath() + File.separator + "imagesnew";
//        File categoryFile=new File(basePath+"/category.json");
//        File newsFile=new File(basePath+"/news.json");

        Constant.initHotWord();
        try{
        newsList=getLocalList(context);
        categoryList=getLocalCategoryList(context);}
        catch (Exception e){
            LogUtil.logError("使用工程里的news数据");
            newsList=getList(context);
            categoryList=getCategoryList(context);
        }
//        if(newsFile.exists()){
//            LogUtil.logError("使用sd卡的news数据");
//            newsList=getLocalList(context);
//        }else {
//            LogUtil.logError("使用工程里的news数据");
//            newsList=getList(context);
//        }
//        if(categoryFile.exists()){
//            LogUtil.logError("使用sd卡的category数据");
//            categoryList=getLocalCategoryList(context);
//        }else {
//            LogUtil.logError("使用工程的category数据");
//            categoryList=getCategoryList(context);
//        }
//        if (SharedpreferencesUtil.isUploadBD(context)) {
//            //TODO 获取工程本地json
//            newsList=getList(context);
//            categoryList=getCategoryList(context);
//        }else {
//            //TODO 获取下载的json
//            newsList=getLocalList(context);
//            categoryList=getLocalCategoryList(context);
//        }

//        if (SharedpreferencesUtil.isUploadBD(context)) {
//            new Thread() {
//                @Override
//                public void run() {
//                    super.run();
////                    Delete.table(NewsModel.class);
////                    Delete.table(CategoryModel.class);
//                    Constant.initHotWord();
//                    //TODO 插入category
//                    insertCategory(context);
//                    //TODO 插入news
//                    insertNews(context);
//                    SharedpreferencesUtil.setUpLoadBD(context, false);
//
//                }
//            }.start();
//        }
    }
    public static void initDataNet(final Context context, final String tag) {

        new Thread() {
            @Override
            public void run() {
                super.run();
//                    Delete.table(NewsModel.class);
//                    Delete.table(CategoryModel.class);
                Constant.initHotWord();
                if ("category".equals(tag)){
                    //TODO 插入category
                    SQLite.delete(CategoryModel.class)
                            .where()
                            .async().execute();
                    insertLocalCategory(context);
                }else if ("news".equals(tag)){
                    //TODO 插入news
                    SQLite.delete(NewsModel.class)
                            .where()
                            .async().execute();
                    insertLocalNews(context);

                }else{

                }


            }
        }.start();

    }
    /**
     * 获取w
     *
     * @return
     */
    private static List<NewsModel> getList(Context context) {
        String json = TestUtil.readTextFileFromRawResourceId(context, R.raw.zy_news);
        NewsListModel menuListModel = new Gson().fromJson(json, NewsListModel.class);
        if (menuListModel != null) {
            LogUtil.logError("数据长度" + menuListModel.getRECORDS().size());
            return menuListModel.getRECORDS();
        }
        return null;
    }
    /**
     * 获取w
     *
     * @return
     */
    private static List<NewsModel> getLocalList(Context context) {
        String json = TestUtil.readTextFile(context, FileUtil.getDownloadResPath() + File.separator + "imagesnew"
                + "/news.json");
        NewsListModel menuListModel = new Gson().fromJson(json, NewsListModel.class);
        if (menuListModel != null) {
            LogUtil.logError("数据长度" + menuListModel.getRECORDS().size());
            LogUtil.logError("使用sd卡的news数据");
            return menuListModel.getRECORDS();
        }
        LogUtil.logError("使用工程的news数据");
        return getList(context);
    }


    private static List<CategoryModel> getLocalCategoryList(Context context) {
        String json = TestUtil.readTextFile(context, FileUtil.getDownloadResPath() + File.separator + "imagesnew"
                + "/category.json");
        CategoryListModel menuListModel = new Gson().fromJson(json, CategoryListModel.class);
        if (menuListModel != null) {
            LogUtil.logError("数据长度" + menuListModel.getRECORDS().size());
            LogUtil.logError("使用sd卡的Category数据");
            return menuListModel.getRECORDS();
        }
        LogUtil.logError("使用工程的category数据");
        return getCategoryList(context);
    }

    private static List<CategoryModel> getCategoryList(Context context) {
        String json = TestUtil.readTextFileFromRawResourceId(context, R.raw.zy_category);
        CategoryListModel menuListModel = new Gson().fromJson(json, CategoryListModel.class);
        if (menuListModel != null) {
            LogUtil.logError("数据长度" + menuListModel.getRECORDS().size());
            return menuListModel.getRECORDS();
        }
        return null;
    }

    private static void insertNews(Context context) {
        long startTime = System.currentTimeMillis();
        LogUtil.logError("开始入库news表");
       // TransactionManager.getInstance().addTransaction(new SaveModelTransaction<>(ProcessModelInfo.withModels(getList(context).getRECORDS())));
        LogUtil.logError("news表入库结束");
        LogUtil.logError("消耗了" + (System.currentTimeMillis() - startTime) + "毫秒");
    }

    private static void insertCategory(Context context) {
        long startTime = System.currentTimeMillis();
        LogUtil.logError("开始入库Category表");
      //  TransactionManager.getInstance().addTransaction(new SaveModelTransaction<>(ProcessModelInfo.withModels(getCategoryList(context).getRECORDS())));
        LogUtil.logError("Category表入库结束");
        LogUtil.logError("消耗了" + (System.currentTimeMillis() - startTime) + "毫秒");
    }



    private static void insertLocalNews(Context context) {
        long startTime = System.currentTimeMillis();
        LogUtil.logError("开始入库news表");
     //   TransactionManager.getInstance().addTransaction(new SaveModelTransaction<>(ProcessModelInfo.withModels(getLocalList(context).getRECORDS())));
        LogUtil.logError("news表入库结束");
        LogUtil.logError("消耗了" + (System.currentTimeMillis() - startTime) + "毫秒");
    }

    private static void insertLocalCategory(Context context) {
        long startTime = System.currentTimeMillis();
        LogUtil.logError("开始入库Category表");
        //TransactionManager.getInstance().addTransaction(new SaveModelTransaction<>(ProcessModelInfo.withModels(getLocalCategoryList(context).getRECORDS())));
        LogUtil.logError("Category表入库结束");
        LogUtil.logError("消耗了" + (System.currentTimeMillis() - startTime) + "毫秒");
    }


    public static void getAllNews(TransactionListener transactionListener) {
        SQLite.select()
                .from(NewsModel.class)
                .where()
                .async().queryList(transactionListener);
    }

    public  NewsModel getNewsListById(String id) {
        LogUtil.logError("fast id = " + id);
        NewsModel newsModel=null;
        for(int i=0;i<newsList.size();i++){
              newsModel=newsList.get(i);
            if(id.equals(newsModel.getId())){
                break;
            }
        }
        return newsModel;
//        SQLite.select()
//                .from(NewsModel.class)
//                .where(NewsModel_Table.id.eq(id))
//                .and(Constant.getCurrentIntProperty(context).eq(1))
//                .async().queryList(transactionListener);
    }
    public   List<CategoryModel> getFastCategoryList() {
        List<CategoryModel> resultList=new ArrayList<>();
        for(int i=0;i<categoryList.size();i++){
            CategoryModel categoryModel=categoryList.get(i);
            if(1869==categoryModel.getParentid()){
                resultList.add(categoryModel);
            }
        }
        Collections.sort(resultList);
        return resultList;
//        SQLite.select()
//                .from(CategoryModel.class)
//                .where(CategoryModel_Table.parentid.eq(1869))
////                .where()
//                .async().queryList(transactionListener);
    }

    public    List<CategoryModel> getManuaCategoryList() {
        List<CategoryModel> resultList=new ArrayList<>();
        for(int i=0;i<categoryList.size();i++){
            CategoryModel categoryModel=categoryList.get(i);
            if(1855==categoryModel.getParentid()){
                resultList.add(categoryModel);
            }
        }
        Collections.sort(resultList);
        return resultList;
//        SQLite.select()
//                .from(CategoryModel.class)
//                .where(CategoryModel_Table.parentid.eq(1855))
////                .where()
//
//                .async().queryList(transactionListener);
    }



    public  CategoryModel getCatgoryByCatid(int catid){
//        SQLite.select()
//                .from(CategoryModel.class)
//                .where(CategoryModel_Table.catid.eq(catid))
////                .where()
//
//                .async().queryList(transactionListener);

        CategoryModel categoryModel=null;
        for(int i=0;i<categoryList.size();i++){
            categoryModel=categoryList.get(i);
            if(catid==categoryModel.getCatid()){
                break;
            }
        }
        return categoryModel;

    }
    public  List<NewsModel> getNewsListByCatId(Context context, int catid) {
        LogUtil.logError("fast catid = " + catid);
//        SQLite.select()
//                .from(NewsModel.class)
//                .where(NewsModel_Table.catid.eq(catid))
////                .where()
//                .and(Constant.getCurrentIntProperty(context).eq(1))
//                .async().queryList(transactionListener);
        List<NewsModel> resultList=new ArrayList<>();
        for(int i=0;i<newsList.size();i++){
            NewsModel newsModel=newsList.get(i);
            if(catid==newsModel.getCatid()&&isCurrentModel(context,newsModel)){
                resultList.add(newsModel);
            }
        }
        return resultList;
    }

    private boolean isCurrentModel(Context context,NewsModel newsModel){
        String currentName=Constant.getCurrentIntProperty(context).getNameAlias().getName();
//        intPropertyList.put("C229_1", NewsModel_Table.sdss);
//        intPropertyList.put("C229_2", NewsModel_Table.sdhh);
//        intPropertyList.put("C229_3", NewsModel_Table.sdzg);
//        intPropertyList.put("C229_4", NewsModel_Table.zdss);
//
//        intPropertyList.put("C229_5", NewsModel_Table.zdhh);
//        intPropertyList.put("C229_6", NewsModel_Table.zdzg);
//        intPropertyList.put("C229_7", NewsModel_Table.zdqj);
        int code=-1;
        if(Constant.getCurrentIntProperty(context).equals(NewsModel_Table.sdss)){
            code=newsModel.getSdss();
        }else if(Constant.getCurrentIntProperty(context).equals(NewsModel_Table.sdhh)){
            code=newsModel.getSdhh();
        }else if(Constant.getCurrentIntProperty(context).equals(NewsModel_Table.sdzg)){
            code=newsModel.getSdzg();
        }else if(Constant.getCurrentIntProperty(context).equals(NewsModel_Table.zdss)){
            code=newsModel.getZdss();
        }else if(Constant.getCurrentIntProperty(context).equals(NewsModel_Table.zdhh)){
            code=newsModel.getZdhh();
        }else if(Constant.getCurrentIntProperty(context).equals(NewsModel_Table.zdzg)){
            code=newsModel.getZdzg();
        }else if(Constant.getCurrentIntProperty(context).equals(NewsModel_Table.zdqj)){
            code=newsModel.getZdqj();
        }
        return code==1;
    }
//    private String getName(IntProperty intProperty){
//        if(intProperty!=null){
//return intProperty.getNameAlias().getName();
//        }else {
//            return "";
//        }
//    }

    public List<NewsModel> searchByWord(Context context, String word) {
//        SQLite.select()
//                .from(NewsModel.class)
//                .where(NewsModel_Table.title.like("%" + word + "%"))
//                .and(Constant.getCurrentIntProperty(context).eq(1))
//                .async().queryList(transactionListener);
        List<NewsModel> resultList=new ArrayList<>();
        if(TextUtils.isEmpty(word)){
            return resultList;
        }
        for(int i=0;i<newsList.size();i++){
            NewsModel newsModel=newsList.get(i);
            if( newsModel.getTitle().contains(word)&&isCurrentModel(context,newsModel)){
                resultList.add(newsModel);
            }
        }
        return resultList;

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
