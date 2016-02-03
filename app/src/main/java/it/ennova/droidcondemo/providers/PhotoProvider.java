package it.ennova.droidcondemo.providers;

import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;

import it.ennova.droidcondemo.exceptions.NoPhotosFoundException;
import it.ennova.droidcondemo.model.MultimediaFile;
import rx.Observable;
import rx.Subscriber;

public class PhotoProvider {

    private static final String CAMERA_IMAGE_BUCKET_NAME = Environment.getExternalStorageDirectory().toString() + "/DCIM/Camera";
    private static final String CAMERA_IMAGE_BUCKET_ID = getBucketId(CAMERA_IMAGE_BUCKET_NAME);
    private static final PhotoProvider instance = new PhotoProvider();

    private final String[] projection;
    private final String selection;
    private final String[] selectionArgs;

    private PhotoProvider() {
        projection = new String[]{ MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DATA,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_TAKEN,
                MediaStore.Images.Media.SIZE };
        selection = MediaStore.Images.Media.BUCKET_ID + " = ?";
        selectionArgs = new String[]{ CAMERA_IMAGE_BUCKET_ID };
    }

    public static PhotoProvider getInstance() {
        return instance;
    }

    /**
     * Matches code in MediaProvider.computeBucketValues. Should be a common
     * function.
     */
    private static String getBucketId(String path) {
        return String.valueOf(path.toLowerCase().hashCode());
    }

    private Cursor getCursorFrom(@NonNull Context context) throws NoPhotosFoundException{
        final Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);

        if (cursor == null || cursor.getCount() == 0) throw new NoPhotosFoundException();

        return cursor;
    }

    public Observable<MultimediaFile> getCameraImagesFrom(@NonNull final Context context) {
        return Observable.create((Observable.OnSubscribe<MultimediaFile>) subscriber -> onPhotoRequested(context, subscriber));
    }

    private void onPhotoRequested(@NonNull Context context,
                                  @NonNull Subscriber<? super MultimediaFile> subscriber) {
        try {
            final Cursor cursor = getCursorFrom(context);
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                int nameColumn = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
                int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);

                do {
                    String fullPath = cursor.getString(columnIndex);
                    String fileName = cursor.getString(nameColumn);
                    long fileSize = cursor.getLong(sizeColumn);

                    String containingFolder = fullPath;

                    containingFolder = containingFolder.replace(Environment.getExternalStorageDirectory().toString(), "");
                    containingFolder = containingFolder.replace(fileName, "");

                    subscriber.onNext(new MultimediaFile(fullPath, fileName, containingFolder, fileSize));
                } while (cursor.moveToNext());
            }
            cursor.close();
            subscriber.onCompleted();
        } catch (Exception e) {
            subscriber.onError(e);
        }
    }

}
