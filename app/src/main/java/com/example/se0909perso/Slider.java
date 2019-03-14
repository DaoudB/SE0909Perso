package com.example.se0909perso;

import android.support.v4.content.ContextCompat;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import java.text.AttributedCharacterIterator;

public class Slider extends View {

    //Constructeurs
    public Slider(Context context){
        super(context);
        init(context, null);
    }
    public  Slider(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context, null);
    }



    // constantes
    final static float DEFAULT_BAR_WIDTH = 10;
    final static float DEFAULT_BAR_LENGTH = 100;
    final static float DEFAULT_CURSOR_DIAMETER = 20;

    //Valeur du Slider
    private float mValue =0;
    private float mMin =0;
    private float mMax =100;


    //attributs de dimension
    private float mBarLength;
    private float mBarWidth;
    private float mCursorDiameter;

    private Paint mCursorPaint =null;
    private Paint mValueBarPaint =null;
    private Paint mBarPaint =null;

    // coloris des different element graphique
    private int mDisabledColor;
    private int mCursorColor;
    private int mBarColor;
    private int mValueBarColor;

    private boolean mEnable = true;

    //

    /**
     * transforme la valeur du slider en ratio entre 0 et 1
     * @param value : valeur du slider
     * @return ratio entre 0 et 1
     */

    private float valueToRatio (float value){
        return (value - mMin) / (mMax- mMin);
    }

    /**
     *
     * @param ratio : entre 0 et 1
     * @return value
     */
    private float ratioToValue (float ratio){
        return ratio * (mMax - mMin) +mMin;
    }

    /**
     *
     * @param value
     * @return
     */

    private Point toPos(float value){
        int x,y;
        x = (int) Math.max(mCursorDiameter,mBarWidth)/2 +getPaddingLeft();
        y = (int) (valueToRatio(value)*mBarLength+mCursorDiameter/2) +getPaddingTop();
        return new Point(x,y);
    }

    /**
     *
     * @param position
     * @return
     */
    private float toValue (Point position){
        float ratio;
        ratio = 1- (position.y - getPaddingTop()- mCursorDiameter /2)/ mBarLength;
        return (ratioToValue(ratio));
    }

    /**
     *
     * @param context
     * @param attrs
     */

    private void init(Context context, AttributeSet attrs){

        mBarLength = dpToPixel(DEFAULT_BAR_LENGTH);
        mCursorDiameter = dpToPixel(DEFAULT_CURSOR_DIAMETER);
        mBarWidth = dpToPixel(DEFAULT_BAR_WIDTH);


        mCursorPaint = new Paint();
        mBarPaint = new Paint();
        mValueBarPaint = new Paint();


        // Suppression aliasing

        mCursorPaint.setAntiAlias(true);
        mBarPaint.setAntiAlias(true);
        mValueBarPaint.setAntiAlias(true);

        mCursorPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mBarPaint.setStyle(Paint.Style.STROKE);
        mValueBarPaint.setStyle(Paint.Style.STROKE);


        mBarPaint.setStrokeCap(Paint.Cap.ROUND);


        mDisabledColor =
                ContextCompat.getColor(context, R.color.colorDisabled);
        mCursorColor = ContextCompat.getColor(context, R.color.colorAccent);
        mBarColor = ContextCompat.getColor(context, R.color.colorPrimary);
        mValueBarColor = ContextCompat.getColor(context, R.color.colorSecondary);

        if(mEnable) {
            mCursorPaint.setColor(mCursorColor);
            mBarPaint.setColor(mBarColor);
            mValueBarPaint.setColor(mValueBarColor);
        }else{
            mCursorPaint.setColor(mDisabledColor);
            mBarPaint.setColor(mDisabledColor);
            mValueBarPaint.setColor(mDisabledColor);
        }

        mBarPaint.setStrokeWidth(mBarWidth);
        mValueBarPaint.setStrokeWidth(mBarWidth);


    }


    /**
     *
     * @param valueInDp
     * @return
     */
    private float dpToPixel (float valueInDp){

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,valueInDp, getResources().getDisplayMetrics());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Point p1, p2;
        p1 = toPos(mMin);
        p2 = toPos(mMax);

        canvas.drawLine(p1.x, p1.y, p2.x, p2.y, mBarPaint);

    }
}