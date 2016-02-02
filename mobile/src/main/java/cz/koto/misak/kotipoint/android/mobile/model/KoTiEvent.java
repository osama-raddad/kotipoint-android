package cz.koto.misak.kotipoint.android.mobile.model;



import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 "headline": "Bazárek v Praskolesích",
 "label":null,
 "eventDate":"2015-10-31T00:00:00.000Z",
 "eventLocation":"Praskolesy",
 "textCapital":"Chcete nabídnout nepotřebné věci?",
 "text": "Kulturní dům Jitřenka 8:00, prodejní poplatek za stůl je 20,-",
 "imageResource":""
 */
public class KoTiEvent implements Parcelable
{

    @SerializedName("id")
    Integer mId;

    @SerializedName("headline")
    String mHeadline;

    @SerializedName("label")
    List<String> mLabel;

    @SerializedName("eventDate")
    Date mEventDate;

    @SerializedName("eventLocation")
    List<String> mEventLocation;

    @SerializedName("textCapital")
    String mTextCapital;

    @SerializedName("text")
    String mText;

    @SerializedName("imageResource")
    String mImageResource;


    /**
     * This constructor should NOT be used for manual initialization!
     */
    @Deprecated
    public KoTiEvent() {
    }

    public KoTiEvent(Integer id, String headLine){
        this.mId = id;
        this.mHeadline = headLine;
    }

    public KoTiEvent(Parcel in){
        this.mId = in.readInt();
        this.mHeadline = in.readString();
        this.mLabel = new ArrayList<>();//in.readArrayList(); TODO fix this
        this.mEventDate = new Date(in.readLong());
        this.mEventLocation = new ArrayList<>();//TODO fix this
        this.mTextCapital = in.readString();
        this.mText = in.readString();
        this.mImageResource = in.readString();
    }

    public static final Creator<KoTiEvent> CREATOR
            = new Creator<KoTiEvent>() {

        public KoTiEvent createFromParcel(Parcel in) {
            return new KoTiEvent(in);
        }

        public KoTiEvent[] newArray(int size) {
            return new KoTiEvent[size];
        }
    };

    public Integer getmId() {
        return mId;
    }

    public void setmId(Integer mId) {
        this.mId = mId;
    }

    public String getmHeadline() {
        return mHeadline;
    }

    public void setmHeadline(String mHeadline) {
        this.mHeadline = mHeadline;
    }

    public List<String> getmLabel() {
        return mLabel;
    }

    public void setmLabel(List<String> mLabel) {
        this.mLabel = mLabel;
    }

    public Date getmEventDate() {
        return mEventDate;
    }

    public void setmEventDate(Date mEventDate) {
        this.mEventDate = mEventDate;
    }

    public List<String> getmEventLocation() {
        return mEventLocation;
    }

    public void setmEventLocation(List<String> mEventLocation) {
        this.mEventLocation = mEventLocation;
    }

    public String getmTextCapital() {
        return mTextCapital;
    }

    public void setmTextCapital(String mTextCapital) {
        this.mTextCapital = mTextCapital;
    }

    public String getmText() {
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmImageResource() {
        return mImageResource;
    }

    public void setmImageResource(String mImageResource) {
        this.mImageResource = mImageResource;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mHeadline);
        dest.writeList(this.mLabel);
        dest.writeLong(this.mEventDate == null?0:this.mEventDate.getTime());
        dest.writeList(this.mEventLocation);
        dest.writeString(this.mTextCapital);
        dest.writeString(this.mText);
        dest.writeString(this.mImageResource);
    }

    @Override
    public String toString() {
        return "KoTiEvent{" +
                "mId=" + mId +
                ", mHeadline='" + mHeadline + '\'' +
                ", mLabel=" + mLabel +
                ", mEventDate=" + mEventDate +
                ", mEventLocation=" + mEventLocation +
                ", mTextCapital='" + mTextCapital + '\'' +
                ", mText='" + mText + '\'' +
                ", mImageResource='" + mImageResource + '\'' +
                '}';
    }
}
