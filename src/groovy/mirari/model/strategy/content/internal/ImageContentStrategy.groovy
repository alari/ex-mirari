@Typed package mirari.model.strategy.content.internal

import eu.medsea.mimeutil.MimeType
import mirari.ko.UnitViewModel
import mirari.model.strategy.content.ContentHolder
import mirari.model.strategy.content.ImageFormats
import org.springframework.beans.factory.annotation.Autowired
import ru.mirari.infra.image.ImageFormat
import ru.mirari.infra.image.ImageHolder
import ru.mirari.infra.image.ImageStorageService

/**
 * @author alari
 * @since 1/6/12 6:30 PM
 */
class ImageContentStrategy extends InternalContentStrategy {
    @Autowired
    private ImageStorageService imageStorageService

    private ImageHolder getImageHolder(ContentHolder unit) {
        new ImgHolder(unit.stringId)
    }

    @Override
    void attachContentToViewModel(ContentHolder unit, UnitViewModel unitViewModel) {
        ImageHolder holder = getImageHolder(unit)
        unitViewModel.put "params", [
                srcPage: imageStorageService.getUrl(holder, ImageFormats.PAGE),
                srcFeed: imageStorageService.getUrl(holder, ImageFormats.FEED),
                srcMax: imageStorageService.getUrl(holder, ImageFormats.MAX),
                srcTiny: imageStorageService.getUrl(holder, ImageFormats.TINY)
        ]
    }

    @Override
    void setViewModelContent(ContentHolder unit, UnitViewModel unitViewModel) {
        void
    }

    @Override
    void setContentFile(ContentHolder unit, File file, MimeType type) {
        if (!isContentFileSupported(type)) return;
        imageStorageService.format(getImageHolder(unit), file)
    }

    @Override
    boolean isContentFileSupported(MimeType type) {
        type.mediaType == "image"
    }

    @Override
    void saveContent(ContentHolder unit) {
        void
    }

    @Override
    void deleteContent(ContentHolder unit) {
        imageStorageService.delete(getImageHolder(unit))
    }

    static public class ImgHolder implements ImageHolder {
        private final String unitId
        public final String imagesPath
        public final String imagesBucket = null//TODO: "mirari-images"

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
