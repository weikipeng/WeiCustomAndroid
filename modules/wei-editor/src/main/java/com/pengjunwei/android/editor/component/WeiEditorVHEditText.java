package com.pengjunwei.android.editor.component;

import android.content.Context;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.pengjunwei.android.editor.R;
import com.pengjunwei.android.editor.WeiEditor;
import com.pengjunwei.android.editor.model.WeiEditorText;
import com.pengjunwei.android.editor.model.WeiEditorVHBaseData;
import com.pengjunwei.android.editor.viewholder.WeiEditorVHBase;
import com.pengjunwei.android.editor.viewholder.WeiEditorVHDelegate;

/**
 * 文本输入框
 * Created by wikipeng on 2017/12/7.
 */
public class WeiEditorVHEditText extends WeiEditorVHBase<WeiEditorText> {

    protected EditText  editText;
    protected WeiEditor editor;

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
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
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

//                String text = Html.toHtml(editText.getText());
                String text = "";

                if (s.length() == 0) {
                    if (mData != null) {
                        WeiEditorText realData = mData.data;
                        if (realData != null && !TextUtils.isEmpty(realData.hint)) {
                            editText.setHint(realData.hint);
                        }
                    }
                }

                SpannableStringBuilder ssb = new SpannableStringBuilder();
                /*
                * if user had pressed enter, replace it with br
                */
                for (int i = 0; i < s.length(); i++) {
                    ssb.clear();
                    if (s.charAt(i) == '\n') {
                        CharSequence subChars = s.subSequence(0, i);
                        ssb.append(subChars);
                        text = Html.toHtml(ssb);

                        if (text.length() > 0) {
                            setText(editText, text);
                        } else {
                            s.clear();
                        }

                        if (editor != null) {
                            editor.insertEditText(getAdapterPosition() + 1);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onBindViewHolder(int position, WeiEditorVHBaseData<WeiEditorText> data) {
        super.onBindViewHolder(position, data);
        if (data == null) {
            return;
        }

        WeiEditorText realData = data.data;
        if (realData == null) {
            return;
        }

        if (TextUtils.isEmpty(realData.hint)) {
            editText.setHint("");
        } else {
            editText.setHint(realData.hint);
        }

        if (realData.isRequestFocus) {
            realData.isRequestFocus = false;
            setFocus(editText);
        }
    }

    public void setFocus(EditText view) {
        view.requestFocus();
        InputMethodManager mgr = (InputMethodManager) itemView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        view.setSelection(view.getText().length());
        //        editorCore.setActiveView(view);
    }

    public void setText(TextView textView, String text) {
        CharSequence toReplace = getSanitizedHtml(text);
        textView.setText(toReplace);
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

}
