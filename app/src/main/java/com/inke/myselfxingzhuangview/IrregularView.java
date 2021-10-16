package com.inke.myselfxingzhuangview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * 第一种实现方式 A
 */
public class IrregularView extends View {
    private int width = -1;
    private int height = -1;
    private Bitmap bitmap;

    public IrregularView(Context context) {
        super(context);
    }

    public IrregularView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IrregularView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if(action != MotionEvent.ACTION_DOWN && action != MotionEvent.ACTION_UP) {
            return super.onTouchEvent(event);
        }

        float x = event.getX();
        float y = event.getY();
        Drawable background = getBackground();
        if(!(background instanceof BitmapDrawable)) {
            return super.onTouchEvent(event);
        }
        bitmap = ((BitmapDrawable)background).getBitmap();
        //判断，区域外无法获取颜色值
        if(null == bitmap || x < 0 || y < 0 || x >= width || y >= height) {
            return false;
        }
        //获取颜色值 透明=0
        int pixel = bitmap.getPixel((int) (x * bitmap.getWidth() / width), (int) (y * bitmap.getHeight() / height));
        if(pixel == Color.TRANSPARENT) {
            return false;
        }
        return super.onTouchEvent(event);
    }
}
