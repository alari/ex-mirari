@Typed package mirari.util.image

/**
 * @author Dmitry Kurinskiy
 * @since 21.10.11 14:53
 */
public enum ImageType {
    PNG("png"),
    JPG("jpg"),
    GIF("gif");

    private final String name

    ImageType(String name) {
        this.name = name
    }

    String toString() {
        name
    }
}