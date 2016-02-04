package cz.koto.misak.kotipoint.android.mobile.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import cz.koto.misak.kotipoint.android.mobile.KoTiPointServerConfig;

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


    /**
     * Return url for image, but without protocol!
     * This url is replaced for localhost flavour with specific IP!
     *
     * localhost:8080/public/gallery/2015-11-15-Racice/racice_003.png
     * 10.0.3.2:8080/public/gallery/2015-11-15-Racice/racice_001.png
     *
     * @return
     */
    public String getUrl() {
        return KoTiPointServerConfig.replaceUrl(mUrl);
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }


    public Long getId() {
        return mId;
    }

    public void setId(Long mId) {
        this.mId = mId;
    }

    @Override
    public String toString() {
        return "GalleryItem{" +
                "mUrl='" + mUrl + '\'' +
                "mUrlReplaced='" + KoTiPointServerConfig.replaceUrl(mUrl) + '\'' +
                ", mId=" + mId +
                '}';
    }
}
