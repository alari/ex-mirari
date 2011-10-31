@Typed package mirari.util.image

/**
 * @author alari
 * @since 10/31/11 11:31 PM
 */
class ImageSize {
    private static Map<String, ImageSize> bySize = [:]

    public final int width
    public final int height

    ImageSize(int width, int height) {
        this.width = width
        this.height = height
    }

    static ImageSize getBySize(String size) {
        if (bySize.containsKey(size)) {
            return bySize.get(size)
        }
        List<Integer> list = []
        for (String s: size.split(/[\*x]/)) {
            list.add Integer.parseInt(s)
        }
        bySize.put(size, new ImageSize(list[0], list[1]))
        return bySize.get(size)
    }
}
