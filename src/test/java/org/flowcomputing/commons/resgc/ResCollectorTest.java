package org.flowcomputing.commons.resgc;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;

import java.util.Random;

/**
 * ResCollectorTest is a test class which contains several test cases for the
 * purpose of validating part of functionalities of class ResCollector.
 * 
 * @author wg
 *
 */
public class ResCollectorTest {
    public class DoubleHolder extends ResHolder<Double, DoubleHolder> {

        public DoubleHolder(Double val) {
            super(val);
        }
    }

    private volatile int count;
    private Double[] m_vals = {0.0, 0.0, 0.0};

    /**
     * Test a register function of collector to verify collector is able to
     * register specified holder and release it properly.
     *
     */
    @Test
    public void ResCollectorRegister() {

        Random random = new Random();

        count = 0;
        DoubleHolder m_holder = null;

        ResReclaim<Double> rr = new ResReclaim<Double>() {
            @Override
            public void reclaim(ContextWrapper<Double> cw) {
                System.out.println(String.format("%f has been reclaimed...",
                        cw.getRes()));
                count--;
                for (Double val : m_vals) {
                  AssertJUnit.assertFalse(0 == val.compareTo(cw.getRes()));
                }
            }
        };

        ResCollector<DoubleHolder, Double> rc = new ResCollector<DoubleHolder, Double>(
                rr);

        ResReclaimContext rrctx= new ResReclaimContext(1);
        for (int i = 0; i < 500; ++i) {
            Double val = new Double(random.nextDouble() * 2000);
            DoubleHolder holder = new DoubleHolder(val);
            // rc.register(holder); /* with default reclaim context */
            rc.register(holder, rrctx); /* with a reclaim context */
            if (2 == i) {
                rc.unregister(holder);
                m_vals[0] = holder.get();
                System.out.println(String.format("** Holder #%d of value %f has been unregistered **", i, holder.get()));
            }
            if (6 == i) {
                holder.cancelAutoReclaim();
                m_vals[1] = holder.get();
                System.out.println(String.format("** Holder #%d of value %f has been cancelled to reclaim **", i, holder.get()));
            }
            if (9 == i) {
                m_holder = holder;
                m_vals[2] = holder.get();
                System.out.println(String.format("** Holder #%d of value %f has been hold by another variable **", i, holder.get()));
            }
            count++;
        }
        AssertJUnit.assertTrue(count > 0);

        rc.waitReclaimCoolDown(rc.WAITRECLAIMTIMEOUT);

        AssertJUnit.assertTrue(count == 3);
        //System.out.println(String.format("********** %f has been hold ***********", m_holder.get()));

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
            public void reclaim(ContextWrapper<Double> cw) {
                System.out.println(String.format("%f has been reclaimed...",
                        cw.getRes()));
                count--;
            }
        };

        ResCollector<DoubleHolder, Double> rc = new ResCollector<DoubleHolder, Double>(
                rr);

        for (int i = 0; i < 100; ++i) {
            Double val = new Double(random.nextDouble() * 2000);
            DoubleHolder dh = new DoubleHolder(val);
            rc.register(dh);
            ++count;
            dh.destroy();
        }

        AssertJUnit.assertTrue(count == 0);

    }
}
