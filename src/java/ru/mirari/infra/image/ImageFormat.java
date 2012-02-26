package ru.mirari.infra.image;

import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Dmitry Kurinskiy
 * @since 24.10.11 13:20
 */
public class ImageFormat implements Comparable<ImageFormat> {
    private final static String TMP_PREFIX = "imageFile";
    private final static String DEFAULT_NAME = "image";
    private final static ImageCropPolicy DEFAULT_CROP = ImageCropPolicy.CENTER;
    private final static ImageType DEFAULT_TYPE = ImageType.JPG;

    public final ImageCropPolicy cropPolicy;
    public final ImageType type;
    public final String name;

    final ImageSize size;

    public ImageFormat(String maxSize) {
        this(ImageSize.getBySize(maxSize));
    }

    public ImageFormat(String maxSize, String name) {
        this(ImageSize.getBySize(maxSize), name);
    }

    public ImageFormat(ImageSize maxSize) {
        this(maxSize, DEFAULT_NAME, DEFAULT_CROP, DEFAULT_TYPE);
    }

    public ImageFormat(ImageSize maxSize, String name) {
        this(maxSize, name, DEFAULT_CROP, DEFAULT_TYPE);
    }

    public ImageFormat(String maxSize, String name, ImageCropPolicy cropPolicy,
                       ImageType type) {
        this(ImageSize.getBySize(maxSize), name, cropPolicy, type);
    }

    public ImageFormat(ImageSize maxSize, String name, ImageCropPolicy cropPolicy,
                       ImageType type) {

        this.size = maxSize;
        this.cropPolicy = cropPolicy;
        this.type = type;
        this.name = name;
    }

    public String toString() {
        return size.toString() + "-" + name + "." + type;
    }

    public void reformat(File original) throws IOException {
        if (cropPolicy.isNoCrop()) {
            Thumbnailator.createThumbnail(original, original, size.width, size.height);
        } else {
            ImageIO.write(format(ImageIO.read(original)), type.toString(), original);
        }
    }

    public File format(final File original) throws IOException {
        File tmp = File.createTempFile(TMP_PREFIX + name, "." + type);

        if (cropPolicy.isNoCrop()) {
            Thumbnailator.createThumbnail(original, tmp, size.width, size.height);
            return tmp;
        }

        ImageIO.write(format(ImageIO.read(original)), type.toString(), tmp);
        return tmp;
    }

    public BufferedImage formatToBuffer(final File original) throws IOException {
        BufferedImage image = ImageIO.read(original);
        return format(image);
    }

    public BufferedImage format(final BufferedImage original) {
        // Too small to resize
        if (original.getWidth() <= size.width && original.getHeight() <= size.height) {
            return original;
        }

        // No crop et al
        if (cropPolicy.isNoCrop()) {
            return Thumbnailator.createThumbnail(original, size.width, size.height);
        }

        try {
            return Thumbnails.of(original)
                    .size(size.width, size.height)
                    .crop(cropPolicy.getPosition())
                    .asBufferedImage();
        } catch (IOException e) {
            System.err.println(e);
        }
        return null;
    }

    public int compareTo(ImageFormat o) {
        return size.compareTo(o.size);
    }
}