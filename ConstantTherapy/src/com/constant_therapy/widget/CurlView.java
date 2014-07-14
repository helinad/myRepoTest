package com.constant_therapy.widget;



import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class CurlView extends View {
	// MyHandler myHandler = new MyHandler();
	private static int DEFAULT_FLIP_VALUE = 20;
	private static int FLIP_SPEED = 180;

	//private long mMoveDelay = 1000 / 30;
	private long mMoveDelay = 1;

	float xTouchValue = DEFAULT_FLIP_VALUE, yTouchValue = DEFAULT_FLIP_VALUE;;

	// String result = "";

	//
	class FlippingHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			//Log.i("Thong bao: ", "Clock Handler is still running");
			CurlView.this.flip();

		}

		public void sleep(long delayMillis) {

			this.removeMessages(0);

			sendMessageDelayed(obtainMessage(0), delayMillis);

		}
	}

	//

	//
	FlippingHandler flippingHandler;
	//
	int width;
	int height;

	float oldTouchX, oldTouchY;
	boolean flipping = false;
	boolean next;

	CurlPoint A, B, C, D, E, F;

	Bitmap visiblePage;
	Bitmap invisiblePage;
	Paint flipPagePaint;

	boolean flip = false;

	Context context;
	//
	int loadedPages = 0;
	long timeToLoad = 0;
	//

	// boolean loadingDone = false;

	boolean onloading = true;

	boolean onMoving = false;

	

	public CurlView(Context context, int width, int height) {
		super(context);
		this.context = context;
		this.width = width;
		this.height = height;
		
	}

	//

	//
	public void init(Bitmap page1, Bitmap page2) {

		//
		flippingHandler = new FlippingHandler();
		//
		flipPagePaint = new Paint();
		flipPagePaint.setColor(Color.GRAY);
		// flipPagePaint.setAntiAlias(true);
		// flipPagePaint.setStyle(Paint.Style.FILL);
		flipPagePaint.setShadowLayer(5, -5, 5, 0x99000000);
		A = new CurlPoint(10, 0);
		B = new CurlPoint(width, height);
		C = new CurlPoint(width, 0);
		D = new CurlPoint(0, 0);
		E = new CurlPoint(0, 0);
		F = new CurlPoint(0, 0);

		xTouchValue = yTouchValue = DEFAULT_FLIP_VALUE;
		visiblePage = page1;
		invisiblePage = page2;
		onMoving = false;
		flipping = false;

		//

		//
		loadData();

	}

	//
	private void loadData() {
		// listOfPages.add("this is my text");
		onloading = false;

	}
	//
	private View visibleView, invisibleView;
	//
	public void setView(View view1, View view2){
		visibleView = view1;
		invisibleView = view2;
		visibleView.setVisibility(VISIBLE);
		invisibleView.setVisibility(GONE);
		this.setVisibility(GONE);
	}

	//
	public void next(){
		visibleView.setVisibility(GONE);
		this.setVisibility(VISIBLE);
		flipping = true;
		next = true;		
		flip();
	}
	//
	public void pre(){
		swap2Page();
		visibleView.setVisibility(GONE);
		this.setVisibility(VISIBLE);
		flipping = true;
		next = false;
		xTouchValue = width-30;
		flip();
	}
	

	//
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		if (!onloading) {
			//
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:

				oldTouchX = event.getX();
				oldTouchY = event.getY();
				flip = true;
				if (oldTouchX > (width >> 1)) {
					xTouchValue = DEFAULT_FLIP_VALUE;
					yTouchValue = DEFAULT_FLIP_VALUE;
					// set invisible page's content
					//
					next = true;
				} else {
					// set invisible page's content
					// invisiblePage.setContent(index-1, null);
					//
					next = false;
					//
					swap2Page();
					xTouchValue = width;
					yTouchValue = DEFAULT_FLIP_VALUE;
				}
				break;
			case MotionEvent.ACTION_UP:
				if (onMoving) {
					xTouchValue = width - A.x;
					onMoving = false;
				}
				flipping = true;
				flip();
				break;
			case MotionEvent.ACTION_MOVE:
				// Log.i("Thong bao: ","x="+x+" y="+y);
				onMoving = true;
				//
				float xMouse = event.getX();
				float yMouse = event.getY();
				//
				xTouchValue -= (xMouse - oldTouchX) / 1;
				//
				yTouchValue -= yMouse - oldTouchY;
				//

				if (xMouse < oldTouchX) {
					if (!next) {
						flip = false;
					}
					next = true;

				} else {
					if (next) {
						flip = false;
					}
					next = false;
				}

				//
				oldTouchX = event.getX();
				oldTouchY = event.getY();

				this.invalidate();

				break;
			}

		}

		return true;
	}

	//

	//
	public void flip() {
		// neu chua load thi load va lat sang trang
		//
		if (flipping) {

			// if (now - mLastMove > mMoveDelay) {
			if (xTouchValue > width || xTouchValue < DEFAULT_FLIP_VALUE) {
				flipping = false;
				if (!flipping) {
					//
					if (next) {

						swap2Page();

					}
					flip = false;
					xTouchValue = DEFAULT_FLIP_VALUE;
					yTouchValue = DEFAULT_FLIP_VALUE;
					swap2View();

				}
				return;
			}
			if (next) {
				// new cd sang trai-> trang moi
				xTouchValue += FLIP_SPEED;
			} else {
				// neu cd sang phai-> trang cu
				xTouchValue -= FLIP_SPEED;

			}
			this.invalidate();
			// }
			// call hander
			flippingHandler.sleep(mMoveDelay);
		}
	}

	//

	@Override
	protected void onDraw(Canvas canvas) {

		// super.onDraw(canvas);
		width = getWidth();
		height = getHeight();

		//
		if (flipping) {
			pointGenerate(xTouchValue, width, height);
		} else {
			// pointGenerateII(xTouchValue, yTouchValue, width, height);
			pointGenerate(xTouchValue, width, height);
		}
		// First Page render
		Paint paint = new Paint();
		canvas.drawColor(Color.GRAY);
		canvas.drawBitmap(visiblePage, 0, 0, paint);

		// Second Page Render
		Path pathX = pathOfTheMask();
		canvas.clipPath(pathX);
		canvas.drawBitmap(invisiblePage, 0, 0, paint);
		canvas.restore();
		// Flip Page render

		Path pathX2 = pathOfFlippedPaper();
		canvas.drawPath(pathX2, flipPagePaint);
		//
		pathX = null;
		pathX2 = null;
		paint = null;
	}

	// float degress =0;
	//
	private Path pathOfTheMask() {
		Path path = new Path();
		path.moveTo(A.x, A.y);
		path.lineTo(B.x, B.y);
		path.lineTo(C.x, C.y);
		path.lineTo(D.x, D.y);
		path.lineTo(A.x, A.y);
		//

		return path;
	}

	//
	private Path pathOfFlippedPaper() {
		Path path = new Path();
		path.moveTo(A.x, A.y);
		path.lineTo(D.x, D.y);
		path.lineTo(E.x, E.y);
		path.lineTo(F.x, F.y);
		path.lineTo(A.x, A.y);
		return path;
	}

	//
	//
	private void pointGenerate(float distance, int width, int height) {
		float xA = width - distance;
		float yA = height;

		// float xB= width;
		// float yB= height;

		// float xC = width;
		// float yC = 0;

		float xD = 0;
		float yD = 0;
		//
		if (xA > width / 2) {
			xD = width;
			yD = height - (width - xA) * height / xA;
		} else {
			xD = 2 * xA;
			yD = 0;
		}
		//

		//
		double a = (height - yD) / (xD + distance - width);
		double alpha = Math.atan(a);
		double _cos = Math.cos(2 * alpha), _sin = Math.sin(2 * alpha);
		// E
		float xE = (float) (xD + _cos * (width - xD));
		float yE = (float) -(_sin * (width - xD));
		// F
		float xF = (float) (width - distance + _cos * distance);
		float yF = (float) (height - _sin * distance);
		//
		if (xA > width / 2) {
			xE = xD;
			yE = yD;
		}
		//
		A.x = xA;
		A.y = yA;
		D.x = xD;
		D.y = yD;
		E.x = xE;
		E.y = yE;
		F.x = xF;
		F.y = yF;
	}

	//
	float oldxF = 0, oldyF = 0;

	private void pointGenerateII(float xTouch, float yTouch, int width, int height) {
		//
		float yA = height;
		float xD = width;
		//

		//
		float xF = width - xTouch + 0.1f;
		float yF = height - yTouch + 0.1f;
		//
		if (A.x == 0) {
			xF = Math.min(xF, oldxF);
			yF = Math.max(yF, oldyF);
		}
		//
		float deltaX = width - xF;
		float deltaY = height - yF;
		//
		float BH = (float) (Math.sqrt(deltaX * deltaX + deltaY * deltaY) / 2);
		double tangAlpha = deltaY / deltaX;
		double alpha = Math.atan(tangAlpha);
		double _cos = Math.cos(alpha), _sin = Math.sin(alpha);
		//
		float xA = (float) (width - (BH / _cos));
		float yD = (float) (height - (BH / _sin));
		//

		xA = Math.max(0, xA);
		if (xA == 0) {
			// yF= Math.max(yF, height-(float) Math.sqrt(width*width-xF*xF));
			oldxF = xF;
			oldyF = yF;
		}
		//
		float xE = xD;
		float yE = yD;
		// if (xA > width / 2) {

		if (yD < 0) {
			xD = width + (float) (tangAlpha * yD);
			yE = 0;
			xE = width + (float) (Math.tan(2 * alpha) * yD);

		}
		//

		//
		A.x = xA;
		A.y = yA;
		D.x = xD;
		D.y = Math.max(0, yD);
		E.x = xE;
		E.y = yE;
		F.x = xF;
		F.y = yF;
	}

	//
	private void swap2Page() {
		Bitmap temp = visiblePage;
		visiblePage = invisiblePage;
		invisiblePage = temp;
		// stringSplitterInOrder();
		temp = null;
	}
	private void swap2View(){
		View t = visibleView;
		visibleView = invisibleView;
		invisibleView = t;
		t=null;
		//
		visibleView.setVisibility(VISIBLE);
		this.setVisibility(GONE);
	}

}
