package mirari.model.strategy.content.impl

import mirari.model.unit.UnitContent
import mirari.ko.UnitViewModel
import eu.medsea.mimeutil.MimeType
import mirari.model.Unit
import mirari.repo.UnitContentRepo
import mirari.util.ApplicationContextHolder
import mirari.infra.CleanHtmlService
import mirari.model.strategy.content.ContentStrategy

/**
 * @author alari
 * @since 1/6/12 5:41 PM
 */
class HtmlContentStrategy extends ContentStrategy{
    final private static UnitContentRepo unitContentRepo
    final private static CleanHtmlService cleanHtmlService
    
    static {
        unitContentRepo = (UnitContentRepo)ApplicationContextHolder.getBean("unitContentRepo")
        cleanHtmlService =  (CleanHtmlService)ApplicationContextHolder.getBean("cleanHtmlService")
    }
    
    @Override
    void attachContentToViewModel(Unit unit, UnitViewModel unitViewModel) {
        unitViewModel.text = unit.content?.text
    }

    @Override
    void setViewModelContent(Unit unit, UnitViewModel unitViewModel) {
        if (!unit.content) unit.content = new UnitContent()
        unit.content.text = unitViewModel.text
    }

    @Override
    void setContentFile(Unit unit, File file, MimeType type) {
        void
    }

    @Override
    boolean isContentFileSupported(MimeType type) {
        return false
    }

    @Override
    void saveContent(Unit unit) {
        if(unit.content) {
            unit.content.text = cleanHtmlService.clean(unit.content.text)
            unitContentRepo.save(unit.content)
        }
    }

    @Override
    void deleteContent(Unit unit) {
        if(unit.content) {
            unitContentRepo.delete(unit.content)
            unit.content = null
        }
    }
}
