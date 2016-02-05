package wackadot;

public class PlayWackadot {
	private static Wackadot[] levelArray = new Wackadot[5];
    // this array for storing 5 levels.
	
	public static void main(String[] argv) {

		for (int i = 0; i <= 4; i++) {

			levelArray[i] = new Wackadot(i);

			levelArray[i].runAsApplication();
		}// can run 5 windows at a time

	}
}
