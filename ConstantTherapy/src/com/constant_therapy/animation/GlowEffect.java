package com.constant_therapy.animation;

import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.view.View;

public class GlowEffect implements Runnable {
    private static final int MAX_ALPHA = 255;
    /**
     * time interval to reschedule a redraw.
     */
    private static final int MIN_TICK_MILLIS = 30;
    /**
     * The alpha of the drawable of this view will be adjusted for the glow effect
     */
    private View mGlowLayer;
 
    /**
     * the time, when the animation started. (Or should have started if we restart the glow animation)
     */
    private long mStartTimeMillis;
    /**
     * duration of the whole animation = glow increase + glow decrease
     */
    private int mDuration;
    /**
     * inner representation of the required alpha value. Used when the animation restarts.
     * We have to store it, because Drawable.getAlpha() doesn't exist.
     */
    private double mAlpha;
    /**
     * The time between each animation steps.
     */
    private int mTickMillis;
 
    public GlowEffect() {
    }
 
    public void glow(View glowLayer, int durationMillis) {
    	
        mGlowLayer = glowLayer;
        // correct duration time if the animation has been restarted and the previous is still in progress
        // we continue the animation from that point
        final long suspectedTimeFromStart = Math.round(mAlpha * mDuration / 2);
        mDuration = durationMillis;
 
        mStartTimeMillis = SystemClock.uptimeMillis() - suspectedTimeFromStart;
        mGlowLayer.setVisibility(View.VISIBLE);
        mGlowLayer.getBackground().setAlpha(0);
        mTickMillis = Math.max(MIN_TICK_MILLIS, durationMillis / MAX_ALPHA);
        mGlowLayer.postDelayed(this, mTickMillis);
    	
    }
    
    
    public void stopGlow(View glowLayer){
    	mGlowLayer = glowLayer;
    	mGlowLayer.clearAnimation();
    }
     
    @Override
    public void run() {
        boolean done = true;
        final Drawable who = mGlowLayer.getBackground();
 
        float normalized = (float) (SystemClock.uptimeMillis() - mStartTimeMillis) / mDuration;
        normalized = Math.min(normalized, 1.0f);
        done = normalized >= 1.0f;
 
        // linear increase for half of the duration, than linear decrease
        //noinspection MagicNumber
        mAlpha = (0.5 - Math.abs(normalized - 0.5f)) * 2.0f;
        int alpha = (int)Math.round(mAlpha * MAX_ALPHA);
 
        alpha = Math.max(0, alpha);
        alpha = Math.min(MAX_ALPHA, alpha);
 
        who.setAlpha(alpha);
        who.invalidateSelf();
 
        if (!done) {
            mGlowLayer.postDelayed(this, mTickMillis);
        } else {
            //mGlowLayer.setVisibility(View.GONE);
        }
    }
 
}
