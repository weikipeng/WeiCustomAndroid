package com.pengjunwei.android.custom.demo.miui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.pengjunwei.android.custom.demo.R;

/**
 * Created by wikipeng on 2017/12/5.
 */
public class MIUIActivity1 extends BaseMIUIActivity {
    protected Button btnAction;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miui_1);
        btnAction = findViewById(R.id.btnAction);
        btnAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MIUIActivity1.this, MIUIActivity2.class);
                startActivity(intent);
            }
        });
    }

}
