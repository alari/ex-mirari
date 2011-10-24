package mirari

import mirari.util.image.ImageCropPolicy

/**
 * @author Dmitry Kurinskiy
 * @since 24.10.11 13:20
 */
public enum ImageFormat {
  AVATAR_LARGE("210*336", ImageCropPolicy.CENTER, "ava-large"),
  AVATAR_MIDDLE("210*336", ImageCropPolicy.CENTER, "ava-middle"),
  AVATAR_TINY("210*336", ImageCropPolicy.CENTER, "ava-tiny");

  final String size
  final ImageCropPolicy cropPolicy
  final String name

  ImageFormat(String size, ImageCropPolicy cropPolicy, String name){
    this.size = size
    this.cropPolicy = cropPolicy
    this.name = name
  }

  String toString(){
    name
  }

  private static Map<String,ImageFormat> byName = [:]

  static {
    values().each {
      byName.put(it.name, it)
    }
  }

  static ImageFormat getByName(String name) {
    return byName.get(name)
  }
}