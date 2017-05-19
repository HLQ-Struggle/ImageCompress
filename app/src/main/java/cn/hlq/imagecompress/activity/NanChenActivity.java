package cn.hlq.imagecompress.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nanchen.compresshelper.CompressHelper;
import com.nanchen.compresshelper.FileUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import cn.hlq.imagecompress.R;
import cn.hlq.imagecompress.utils.StringUtils;

/**
 * create by heliquan at 2017年5月18日
 * 本Activity主要使用南尘提供的CompressHelper进行图片压缩测试
 * 附上南尘GitHub地址：https://github.com/nanchen2251/CompressHelper
 * 吃水不忘挖井人，开源不宜，且行且珍惜！
 */
public class NanChenActivity extends Activity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView mImageOld, mWeightImageOld;
    private ImageView mImageNew, mWeightImageNew;
    private TextView mTextOld, mWeightTextOld;
    private TextView mTextNew, mWeightTextNew;
    private TextView mOldCompressMs, mNewCompressMs;
    private TextView tvTestEnd1, tvTestEnd2, tvTestEnd3, tvTestEnd4, tvTestEnd5, tvEnd;

    private File oldFile;
    private File newFile;

    private String imageStatus = "";
    private long defaultStarTime, defaultEndTime;
    private long weightStarTime, weightEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nan_chen);
        initView();
    }

    private void initView() {
        // 压缩所耗时间
        mOldCompressMs = (TextView) findViewById(R.id.id_nanchen_default_compress_time);
        mNewCompressMs = (TextView) findViewById(R.id.id_nanchen_weight_compress_time);
        // 使用默认配置进行压缩图片
        mImageOld = (ImageView) findViewById(R.id.id_nanchen_default_old_image);
        mImageNew = (ImageView) findViewById(R.id.id_nanchen_default_new_image);
        mTextOld = (TextView) findViewById(R.id.id_nanchen_default_old_image_size);
        mTextNew = (TextView) findViewById(R.id.id_nanchen_default_new_image_size);
        // 使用自定义配置进行压缩图片
        mWeightImageOld = (ImageView) findViewById(R.id.id_nanchen_weight_old_image);
        mWeightImageNew = (ImageView) findViewById(R.id.id_nanchen_weight_new_image);
        mWeightTextOld = (TextView) findViewById(R.id.id_nanchen_weight_old_image_size);
        mWeightTextNew = (TextView) findViewById(R.id.id_nanchen_weight_new_image_size);
        // 显示测试多张图片结果
        tvTestEnd1 = (TextView) findViewById(R.id.id_more_img_test_1);
        tvTestEnd2 = (TextView) findViewById(R.id.id_more_img_test_2);
        tvTestEnd3 = (TextView) findViewById(R.id.id_more_img_test_3);
        tvTestEnd4 = (TextView) findViewById(R.id.id_more_img_test_4);
        tvTestEnd5 = (TextView) findViewById(R.id.id_more_img_test_5);
        tvEnd = (TextView) findViewById(R.id.id_more_img_test_end);

        // 使用默认配置压缩图片
        // 选取要压缩的图片
        findViewById(R.id.id_nanchen_default_takephoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageStatus = "default";
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        // 开始压缩图片
        findViewById(R.id.id_nanchen_default_compress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 默认压缩方法，多张图片直接加入循环即可
                // 提供俩种方式，大家可自行选择：compressToFile compressToBitmap
                defaultStarTime = System.currentTimeMillis();
                newFile = CompressHelper.getDefault(getApplicationContext()).compressToFile(oldFile);
                defaultEndTime = System.currentTimeMillis();
                mOldCompressMs.setText(StringUtils.getMS(defaultStarTime, defaultEndTime));
                Log.e("HLQ_Struggle", "压缩后图片地址：" + newFile.getAbsolutePath());
                // 设置ImageView显示图片
                mImageNew.setImageBitmap(BitmapFactory.decodeFile(newFile.getAbsolutePath()));
                // 设置压缩后图片大小
                mTextNew.setText(String.format("Size : %s", StringUtils.getReadableFileSize(newFile.length())));
            }
        });

        // 使用自定义配置压缩图片
        // 选取要压缩的图片
        findViewById(R.id.id_nanchen_weight_takephoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageStatus = "weight";
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
        // 开始压缩图片
        findViewById(R.id.id_nanchen_weight_compress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String yourFileName = "HLQ_test.jpg";
                // 你也可以自定义压缩
                weightStarTime = System.currentTimeMillis();
                newFile = new CompressHelper.Builder(getApplicationContext())
                        .setMaxWidth(720)  // 默认最大宽度为720
                        .setMaxHeight(960) // 默认最大高度为960
                        .setQuality(60)    // 默认压缩质量为80
                        .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
                        .setFileName(yourFileName) // 设置你的文件名 具体使用可以仿造系统拍照时存储照片的规则
                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
                        .build()
                        .compressToFile(oldFile);
                weightEndTime = System.currentTimeMillis();
                mNewCompressMs.setText(StringUtils.getMS(weightStarTime, weightEndTime));
                mWeightImageNew.setImageBitmap(BitmapFactory.decodeFile(newFile.getAbsolutePath()));
                mWeightTextNew.setText(String.format("Size : %s", StringUtils.getReadableFileSize(newFile.length())));
            }
        });
        long moreStart = System.currentTimeMillis();
        Log.e("HLQ_Struggle", "开始时间：" + moreStart);
        // 测试多张图片压缩
        for (int i = 0; i < initImgsPath().size(); i++) {
            File testEnd = CompressHelper.getDefault(getApplicationContext()).compressToFile(new File(initImgsPath().get(i)));
            String end = "图片地址：" + initImgsPath().get(i) + "\n" + "图片大小：" + String.format("Size : %s", StringUtils.getReadableFileSize(new File(initImgsPath().get(i)).length())) + "\n" + "压缩后大小为：" + String.format("Size : %s", StringUtils.getReadableFileSize(testEnd.length())) + "\n";
            if (i == 0) {
                tvTestEnd1.setText(end);
            }
            if (i == 1) {
                tvTestEnd2.setText(end);
            }
            if (i == 2) {
                tvTestEnd3.setText(end);
            }
            if (i == 3) {
                tvTestEnd4.setText(end);
            }
            if (i == 4) {
                tvTestEnd5.setText(end);
            }
        }
        long moreEnd = System.currentTimeMillis();
        Log.e("HLQ_Struggle", "结束时间：" + moreEnd);
        tvEnd.setText(StringUtils.getMS(moreStart, moreEnd));
    }

    private ArrayList<String> initImgsPath() {
        ArrayList<String> imgPath = new ArrayList<>();
        // HLQ测试图片地址
//        imgPath.add("/storage/emulated/0/sougu/xlgg.png");
//        imgPath.add("/storage/emulated/0/sougu/2017010601.png");
//        imgPath.add("/storage/emulated/0/sougu/20170420102400.png");
//        imgPath.add("/storage/emulated/0/sougu/2017021701.png");
//        imgPath.add("/storage/emulated/0/DCIM/Camera/05227-1-13JW.png");
        // 飞姐测试图片地址
        imgPath.add("/storage/sdcard1/相册/子洋/1334979321390.jpg");
        imgPath.add("/storage/sdcard1/相册/子洋/1334979322442.jpg");
        imgPath.add("/storage/sdcard1/相册/子洋/1334979321730.jpg");
        imgPath.add("/storage/sdcard1/相册/子洋/1334979322676.jpg");
        imgPath.add("/storage/sdcard1/相册/子洋/1334979322962.jpg");
        return imgPath;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "打开图片失败~", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                // 获取图片路径
                oldFile = FileUtil.getTempFile(this, data.getData());
                switch (imageStatus) {
                    case "default":
                        // 设置获取图片显示ImageView
                        mImageOld.setImageBitmap(BitmapFactory.decodeFile(oldFile.getAbsolutePath()));
                        // 设置获取图片大小
                        mTextOld.setText(String.format("Size : %s", StringUtils.getReadableFileSize(oldFile.length())));
                        break;
                    case "weight":
                        // 设置获取图片显示ImageView
                        mWeightImageOld.setImageBitmap(BitmapFactory.decodeFile(oldFile.getAbsolutePath()));
                        // 设置获取图片大小
                        mWeightTextOld.setText(String.format("Size : %s", StringUtils.getReadableFileSize(oldFile.length())));
                        break;
                }
            } catch (IOException e) {
                Toast.makeText(this, "读取图片失败~", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
    }

    /**
     * 设置背景颜色
     *
     * @return
     */
    private int getRandomColor() {
        Random rand = new Random();
        return Color.argb(100, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

}
