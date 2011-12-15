@Typed package mirari.morphia.unit.single

import mirari.ko.UnitViewModel
import ru.mirari.infra.FileStorageService
import mirari.ApplicationContextHolder

/**
 * @author alari
 * @since 12/15/11 6:25 PM
 */
class AudioUnit extends FileUnit{
    private Set<String> soundTypes = []
    
    List<String> getFileNames() {
        soundTypes.collect {Type.forName(it).filename}
    }

    void attachMedia(Type type) {
        soundTypes.add(type.name)
    }
    
    UnitViewModel getViewModel() {
        UnitViewModel uvm = super.viewModel
        FileStorageService fileStorageService = (FileStorageService)ApplicationContextHolder.getBean("fileStorageService")
        Map<String,String> params = [:]
        for(String s : soundTypes) {
            params.put(s, fileStorageService.getUrl(this, Type.forName(s).filename))
        }
        uvm.put("params", params)
        uvm
    }
    
    static public enum Type {
        MP3("mpeg", "sound.mp3"),
        WEBM("webm", "sound.webm"),
        OGG("ogg", "sound.ogg");
        
        static private Map<String, Type> byName = [:]
        
        static {
            for(Type t in Type.values()) byName.put(t.name, t)
        }
        
        final private String name
        final private String filename
        
        private Type(String t, String fn) {
            name = t
            filename = fn
        }
        
        String getName() {
            name
        }
        
        String getFilename() {
            filename
        }
        
        static public Type forName(String name) {
            byName.get(name.toLowerCase())
        }
    }
}
