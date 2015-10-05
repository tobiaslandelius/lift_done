package lift;

public class Main {

	public static void main(String[] args) {
		LiftView lv = new LiftView();
		Monitor monitor = new Monitor(lv);
		new Lift(monitor, lv).start();
		for (int i = 0; i < 20; i++) {
			new Person(monitor).start();;
		}
	}
}
