@Typed package mirari.util.image

/**
 * @author Dmitry Kurinskiy
 * @since 21.10.11 14:58
 */
public enum ImageCropPolicy {
  TOP_LEFT(5),
  TOP_CENTER(7),
  TOP_RIGHT(6),
  CENTER_LEFT(13),
  CENTER_RIGHT(14),
  CENTER(15),
  BOTTOM_LEFT(9),
  BOTTOM_RIGHT(10),
  BOTTOM_CENTER(11);

  private byte policy

  ImageCropPolicy(int policy) {
    this.policy = policy
  }

  boolean isTop() {
    (policy & 12) == 4
  }

  boolean isBottom() {
    (policy & 12) == 8
  }

  boolean isLeft() {
    (policy & 3) == 1
  }

  boolean isRight() {
    (policy & 3) == 2
  }
}
