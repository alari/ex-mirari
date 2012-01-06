package mirari.model.strategy.content.internal

import ru.mirari.infra.image.ImageStorageService
import mirari.util.ApplicationContextHolder
import mirari.model.Unit
import mirari.ko.UnitViewModel
import ru.mirari.infra.image.ImageHolder
import mirari.model.strategy.content.ContentStrategy
import ru.mirari.infra.image.ImageFormat
import mirari.model.strategy.content.ImageFormats
import eu.medsea.mimeutil.MimeType

/**
 * @author alari
 * @since 1/6/12 6:30 PM
 */
class ImageContentStrategy extends InternalContentStrategy{
    static private final ImageStorageService imageStorageService

    static {
        imageStorageService = (ImageStorageService)ApplicationContextHolder.getBean("imageStorageService")
    }
    
    private ImageHolder getImageHolder(Unit unit) {
        new ImgHolder(unit.stringId)
    }

    @Override
    void attachContentToViewModel(Unit unit, UnitViewModel unitViewModel) {
        ImageHolder holder = getImageHolder(unit)
        unitViewModel.put "params", [
                srcPage: imageStorageService.getUrl(holder, ImageFormats.PAGE),
                srcFeed: imageStorageService.getUrl(holder, ImageFormats.FEED),
                srcMax: imageStorageService.getUrl(holder, ImageFormats.MAX),
                srcTiny: imageStorageService.getUrl(holder, ImageFormats.TINY)
        ]
    }

    @Override
    void setViewModelContent(Unit unit, UnitViewModel unitViewModel) {
        void
    }

    @Override
    void setContentFile(Unit unit, File file, MimeType type) {
        if(!isContentFileSupported(type)) return;
        imageStorageService.format(getImageHolder(unit), file)
    }

    @Override
    boolean isContentFileSupported(MimeType type) {
        type.mediaType == "image"
    }

    @Override
    void saveContent(Unit unit) {
        void
    }

    @Override
    void deleteContent(Unit unit) {
        imageStorageService.delete(getImageHolder(unit))
    }
    
    static public class ImgHolder implements ImageHolder{
        private final String unitId
        public final String imagesPath
        public final String imagesBucket = null //TODO:"images"

        ImgHolder(String unitId) {
            this.unitId = unitId
            imagesPath = "i/".concat(unitId)
        }

        @Override
        String getImagesPath() {
            imagesPath
        }

        @Override
        String getImagesBucket() {
            imagesBucket
        }

        @Override
        List<ImageFormat> getImageFormats() {
            [
                    ImageFormats.MAX,
                    ImageFormats.PAGE,
                    ImageFormats.FEED,
                    ImageFormats.TINY,
            ]
        }

        @Override
        ImageFormat getDefaultImageFormat() {
            ImageFormats.FEED
        }
    }
}
