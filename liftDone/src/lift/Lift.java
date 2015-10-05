package lift;

public class Lift extends Thread {

	private final LiftView lv;
	private Monitor monitor;

	public Lift(Monitor monitor, LiftView lv) {
		this.monitor = monitor;
		this.lv = lv;
	}
	
	public void run() {
		while(true) {
			int[] i = monitor.newFloor();
			lv.moveLift(i[0], i[1]);
		}
	}
}
