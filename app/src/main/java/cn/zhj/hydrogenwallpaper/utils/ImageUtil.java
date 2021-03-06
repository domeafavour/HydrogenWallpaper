package cn.zhj.hydrogenwallpaper.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ImageUtil {
    /**
     * 把两个位图覆盖合成为一个位图，上下拼接
     *
     * @param isBaseMax 是否以高度大的位图为准，true则小图等比拉伸，false则大图等比压缩
     * @return 拼接之后的图片
     */
    public static Bitmap combine2Images(Bitmap topBitmap, Bitmap bottomBitmap, boolean isBaseMax) {

        if (topBitmap == null || topBitmap.isRecycled()
                || bottomBitmap == null || bottomBitmap.isRecycled()) {
            return null;
        }
        int width ;
        if (isBaseMax) {
            width = topBitmap.getWidth() > bottomBitmap.getWidth() ? topBitmap.getWidth() : bottomBitmap.getWidth();
        } else {
            width = topBitmap.getWidth() < bottomBitmap.getWidth() ? topBitmap.getWidth() : bottomBitmap.getWidth();
        }
        Bitmap tempBitmapT = topBitmap;
        Bitmap tempBitmapB = bottomBitmap;

        if (topBitmap.getWidth() != width) {
            tempBitmapT = Bitmap.createScaledBitmap(topBitmap, width, (int) (topBitmap.getHeight() * 1f / topBitmap.getWidth() * width), false);
        } else if (bottomBitmap.getWidth() != width) {
            tempBitmapB = Bitmap.createScaledBitmap(bottomBitmap, width, (int) (bottomBitmap.getHeight() * 1f / bottomBitmap.getWidth() * width), false);
        }

        int height = tempBitmapT.getHeight() + tempBitmapB.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Rect topRect = new Rect(0, 0, tempBitmapT.getWidth(), tempBitmapT.getHeight());
        Rect bottomRect = new Rect(0, 0, tempBitmapB.getWidth(), tempBitmapB.getHeight());

        Rect bottomRectT = new Rect(0, tempBitmapT.getHeight(), width, height);

        canvas.drawBitmap(tempBitmapT, topRect, topRect, null);
        canvas.drawBitmap(tempBitmapB, bottomRect, bottomRectT, null);
        return bitmap;
    }

    public static Bitmap getColorImage(int width, int height, int color) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(color);
        return bitmap;
    }

    public static boolean save(Bitmap bitmap, String path) {
        final String imgName = System.currentTimeMillis() + ".jpg";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        if (file.exists()) {
            String imagePath = path + File.separator + imgName;
            try {
                File imageFile = new File(imagePath);
                FileOutputStream outStream = new FileOutputStream(imageFile);
                return bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }
    }
}
