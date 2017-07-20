package cn.hlq.imagecompress.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nanchen.compresshelper.FileUtil;

import java.io.File;
import java.io.IOException;

import cn.hlq.compress.NativeUtil;
import cn.hlq.imagecompress.R;
import cn.hlq.imagecompress.utils.StringUtils;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * 底层通过jpeg lib库开启哈夫曼算法 实现图片压缩
 */
public class JpegActivity extends Activity {

    private static final int REQUEST_PHOTO_CODE = 1000;

    private File oldFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpeg);
        initView();
    }

    private void initView() {
        findViewById(R.id.id_jpeg_choose).setOnClickListener(new View.OnClickListener() {
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
                ((ImageView) (findViewById(R.id.id_jpeg_new))).setImageBitmap(BitmapFactory.decodeFile(oldFile.getAbsolutePath()));
                // 设置获取图片大小
                ((TextView) (findViewById(R.id.id_jpeg_new_size))).setText(String.format("Size : %s", StringUtils.getReadableFileSize(oldFile.length())));
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                NativeUtil.compressBitmap(bitmap, Environment.getExternalStorageDirectory()+"/jpeg.png");
                // 设置获取图片显示ImageView
                ((ImageView) (findViewById(R.id.id_jpeg_old))).setImageBitmap(BitmapFactory.decodeFile(Environment.getExternalStorageDirectory()+"/jpeg.png"));
                // 设置获取图片大小
                File file=new File(Environment.getExternalStorageDirectory()+"/jpeg.png");
                ((TextView) (findViewById(R.id.id_jpeg_old_size))).setText(String.format("Size : %s", StringUtils.getReadableFileSize(file.length())));
            } catch (IOException e) {
                Toast.makeText(this, "读取图片失败~", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

}
