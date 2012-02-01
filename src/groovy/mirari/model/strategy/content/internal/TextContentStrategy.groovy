@Typed package mirari.model.strategy.content.internal

import org.pegdown.Extensions as PegOpt

import eu.medsea.mimeutil.MimeType
import mirari.infra.CleanHtmlService
import mirari.ko.UnitViewModel
import mirari.model.strategy.content.ContentHolder
import mirari.model.unit.UnitContent
import mirari.repo.UnitContentRepo
import org.pegdown.PegDownProcessor
import org.springframework.beans.factory.annotation.Autowired

/**
 * @author alari
 * @since 1/6/12 5:41 PM
 */
class TextContentStrategy extends InternalContentStrategy {
    @Autowired private UnitContentRepo unitContentRepo
    @Autowired private CleanHtmlService cleanHtmlService

    private ThreadLocal<PegDownProcessor> processorThreadLocal = new ThreadLocal<PegDownProcessor>();

    private PegDownProcessor getProcessor() {
        if (processorThreadLocal.get() === null) {
            processorThreadLocal.set(new PegDownProcessor(PegOpt.ALL))
        }
        processorThreadLocal.get()
    }

    @Override
    void attachContentToViewModel(ContentHolder unit, UnitViewModel unitViewModel) {
        unitViewModel.params.text = unit.content?.text
        unitViewModel.params.html = cleanHtmlService.clean processor.markdownToHtml(unitViewModel.params.text)
    }

    @Override
    void setViewModelContent(ContentHolder unit, UnitViewModel unitViewModel) {
        if (!unit.content) unit.content = new UnitContent()
        unit.content.text = unitViewModel.params.text
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
        if (unit.content) {
            unitContentRepo.save(unit.content)
        }
    }

    @Override
    void deleteContent(ContentHolder unit) {
        if (unit.content) {
            unitContentRepo.delete(unit.content)
            unit.content = null
        }
    }
}
