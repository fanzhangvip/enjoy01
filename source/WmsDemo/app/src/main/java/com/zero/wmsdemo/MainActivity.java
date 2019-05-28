package com.zero.wmsdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_show)
    TextView tvShow;

    private SuspensionWindowUtil windowUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        windowUtil = new SuspensionWindowUtil(this);
    }

    @OnClick(R.id.btn_show)
    public void onBtnShowClicked() {
        windowUtil.showSuspensionView();
    }

    @OnClick(R.id.btn_hide)
    public void onBtnHideClicked() {
        windowUtil.hideSuspensionView();
    }
}
