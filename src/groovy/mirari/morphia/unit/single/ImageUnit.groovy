@Typed package mirari.morphia.unit.single

import ru.mirari.image.ImageCropPolicy
import ru.mirari.image.ImageFormat
import ru.mirari.image.ImageHolder
import ru.mirari.image.ImageType

/**
 * @author alari
 * @since 10/27/11 8:27 PM
 */
class ImageUnit extends FileUnit implements ImageHolder {
    static final ImageFormat FORMAT_MAX = new ImageFormat("1920*1920", "im-max", ImageCropPolicy.NONE, ImageType.PNG)
    static final ImageFormat FORMAT_PAGE = new ImageFormat("600*500", "im-page", ImageCropPolicy.NONE, ImageType.PNG)
    static final ImageFormat FORMAT_FEED = new ImageFormat("160*160", "im-feed", ImageCropPolicy.NONE, ImageType.PNG)
    static final ImageFormat FORMAT_TINY = new ImageFormat("90*90", "im-tiny", ImageCropPolicy.CENTER, ImageType.PNG)

    String getImagesPath() {
        filesPath
    }

    String getImagesBucket() {
        filesBucket
    }

    List<ImageFormat> getImageFormats() {
        [
                FORMAT_PAGE,
                FORMAT_MAX,
                FORMAT_FEED,
                FORMAT_TINY,
        ]
    }

    ImageFormat getDefaultImageFormat() {
        FORMAT_FEED
    }
}
