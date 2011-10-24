package mirari

import mirari.util.image.ImageCropPolicy

/**
 * @author Dmitry Kurinskiy
 * @since 24.10.11 13:20
 */
public enum ImageFormat {
  AVATAR_LARGE("210*336", ImageCropPolicy.CENTER);

  final String size
  final ImageCropPolicy cropPolicy

  ImageFormat(String size, ImageCropPolicy cropPolicy){
    this.size = size
    this.cropPolicy = cropPolicy
  }
}