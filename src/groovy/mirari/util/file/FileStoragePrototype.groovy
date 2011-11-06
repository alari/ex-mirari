@Typed package mirari.util.file

/**
 * @author alari
 * @since 11/4/11 10:28 AM
 */
abstract class FileStoragePrototype implements FileStorage {


    String getUrl(final FileHolder holder, String filename) {
        getUrl(holder.filesPath, filename, holder.filesBucket)
    }


    void delete(final FileHolder holder, String filename = null) {
        if (filename) {
            delete(holder.filesPath, filename, holder.filesBucket)
        } else {
            for (String fn in holder.fileNames) {
                delete(holder.filesPath, fn, holder.filesBucket)
            }
        }
    }

    void store(final File file, final FileHolder holder, String filename) {
        store(file, holder.filesPath, filename, holder.filesBucket)
    }
}
