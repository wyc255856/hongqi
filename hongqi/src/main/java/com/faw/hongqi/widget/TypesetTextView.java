/**
 * Project Name:TextViewDemo
 * File Name:TypesetTextView.java
 * Package Name:com.example.view
 * Date:2014-11-29����10:10:31
 * 
 *
*/

package com.faw.hongqi.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * ClassName:TypesetTextView 
 * Date:     2014-11-29 ����10:10:31 
 * @author   Lenovo
 * @version  
 * @since    JDK 1.7
 * @see 	 
 */
public class TypesetTextView extends TextView {


    private int mLineY;

    private int mViewWidth;

    public static final String TWO_CHINESE_BLANK = "  ";
    
    
    private StringBuffer mText;
    private StringBuffer newText =null;
    private Paint mPaint;
    /**VIEW�ĸ߶�*/
    private int mHeight = 0;
    /**�и�*/
    private static final int LINE_HEIGHT = 40;
    private int oneLine;//һ����ʾ���ָ���
    
    private int number_of_words;//��ʾ������
    public TypesetTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        
        String text = getText().toString();// ��ȡ�ı�����
        if(null==mText){//�Ե���ģʽ�����ֽ��в��
            mText = new StringBuffer(text);
            TextPaint paint = getPaint();//��ȡ����
            paint.setColor(getCurrentTextColor());// ��ȡ������ɫ�������õ�������
            paint.setTextSize(getTextSize());//�������ִ�С
            paint.setTypeface(getTypeface());//�������壬������������ͣ���ϸ��������б����ɫ��
            paint.drawableState = getDrawableState();
            mViewWidth = getMeasuredWidth();//��ȡ��д�����Ŀ�
            mPaint = paint;
            caculateChangeLine();//�����ֽ��з��д���
        }
        mLineY = getPaddingTop();//����ͷ���ڱ߾�
        mLineY += getTextSize();
        Layout layout = getLayout();//������ֿ���ͼ

        if (layout == null)
        {
            return;
        }
        Paint.FontMetrics fm = mPaint.getFontMetrics();

        int textHeight = (int) (Math.ceil(fm.descent - fm.ascent));
        textHeight = (int) (textHeight * layout.getSpacingMultiplier() + layout
                .getSpacingAdd());//��ȡ���ֵĸ߶�

        String[] split = newText.toString().split("\n");//���ָ�õ����ֽ����Ű�
        if(null!=split&&split.length>0){//�˴������ı���ʾ�ĸ߶ȣ�����һЩ�ֻ��޷���ʾ
            int i = (split.length)+3;//�������������Ա�����ʾ��ȫ
           int setheight= textHeight*i;
           setHeight(setheight);//����textview�߶�
        }
        
