package locon.circularanimationview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by sombose on 24/10/15.
 */
public class CircularView extends View {

    Context context;
    private int innerRadius;
    private int outerRadius;
    private RectF circleArc;
    private int fillColor;
    private int innerFillColor;
    private int blankColor;
    private int endAngle;
    private Paint innerCirclePaint;
    private Paint outerCirclePaint;

    public CircularView(Context context) {
        super(context);
        this.context = context;
    }

    public CircularView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs);
    }

    public CircularView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(attrs);
    }

    private void initPaint() {
//        innerCirclePaint = new Paint();
//        innerCirclePaint.setColor(innerFillColor);
        Log.d("Som", "init paint : " + fillColor);
        outerCirclePaint = new Paint();
        outerCirclePaint.setStyle(Paint.Style.FILL);
        outerCirclePaint.setColor(fillColor);
        outerCirclePaint.setAntiAlias(true);
    }

    public void init(AttributeSet attrs) {
        Log.d("Som", "init");
        setWillNotDraw(false);
        TypedArray attrsArray = getContext().obtainStyledAttributes(attrs, R.styleable.circleview);
        innerRadius = attrsArray.getInteger(R.styleable.circleview_inner_radius, 0);
        outerRadius = attrsArray.getInteger(R.styleable.circleview_outer_radius, 0);
        blankColor = attrsArray.getColor(R.styleable.circleview_blank_color, 16777215);
//        innerFillColor = attrsArray.getColor(R.color.white, getContext().getResources().getColor(R.color.white));
        fillColor = attrsArray.getColor(R.styleable.circleview_fill_color, 0);
        endAngle = attrsArray.getInteger(R.styleable.circleview_angle_end, 0);
        attrsArray.recycle();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        Log.d("Som", "" + outerRadius);
        Log.d("Som", "" + circleArc.width() + " " + circleArc.height());
        initPaint();
        canvas.translate(getMeasuredWidth() / 2 - outerRadius, 0);
        canvas.drawArc(circleArc, 270, endAngle, true, outerCirclePaint);
        canvas.restore();
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int measuredWidth = measureWidth(widthMeasureSpec);
        if (outerRadius == 0) // No radius specified.
        {                     // Lets see what we can make.
            // Check width size. Make radius half of available.
            outerRadius = measuredWidth / 2;
            int tempRadiusHeight = measureHeight(heightMeasureSpec) / 2;
            if (tempRadiusHeight < outerRadius)
                // Check height, if height is smaller than
                // width, then go half height as radius.
                outerRadius = tempRadiusHeight;
        }
        // Remove 2 pixels for the stroke.
        int circleDiameter = outerRadius * 2;
        // RectF(float left, float top, float right, float bottom)
        circleArc = new RectF(0, 0, circleDiameter, circleDiameter);
        int measuredHeight = measureHeight(heightMeasureSpec) + 20;
        measuredWidth = measuredWidth + 20;
//        int measuredWidth = measureHeight(widthMeasureSpec) + 50;
        setMeasuredDimension(measuredWidth, measuredHeight);
        Log.d("Som", "measuredHeight =>" + String.valueOf(measuredHeight) + "px measuredWidth => " + String.valueOf(measuredWidth) + "px");
    }

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 0;
        if (specMode == MeasureSpec.AT_MOST) {
            result = outerRadius * 2;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }

    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result = 0;
        if (specMode == MeasureSpec.AT_MOST) {
            result = specSize;
        } else if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        }
        return result;
    }

    public void setFillAngle(int angle) {
        this.endAngle = angle;
        invalidate();
        requestLayout();
    }
}
