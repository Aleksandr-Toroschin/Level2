public class Cat implements Master {
    private final String type;
    private final int runLength;
    private final float jumpHeight;
    private boolean active;

    Cat(int runLength, float jumpHeight) {
        this.runLength = runLength;
        this.jumpHeight = jumpHeight;
        this.type = "Кот";
        this.active = true;
    }

    @Override
    public void Jump(Barrier barrier) {
        if (!active) {
            System.out.println(type + " не может прыгать, т.к. сошел с дистанции.");
        } else if (barrier instanceof Wall) {
            if (((Wall)barrier).getValue()<=jumpHeight) {
                System.out.println(type + " прыгнул");
            } else {
                System.out.println(type + " не может прыгнуть на "+((Wall)barrier).getValue());
                active = false;
            }
        }
    }

    @Override
    public void Run(Barrier barrier) {
        if (!active) {
            System.out.println(type + " не может бежать, т.к. сошел с дистанции.");
        } else if (barrier instanceof Track) {
            if (((Track)barrier).getValue()<=runLength) {
                System.out.println(type + " пробежал");
            } else {
                System.out.println(type + " не может пробежать "+((Track)barrier).getValue());
                active = false;
            }
        }
    }
}
