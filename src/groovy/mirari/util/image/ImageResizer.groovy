@Typed package mirari.util.image

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import net.coobird.thumbnailator.Thumbnailator
import org.apache.log4j.Logger

/**
 * @author Dmitry Kurinskiy
 * @since 21.10.11 15:06
 */
class ImageResizer {
  static private final Logger log = Logger.getLogger(ImageResizer)

  static File createResized(File originalImageFile, int maxWidth, int maxHeight, ImageType type = ImageType.PNG) {
    File tmp = File.createTempFile("imageFile", "." + type)
    Thumbnailator.createThumbnail(originalImageFile, tmp, maxWidth, maxHeight)
    tmp
  }

  static File createResized(File originalImageFile, String maxSize, ImageType type = ImageType.PNG) {
    List<Integer> size = parseSize(maxSize)
    int maxWidth = size[0]
    int maxHeight = size[1]
    createResized(originalImageFile, maxWidth, maxHeight, type)
  }

  static File createCropResized(File originalImageFile, String strictSize, CropPolicy cropPolicy = CropPolicy.CENTER, ImageType type = ImageType.PNG) {
    List<Integer> size = parseSize(strictSize)
    int maxWidth = size[0]
    int maxHeight = size[1]
    createCropResized(originalImageFile, maxWidth, maxHeight, cropPolicy, type)
  }

  static File createCropResized(File originalImageFile, int maxWidth, int maxHeight, CropPolicy cropPolicy = CropPolicy.CENTER, ImageType type = ImageType.PNG) {
    BufferedImage im = ImageIO.read(originalImageFile)
    double yAspect = im.height / maxHeight
    double xAspect = im.width / maxWidth

    if (xAspect != yAspect) {
      int x0, y0, w, h

      if (xAspect > yAspect) {
        // Too wide image
        y0 = 0
        h = im.height
        w = (yAspect/xAspect)*im.width

        if(cropPolicy.isLeft()) {
          x0 = 0
        } else if(cropPolicy.isRight()) {
          x0 = im.width - w
        } else {
          x0 = (im.width - w)/2
        }
      } else {
        // Too high image
        x0 = 0
        w = im.width
        h = (xAspect/yAspect)*im.height

        if(cropPolicy.isTop()) {
          y0 = 0
        } else if(cropPolicy.isBottom()) {
          y0 = im.height - h
        } else {
          y0 = (im.height - h)/2
        }
      }
      im = im.getSubimage(x0, y0, w, h)
    }
    File tmp = File.createTempFile("imageFile", "." + type)

    // Preparing input byte array stream
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(im, type.toString(), baos);
    InputStream is = new ByteArrayInputStream(baos.toByteArray());

    tmp.withOutputStream {
      Thumbnailator.createThumbnail(is, it, maxWidth, maxHeight)
    }
    tmp
  }

  static private List<Integer> parseSize(String size) {
    List<Integer> list = []
    for(String s : size.split(/[\*x]/)){
      list.add Integer.parseInt(s)
    }
    list
  }
}
