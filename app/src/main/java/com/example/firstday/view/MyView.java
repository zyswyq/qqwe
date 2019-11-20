package com.example.firstday.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.example.firstday.R;

import static com.example.firstday.view.MyView.Shape.Circle;
import static com.example.firstday.view.MyView.Shape.Rectangle;
import static com.example.firstday.view.MyView.Shape.Square;


public class MyView extends View {

    private int width;
    private int height;

    public enum Shape {
        Circle, Rectangle, Square
    }

    private Enum myShape = Circle; // 图形
    private int myViewColor = Color.WHITE;
    private int myTextSize = 12;
    private int myTextColor = Color.BLACK;
    private String myTextStr = "hhhh";

    public void setMyShape(Enum myShape) {
        this.myShape = myShape;
        refreshCanvas();
    }

    public void setMyTextColor(int myTextColor) {
        this.myTextColor = myTextColor;
        refreshCanvas();
    }

    public void setMyTextStr(String myTextStr) {
        this.myTextStr = myTextStr;
        refreshCanvas();
    }

    public void setMyViewColor(int myViewColor) {
        this.myViewColor = myViewColor;
        refreshCanvas();
    }

    public void setMyTextSize(int myTextSize) {
        this.myTextSize = myTextSize;
        refreshCanvas();
    }

    private void refreshCanvas() {
        initPaint();
        invalidate();

    }

    public Enum getMyShape() {
        return myShape;
    }

    public int getMyTextColor() {
        return myTextColor;
    }

    public int getMyTextSize() {
        return myTextSize;
    }

    public int getMyViewColor() {
        return myViewColor;
    }

    public String getMyTextStr() {
        return myTextStr;
    }

    private Paint viewP;
    private TextPaint textP;

    private void initPaint() {
        textP = new TextPaint();
        viewP = new Paint();
        textP.setColor(myTextColor);
        textP.setTextSize(myTextSize);
        viewP.setColor(myViewColor);
    }

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.myView, defStyleAttr, 0);
        switch (array.getInt(R.styleable.myView_myShape, 0)) {
            case 0:
                myShape = Circle;
                break;
            case 1:
                myShape = Rectangle;
                break;
            case 2:
                myShape = Square;
                break;
        }
        myTextSize = array.getInt(R.styleable.myView_myTextSize, myTextSize);
        myTextColor = array.getInt(R.styleable.myView_myTextColor, myTextColor);
        myTextStr = array.getString(R.styleable.myView_myText);
        myViewColor = array.getInt(R.styleable.myView_myViewColor, myViewColor);
        array.recycle();
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (Circle.equals(myShape)) {
            canvas.drawCircle(width / 2, height / 2, Math.min(height, width) / 2, viewP);
        } else if (Rectangle.equals(myShape)) {
            Rect rect = new Rect(0, 0, width, height);
            canvas.drawRect(rect, viewP);
        } else if (Square.equals(myShape)) {

            Rect rect = new Rect(width / 2 - Math.min(width, height) / 2, height / 2 - Math.min(width, height) / 2, width / 2 + Math.min(width, height) / 2, height / 2 + Math.min(width, height) / 2);
            canvas.drawRect(rect, viewP);
        }
       /* @SuppressLint("DrawAllocation") StaticLayout myStaticLayout = new StaticLayout(myTextStr,  textP, canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        myStaticLayout.draw(canvas);*/

//        textP.breakText(myTextStr, true, width, null);
        //Text折行先没做
        float textWidtn = textP.measureText(myTextStr);
        if (textWidtn > width) {
            canvas.drawText(myTextStr, 0, height / 2 + myTextSize / 2, textP);
        } else {
            canvas.drawText(myTextStr, width / 2 - textWidtn / 2, height / 2 + myTextSize / 2, textP);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int minimumWidth = getSuggestedMinimumWidth();
        final int minimumHeight = getSuggestedMinimumHeight();

        width = getMyWidth(minimumWidth, widthMeasureSpec);
        height = getMyHeight(minimumHeight, heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int getMyHeight(int minimumHeight, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                minimumHeight = size;
                break;
            case MeasureSpec.EXACTLY:
                minimumHeight = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                minimumHeight = Math.max(minimumHeight, size);
                break;
        }
        return minimumHeight;
    }

    private int getMyWidth(int minimumWidth, int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                minimumWidth = size;
                break;
            case MeasureSpec.EXACTLY:
                minimumWidth = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                minimumWidth = Math.max(minimumWidth, size);
                break;
        }
        return minimumWidth;
    }
}
