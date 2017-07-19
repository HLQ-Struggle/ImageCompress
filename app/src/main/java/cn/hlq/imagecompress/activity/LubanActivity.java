package cn.hlq.imagecompress.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nanchen.compresshelper.FileUtil;

import java.io.File;
import java.io.IOException;

import cn.hlq.imagecompress.R;
import cn.hlq.imagecompress.utils.StringUtils;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 使用LuBan进行图片压缩处理
 * GitHub地址：https://github.com/Curzibn/Luban
 */
public class LubanActivity extends Activity {

    private static final int REQUEST_PHOTO_CODE = 1000;

    private File oldFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luban);
        initListener();
    }

    private void initListener() {
        // 选择图片
        findViewById(R.id.id_luban_choose_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_PHOTO_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PHOTO_CODE && resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "打开图片失败~", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                // 获取图片路径
                oldFile = FileUtil.getTempFile(this, data.getData());
                // 设置获取图片显示ImageView
                ((ImageView) (findViewById(R.id.id_luban_choose_pic_show))).setImageBitmap(BitmapFactory.decodeFile(oldFile.getAbsolutePath()));
                // 设置获取图片大小
                ((TextView) (findViewById(R.id.id_luban_choose_pic_size))).setText(String.format("Size : %s", StringUtils.getReadableFileSize(oldFile.length())));
                // 启动Luban进行图片压缩
                Luban.with(this) // 初始化
                        .load(oldFile) // 要压缩的图片
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                // 压缩开始前调用 可以在方法内启动loading UI
                                Toast.makeText(LubanActivity.this, "我要开着Luban浪荡了~", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(File newFile) {
                                // 压缩成功后调用，返回压缩后的图片文件
                                Toast.makeText(LubanActivity.this, "开车到达目的地~", Toast.LENGTH_SHORT).show();
                                // 设置获取图片显示ImageView
                                ((ImageView) (findViewById(R.id.id_luban_compress_end_show))).setImageBitmap(BitmapFactory.decodeFile(newFile.getAbsolutePath()));
                                // 设置获取图片大小
                                ((TextView) (findViewById(R.id.id_luban_compress_end_size))).setText(String.format("Size : %s", StringUtils.getReadableFileSize(newFile.length())));
                            }

                            @Override
                            public void onError(Throwable e) {
                                // 压缩过程中出现异常
                                Toast.makeText(LubanActivity.this, "丫的，翻车了" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }).launch(); // 启动压缩
            } catch (IOException e) {
                Toast.makeText(this, "读取图片失败~", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }
}
