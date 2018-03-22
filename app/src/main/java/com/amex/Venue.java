package com.amex;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by addie on 17-03-2018.
 */

public class Venue implements Parcelable {

    private String mAddress,mOffer,mTester;
    private double mLat,mLong;
private int mId;

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public Venue(String mAddress, String mOffer, String mTester, double mLat, double mLong, int mId) {
        this.mAddress = mAddress;
        this.mOffer = mOffer;
        this.mTester = mTester;
        this.mLat = mLat;
        this.mLong = mLong;
        this.mId = mId;
    }

    public Venue(String mAddress, String mOffer, String mTester, double mLat, double mLong) {
        this.mAddress = mAddress;
        this.mOffer = mOffer;
        this.mTester = mTester;
        this.mLat = mLat;
        this.mLong = mLong;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmOffer() {
        return mOffer;
    }

    public void setmOffer(String mOffer) {
        this.mOffer = mOffer;
    }

    public String getmTester() {
        return mTester;
    }

    public void setmTester(String mTester) {
        this.mTester = mTester;
    }

    public double getmLat() {
        return mLat;
    }

    public void setmLat(double mLat) {
        this.mLat = mLat;
    }

    public double getmLong() {
        return mLong;
    }

    public void setmLong(double mLong) {
        this.mLong = mLong;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mAddress);
        dest.writeString(this.mOffer);
        dest.writeString(this.mTester);
        dest.writeDouble(this.mLat);
        dest.writeDouble(this.mLong);
        dest.writeInt(this.mId);
    }

    protected Venue(Parcel in) {
        this.mAddress = in.readString();
        this.mOffer = in.readString();
        this.mTester = in.readString();
        this.mLat = in.readDouble();
        this.mLong = in.readDouble();
        this.mId = in.readInt();
    }

    public static final Creator<Venue> CREATOR = new Creator<Venue>() {
        @Override
        public Venue createFromParcel(Parcel source) {
            return new Venue(source);
        }

        @Override
        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };
}
