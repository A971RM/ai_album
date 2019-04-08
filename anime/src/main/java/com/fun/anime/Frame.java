package com.fun.anime;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 动画画面内容实体对象
 */
public class Frame implements Serializable, Parcelable {

    private int type;                   // 画面类型
    private String imagePath;           // 画面图片路径
    private FrameText frameText;        // 画面文字
    private Float playTime;             // 播放时间

    public Frame() {
    }

    protected Frame(Parcel in) {
        type = in.readInt();
        imagePath = in.readString();
        frameText = in.readParcelable(FrameText.class.getClassLoader());
        if (in.readByte() == 0) {
            playTime = null;
        } else {
            playTime = in.readFloat();
        }
    }

    public static final Creator<Frame> CREATOR = new Creator<Frame>() {
        @Override
        public Frame createFromParcel(Parcel in) {
            return new Frame(in);
        }

        @Override
        public Frame[] newArray(int size) {
            return new Frame[size];
        }
    };

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public FrameText getFrameText() {
        return frameText;
    }

    public void setFrameText(FrameText frameText) {
        this.frameText = frameText;
    }

    public Float getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Float playTime) {
        this.playTime = playTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(type);
        dest.writeString(imagePath);
        dest.writeParcelable(frameText, flags);
        if (playTime == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeFloat(playTime);
        }
    }
}
