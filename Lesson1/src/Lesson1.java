public class Lesson1 {
    public static void main(String[] args) {
        Human human = new Human(300, 2F);
        Robot robot = new Robot(500, 0.2F);
        Cat cat = new Cat(900, 3F);

        Master[] masters = {human, robot, cat};
        Barrier[] barriers = {new Wall(1.5F), new Track(450F)};

        for (Master master : masters) {
            for (Barrier barrier : barriers) {
                overcome(master, barrier);
            }
        }
    }

    public static void overcome(Master master, Barrier barrier) {
        if (barrier instanceof Track) {
            master.Run(barrier);
        } else if (barrier instanceof Wall) {
            master.Jump(barrier);
        }
    }
}
