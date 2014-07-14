package com.constant_therapy.animation;

import android.view.View;

public interface SwipeListener {
	 void onSwipeItem(boolean isRight, int pos);
	
     
     void onClickItem(int pos);
  
     
     
     void onLongClickItem(View view, int pos);
   
     
}
