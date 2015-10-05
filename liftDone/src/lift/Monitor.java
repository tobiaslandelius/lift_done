
package lift;

public class Monitor {

	private int here;
	private int next;
	private int direction;
	private int nbrOfPersons;
    private boolean isMoving;
	
	private int[] waitEntry;
	private int[] waitExit;
	private int load;
	private LiftView lv;

	public Monitor(LiftView liftView) {
		this.lv = liftView;
		direction = 1;
		waitEntry = new int[7];
		waitExit = new int[7];
	}

	private void drawLift(int from, int load) {
		lv.drawLift(from, load);
	}

	private void drawLevel(int level) {
		lv.drawLevel(level, waitEntry[level]);
	}

	public synchronized void makeTrip(int from, int to) {
		nbrOfPersons++;
		lv.drawLevel(from, ++waitEntry[from]);
		notifyAll();
		try {
			while (isMoving || from != here || load == 4 || waitExit[here] != 0) {
				wait();
			}
			load++;
			waitEntry[from]--;
			waitExit[to]++;
			drawLift(from, load);
			drawLevel(from);
			notifyAll();
			while (here != to || isMoving) {
				wait();
			}
			load--;
			waitExit[to]--;
			nbrOfPersons--;
			drawLift(to, load);
			drawLevel(here);
			notifyAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized int[] newFloor() {
        isMoving = false;
        here = next;
        if (here == 6) {
            direction = -1;
        } else if (here == 0) {
            direction = 1;
        }
        next += direction;
		notifyAll();
		try {
			while (waitExit[here] != 0 || (waitEntry[here] != 0 && load < 4) || nbrOfPersons == 0) {
				wait();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        int tempFrom = new Integer(here);
        int tempNext = new Integer(next);
        isMoving = true;
        int[] i = {tempFrom, tempNext};
        return i;
	}
}
