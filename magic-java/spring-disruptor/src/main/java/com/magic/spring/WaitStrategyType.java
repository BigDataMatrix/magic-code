package com.magic.spring;

import com.lmax.disruptor.*;

public enum  WaitStrategyType {

    /**
     * @see com.lmax.disruptor.BlockingWaitStrategy
     */
    BLOCKING {
        public WaitStrategy instance() {
            return new BlockingWaitStrategy();
        }
    },

    /**
     * @see com.lmax.disruptor.BusySpinWaitStrategy
     */
    BUSY_SPIN {
        public WaitStrategy instance() {
            return new BusySpinWaitStrategy();
        }
    },

    /**
     * @see com.lmax.disruptor.LiteBlockingWaitStrategy
     */
    LITE_BLOCKING {
        public WaitStrategy instance() {
            return new LiteBlockingWaitStrategy();
        }
    },

    /**
     * @see com.lmax.disruptor.SleepingWaitStrategy
     */
    SLEEPING_WAIT {
        public WaitStrategy instance() {
            return new SleepingWaitStrategy();
        }
    },

    /**
     * @see com.lmax.disruptor.YieldingWaitStrategy
     */
    YIELDING {
        public WaitStrategy instance() {
            return new YieldingWaitStrategy();
        }
    };

    abstract WaitStrategy instance();

}
