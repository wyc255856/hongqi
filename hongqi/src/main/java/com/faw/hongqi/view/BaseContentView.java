package com.faw.hongqi.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.faw.hongqi.model.ContentItemModel;
import com.faw.hongqi.util.Constant;
import com.faw.hongqi.util.FileUtil;
import com.faw.hongqi.util.GlideRoundTransform;
import com.faw.hongqi.util.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class BaseContentView extends LinearLayout {

    public BaseContentView(Context context) {
        super(context);
    }

    public BaseContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void setContent(ContentItemModel data);

    public Bitmap getBitmap(Context mContext, String fileName) {
        Bitmap bitmap = null;
        AssetManager assetManager = mContext.getAssets();
        try {
            InputStream inputStream = assetManager.open(fileName);//filename是assets目录下的图片名
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }



    public void setHtmlText(TextView textView, String text) {

        textView.setText(Html.fromHtml(text));
    }

    //设置图片圆角角度
    RoundedCorners roundedCorners = new RoundedCorners(10);
    //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
    RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);

    public void setImage(Context mContext, ImageView imageView, String fileName) {
//        if(Constant.TEST){
//            Glide.with(this).load("file:///android_asset/" + fileName).into(imageView);
//        }else {
        String url = (FileUtil.getResPath() + fileName).replace("/HONGQIH9/standard", "");
        LogUtil.logError("file url = " + url);
        File file = new File(url);
            LogUtil.logError("file url = " + file.exists());
        if (file.exists()) {
//                Glide.with(mContext)
//                        .load(Uri.fromFile(file)).transform(new CenterCrop(), new GlideRoundTransform(mContext, 10))
//                        .into(imageView);
            LogUtil.logError("file url = " + file.exists());
            Glide.with(mContext)
                    .load(Uri.fromFile(file)).apply(new RequestOptions()
                    .transform(new GlideRoundTransform(mContext, 10))).into(imageView);
        }
//        }




//        Bitmap bitmap = getBitmap(mContext, fileName);
//        if (bitmap != null)
//            imageView.setImageBitmap(getBitmap(mContext, fileName));
    }
//    public void setLongImage(Context mContext, final ImageView imageView, String fileName) {
//
//        String url = (FileUtil.getResPath() + fileName).replace("/HONGQIH9/standard", "");
//        File file = new File(url);
//        LogUtil.logError("file url = " + url);
//////        if (file.exists()) {
//        LogUtil.logError("file url = " + file.exists());
//            Glide.with(mContext).asBitmap()
//                    .load(Uri.fromFile(file))
//                    .into(imageView);
//
////        LogUtil.logError("file url = " + imageView.getHeight()+"="+imageView.getMaxScale());
//
//
//////        }
//    }
    public void setLongImage(Context mContext, final SubsamplingScaleImageView imageView, String fileName) {

        String url = (FileUtil.getResPath() + fileName).replace("/HONGQIH9/standard", "");
        File file = new File(url);
        LogUtil.logError("file url = " + url);
////        if (file.exists()) {
            LogUtil.logError("file url = " + file.exists());
//            Glide.with(mContext).asBitmap()
//                    .load(Uri.fromFile(file))
//                    .into(imageView);

//        LogUtil.logError("file url = " + imageView.getHeight()+"="+imageView.getMaxScale());

        imageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        imageView.setZoomEnabled(false);
        imageView.setMinScale(0.98f);//最小显示比例
        imageView.setMaxScale(10.0f);//最大显示比例（太大了图片显示会失真，因为一般微博长图的宽度不会太宽）
        Glide.with(this)
                .load(Uri.fromFile(file)).downloadOnly(new SimpleTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                imageView.setImage(ImageSource.uri(Uri.fromFile(resource)), new ImageViewState(0.98f, new PointF(0, 0), 0));
            }
        });
////        }
    }

    @SuppressLint("WrongThread")
    private void setBitmapToImg(Bitmap resource, ImageView mImageView1) {
        try {
            Rect mRect = new Rect();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            resource.compress(Bitmap.CompressFormat.PNG, 100, baos);

            InputStream isBm = new ByteArrayInputStream(baos.toByteArray());

            //BitmapRegionDecoder newInstance(InputStream is, boolean isShareable)
            //用于创建BitmapRegionDecoder，isBm表示输入流，只有jpeg和png图片才支持这种方式，
            // isShareable如果为true，那BitmapRegionDecoder会对输入流保持一个表面的引用，
            // 如果为false，那么它将会创建一个输入流的复制，并且一直使用它。即使为true，程序也有可能会创建一个输入流的深度复制。
            // 如果图片是逐步解码的，那么为true会降低图片的解码速度。如果路径下的图片不是支持的格式，那就会抛出异常
            BitmapRegionDecoder decoder = BitmapRegionDecoder.newInstance(isBm, true);

            final int imgWidth = decoder.getWidth();
            final int imgHeight = decoder.getHeight();

            BitmapFactory.Options opts = new BitmapFactory.Options();

            //计算图片要被切分成几个整块，
            // 如果sum=0 说明图片的长度不足3000px，不进行切分 直接添加
            // 如果sum>0 先添加整图，再添加多余的部分，否则多余的部分不足3000时底部会有空白
            int sum = imgHeight / 3000;

            int redundant = imgHeight % 3000;

            List<Bitmap> bitmapList = new ArrayList<>();

            //说明图片的长度 < 3000
            if (sum == 0) {
                //直接加载
                bitmapList.add(resource);
            } else {
                //说明需要切分图片
                for (int i = 0; i < sum; i++) {
                    //需要注意：mRect.set(left, top, right, bottom)的第四个参数，
                    //也就是图片的高不能大于这里的4096
                    mRect.set(0, i * 3000, imgWidth, (i + 1) * 3000);
                    Bitmap bm = decoder.decodeRegion(mRect, opts);
                    bitmapList.add(bm);
                }

                //将多余的不足3000的部分作为尾部拼接
                if (redundant > 0) {
                    mRect.set(0, sum * 3000, imgWidth, imgHeight);
                    Bitmap bm = decoder.decodeRegion(mRect, opts);
                    bitmapList.add(bm);
                }

            }

            Bitmap bigbitmap = Bitmap.createBitmap(imgWidth, imgHeight, Bitmap.Config.ARGB_8888);
            Canvas bigcanvas = new Canvas(bigbitmap);

            Paint paint = new Paint();
            int iHeight = 0;

            //将之前的bitmap取出来拼接成一个bitmap
            for (int i = 0; i < bitmapList.size(); i++) {
                Bitmap bmp = bitmapList.get(i);
                bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
                iHeight += bmp.getHeight();

                bmp.recycle();
                bmp = null;
            }

            mImageView1.setImageBitmap(bigbitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
