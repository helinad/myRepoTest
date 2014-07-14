package com.constant_therapy.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ShadowImageView extends ImageView {

    private static Paint paintShadow;
    private static Paint paintBackground;
    private static Paint paintBitmap;

    private boolean disableRandomRotation;

    public ShadowImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paintShadow     = new Paint();
        paintBackground = new Paint();
        paintBitmap     = new Paint();

        setLayerType(LAYER_TYPE_SOFTWARE, null);

        paintShadow.setColor(Color.WHITE);
        paintShadow.setShadowLayer(1.5f, 2.0f, 2.0f, Color.GRAY);
        paintShadow.setAntiAlias(true);

        paintBackground.setColor(Color.WHITE);
        paintBackground.setStyle(Paint.Style.FILL);
        paintBackground.setAntiAlias(true);

        paintBitmap.setFilterBitmap(true);
        paintBitmap.setAntiAlias(true);
    }

    public void setRandomRotationDisabled()
    {
        disableRandomRotation = false;
    }

    private int getRandomRotation()
    {
        return -2 + (int) (Math.random() * ((3 - (-2)) + 1));
    }

    private Rect getRectForSize(int x, int y, int width, int height)
    {
        return new Rect(x,y,width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }
        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }

        canvas.save();

       /* if(disableRandomRotation){
            canvas.rotate(2);
        } else {
            canvas.rotate(getRandomRotation());
        }*/

        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

        Rect background         = getRectForSize(3, 3, getWidth()-3, getHeight()-3);
        Rect imageContainer     = getRectForSize(4, 4, getWidth()-4, getHeight()-4);
        Rect backgroundShadow   = getRectForSize(3, 3, getWidth()-3, getHeight()-3);

        canvas.drawRect(background, paintBackground);
        canvas.drawRect(backgroundShadow, paintShadow);
        canvas.drawBitmap(bitmap, null, imageContainer, paintBitmap);

        canvas.restore();
    }
}