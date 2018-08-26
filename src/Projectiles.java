import java.awt.Color;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Projectiles {
	/*
	 * =========================================================================
	 * Projectiles class consists of the two different bullets that the tank can
	 * fire and how they react to other objects in game
	 * =========================================================================
	 */
	private int x, y;
	private float speed;
	private boolean onScreen;
	private int spawnedBy;
	private int firePower;
	private int ricochetCount;
	GiffAnime bullet;
	GiffAnime upgradedBullet;
	private int threshold = 16;
	private boolean upgraded;

	public Projectiles(int posx, int posy) {
		x = posx;
		y = posy;
		speed = 5.0f;
		onScreen = false;
		spawnedBy = 0;
		ricochetCount = 1;
		bullet = new GiffAnime("deathBall_64__3.png", posx, posy, 64, 64, 60, 3);
		upgradedBullet = new GiffAnime("bullet_96_8.png", posx, posy, 96, 96, 60, 8);
		upgradedBullet.hide();
		upgraded = false;
	}

	// method that uses the EZ moveForward method to move bullet
	public void moveForward() {
		bullet.moveForward(speed);
		upgradedBullet.moveForward(speed);
		upgradedBullet.animate();
		bullet.animate();
	}

	// translates bullet to x and y
	public void translateTo(double x, double y) {
		bullet.translateTo(x, y);
		upgradedBullet.translateTo(x, y);
	}

	public int getX() {
		return bullet.getXCenter();
	}

	public int getY() {
		return bullet.getYCenter();
	}

	// so that rotation angle of the projectile is the same as the rotation
	// angle of the tank, so that ricohets work properly
	public void setAngle(double rotationValue) {
		bullet.rotateTo(rotationValue);
		upgradedBullet.rotateTo(rotationValue);
	}

	// returns angle of bullet
	public double getAngle() {
		return bullet.getRotation();
	}

	// gets all the edges
	public int getTopEdge() {
		if (upgraded) {
			return upgradedBullet.getYCenter() - upgradedBullet.getHeight() / 4;
		}
		return bullet.getYCenter() - bullet.getHeight() / 2;
	}

	public int getBottomEdge() {
		if (upgraded) {
			return upgradedBullet.getYCenter() + upgradedBullet.getHeight() / 4;
		}
		return bullet.getYCenter() + bullet.getHeight() / 2;
	}

	public int getRightEdge() {
		if (upgraded) {
			return upgradedBullet.getXCenter() + upgradedBullet.getWidth() / 4;
		}
		return bullet.getXCenter() + bullet.getWidth() / 2;
	}

	public int getLeftEdge() {
		if (upgraded) {
			return upgradedBullet.getXCenter() - upgradedBullet.getWidth() / 4;
		}
		return bullet.getXCenter() - bullet.getWidth() / 2;
	}

	// if you have power up then switch the graphics to upgraded bullet
	public void powerUp() {
		if (!upgraded) {
			upgraded = true;
			bullet.hide();
			upgradedBullet.show();
		}
	}

	// switch back to normal bullet after upgrade has worn out
	public void powerDown() {
		if (upgraded) {
			upgraded = false;
			bullet.show();
			upgradedBullet.hide();
		}
	}

	// sets off screen once the bullet has colided with something
	void setOffScreen(int worldHeight, int worldWidth, boolean hasCollided) {

		if (this.getBottomEdge() < -20) {
			onScreen = false;
		}

		if (this.getTopEdge() > worldHeight + 20) {
			onScreen = false;
		}

		if (this.getRightEdge() < -20) {
			onScreen = false;
		}

		if (this.getLeftEdge() > worldWidth + 20) {
			onScreen = false;
		}

		if (hasCollided) {
			onScreen = false;
		}
	}

	boolean isOnScreen() {
		return onScreen;
	}

	void setOnScreen() {
		onScreen = true;
	}

	void setSpawnedBy(int playerID) {
		spawnedBy = playerID;
	}

	int getSpawnedBy() {
		return spawnedBy;
	}

	// makes bullet more powerfull for upgrade
	void setFirePower(int power) {
		firePower = power;
	}

	// returns firePower
	int getFirePower() {
		return firePower;
	}

	// returns Ricochet Count
	int getRicochetCount() {
		return ricochetCount;
	}

	// able to set number of times a projectile Ricochets
	void resetRicochetCount(int value) {
		ricochetCount = value;
	}

	// makes Ricochet for outer edges
	void ricochet(int worldHeight, int worldWidth) {

		if (this.getBottomEdge() >= worldHeight && ricochetCount > 0) {
			bullet.rotateTo(180 - bullet.getRotation());
			upgradedBullet.rotateTo(180 - upgradedBullet.getRotation());
			speed = -speed;
			ricochetCount--;

		} else if (this.getLeftEdge() <= 0 && ricochetCount > 0) {
			bullet.rotateTo(360 - bullet.getRotation());
			upgradedBullet.rotateTo(360 - upgradedBullet.getRotation());
			speed = -speed;
			ricochetCount--;
		} else if (this.getRightEdge() >= worldWidth && ricochetCount > 0) {
			bullet.rotateTo(360 - bullet.getRotation());
			upgradedBullet.rotateTo(360 - upgradedBullet.getRotation());
			speed = -speed;
			ricochetCount--;
		} else if (this.getTopEdge() <= 0 && ricochetCount > 0) {
			bullet.rotateTo(540 - bullet.getRotation());
			upgradedBullet.rotateTo(540 - upgradedBullet.getRotation());
			speed = -speed;
			ricochetCount--;
		}
	}

	// this is a method that detects and collision and makes bullet Ricochet off
	// the inner walls
	void ObstacleRicochet(ArrayList<Integer> xS, ArrayList<Integer> yS) {
		for (int i = 0; i < xS.size(); i++) {
			int a = xS.get(i);
			int d = yS.get(i);
			if (this.getLeftEdge() + 20 <= a - threshold && this.getRightEdge() - 20 >= a - threshold
					&& getY() >= d - threshold && getY() <= d + threshold) {
				if (ricochetCount == 0) {
					onScreen = false;
					bullet.translateTo(-100, -100);
					upgradedBullet.translateTo(-100, -100);
				}
				if (ricochetCount > 0) {
					bullet.rotateTo(360 - bullet.getRotation());
					upgradedBullet.rotateTo(360 - upgradedBullet.getRotation());
					speed = -speed;
					ricochetCount--;
				}
			} else if (this.getLeftEdge() + 20 <= a + threshold && this.getRightEdge() - 20 >= a + threshold
					&& getY() >= d - threshold && getY() <= d + threshold) {
				if (ricochetCount == 0) {
					onScreen = false;
					bullet.translateTo(-100, -100);
					upgradedBullet.translateTo(-100, -100);
				}
				if (ricochetCount > 0) {
					bullet.rotateTo(360 - bullet.getRotation());
					upgradedBullet.rotateTo(360 - upgradedBullet.getRotation());
					speed = -speed;
					ricochetCount--;
				}
			} else if (this.getBottomEdge() - 20 >= d - threshold && this.getTopEdge() + 20 <= d - threshold
					&& getX() >= a - threshold && getX() <= a + threshold) {
				if (ricochetCount == 0) {
					onScreen = false;
					bullet.translateTo(-100, -100);
					upgradedBullet.translateTo(-100, -100);
				}
				if (ricochetCount > 0) {
					bullet.rotateTo(180 - bullet.getRotation());
					upgradedBullet.rotateTo(180 - upgradedBullet.getRotation());
					speed = -speed;
					ricochetCount--;
				}
			} else if (this.getTopEdge() + 20 <= d + threshold && this.getBottomEdge() - 20 >= d + threshold
					&& getX() >= a - threshold && getX() <= a + threshold) {
				if (ricochetCount == 0) {
					onScreen = false;
					bullet.translateTo(-100, -100);
					upgradedBullet.translateTo(-100, -100);
				}
				if (ricochetCount > 0) {
					bullet.rotateTo(540 - bullet.getRotation());
					upgradedBullet.rotateTo(540 - upgradedBullet.getRotation());
					speed = -speed;
					ricochetCount--;
				}
			}
		}
	}

	/*
	 * =====================================================================
	 * when projectile Ricochets velocity gets reset or else projectile will
	 * move the opposite way it should.
	 * =====================================================================
	 */
	void resetSpeed() {
		speed = Math.abs(speed);
	}

	public boolean isUpgraded() {
		return upgraded;
	}

}
