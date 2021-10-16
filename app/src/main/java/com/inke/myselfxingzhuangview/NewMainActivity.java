package com.inke.myselfxingzhuangview;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class NewMainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmain);
        findViewById(R.id.idv_1).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (mToast != null) {
            mToast.cancel();
        }
        switch (v.getId()) {
            case R.id.idv_1:
                mToast = Toast.makeText(this, v.getTag(v.getId()).toString(), Toast.LENGTH_SHORT);
                break;
        }
        mToast.show();
    }
}
