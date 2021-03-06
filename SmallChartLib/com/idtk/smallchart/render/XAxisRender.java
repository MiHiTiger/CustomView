package com.idtk.smallchart.render;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

import com.idtk.smallchart.data.XAxisData;

import java.text.NumberFormat;

/**
 * Created by Idtk on 2016/6/6.
 * Blog : http://www.idtkm.com
 * GitHub : https://github.com/Idtk
 */
public class XAxisRender extends Render {

    private Paint mPaint = new Paint();
    private XAxisData xAxisData = new XAxisData();
    private NumberFormat numberFormat;
    private PointF mPoint = new PointF();

    public XAxisRender(XAxisData xAxisData) {
        super();
        this.xAxisData = xAxisData;
        mPaint.setColor(xAxisData.getColor());
        mPaint.setTextSize(xAxisData.getTextSize());
        mPaint.setStrokeWidth(xAxisData.getPaintWidth());
        //设置小数点位数
        numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(xAxisData.getDecimalPlaces());
    }

    @Override
    public void drawGraph(Canvas canvas) {
//        canvas.drawText("XAxisRender",0,0,paint);
        canvas.drawLine(0,0,xAxisData.getAxisLength(),0,mPaint);

        //X轴
        for (int i=0;(xAxisData.getInterval()*i+xAxisData.getMinimum())<=xAxisData.getMaximum();i++){
            canvas.drawLine((float) (xAxisData.getInterval()*i*xAxisData.getAxisScale()),0,
                    (float) (xAxisData.getInterval()*i*xAxisData.getAxisScale()),
                    -xAxisData.getAxisLength()/100,mPaint);
            canvas.save();
            canvas.scale(1,-1);
            //根据Paint的TextSize计算X轴的值
            float TextPathX = (float) (xAxisData.getInterval()*i*xAxisData.getAxisScale());
            float TextPathY = (mPaint.descent()+mPaint.ascent())-xAxisData.getAxisLength()/100;
//            canvas.drawText(numberFormat.format(xAxisData.getInterval()*i+xAxisData.getMinimum()), TextPathX,-TextPathY,mPaint);
            mPoint.x = TextPathX;
            mPoint.y = -TextPathY;
            textCenter(new String[]{numberFormat.format(xAxisData.getInterval()*i+xAxisData.getMinimum())},
                    mPaint,canvas, mPoint, Paint.Align.CENTER);
            canvas.restore();
        }
        canvas.drawLine(xAxisData.getAxisLength(),0,xAxisData.getAxisLength()*0.99f,xAxisData.getAxisLength()*0.01f,mPaint);
        canvas.drawLine(xAxisData.getAxisLength(),0,xAxisData.getAxisLength()*0.99f,-xAxisData.getAxisLength()*0.01f,mPaint);
        canvas.save();
        canvas.scale(1,-1);
        canvas.drawText(xAxisData.getUnit(),xAxisData.getAxisLength(),(mPaint.descent()+mPaint.ascent())-xAxisData.getAxisLength()/100,mPaint);
        canvas.restore();
    }

}
