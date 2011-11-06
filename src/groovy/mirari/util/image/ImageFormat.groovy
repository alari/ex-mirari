@Typed package mirari.util.image

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import net.coobird.thumbnailator.Thumbnailator

/**
 * @author Dmitry Kurinskiy
 * @since 24.10.11 13:20
 */
class ImageFormat implements Comparable<ImageFormat> {
    private final static String TMP_PREFIX = "imageFile"
    private final static String DEFAULT_NAME = "image"

    final ImageCropPolicy cropPolicy
    final ImageType type
    final String name

    final ImageSize size

    ImageFormat(String maxSize, String name = DEFAULT_NAME, ImageCropPolicy cropPolicy = ImageCropPolicy.CENTER,
                ImageType type = ImageType.PNG) {

        this.size = ImageSize.getBySize(maxSize)
        this.cropPolicy = cropPolicy
        this.type = type
        this.name = name
    }

    ImageFormat(ImageSize maxSize, String name = DEFAULT_NAME, ImageCropPolicy cropPolicy = ImageCropPolicy.CENTER,
                ImageType type = ImageType.PNG) {

        this.size = maxSize
        this.cropPolicy = cropPolicy
        this.type = type
        this.name = name
    }

    String toString() {
        size.toString() + "-" + name + "." + type
    }

    void reformat(File original) {
        if (cropPolicy.isNoCrop()) {
            Thumbnailator.createThumbnail(original, original, size.width, size.height)
        } else {
            ImageIO.write(format(ImageIO.read(original)), type.toString(), original)
        }
    }

    File format(final File original) {
        File tmp = File.createTempFile(TMP_PREFIX + name, "." + type)

        if (cropPolicy.isNoCrop()) {
            Thumbnailator.createThumbnail(original, tmp, size.width, size.height)
            return tmp
        }

        ImageIO.write(format(ImageIO.read(original)), type.toString(), tmp)
        tmp
    }

    BufferedImage formatToBuffer(final File original) {
        BufferedImage image = ImageIO.read(original)
        format(image)
    }

    BufferedImage format(final BufferedImage original) {
        // Too small
        if (original.width <= size.width && original.height <= size.height) {
            return original
        }

        // No crop et al
        if (cropPolicy.isNoCrop()) {
            return Thumbnailator.createThumbnail(original, size.width, size.height)
        }

        // We need to deeply modify an image
        BufferedImage workingImage = original

        // At first we resize
        ImageSize resize = calcResizeForCrop(workingImage)
        workingImage = Thumbnailator.createThumbnail(workingImage, resize.width, resize.height)
        if (workingImage.width == size.width && workingImage.height == size.height) {
            return workingImage
        }

        // Then we crop
        int x0 = 0, y0 = 0
        int w = Math.min(size.width, workingImage.width);
        int h = Math.min(size.height, workingImage.height);

        if (workingImage.width > size.width) {
            // Too wide image
            if (cropPolicy.isLeft()) {
                x0 = 0
            } else if (cropPolicy.isRight()) {
                x0 = workingImage.width - w
            } else {
                x0 = Math.ceil((workingImage.width - w) / 2)
            }
        }
        if (workingImage.height > size.height) {
            // Too tall image
            if (cropPolicy.isTop()) {
                y0 = 0
            } else if (cropPolicy.isBottom()) {
                y0 = workingImage.height - h
            } else {
                y0 = Math.ceil((workingImage.height - h) / 2)
            }
        }

        // The job is done
        workingImage.getSubimage(x0, y0, w, h)
    }

    private ImageSize calcResizeForCrop(final BufferedImage image) {
        double yAspect = image.height / size.height
        double xAspect = image.width / size.width

        if (xAspect == yAspect) {
            return new ImageSize(size.width, size.height)
        }

        if (xAspect > yAspect) {
            return new ImageSize(image.width, size.height)
        } else {
            return new ImageSize(size.width, image.height)
        }

    }

    static public List<Integer> parseSize(String size) {
        List<Integer> list = []
        for (String s: size.split(/[\*x]/)) {
            list.add Integer.parseInt(s)
        }
        list
    }

    int compareTo(ImageFormat o) {
        return size.compareTo(o.size)
    }
}