            for (int i = 0; i <split.length; i++)
            {
               //�˴�ΪԴ�����ϵ�д���������Ż������⻹�Ǵ���
//                layout.getLineCount()//��ȡ��ʾ������
//                int lineStart = layout.getLineStart(i);
//                int lineEnd = layout.getLineEnd(i);//��ȡÿ��Ҫ��ʾ������
                String string = split[i];
                float width = StaticLayout.getDesiredWidth(string, 0,
                        string.length(), getPaint());
                
                if(null==string||TextUtils.isEmpty(string)){
                    continue;
                }
                    int strWidth = (int) mPaint.measureText(string+"�ú�");//��֤�Ƿ��㹻һ����Ļ�Ŀ��
                    if (needScale(string)&&string.trim().length()>number_of_words-5&&mViewWidth<strWidth)//�ж��Ƿ��㹻һ����ʾ���������㹻�ý����ֵĴ�������ֱ�ӻ�����
                                                                            //��������������������ּ�౻���������ּ�����Ӱ���Ű�
                    {// �ж��Ƿ��β����Ҫ���У����Ҳ����ı����һ��
                        drawScaledText(canvas, getPaddingLeft(), split[i], width,i);
                    }
                    else
                    {

                        canvas.drawText(split[i], getPaddingLeft(), mLineY, mPaint);// ���ַ���ֱ�ӻ����ؼ���
                    }
                mLineY += textHeight;
            }


    }
    /**
    * @Description:�����һ����ʾ������
    * @param     �趨�ļ�
    */
    private String caculateOneLine(String str)
    {
        //��һ��û��\n�����ֽ��л���
        String returnStr = "";
        int strWidth = (int) mPaint.measureText(str);
        int len = str.length();
        int lineNum = strWidth/mViewWidth; //���֪���ֶ�����
        int tempWidth = 0;
        String lineStr;
        int returnInt = 0;
        if(lineNum == 0)
        {
            returnStr = str;
            mHeight += LINE_HEIGHT;
            return returnStr;
        }
        else
        {

            oneLine = len/(lineNum + 1);    //һ�д���ж��ٸ���
            if(number_of_words<oneLine){
                number_of_words=oneLine;
            }
            
            lineStr = str.substring(0, oneLine);
            tempWidth = (int) mPaint.measureText(lineStr);
            
            
            
            if(tempWidth < mViewWidth) //���С�� �ҵ�����Ǹ�
            {
                while(tempWidth < mViewWidth)
                {
                    oneLine++;
                    lineStr = str.substring(0, oneLine);
                    tempWidth = (int) mPaint.measureText(lineStr);
                }
                returnInt = oneLine - 1;
                returnStr = lineStr.substring(0, lineStr.length() - 2);
            }
            else//���ڿ��ҵ�С��
            {
                while(tempWidth > mViewWidth)
                {
                    oneLine--;
                    lineStr = str.substring(0, oneLine);
                    tempWidth = (int) mPaint.measureText(lineStr);
                }
                returnStr = lineStr.substring(0, lineStr.length() - 1);
                returnInt = oneLine;
            }
            mHeight += LINE_HEIGHT;
            returnStr += "\n" + caculateOneLine(str.substring(returnInt-1));
        }
        return returnStr;
    }
    
    public void caculateChangeLine()
    {
        newText=new StringBuffer();
        String tempStr[] = mText.toString().split("\n");
        int len = tempStr.length;
        for(int i=0;i<len;i++)
        {
            String caculateOneLine = caculateOneLine(tempStr[i]);
            if(!TextUtils.isEmpty(caculateOneLine)){
                newText.append(caculateOneLine);
                newText.append("\n");  
            }

        }
        this.setHeight(mHeight);
    }
        

    private void drawScaledText(Canvas canvas, int lineStart, String line,
                                float lineWidth, int currentline) {
        float x = 0;
        if (isFirstLineOfParagraph(lineStart, line))
        {// �ж��Ƿ��ǵ�һ��
            canvas.drawText(TWO_CHINESE_BLANK, x, mLineY, getPaint());
            float bw = StaticLayout.getDesiredWidth(TWO_CHINESE_BLANK, getPaint());
            x += bw;

            line = line.substring(3);
        }
        int gapCount = line.length() - 1;
        int i = 0;
        if (line.length() > 2 && line.charAt(0) == 12288
                && line.charAt(1) == 12288)
        {
            String substring = line.substring(0, 2);
            float cw = StaticLayout.getDesiredWidth(substring, getPaint());
            canvas.drawText(substring, x, mLineY, getPaint());
            x += cw;
            i += 2;
        }

        float d = (mViewWidth - lineWidth) / gapCount;
        for (; i < line.length(); i++)
        {
            String c = String.valueOf(line.charAt(i));
            float cw = StaticLayout.getDesiredWidth(c, getPaint());
            canvas.drawText(c, x, mLineY, getPaint());
            x += cw + d;
        }
    }

    private boolean isFirstLineOfParagraph(int lineStart, String line) {
        return line.length() > 3 && line.charAt(0) == ' '
                && line.charAt(1) == ' ';
    }

    private boolean needScale(String line) {// �ж��Ƿ���Ҫ����
        if (line == null || line.length() == 0)
        {
            return false;
        }
        else
        {
            char charAt = line.charAt(line.length() - 1);
            return charAt != '\n';
        }
    }


}

