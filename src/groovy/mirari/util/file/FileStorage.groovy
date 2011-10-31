@Typed package mirari.util.file

/**
 * @author Dmitry Kurinskiy
 * @since 21.10.11 12:34
 */
public interface FileStorage {
    void store(File file, String path, String filename, String bucket)

    void delete(String path, String filename, String bucket)

    String getUrl(String path, String filename, String bucket)
}