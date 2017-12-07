package com.pengjunwei.android.editor.viewholder;

import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wikipeng on 2017/12/7.
 */
public class WeiEditorVHProvider {
    private static WeiEditorVHProvider sGlobalProvider;

    SparseArray<Class>   typeClassMap;
    SparseArray<Integer> layoutIdMap;
    Map<Class, Integer>  classTypeMap;

    public WeiEditorVHProvider() {
        typeClassMap = new SparseArray<>();
        layoutIdMap = new SparseArray<>();
        classTypeMap = new HashMap<>();
    }

    public static WeiEditorVHProvider getGlobalProvider() {
        synchronized (WeiEditorVHProvider.class) {
            if (sGlobalProvider == null) {
                sGlobalProvider = new WeiEditorVHProvider();
            }
        }

        return sGlobalProvider;
    }

    public WeiEditorVHBase onCreateViewHolder(ViewGroup parent, int viewType, WeiEditorVHDelegate delegate) {
        //        View view = LayoutInflater.from(parent.getContext())
        //                .inflate(R.layout.wei_editor_menu_item_layout, parent, false);
        //        return new WeiEditorVHMenuItem(view);

        WeiEditorVHBase viewHolder = null;

        ///////////////////////////////////////////////////////////////////////////
        //根据viewType 获取 layoutResId资源id
        ///////////////////////////////////////////////////////////////////////////
        View view        = null;
        int  layoutResId = layoutIdMap.get(viewType);

        if (layoutResId > 0) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(layoutResId, parent, false);
        }

        ///////////////////////////////////////////////////////////////////////////
        //根据viewType 获取 ViewHolder类
        ///////////////////////////////////////////////////////////////////////////
        Class viewHolderClass = typeClassMap.get(viewType);
        if (viewHolderClass == null) {
            throw new IllegalArgumentException(" viewType not mapped ");
        }


        Constructor   constructor  = null;
        Constructor[] constructors = viewHolderClass.getConstructors();
        for (Constructor constructorItem : constructors) {
            if (constructorItem == null) {
                continue;
            }
            Type[] genericParameterTypes = constructorItem.getGenericParameterTypes();
            if (genericParameterTypes.length == 2) {
                constructor = constructorItem;
                break;
            }
        }

        if (constructor != null) {
            try {
                viewHolder = (WeiEditorVHBase) constructor.newInstance(new Object[]{view, delegate});
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return viewHolder;
    }

    public int getViewType(Class clazz) {
        if (classTypeMap.containsKey(clazz)) {
            return classTypeMap.get(clazz);
        }
        return 0;
    }

    public void register(Class dataClass, int type, int layoutResId, Class viewHolderClass) {
        classTypeMap.put(dataClass, type);
        typeClassMap.put(type, viewHolderClass);
        layoutIdMap.put(type, layoutResId);
    }
}
