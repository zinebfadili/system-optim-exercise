package mcp;

import java.util.ArrayList;
import java.util.List;

public class Core {
	
	private int id;
	private double WCETFactor;
	private List<Task> tasks;
	
	public Core(int id, double WCETFactor){
		this.id = id;
		this.WCETFactor = WCETFactor;
		this.tasks = new ArrayList<Task>();
	}
	
	public Core(int id, double WCETFactor, List<Task> tasks){
		this.id = id;
		this.WCETFactor = WCETFactor;
		this.tasks = tasks;
	}
	
	public double getWCETFactor() {
		return WCETFactor;
	}
	
	public int getId() {
		return id;
	}
	
	public List<Task> getTasks(){
		return tasks;
	}
	
	public boolean addTask(Task t) {
		return tasks.add(t);
	}
	
	public boolean addTaskList(List<Task> tasks) {
		return this.tasks.addAll(tasks);
	}
	
	public Task getTaskByIndex(int idx) {
		return tasks.remove(idx);
	}
	
	public Task getRandomTask() {
		int i =  (int)Math.random() * tasks.size();
		return getTaskByIndex(i);
	}

	public Task swapRandomTask(Task t1) {
		int i =  (int)Math.random() * tasks.size();
		Task t2 = getTaskByIndex(i);
		tasks.add(i, t1);
		return t2;
	}
}
