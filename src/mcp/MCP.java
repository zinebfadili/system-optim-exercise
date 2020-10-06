package mcp;

import java.util.ArrayList;
import java.util.List;

public class MCP {
	
	private int id;
	
	private List<Core> cores;
	
	public MCP(int id) {
		this.id = id;
		cores = new ArrayList<Core>();
	}
	
	public MCP(int id, List<Core> cores) {
		this.id = id;
		this.cores = cores;
	}

	public int getId() {
		return id;
	}
	
	public List<Core> getCores() {
		return cores;
	}
	
	public boolean addCore(Core c) {
		return cores.add(c);
	}
	
	public Core getCore(int id) {
		return cores.stream()
			.filter(c->c.getId() == id)
			.findFirst()
			.orElse(null);
	}
	
	/*
	 * Returns the sum of the total laxity of all cores
	 * */
	public int getLaxity() {
		int laxity = 0;
		for(Core core: cores) {
			laxity += core.getLaxity();
		}
		
		return laxity;
		
	}

	@Override
	public String toString(){
		return id + " " + this.cores.toString();
	}
}
