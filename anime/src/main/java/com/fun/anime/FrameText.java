package com.fun.anime;

import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 动画画面文字实体对象
 */
public class FrameText implements Serializable, Parcelable {

    private String fontName;            // 字体中文名称
    private String typeFacePath;        // 字体路径
    private String typeface;            // 字体名称
    private int textSize = 24;          // 字体大小
    private String textColor = "#ffffff"; // 字体颜色
    private boolean textBold = false;     // 字体是否加粗
    private String borderColor;         // 文字边框颜色
    private float borderWidth;          // 文字边框宽度
    private String text;                // 文字内容
    private Rect rect;                  // 定位文字位置
    private int showTemplateId;         // 显示特效模板

    public FrameText() {

    }

    protected FrameText(Parcel in) {
        fontName = in.readString();
        typeFacePath = in.readString();
        typeface = in.readString();
        textSize = in.readInt();
        textColor = in.readString();
        textBold = in.readByte() != 0;
        borderColor = in.readString();
        borderWidth = in.readFloat();
        text = in.readString();
        rect = in.readParcelable(Rect.class.getClassLoader());
        showTemplateId = in.readInt();
    }

    public static final Creator<FrameText> CREATOR = new Creator<FrameText>() {
        @Override
        public FrameText createFromParcel(Parcel in) {
            return new FrameText(in);
        }

        @Override
        public FrameText[] newArray(int size) {
            return new FrameText[size];
        }
    };

    public String getFontName() {
        return fontName;
    }

    public void setFontName(String fontName) {
        this.fontName = fontName;
    }

    public String getTypeFacePath() {
        return typeFacePath;
    }

    public void setTypeFacePath(String typeFacePath) {
        this.typeFacePath = typeFacePath;
    }

    public String getTypeface() {
        return typeface;
    }

    public void setTypeface(String typeface) {
        this.typeface = typeface;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public float getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(float borderWidth) {
        this.borderWidth = borderWidth;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Rect getRect() {
        return rect;
    }

    public void setRect(Rect rect) {
        this.rect = rect;
    }

    public boolean isTextBold() {
        return textBold;
    }

    public void setTextBold(boolean textBold) {
        this.textBold = textBold;
    }

    public int getShowTemplateId() {
        return showTemplateId;
    }

    public void setShowTemplateId(int showTemplateId) {
        this.showTemplateId = showTemplateId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fontName);
        dest.writeString(typeFacePath);
        dest.writeString(typeface);
        dest.writeInt(textSize);
        dest.writeString(textColor);
        dest.writeByte((byte) (textBold ? 1 : 0));
        dest.writeString(borderColor);
        dest.writeFloat(borderWidth);
        dest.writeString(text);
        dest.writeParcelable(rect, flags);
        dest.writeInt(showTemplateId);
    }
}
