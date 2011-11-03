@Typed package mirari.morphia.unit.single

import mirari.util.image.ImageFormat
import mirari.util.image.ImageCropPolicy

/**
 * @author alari
 * @since 10/27/11 8:27 PM
 */
class ImageUnit extends FileUnit {
    static final ImageFormat FORMAT_PAGE = new ImageFormat("600*500", "im-page-size", ImageCropPolicy.NONE)
}
