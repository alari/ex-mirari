@Typed package mirari.util.file

/**
 * @author Dmitry Kurinskiy
 * @since 21.10.11 12:34
 */
public interface FileStorage {
    void store(final File file, String path, String filename, String bucket)

    void store(final File file, final FileHolder holder, String filename)

    void delete(String path, String filename, String bucket)

    void delete(final FileHolder holder, String filename)

    String getUrl(String path, String filename, String bucket)

    String getUrl(final FileHolder holder, String filename)
}