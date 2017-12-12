package com.pengjunwei.android.editor.component;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.pengjunwei.android.editor.R;
import com.pengjunwei.android.editor.WeiEditor;
import com.pengjunwei.android.editor.model.WeiEditorText;
import com.pengjunwei.android.editor.model.WeiEditorVHBaseData;
import com.pengjunwei.android.editor.view.WeiEditorEditText;
import com.pengjunwei.android.editor.viewholder.WeiEditorVHBase;
import com.pengjunwei.android.editor.viewholder.WeiEditorVHDelegate;
import com.pengjunwei.common.lib.LogTool;

/**
 * 文本输入框
 * Created by wikipeng on 2017/12/7.
 */
public class WeiEditorVHEditText extends WeiEditorVHBase<WeiEditorText> {

    protected WeiEditorEditText editText;
    protected WeiEditor         editor;

    protected TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            //                String text = Html.toHtml(editText.getText());
            //                Object tag = editText.getTag(R.id.control_tag);
            //                if (s.length() == 0 && tag != null)
            //                    editText.setHint(tag.toString());
            //                if (s.length() > 0) {
            //                /*
            //                * if user had pressed enter, replace it with br
            //                */
            //                    for (int i = 0; i < s.length(); i++) {
            //                        if (s.charAt(i) == '\n') {
            //                            CharSequence           subChars = s.subSequence(0, i);
            //                            SpannableStringBuilder ssb      = new SpannableStringBuilder(subChars);
            //                            text = Html.toHtml(ssb);
            //                            if (text.length() > 0)
            //                                setText(editText, text);
            //                            else
            //                                s.clear();
            //                            int index = editorCore.getParentView().indexOfChild(editText);
            //                    /* if the index was 0, set the placeholder to empty, behaviour happens when the user just press enter
            //                     */
            //                            if (index == 0) {
            //                                editText.setHint(null);
            //                                editText.setTag(R.id.control_tag, hint);
            //                            }
            //                            int position = index + 1;
            //                            String newText = null;
            //                            int lastIndex = s.length() - 1;
            //                            int nextIndex = i + 1;
            //                            if (nextIndex < lastIndex)
            //                                newText = s.subSequence(nextIndex, lastIndex).toString();
            //                            insertEditText(position, hint, newText);
            //                        }
            //                    }
            //                }
            //                if (editorCore.getEditorListener() != null) {
            //                    editorCore.getEditorListener().onTextChanged(editText, s);
            //                }

            String text = Html.toHtml(editText.getText());
            //                String text = "";

            WeiEditorText realData = getRealData();

            if (s.length() == 0) {
                if (realData != null && !TextUtils.isEmpty(realData.hint)) {
                    editText.setHint(realData.hint);
                }
                return;
            }

            SpannableStringBuilder ssb     = new SpannableStringBuilder();
            String                 newText = null;
                /*
                * if user had pressed enter, replace it with br
                */
            for (int i = 0; i < s.length(); i++) {
                ssb.clear();
                if (s.charAt(i) == '\n') {
                    CharSequence subChars = s.subSequence(0, i);
                    ssb.append(subChars);
                    text = Html.toHtml(ssb);

                    int lastIndex = s.length();
                    if (lastIndex > 0 && s.charAt(lastIndex - 1) == '\n') {
                        lastIndex = lastIndex - 1;
                    }

                    int nextIndex = i + 1;
                    if (nextIndex < lastIndex) {
                        newText = s.subSequence(nextIndex, lastIndex).toString();
                    }

                    if (editor != null) {
                        editText.clearFocus();
                        //                        editText.setFocusableInTouchMode(false);
                        //                        editText.setFocusable(false);
                        //                        editText.setFocusableInTouchMode(true);
                        //                        editText.setFocusable(true);

                        LogTool.getInstance().saveLog(WeiEditorVHEditText.this.toString()
                                , " insertEditText 文本=====> " + text);
                        editor.insertEditText(getAdapterPosition() + 1, newText);
                    }


                    if (text.length() > 0) {
                        editText.removeTextChangedListener(this);
                        setText(editText, text);
                        editText.addTextChangedListener(this);
                    } else {
                        s.clear();
                    }
                }
            }

