package mcp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class MCPTests {


	@Test
	public void getIdTest() {
		MCP mcp = new MCP(1);
		assertEquals(1, mcp.getId());
	}
	
	@Test
	public void getCoreTest() {
		MCP mcp = new MCP(1);
		mcp.addCore(new Core(1, 1.5));
		Core c = mcp.getCore(1);
		assertEquals(1, c.getId());
	}
	
	@Test
	public void getCoresTest() {
		MCP mcp = new MCP(1);
		mcp.addCore(new Core(1, 1.5));
		assertTrue(mcp.getCores().size() > 0);	
	}
	
	@Test
	public void getTotalLaxityTest() {
		//set up MCP0
		List<Core> mcp0cores = new ArrayList<Core>();
		mcp0cores.add(new Core(0, 1.1));
		mcp0cores.get(0).addTask(new Task(7, 2840, 80000L, 80000L));
		mcp0cores.add(new Core(1, 1.3));
		mcp0cores.get(1).addTask(new Task(0, 84, 40000L, 40000L));
		mcp0cores.add(new Core(2, 1.2));
		mcp0cores.get(2).addTask(new Task(8, 650, 10000L, 10000L));
		mcp0cores.add(new Core(3, 1.1));
		mcp0cores.get(3).addTask(new Task(1, 3200, 80000L, 80000L));
		MCP mcp0 = new MCP(0, mcp0cores);
		
		//set up MCP1
		List<Core> mcp1cores = new ArrayList<Core>();
		mcp1cores.add(new Core(0, 1.3));
		mcp1cores.get(0).addTask(new Task(5, 240, 5000L, 5000L));
		mcp1cores.add(new Core(1, 1.5));
		mcp1cores.get(1).addTask(new Task(6, 278, 20000L, 20000L));
		mcp1cores.add(new Core(2, 1.0));
		mcp1cores.get(2).addTask(new Task(2, 26, 10000L, 10000L));
		mcp1cores.get(2).addTask(new Task(3, 1247, 40000L, 40000L));
		mcp1cores.add(new Core(3, 1.4));
		mcp1cores.get(3).addTask(new Task(4, 192, 10000L, 10000L));
		MCP mcp1 = new MCP(1, mcp1cores);
		
		int totalLaxity = mcp0.getLaxity() + mcp1.getLaxity();
		
		assertEquals(285167, totalLaxity);
	}

}


