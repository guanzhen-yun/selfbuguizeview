package com.inke.myselfxingzhuangview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class IrregularDrawView extends View {
    private Paint paint;
    private int[] colors = {0xFFD21D22, 0xFFFBD109, 0xFF4BB748, 0xFF2F7ABB};
    private int width = -1;
    private int height = -1;
    private Bitmap bitmap;

    private int cx;
    private int cy;

    public IrregularDrawView(Context context) {
        this(context, null);
    }

    public IrregularDrawView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IrregularDrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        cx = width / 2;
        cy = height / 2;
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //创建一个新的Canvas
        Canvas canvasTemp = new Canvas(bitmap);

        int outerCr = 100;
        int innerCr = 50;
        RectF innerRectF = new RectF(cx - innerCr, cy - innerCr, cx + innerCr, cy + innerCr);
        RectF outerRectF = new RectF(cx - outerCr, cy - outerCr, cx + outerCr, cy + outerCr);

        Path path = new Path();
        //红色区域
        path.addArc(innerRectF, 150, 120); //顺正 逆负
        path.lineTo((float) (cx + outerCr * Math.sqrt(3) / 2), cy - innerCr);//上减下加  左减右加
        path.addArc(outerRectF, -30, -120);
        path.lineTo((float) (cx - innerCr * Math.sqrt(3) / 2), cy + innerCr / 2);
        paint.setColor(colors[0]);
        //
        canvasTemp.drawPath(path, paint);

        Matrix matrix = new Matrix();
        matrix.setRotate(120, cx, cy);

        //黄色区域
        path.transform(matrix);
        paint.setColor(colors[1]);
        canvasTemp.drawPath(path, paint);
        //绿色区域
        path.transform(matrix);
        paint.setColor(colors[2]);
        canvasTemp.drawPath(path, paint);
        //蓝色
        paint.setColor(Color.WHITE);
        canvasTemp.drawCircle(cx, cy, innerCr, paint);
        paint.setColor(colors[3]);
        canvasTemp.drawCircle(cx, cy, innerCr - 5, paint);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action != MotionEvent.ACTION_DOWN && action != MotionEvent.ACTION_UP) {
            return super.onTouchEvent(event);
        }

        float x = event.getX();
        float y = event.getY();

        //判断，区域外无法获取颜色值
        if(null == bitmap || x < 0 || y < 0 || x >= width || y >= height) {
            return false;
        }
        //获取颜色值 透明=0
        int pixel = bitmap.getPixel((int) (x * bitmap.getWidth() / width), (int) (y * bitmap.getHeight() / height));
        if(pixel == Color.TRANSPARENT) {
            return false;
        }else {
            //判断颜色值
            this.setTag(this.getId(), 3);
            for (int i = 0; i < colors.length; i++) {
                if(colors[i] == pixel) {
                    this.setTag(this.getId(), i);
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
