package com.example.thwissa.fragment.entertainment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

public class RoundedImageView extends androidx.appcompat.widget.AppCompatImageView {

    public RoundedImageView(Context context) {
        super(context);
    }
    @SuppressLint("NonConstantResourceId")
    public RoundedImageView(Context context, AttributeSet attr) {
        super(context, attr);
    }
    public RoundedImageView(Context context, AttributeSet attr, int defStyleAttr) {
        super(context, attr,defStyleAttr);
    }



    @Override
    public void setImageDrawable(@Nullable Drawable drawable) {
        assert drawable != null;
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        int min = Math.min(bitmap.getHeight(), bitmap.getWidth());
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, min, min);
        RoundedBitmapDrawable rbd = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        rbd.setCircular(true);

        super.setImageDrawable(rbd);
    }
}
