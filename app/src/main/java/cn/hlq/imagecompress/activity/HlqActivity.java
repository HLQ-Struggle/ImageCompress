package cn.hlq.imagecompress.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
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

/**
 * 常见的压缩 create by heliquan at 2017年7月23日
 */
public class HlqActivity extends Activity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private TextView tvChooseSize, tvZLPic, tvCCPic, tvCYLPic;
    private ImageView ivChoosePic, ivZLPic, ivCCPic, ivCYLPic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hlq);
        initView();
    }

    private void initView() {
        tvChooseSize = (TextView) findViewById(R.id.id_hlq_choose_size);
        ivChoosePic = (ImageView) findViewById(R.id.id_hlq_choose_show);
        ivZLPic = (ImageView) findViewById(R.id.id_hlq_choose_zl_pic);
        tvZLPic = (TextView) findViewById(R.id.id_hlq_choose_zl_size);
        ivCCPic = (ImageView) findViewById(R.id.id_hlq_choose_cc_pic);
        tvCCPic = (TextView) findViewById(R.id.id_hlq_choose_cc_size);
        ivCYLPic = (ImageView) findViewById(R.id.id_hlq_choose_cyl_pic);
        tvCYLPic = (TextView) findViewById(R.id.id_hlq_choose_cyl_size);

        findViewById(R.id.id_hlq_choose_pic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });
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
                File newFile = FileUtil.getTempFile(this, data.getData());
                Bitmap bitmap = BitmapFactory.decodeFile(newFile.getAbsolutePath());
                ivChoosePic.setImageBitmap(bitmap);
                tvChooseSize.setText(String.format("选取图片大小 : %s", StringUtils.getReadableFileSize(newFile.length())));
                // 质量压缩
                Bitmap zlBitmap = NativeUtil.compressImageToBitmap(bitmap);
                ivZLPic.setImageBitmap(zlBitmap);
                tvZLPic.setText(String.format("压缩图片大小 : %s", StringUtils.getBitmapSize(zlBitmap)));
                // 尺寸压缩
                Bitmap ccBitmap = NativeUtil.compressBitmapToBitmap(bitmap);
                ivCCPic.setImageBitmap(ccBitmap);
                tvCCPic.setText(String.format("压缩图片大小 : %s", StringUtils.getBitmapSize(ccBitmap)));
                // 采样率压缩
                Bitmap cylBitmap = NativeUtil.compressBitmap(newFile.getAbsolutePath());
                ivCYLPic.setImageBitmap(cylBitmap);
                tvCYLPic.setText(String.format("压缩图片大小 : %s", StringUtils.getBitmapSize(cylBitmap)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
