package cz.koto.misak.kotipoint.android.mobile.entity;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.security.MessageDigest;

public class GalleryItem implements Parcelable {

    @SerializedName("url")
    String mUrl;

    //http://erlycoder.com/49/javascript-hash-functions-to-convert-string-into-integer-hash-
    @SerializedName("id")
    Long mId;

    public GalleryItem(String url, Long id){
        this.mUrl = url;
        this.mId = id;
    }

    protected GalleryItem(Parcel in) {
        mUrl = in.readString();
        mId = in.readLong();
    }

    public static final Creator<GalleryItem> CREATOR = new Creator<GalleryItem>() {
        @Override
        public GalleryItem createFromParcel(Parcel in) {
            return new GalleryItem(in);
        }

        @Override
        public GalleryItem[] newArray(int size) {
            return new GalleryItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mUrl);
        dest.writeLong(this.mId);
    }


    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }


    public Long getmId() {
        return mId;
    }

    public void setmId(Long mId) {
        this.mId = mId;
    }
}
