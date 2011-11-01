@Typed package mirari.util.image

import org.springframework.beans.factory.annotation.Autowired
import mirari.util.file.FileStorage
import java.awt.image.BufferedImage
import javax.imageio.ImageIO

/**
 * @author alari
 * @since 11/1/11 1:10 PM
 */
class ImageStorage {
    @Autowired FileStorage fileStorage

    String getFilename(final ImageFormat format, final String filename = null) {
        (filename ?: format.name) + "." + format.type.toString()
    }

    void storeFormatted(final ImageFormat format, final File image, String path, String filename = null,
                       String bucket = null) {
        filename = getFilename (format, filename)
        fileStorage.store(image, path, filename, bucket)
    }

    void storeFormatted(final ImageFormat format, final BufferedImage image, String path, String filename = null,
                       String bucket = null) {
        File tmp = File.createTempFile(getClass().simpleName+format.name, "."+format.type.toString())
        ImageIO.write(image, format.type.toString(), tmp)
        storeFormatted(format, tmp, path, filename, bucket)
        tmp.delete()
    }

    void format(final ImageFormat format, final File image, String path, String filename = null,
                String bucket = null) {
        storeFormatted(format, format.formatToBuffer(image), path, filename, bucket)
    }

    void format(final ImageFormat format, final BufferedImage image, String path, String filename = null,
                String bucket = null) {
        storeFormatted(format, format.format(image), path, filename, bucket)
    }

    void formatAndDelete(List<ImageFormat> formats, File original, String path, String bucket = null) {
        List<ImageFormat> imageFormats = formats.sort()

        ImageFormat largest = imageFormats.pop()
        BufferedImage im = largest.formatToBuffer(original)

        for (ImageFormat f in imageFormats) {
            format(f, im, path, null, bucket)
        }
        storeFormatted(largest, im, path, null, bucket)
        original.delete()
    }

    String getUrl(ImageFormat format, String path, String filename = null, String bucket=null){
        fileStorage.getUrl(path, getFilename(format, filename), bucket)
    }
}
