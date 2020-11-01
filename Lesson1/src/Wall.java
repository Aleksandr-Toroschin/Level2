public class Wall implements Barrier {
    private final float value;

    Wall(float height) {
        this.value = height;
    }

    public float getValue() {
        return value;
    }
}
