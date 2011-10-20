package mirari

import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import net.coobird.thumbnailator.Thumbnailator

/**
 * @author Dmitry Kurinskiy
 * @since 20.10.2011
 */
class ImageFileService {

  static transactional = false

  static final String TYPE_PNG = "png"
  static final String TYPE_JPG = "jpg"
  static final String TYPE_GIF = "gif"

  static final byte CROP_X_LEFT = 1
  static final byte CROP_X_CENTER = 3
  static final byte CROP_X_RIGHT = 2

  static final byte CROP_Y_TOP = 4
  static final byte CROP_Y_BOTTOM = 8
  static final byte CROP_Y_CENTER = 12

  /**
   * Creates a resized copy of an image that fits into maxWidth*maxHeight rectangle
   *
   * @param originalImageFile
   * @param maxWidth
   * @param maxHeight
   * @param type
   * @return
   */
  File createThumb(File originalImageFile, int maxWidth, int maxHeight, String type = TYPE_PNG) {
    File tmp = File.createTempFile("imageFile", "." + type)
    Thumbnailator.createThumbnail(originalImageFile, tmp, maxWidth, maxHeight)
    tmp
  }

  /**
   * Creates a resized copy of an image that is cropped to perfectly fit maxWidth*maxHeight rectangle
   *
   * @param originalImageFile
   * @param maxWidth
   * @param maxHeight
   * @param crop Use service constants CROP_* with bitwise operator "|"
   * @param type Use service constants TYPE_*
   * @return cropped image saved in a file
   */
  File createCroppedThumb(File originalImageFile, int maxWidth, int maxHeight, byte crop = CROP_X_CENTER | CROP_Y_CENTER, String type = TYPE_PNG) {
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

        if((crop & CROP_X_CENTER) == CROP_X_LEFT) {
          x0 = 0
        } else if((crop & CROP_X_CENTER) == CROP_X_RIGHT) {
          x0 = im.width - w
        } else {
          x0 = (im.width - w)/2
        }
      } else {
        // Too high image
        x0 = 0
        w = im.width
        h = (xAspect/yAspect)*im.height

        if((crop & CROP_Y_CENTER) == CROP_Y_TOP) {
          y0 = 0
        } else if((crop & CROP_Y_CENTER) == CROP_Y_BOTTOM) {
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
		ImageIO.write(im, type, baos);
    InputStream is = new ByteArrayInputStream(baos.toByteArray());

    tmp.withOutputStream {
      Thumbnailator.createThumbnail(is, it, maxWidth, maxHeight)
    }
    tmp
  }
}
