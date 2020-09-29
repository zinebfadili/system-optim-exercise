package mcp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CoreTests {
	
	Core core;
	List<Task> tasks;

	@Before
	public void setUp() throws Exception {
		tasks = new ArrayList<Task>();
		tasks.add(new Task(1, 1, 3L, 4L));
		tasks.add(new Task(2, 1, 4L, 5L));
		tasks.add(new Task(3, 2, 5L, 6L));
		tasks.add(new Task(4, 1, 10L, 11L));
		core = new Core(1, 1.0, tasks);
	}

	@Test
	public void testGetUnschedulable() {
		assertEquals(0, core.getUnschedulable());
	}

	@Test
	public void testGetWCRT() {
		assertEquals(10, core.getWCRT(3));
	}
	
}
