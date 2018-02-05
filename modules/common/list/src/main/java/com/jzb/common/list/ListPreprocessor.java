package com.jzb.common.list;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by wikipeng on 2017/12/26.
 */
public class ListPreprocessor<K, T> {
    protected int                            removeCount;
    protected Set<K>                         keyList;
    protected IListSignature<K, T>           signature;
    protected List<IProcessorInterceptor<T>> interceptors;

    //    public interface IListSignature<K, T> {
    //        K getSignature(T tempObject);
    //    }


    /**
     * 去重通用方法
     */
    public int processor(List<T>... tempListList) {
        int removedCount = 0;

        if (keyList == null) {
            keyList = new HashSet<>();
        }

        //        Object[] resultObject = new Object[2];

        List<T> resultList = null;

        ///////////////////////////////////////////////////////////////////////////
        // 去重
        ///////////////////////////////////////////////////////////////////////////
        if (tempListList != null) {
            int listSize          = tempListList.length;
            T   tempItem          = null;
            K   tempItemSignature = null;

            for (int t = 0; t < listSize; t++) {
                List<T> tempList = tempListList[t];
                if (tempList == null) {
                    //如果列表为空则继续
                    continue;
                } else if (resultList == null) {
                    //给返回结果赋值
                    resultList = tempList;
                }

                int size = tempList.size();

                for (int i = size - 1; i >= 0; i--) {
                    tempItem = tempList.get(i);
                    tempItemSignature = signature.getSignature(tempItem);
                    if (keyList.contains(tempItemSignature)) {
                        removedCount++;
                        continue;
                    } else {
                        if (interceptors != null) {
                            for (IProcessorInterceptor<T> interceptor : interceptors) {
                                if (interceptor != null) {
                                    interceptor.interceptor(tempItem);
                                }
                            }
                        }
                        resultList.add(tempItem);
                        keyList.add(tempItemSignature);
                    }
                }
            }
        }

        return removedCount;

        //        resultObject[0] = resultList;
        //        resultObject[1] = removedCount;
        //
        //        return resultObject;
    }

    public interface IListSignature<K, T> {
        K getSignature(T tempObject);
    }

    public interface IProcessorInterceptor<T> {
        void interceptor(T tempItem);
    }

    public void setListSignature(IListSignature<K, T> signature) {
        this.signature = signature;
    }

    public void addinterceptor(IProcessorInterceptor<T> interceptor) {
        if (interceptors == null) {
            interceptors = new ArrayList<>();
        }

        interceptors.add(interceptor);
    }

    public void reset(){
        if (keyList != null) {
            keyList.clear();
        }
    }
}
