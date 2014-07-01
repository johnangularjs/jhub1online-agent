package test.org.jhub1.agent.run.cli;

import static org.junit.Assert.assertFalse;

import org.jhub1.agent.AgentSample;
import org.jhub1.agent.ChannelType;
import org.jhub1.agent.Sample;
import org.jhub1.agent.randoms.Randoms;
import org.junit.Test;

public class RandomsTest {

	@Test
	public void checkSHA1() {
		// For the same sample HASH is always going to be different
		Sample sample = new AgentSample(ChannelType.FILE, "MySample:223");
//		assertFalse(Randoms.getInstance().generateStrongHash(sample.weakHashCode()).equals(Randoms.getInstance().generateStrongHash(sample.weakHashCode())));
	}
	
}
