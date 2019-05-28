package com.zero.suspensiondemo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_hidesuspension)
    public void onBtnShowClicked() {
    }

    @OnClick(R.id.btn_hidesuspension)
    public void onBtnHideClicked() {
    }

    @OnClick(R.id.btn_showdialog)
    public void onBtnShowDialogClicked() {
        Context mContext = getApplicationContext();
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.show();
    }
}
