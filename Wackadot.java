package wackadot;

import fang2.core.*;
import fang2.sprites.*;
import fang2.attributes.*;
import fang2.transformers.*;
import java.math.*;

/**
 * All about my game here.
 * 
 * @author Zhi Qiao
 */
//package wackadot;
//
//public class PlayWackadot {
//	private static Wackadot[] levelArray = new Wackadot[5];
//    // this array for storing 5 levels.
//	
//	public static void main(String[] argv) {
//
//		for (int i = 0; i <= 4; i++) {
//
//			levelArray[i] = new Wackadot(i);
//
//			levelArray[i].runAsApplication();
//		}// can run 5 windows at a time
//
//	}
//}

public class Wackadot extends Game {

	private Sprite dot;
	//private Sprite redDot;
	//private Sprite blueDot;
	private StringSprite scoreSprite;
	private int score;
	private int timeLeft;
	private StringSprite timerSprite;
	private static int cumScore;// in order to make all the levels share the
								// same cumulative score, it should be set in
								// static.
	private static int[] scoreArray = new int[5]; // I used scoreArray to store
													// scores for each 5 levels.
	private final int xlevel;
	private int xblue;
	private int xred;
	int size;
	Sprite[] Red;// array for red dots
	Sprite[] Blue;// array for blue dots
	private static int count_level;

	public Wackadot(int level) {// this is the constructor I created with
								// parameter level

		size = (int) Math.pow(2, (level));// the size of dots is equal the
											// 2^(number of level)
		xlevel = count_level;
		Red = new Sprite[size];// here i call the number of red dots is equal to
								// the size of dots for each level.
		Blue = new Sprite[size];// similar to the line above.
		count_level++;

	}

	public void setup() {

		
		cumScore = 0;
		timeLeft = 10;
		makeSprites();
		addSprites();
		scheduleRelative(new TimeUpdater(), 1);

	}

	class TimeUpdater implements Alarm {
		public void act() {
			timeLeft--;
			updateTimer();
			if (timeLeft > 0) {
				scheduleRelative(this, 1);
			}
		}
	}

	private void makeSprites() {
		dot = new OvalSprite(0.1, 0.1);
		dot.setLocation(0.5, 0.5);
		dot.setColor(getColor("Red"));

		for (int i = 0; i < size; i++) {
			Red[i] = new OvalSprite(0.1, 0.1);
			Red[i].setLocation(randomDouble(), randomDouble());
			Red[i].setColor(getColor("red"));

			Blue[i] = new OvalSprite(0.1, 0.1);
			Blue[i].setLocation(randomDouble(), randomDouble());
			Blue[i].setColor(getColor("blue"));

		}

		scoreSprite = new StringSprite("Score: " + score);
		scoreSprite.setHeight(0.1);
		scoreSprite.rightJustify();
		scoreSprite.topJustify();
		scoreSprite.setLocation(1, 0);

		timerSprite = new StringSprite("Timer: " + timeLeft);
		timerSprite.leftJustify();
		timerSprite.topJustify();
		timerSprite.setHeight(0.1);
		timerSprite.setLocation(0, 0);
	}

	private void addSprites() {
		addSprite(dot);

		for (int i = 0; i < size; i++) {
			addSprite(Red[i]);
			addSprite(Blue[i]);
		}

		addSprite(scoreSprite);
		addSprite(timerSprite);
	}

	private void updateTimer() {
		timerSprite.setText("Timer: " + timeLeft);
	}

	private void repositionRandomly(Sprite sprite) {
		sprite.setLocation(randomDouble(), randomDouble());
	}

	private void updateScore() {
		scoreSprite.setText("Score: " + cumScore);// here i set the score at the
													// right side corner as
													// accumulative score.
		scoreArray[xlevel] = score; // I stored scores of 5 different levels in
									// scoreArray
		setHelpText("<p>Current score is " + score + "</p>"
				+ "<p>Accumulative score is " + cumScore + "</p>"
				+ "level 1 score" + scoreArray[0] + "<br>" + "level 2 score"
				+ scoreArray[1] + "<br>" + "level 3 score" + scoreArray[2]
				+ "<br>" + "level 4 score" + scoreArray[3] + "<br>"
				+ "level 5 score" + scoreArray[4] + "<br>");

		// in help menu displays the current level score, the accumulative score,
		// and also other scores which played on previous level from scoreArray.
	}

	private void handleCollisions() {
		for (int i = 0; i < size; i++) {
			if (dot.intersects(Blue[i])) {
				if (dot.getColor().equals(getColor("blue"))
						&& Blue[i].getColor().equals(getColor("blue"))) {
					/* making sure that the mouse dot is blue and the dots 
					 * being hit is also blue*/
					Blue[i].setColor(getColor("green"));// set the blue dots to
														// green after being hit
					xblue++;
					if (xblue == size) {/* when xblue is equal to the size, it
										 means all the blue has been hit, so
										 we add score(which has the same
										 amount as size).*/
						score = score + (xblue) + 1;
						cumScore = cumScore + (xblue) + 1;
						for (int k = 0; k < size; k++) {
							Blue[k].setColor(getColor("blue"));
							repositionRandomly(Blue[k]);
						}// end for loop j
						dot.setColor(getColor("red"));// change mouse dot color
														// to red after all the
														// blue dots been hit
						xblue = 0;// set xblue to 0 for the next round
					}
					updateScore();
				}
				if (dot.getColor().equals(getColor("red"))
						&& Blue[i].getColor().equals(getColor("blue")))
			     /* if red dot mouse hit blue dots.
				 */
				{
					xred = 0;// set xred to 0 for next attempt.
					repositionRandomly(Blue[i]);
					for (int j = 0; j < size; j++) {
						Red[j].setColor(getColor("red"));// set the green dots
															// back to red

					}

					score--; // minus score for hitting wrong color.
					cumScore--;

				}
				updateScore();
			}

			if (dot.intersects(Red[i])) {// similar to intersects with Blue[i]

				if (dot.getColor().equals(getColor("red"))
						&& Red[i].getColor().equals(getColor("red"))) {
					Red[i].setColor(getColor("green"));
					xred++;
					if (xred == size) {
						score = score + xred + 1;
						cumScore = cumScore + xred + 1;
						for (int j = 0; j < size; j++) {
							Red[j].setColor(getColor("red"));
							repositionRandomly(Red[j]);
						}
						dot.setColor(getColor("blue"));
						xred = 0;

					}

					updateScore();
				}
				if (dot.getColor().equals(getColor("blue"))
						&& Red[i].getColor().equals(getColor("red"))) {
					xblue = 0;
					repositionRandomly(Red[i]);
					for (int k = 0; k < size; k++) {
						Blue[k].setColor(getColor("blue"));
					}
					score--;
					cumScore--;

				}
				updateScore();
			} 

		}

	}

	public void advance() {

		if (timeLeft > 0) {
			if (score < 32) {// game only keeps running when score is less than 32
								
				Location2D mouse = getMouse2D();
				dot.setLocation(mouse);
				handleCollisions();

			}

			else
				timeLeft = 1;

		}
	}
}// end

