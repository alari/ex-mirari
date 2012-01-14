@Typed package mirari.model.strategy.content.internal

import eu.medsea.mimeutil.MimeType
import mirari.ko.UnitViewModel
import mirari.model.strategy.content.ContentData
import mirari.model.strategy.content.ContentHolder
import mirari.model.strategy.content.SoundType
import ru.mirari.infra.file.FileHolder

/**
 * @author alari
 * @since 1/6/12 5:59 PM
 */
class SoundContentStrategy extends FilesHolderContentStrategy{
    @Override
    protected Holder getFileHolder(ContentHolder unit) {
        Holder holder = super.getFileHolder(unit)
        //TODO: holder.filesBucket = "mirari-sound"
        holder
    }
    
    private Set<String> getSoundTypes(ContentHolder unit) {
        ContentData.SOUND_TYPES.getSetFrom(unit)
    }
    
    private void setSoundTypes(ContentHolder unit, Set<String> types) {
        ContentData.SOUND_TYPES.putTo(unit, types)
    }
    
    private List<String> getFileNames(ContentHolder unit) {
        List<String> files = []
        for (String s: getSoundTypes(unit)) {
            files.add(SoundType.forName(s).filename)
        }
        files
    }

    @Override
    void attachContentToViewModel(ContentHolder unit, UnitViewModel unitViewModel) {
        Map<String, String> params = [:]
        FileHolder holder = getFileHolder(unit)
        for (String s: getSoundTypes(unit)) {
            params.put(s, fileStorageService.getUrl(holder, SoundType.forName(s).filename))
        }
        unitViewModel.put("params", params)
    }

    @Override
    void setViewModelContent(ContentHolder unit, UnitViewModel unitViewModel) {
        void
    }

    @Override
    void setContentFile(ContentHolder unit, File file, MimeType type) {
        if(!isContentFileSupported(type)) return;
        FileHolder holder = getFileHolder(unit)
        SoundType soundType = SoundType.forName(type.subType)

        fileStorageService.store(file, holder, soundType.filename)
        
        Set<String> currentTypes = getSoundTypes(unit)
        currentTypes.add(soundType.name)
        setSoundTypes(unit, currentTypes)
    }

    @Override
    boolean isContentFileSupported(MimeType type) {
        if (type.mediaType != "audio" ) return false;
        SoundType.forName(type.subType) != null
    }

    @Override
    void saveContent(ContentHolder unit) {
        void
    }

    @Override
    void deleteContent(ContentHolder unit) {
        Holder holder = getFileHolder(unit)
        holder.fileNames = getFileNames(unit)
        fileStorageService.delete(holder)
        ContentData.SOUND_TYPES.putTo(unit, "")
    }
}
