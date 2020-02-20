package com.faw.hongqi.util;


/**
 * Description:网络请求回调
 */
public interface NetWorkCallBack {
    void onSuccess(Object data) ;

    void onFail(Object error);
}