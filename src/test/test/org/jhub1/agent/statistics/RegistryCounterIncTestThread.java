package test.org.jhub1.agent.statistics;

import org.jhub1.agent.statistics.Registry;

public class RegistryCounterIncTestThread implements Runnable  {

	public static final String NAME = "TestingCounter";
	
	@Override
	public void run() {
		for(int i = 0; i<5; i++) {
			Registry.getInstance().increaseEventCounter(RegistryCounterIncTestThread.class, NAME);
		}
	}
}
