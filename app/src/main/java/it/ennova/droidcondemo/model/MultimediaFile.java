package it.ennova.droidcondemo.model;


import android.os.Parcel;
import android.os.Parcelable;

public class MultimediaFile implements Parcelable {
    private final String fullPath;
    private final String fileName;
    private final long fileSize;
    private final String containingFolder;

    public MultimediaFile(String fullPath, String fileName, String containingFolder, long fileSize) {
        this.fullPath = fullPath;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.containingFolder = containingFolder;
    }

    protected MultimediaFile(Parcel in) {
        fullPath = in.readString();
        fileName = in.readString();
        fileSize = in.readLong();
        containingFolder = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fullPath);
        dest.writeString(fileName);
        dest.writeLong(fileSize);
        dest.writeString(containingFolder);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MultimediaFile> CREATOR = new Creator<MultimediaFile>() {
        @Override
        public MultimediaFile createFromParcel(Parcel in) {
            return new MultimediaFile(in);
        }

        @Override
        public MultimediaFile[] newArray(int size) {
            return new MultimediaFile[size];
        }
    };

    public String getFullPath() {
        return fullPath;
    }

    public String getFileName() {
        return fileName;
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getContainingFolder() {
        return containingFolder;
    }
}
