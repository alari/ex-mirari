@Typed package mirari.model.unit.content.internal

import mirari.model.unit.content.ContentData
import mirari.model.unit.content.ContentHolder
import mirari.model.unit.content.SoundType
import mirari.vm.UnitVM
import ru.mirari.infra.file.FileHolder
import ru.mirari.infra.file.FileInfo

/**
 * @author alari
 * @since 1/6/12 5:59 PM
 */
class SoundContentStrategy extends FilesHolderContentStrategy {
    @Override
    protected Holder getFileHolder(ContentHolder unit) {
        Holder holder = super.getFileHolder(unit)
        holder.filesBucket = "mirarisounds"
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
    void attachContentToViewModel(ContentHolder unit, UnitVM unitViewModel) {
        Map<String, String> params = [:]
        FileHolder holder = getFileHolder(unit)
        for (String s: getSoundTypes(unit)) {
            params.put(s, fileStorageService.getUrl(holder, SoundType.forName(s).filename))
        }
        unitViewModel.params.putAll(params)
    }

    @Override
    void setViewModelContent(ContentHolder unit, UnitVM unitViewModel) {
        void
    }

    @Override
    void setContentFile(ContentHolder unit, FileInfo fileInfo) {
        if (!isContentFileSupported(fileInfo)) return;
        FileHolder holder = getFileHolder(unit)
        SoundType soundType = SoundType.forName(fileInfo.subType)

        fileStorageService.store(fileInfo.file, holder, soundType.filename)

        Set<String> currentTypes = getSoundTypes(unit)
        currentTypes.add(soundType.name)
        setSoundTypes(unit, currentTypes)

        unit.title = fileInfo.title
    }

    @Override
    boolean isContentFileSupported(FileInfo type) {
        if (type.mediaType != "audio") return false;
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
