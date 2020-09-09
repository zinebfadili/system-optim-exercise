package mcp;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MCPTests {
	
	MCP mcp;

	@Before
	public void setUp() throws Exception {
		mcp = new MCP(1);
	}


	@Test
	public void getIdTest() {
		assertEquals(1, mcp.getId());
	}
	
	@Test
	public void getCoreTest() {
		mcp.addCore(new Core(1, 1.5));
		Core c = mcp.getCore(1);
		assertEquals(1, c.getId());
	}
	
	@Test
	public void getCoresTest() {
		mcp.addCore(new Core(1, 1.5));
		assertTrue(mcp.getCores().size() > 0);	
	}

}


