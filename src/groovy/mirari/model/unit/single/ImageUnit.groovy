@Typed package mirari.model.unit.single

import mirari.ko.UnitViewModel
import mirari.util.ApplicationContextHolder
import ru.mirari.infra.image.*

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
                FORMAT_TINY
        ]
    }

    ImageFormat getDefaultImageFormat() {
        FORMAT_FEED
    }

    UnitViewModel getViewModel() {
        UnitViewModel uvm = super.viewModel
        ImageStorageService imageStorageService = (ImageStorageService) ApplicationContextHolder.getBean("imageStorageService")
        uvm.put "params", [
                srcPage: imageStorageService.getUrl(this, FORMAT_PAGE),
                srcFeed: imageStorageService.getUrl(this, FORMAT_FEED),
                srcMax: imageStorageService.getUrl(this, FORMAT_MAX),
                srcTiny: imageStorageService.getUrl(this, FORMAT_TINY)
        ]
        uvm
    }
}
