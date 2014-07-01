package test.org.jhub1.agent.statistics;

import org.jhub1.agent.statistics.Registry;

public class RegistryCounterIncResetTestThread implements Runnable  {

	private static final String NAME = "TestingCounter";
	
	@Override
	public void run() {
	//	System.out.println("RUN");
		for(int i = 0; i<5; i++) {
		//	System.out.println(Registry.getInstance().getEventCounter(RegistryTestThread.class, NAME));
			synchronized (this) {
			checkResetCondition();
			Registry.getInstance().increaseEventCounter(RegistryCounterIncResetTestThread.class, NAME);
			}
		}
	}
	
	private void checkResetCondition() {
	//	System.out.println("REa");
		if(Registry.getInstance().getEventCounter(RegistryCounterIncResetTestThread.class, NAME) == 25L) {
			System.out.println("RESET - " + Thread.currentThread().getName());
			Registry.getInstance().resetEventCounter(RegistryCounterIncResetTestThread.class, NAME);
		}
	}

}
