@Typed package mirari.model.unit.content

import ru.mirari.infra.image.ImageCropPolicy
import ru.mirari.infra.image.ImageFormat
import ru.mirari.infra.image.ImageType

/**
 * @author alari
 * @since 1/6/12 6:36 PM
 */
class ImageFormats {
    static final ImageFormat MAX = new ImageFormat("1920*1920", "im-max", ImageCropPolicy.NONE, ImageType.JPG)
    static final ImageFormat PAGE = new ImageFormat("600*500", "im-page", ImageCropPolicy.NONE, ImageType.JPG)
    static final ImageFormat FEED = new ImageFormat("120*120", "im-feed", ImageCropPolicy.NONE, ImageType.JPG)
    static final ImageFormat THUMB = new ImageFormat("90*90", "im-thumb", ImageCropPolicy.CENTER, ImageType.JPG)
}
