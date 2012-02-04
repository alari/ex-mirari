package ru.mirari.infra.image;

/**
 * @author Dmitry Kurinskiy
 * @since 21.10.11 14:58
 */
public enum ImageCropPolicy {
    NONE(0),
    TOP_LEFT(5),
    TOP_CENTER(7),
    TOP_RIGHT(6),
    CENTER_LEFT(13),
    CENTER_RIGHT(14),
    CENTER(15),
    BOTTOM_LEFT(9),
    BOTTOM_RIGHT(10),
    BOTTOM_CENTER(11);

    private byte policy;

    ImageCropPolicy(int policy) {
        this.policy = (byte) policy;
    }

    boolean isNoCrop() {
        return policy == 0;
    }

    boolean isTop() {
        return (policy & 12) == 4;
    }

    boolean isBottom() {
        return (policy & 12) == 8;
    }

    boolean isLeft() {
        return (policy & 3) == 1;
    }

    boolean isRight() {
        return (policy & 3) == 2;
    }
}