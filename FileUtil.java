import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by HUNGDH on 11/9/2016.
 */

public class FileUtils {

    private static FileUtils instance = null;

    private static Context mContext;

    private static final String APP_DIR = "HUNGDH";

    public static FileUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (FileUtils.class) {
                if (instance == null) {
                    mContext = context;
                    instance = new FileUtils();
                }
            }
        }
        return instance;
    }

    private FileUtils() {
        if (isSDCanWrite()) {
            creatSDDir(APP_DIR);
        }
    }

    public boolean isSDCanWrite() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)
                && Environment.getExternalStorageDirectory().canWrite()
                && Environment.getExternalStorageDirectory().canRead()) {
            return true;
        } else {
            return false;
        }
    }

    public File creatSDDir(String dirName) {
        File dir = new File(getLocalPath() + dirName);
        dir.mkdirs();
        return dir;
    }

    private static String getLocalPath() {
        String sdPath = null;
        sdPath = Environment.getExternalStorageDirectory() + "/";
        return sdPath;
    }

    public String getAppDirPath() {
        String path = null;
        if (getLocalPath() != null) {
            path = getLocalPath() + APP_DIR + "/";
        }
        return path;
    }

    public File createTempFile(String prefix, String extension) throws IOException {
        File file = new File(getAppDirPath() + "/" + prefix
                + System.currentTimeMillis() + extension);
        file.createNewFile();
        return file;
    }

    public File createFileWithName(String name, String extension) throws IOException {
        name = name.trim().replaceAll(" ", "_").replaceAll("[-+.^:,]", "");
        File file = new File(getAppDirPath() + "/" + name + extension);
        if (file.exists()) {
            file = new File(getAppDirPath() + "/" + name + "_" + System.currentTimeMillis() + extension);
        } else {
            file.createNewFile();
        }
        return file;
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);
        return file.delete();
    }

    public File[] getListFiles() {
        String path = Environment.getExternalStorageDirectory() + File.separator + APP_DIR;
        File f = new File(path);
        File[] files = f.listFiles();
        return files;
    }

    public static String getFileName(String fullName) {
        return fullName.substring(0, fullName.lastIndexOf("."));
    }

    public static String getFileType(String fullName) {
        return fullName.substring(fullName.lastIndexOf(".") + 1, fullName.length());
    }

    public static Date getFileLastModified(String pathFile) {
        File file = new File(pathFile);
        return new Date(file.lastModified());
    }
    
    public static String getLastModified(File file) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date(file.lastModified());
        return sdf.format(date);
    }

    public static String getFileSize(File file) {
        String value = null;
        long size = file.length() / 1024;//call function and convert bytes into Kb
        if (size >= 1024)
            value = size / 1024 + " MB";
        else
            value = size + " KB";
        return value;
    }
    
    public static void scanMedia(Context context, String filePath) {
        MediaScannerConnection.scanFile(
                context, new String[]{filePath}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("myLog", "Finished scanning " + path + " New row: " + uri);
                    }
                });
    }
    
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            e.printStackTrace();
            return contentUri.getPath();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