            if (realData != null) {
                realData.text = text;
                LogTool.getInstance().saveLog(WeiEditorVHEditText.this.toString()
                        , " 保存输入框 文本=====> " + text);
            }
        }
    };


    public WeiEditorVHEditText(View itemView, WeiEditorVHDelegate delegate) {
        super(itemView, delegate);

        if (delegate instanceof WeiEditor) {
            editor = (WeiEditor) delegate;
        }

        editText = itemView.findViewById(R.id.editorEditText);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    editText.clearFocus();
                } else {
                    //                    editorCore.setActiveView(v);
                }

                WeiEditorText realData = getRealData();

                if (realData == null) {
                    return;
                }

                realData.isFocus = hasFocus;
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                //                RecyclerView recyclerView = getRecyclerView();
                //                if (recyclerView != null) {
                //                    int position = getLayoutPosition();
                //                    RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForLayoutPosition(position + 1);
                //                    if (viewHolder == null) {
                //                        recyclerView.smoothScrollToPosition(position + 1);
                //                        return true;
                //                    }
                //                }

                if (editor != null) {
                    int position = getLayoutPosition();
                    editor.scrollToPosition(position + 1);
                    if (actionId == EditorInfo.IME_ACTION_DONE) {

                    }
                    LogTool.getInstance().saveLog("onEditorAction ===> true", " actionId:", actionId);
                    return false;
                }

                return false;
            }
        });
    }

    @Override
    public void onBindViewHolder(int position, WeiEditorVHBaseData<WeiEditorText> data) {
        //        LogTool.getInstance().saveLog(getClass().getName(), this, "    onBindViewHolder======> " + position);
        super.onBindViewHolder(position, data);
        //        LogTool.getInstance().saveLog(getClass().getName(), this, "    after onBindViewHolder======> " + position);


        WeiEditorText realData = getRealData();
        if (realData == null) {
            return;
        }

        updateTextPure(realData.text, realData.hint);

        if (realData.isRequestFocus) {
            realData.isRequestFocus = false;
            requestFocus(editText);
        }
    }

    public void updateTextPure(String text, String hint) {
        editText.removeTextChangedListener(textWatcher);

        if (TextUtils.isEmpty(text)) {
            editText.setText("");

            if (TextUtils.isEmpty(hint)) {
                editText.setHint("");
            } else {
                editText.setHint(hint);
            }

        } else {
            LogTool.getInstance().saveLog(" onBindViewHolder 设置 文本=====> " + text);
            //            editText.setText(realData.text);
            setText(editText, text);
        }

        editText.addTextChangedListener(textWatcher);
    }

    protected void requestFocus(final WeiEditorEditText tempEditText) {
        //            editText.setFocusable(true);
        //            editText.setFocusableInTouchMode(true);
        //            editor.scrollToPosition(position);
        tempEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                setFocus(tempEditText);
                tempEditText.setSelection(0);
            }
        }, 0);
    }


    public void setFocus(EditText view) {
        view.requestFocus();
        InputMethodManager mgr = (InputMethodManager) itemView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (mgr != null) {
            mgr.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
        view.setSelection(view.getText().length());
        //        editorCore.setActiveView(view);
    }

    public void setText(TextView textView, String text) {
        CharSequence toReplace = getSanitizedHtml(text);
        textView.setText(toReplace);

        LogTool.getInstance().saveLog(" 222 setText 文本=====> " + toReplace);
    }

    CharSequence getSanitizedHtml(String text) {
        Spanned      spanned   = Html.fromHtml(text);
        CharSequence toReplace = noTrailingwhiteLines(spanned);
        return toReplace;
    }

    public CharSequence noTrailingwhiteLines(CharSequence text) {
        if (text.length() == 0)
            return text;
        while (text.charAt(text.length() - 1) == '\n') {
            text = text.subSequence(0, text.length() - 1);
        }
        return text;
    }

    @Override
    public void onViewAttachedToWindow() {
        LogTool.getInstance().saveLog(getClass().getName(), this, "    onViewAttachedToWindow======> ");
    }

    @Override
    public void onViewDetachedFromWindow() {
        LogTool.getInstance().saveLog(getClass().getName(), this, "    onViewDetachedFromWindow======> ");

        WeiEditorText realData = getRealData();
        if (realData == null) {
            return;
        }

        if (editor != null && realData.isFocus) {

        }
    }
}