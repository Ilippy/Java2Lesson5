package Threads;

public class Main extends Thread {
	private static final String TIME_SPEND_ON_THREAD1 = "Время пересчета 1ой половины массива составило ";
	private static final String TIME_SPEND_ON_THREAD2 = "Время пересчета 2ой половины массива составило ";
	private static final int size = 10000000;
	private static final int h = size / 2;
	private static final String TIME_SPEND_ON_1_METHOD = "Время пересчета массива у первого метода сотавило ";
	private static final String TIME_SPEND_ON_2_METHOD = "Время разбивки,пересчета и склеивания массива у второго метода сотавило ";
	private static Thread thread1, thread2;

    public static void main(String[] args) {

		System.out.println(TIME_SPEND_ON_1_METHOD + arrayChange1());
		System.out.println(TIME_SPEND_ON_2_METHOD + arrayChange2());
	}

    private static long arrayChange1(){
		float[] array = new float[size];
		arraySetToOne(array);
		long a = System.currentTimeMillis();
		makeCalc(array);
		return System.currentTimeMillis() - a;
	}

	private static  long arrayChange2(){
		float[] array = new float[size];
		arraySetToOne(array);
		long a = System.currentTimeMillis();
		float[] firstHalfArray = new float[h];
		float[] secondHalfArray = new float[h];
		System.arraycopy(array, 0, firstHalfArray, 0, h);
		System.arraycopy(array, h, secondHalfArray, 0, h);
		thread1 = new Thread(() -> {
			long b = System.currentTimeMillis();
			makeCalc(firstHalfArray);
			b = System.currentTimeMillis() - b;
			System.out.println(TIME_SPEND_ON_THREAD1 +b);
		});
		thread2 = new Thread(() -> {
			long c = System.currentTimeMillis();
			makeCalc(secondHalfArray);
			c = System.currentTimeMillis() - c;
			System.out.println(TIME_SPEND_ON_THREAD2 +c);
		});
		thread1.start();
		thread2.start();
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.arraycopy(firstHalfArray, 0, array, 0, h);
		System.arraycopy(secondHalfArray, 0, array, h,h);


		return System.currentTimeMillis() - (a);
	}

	private static void makeCalc(float[] array){
		for (int i = 0; i < array.length; i++) {
			array[i] = (float)(array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
		}
	}

	private static void arraySetToOne(float[] array){
		for (int i = 0; i < array.length; i++) {
			array[i] = 1;

		}
	}

}


