package com.example.projetoiseaux.ui.share.NewShare.location;

import android.location.Address;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Locale;

public class ParcelAddr extends Address implements Parcelable {
    /**
     * Constructs a new Address object set to the given Locale and with all
     * other fields initialized to null or false.
     *
     * @param locale
     */
    public ParcelAddr(Locale locale) {
        super(locale);
    }

    // 重写describeContents()方法
    @Override
    public int describeContents() {
        return 0;
    }
    // 重写writeToParcel()方法
    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
//    // 创建一个Parcelable.Creator接口
//    public static final Parcelable.Creator<ParcelAddr> CREATOR = new Parcelable.Creator<ParcelAddr>(){
//        @Override
//        public ParcelAddr createFromParcel(ParcelAddr parcel) {
//            ParcelAddr person = new ParcelAddr();
//            person.mName = parcel.readString();
//            person.mAddess = parcel.readString();
//            return person;
//        }
//
//        @Override
//        public Person[] newArray(int i) {
//            return new Person[i];
//        }
//    };

}
