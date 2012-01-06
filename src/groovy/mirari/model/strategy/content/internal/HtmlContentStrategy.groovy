@Typed package mirari.model.strategy.content.internal

import eu.medsea.mimeutil.MimeType
import mirari.infra.CleanHtmlService
import mirari.ko.UnitViewModel
import mirari.model.strategy.content.ContentHolder
import mirari.model.unit.UnitContent
import mirari.repo.UnitContentRepo
import mirari.util.ApplicationContextHolder
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author alari
 * @since 1/6/12 5:41 PM
 */
class HtmlContentStrategy extends InternalContentStrategy{
    @Autowired private UnitContentRepo unitContentRepo
    @Autowired private CleanHtmlService cleanHtmlService

    @Override
    void attachContentToViewModel(ContentHolder unit, UnitViewModel unitViewModel) {
        unitViewModel.text = unit.content?.text
    }

    @Override
    void setViewModelContent(ContentHolder unit, UnitViewModel unitViewModel) {
        if (!unit.content) unit.content = new UnitContent()
        unit.content.text = unitViewModel.text
    }

    @Override
    void setContentFile(ContentHolder unit, File file, MimeType type) {
        void
    }

    @Override
    boolean isContentFileSupported(MimeType type) {
        return false
    }

    @Override
    void saveContent(ContentHolder unit) {
        if(unit.content) {
            unit.content.text = cleanHtmlService.clean(unit.content.text)
            unitContentRepo.save(unit.content)
        }
    }

    @Override
    void deleteContent(ContentHolder unit) {
        if(unit.content) {
            unitContentRepo.delete(unit.content)
            unit.content = null
        }
    }
}
