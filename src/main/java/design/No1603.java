package design;

/**
 * 1603. 设计停车系统
 *
 * @author Sean Yu
 */
public class No1603 {
}


/**
 * 因为只有停车这一个操作，所以不用考虑把停车后将车开走这种情况，所以只需要记录数字即可，不需要使用集合
 */
class ParkingSystem {
    private int bigCount;
    private int mediumCount;
    private int smallCount;
    private final int maxBig;
    private final int maxMedium;
    private final int maxSmall;

    public ParkingSystem(int big, int medium, int small) {
        maxBig = big;
        maxMedium = medium;
        maxSmall = small;
    }

    public boolean addCar(int carType) {
        switch (carType) {
            case 1: {
                if (bigCount == maxBig) {
                    return false;
                } else {
                    bigCount++;
                    return true;
                }
            }
            case 2: {
                if (mediumCount == maxMedium) {
                    return false;
                } else {
                    mediumCount++;
                    return true;
                }
            }
            case 3: {
                if (smallCount == maxSmall) {
                    return false;
                } else {
                    smallCount++;
                    return true;
                }
            }
            default: {
                return false;
            }
        }
    }
}

/**
 * Your ParkingSystem object will be instantiated and called as such:
 * ParkingSystem obj = new ParkingSystem(big, medium, small);
 * boolean param_1 = obj.addCar(carType);
 */

