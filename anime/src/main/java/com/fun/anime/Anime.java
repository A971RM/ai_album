package com.fun.anime;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 动画资源实体对象
 */
public class Anime implements Serializable, Parcelable {

    private long id;                // 动画编号
    private int templateId;         // 动画模板编号
    private List<Frame> frames = new ArrayList<>();
    private int height;             // 画面高
    private int width;              // 画面宽
    private String videoPath;       // 合成输出视频路径
    private int backgroundType;     // 颜色1、图片2
    private String backgroundColor; // 颜色背景 "#FFFFFF"
    private String backgroundPath;  // 图片背景文件路径
    private float backgroundAlpha;  // 图片背景透明度  0-1
    private String musicName;       // 背景音乐名称
    private String musicAuthor;     // 背景音乐作者
    private String musicPath;       // 背景音乐路径
    private int musicOffset;        // 背景音乐播放起始位置（毫秒）
    private int musicDuration;      // 背景音乐时长（毫秒）
    private float musicVolume;      // 背景音乐音量 0-1.0
    private float duration;         // 动画时长（毫秒）
    private String coverPath;       // 封面图片地址
    private boolean showLogo;       // 是否显示水印
    private String logoPath;        // 水印图片地址

    public static final int BACKGROUND_TYPE_COLOR = 1;
    public static final int BACKGROUND_TYPE_IMAGE = 2;

    protected Anime(Parcel in) {
        id = in.readLong();
        templateId = in.readInt();
        frames = in.createTypedArrayList(Frame.CREATOR);
        height = in.readInt();
        width = in.readInt();
        videoPath = in.readString();
        backgroundType = in.readInt();
        backgroundColor = in.readString();
        backgroundPath = in.readString();
        backgroundAlpha = in.readFloat();
        musicName = in.readString();
        musicAuthor = in.readString();
        musicPath = in.readString();
        musicOffset = in.readInt();
        musicDuration = in.readInt();
        musicVolume = in.readFloat();
        duration = in.readFloat();
        coverPath = in.readString();
        showLogo = in.readByte() != 0;
        logoPath = in.readString();
    }

    public Anime() {
    }

    public static final Creator<Anime> CREATOR = new Creator<Anime>() {
        @Override
        public Anime createFromParcel(Parcel in) {
            return new Anime(in);
        }

        @Override
        public Anime[] newArray(int size) {
            return new Anime[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public List<Frame> getFrames() {
        return frames;
    }

    public void setFrames(List<Frame> frames) {
        this.frames = frames;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public int getBackgroundType() {
        return backgroundType;
    }

    public void setBackgroundType(int backgroundType) {
        this.backgroundType = backgroundType;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBackgroundPath() {
        return backgroundPath;
    }

    public void setBackgroundPath(String backgroundPath) {
        this.backgroundPath = backgroundPath;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getMusicAuthor() {
        return musicAuthor;
    }

    public void setMusicAuthor(String musicAuthor) {
        this.musicAuthor = musicAuthor;
    }

    public String getMusicPath() {
        return musicPath;
    }

    public void setMusicPath(String musicPath) {
        this.musicPath = musicPath;
    }

    public int getMusicOffset() {
        return musicOffset;
    }

    public void setMusicOffset(int musicOffset) {
        this.musicOffset = musicOffset;
    }

    public int getMusicDuration() {
        return musicDuration;
    }

    public void setMusicDuration(int musicDuration) {
        this.musicDuration = musicDuration;
    }

    public float getBackgroundAlpha() {
        return backgroundAlpha;
    }

    public void setBackgroundAlpha(float backgroundAlpha) {
        this.backgroundAlpha = backgroundAlpha;
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public void setMusicVolume(float musicVolume) {
        this.musicVolume = musicVolume;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public boolean isShowLogo() {
        return showLogo;
    }

    public void setShowLogo(boolean showLogo) {
        this.showLogo = showLogo;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(templateId);
        dest.writeTypedList(frames);
        dest.writeInt(height);
        dest.writeInt(width);
        dest.writeString(videoPath);
        dest.writeInt(backgroundType);
        dest.writeString(backgroundColor);
        dest.writeString(backgroundPath);
        dest.writeFloat(backgroundAlpha);
        dest.writeString(musicName);
        dest.writeString(musicAuthor);
        dest.writeString(musicPath);
        dest.writeInt(musicOffset);
        dest.writeInt(musicDuration);
        dest.writeFloat(musicVolume);
        dest.writeFloat(duration);
        dest.writeString(coverPath);
        dest.writeByte((byte) (showLogo ? 1 : 0));
        dest.writeString(logoPath);
    }
}
