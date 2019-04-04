package utility;

import java.util.Random;

public final class RandUtil{

	public static final Random RAND = new Random();

	public static int getFrom( int...nums){
		return nums[RAND.nextInt( nums.length)];
	}

	public static double getFrom( double...nums){
		return nums[RAND.nextInt( nums.length)];
	}

	@SafeVarargs
	public static <T> T getFrom( T...ts){
		return ts[RAND.nextInt( ts.length)];
	}

	public static int getPosNeg(){
		return RAND.nextBoolean() ? 1 : -1;
	}

	public static int getInt( int min, int max){
		return RAND.nextInt( max - min) + min;
	}

	public static int getInt( int max){
		return RAND.nextInt( max);
	}

	public static double getDouble( double min, double max){
		return RAND.nextDouble() * (max - min) + min;
	}

	public static double getDouble( double max){
		return RAND.nextDouble() * max;
	}

}
