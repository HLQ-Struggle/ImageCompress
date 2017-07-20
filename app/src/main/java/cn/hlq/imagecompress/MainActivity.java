package cn.hlq.imagecompress;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import cn.hlq.imagecompress.activity.JpegActivity;
import cn.hlq.imagecompress.activity.LubanActivity;
import cn.hlq.imagecompress.activity.NanChenActivity;

public class MainActivity extends Activity {

    private MainActivity selfActivity = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        // 使用Luban压缩
        findViewById(R.id.id_luban_compress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(selfActivity, LubanActivity.class));
            }
        });
        // 使用jpeg开启哈夫曼算法进行图片压缩
        findViewById(R.id.id_jpeg_compress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(selfActivity, JpegActivity.class));
            }
        });
        // 使用南尘提供图片压缩方式
        findViewById(R.id.id_nanchen_default_compress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(selfActivity, NanChenActivity.class));
            }
        });
    }

}
