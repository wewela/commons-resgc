package org.flowcomputing.commons.resgc;

import static org.testng.AssertJUnit.*;

import java.util.Random;

import org.testng.annotations.Test;

/**
 * ResCollectorTest is a test class which contains several test cases for the
 * purpose of validating part of functionalities of class ResCollector.
 * 
 * @author wg
 *
 */
public class ResCollectorTest {
	public class DoubleHolder extends ResHolder<Double> {

		public DoubleHolder(Double val) {
			super(val);
		}
	}

	private int count;

	/**
	 * Test a register function of collector to verify collector is able to
	 * register specified holder and release it properly.
	 * 
	 */
	@Test
	public void ResCollectorRegister() {

		Random random = new Random();

		count = 0;

		ResReclaim<Double> rr = new ResReclaim<Double>() {
			@Override
			public void reclaim(Double val) {
				System.out.println(String.format("%f has been reclaimed...",
						val));
				count--;
			}
		};

		ResCollector<DoubleHolder, Double> rc = new ResCollector<DoubleHolder, Double>(
				rr);

		for (int i = 0; i < 100; i++) {
			Double val = new Double(random.nextDouble() * 2000);
			rc.register(new DoubleHolder(val), val);
			count++;
		}
		assertTrue(count > 0);

		System.gc();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}

		assertTrue(count == 0);

	}

	/**
	 * Test a holder's destroy function that hosted by a collector. the holder
	 * will be forced to release its managed resource.
	 * 
	 */
	@Test
	public void ResCollectorDestroy() {

		Random random = new Random();

		count = 0;

		ResReclaim<Double> rr = new ResReclaim<Double>() {
			@Override
			public void reclaim(Double val) {
				System.out.println(String.format("%f has been reclaimed...",
						val));
				count--;
			}
		};

		ResCollector<DoubleHolder, Double> rc = new ResCollector<DoubleHolder, Double>(
				rr);

		for (int i = 0; i < 100; i++) {
			Double val = new Double(random.nextDouble() * 2000);
			DoubleHolder dh = new DoubleHolder(val);
			rc.register(dh, val);
			count++;
			dh.destroy();
		}

		assertTrue(count == 0);

	}
}
