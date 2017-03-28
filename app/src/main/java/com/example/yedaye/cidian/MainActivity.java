package com.example.yedaye.cidian;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView mTxtShowResult; // 显示结果
    private ImageView mImgVocieQuery;  // 语音查询
    private EditText mEdtKeyord;  // 输入查询
    private Button mTxtStartQuery;  // 查询按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        mTxtShowResult = (TextView) findViewById(R.id.txt_show_result);
        mImgVocieQuery = (ImageView) findViewById(R.id.img_vocie_query);
        mEdtKeyord = (EditText) findViewById(R.id.edt_key_word);
        mTxtStartQuery = (Button) findViewById(R.id.txt_start_query);

        mTxtStartQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入内容
                String input = mEdtKeyord.getText().toString().trim();
                // 执行请求
                AsycHttp.getInstnce().requestForGet(Contants.PATH_QUEY, input, monResponseLister);
            }
        });
    }

    //    // 接受回调的数据
    AsycHttp.onResponseLister monResponseLister = new AsycHttp.onResponseLister() {

        @Override
        public void onSuccess(String result) {
            // 解析数据
            mTxtShowResult.setText(Parser.parserData(result).toString());
        }

        @Override
        public void onFauled(String error) {
            Toast.makeText(MainActivity.this, "数据错误" + error, Toast.LENGTH_SHORT).show();
        }
    };
}
