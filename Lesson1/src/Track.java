public class Track implements Barrier {
    private final float value;

    Track(float length) {
        this.value = length;
    }

    public float getValue() {
        return value;
    }
}
