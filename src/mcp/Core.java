package mcp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.List;

public class Core {
	
	private int id;
	private boolean sorted = false;
	private double WCETFactor;
	private List<Task> tasks;
	
	public void sortTasks()
	{
		Comparator<Task> byPriority = Comparator.comparing(Task::getPriority);
		tasks.sort(byPriority);
	}
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
		sorted = false;
		return tasks.add((Task) t);
	}
	
	public boolean addTaskList(List<Task> tasks) {
		sorted = false;
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
		sorted = false;
		int i =  (int)Math.random() * tasks.size();
		Task t2 = getTaskByIndex(i);
		tasks.add(i, t1);
		return t2;
	}
	
	public int getUnschedulable() {
		if(!sorted) {
			Collections.sort(tasks);
			sorted = true;
		}
		int unschedulable = 0;
		double responseTime = 0;
		for(int i = 0; i < tasks.size(); i++) {
			responseTime = getWCRT(i);
			if(responseTime > tasks.get(i).getDeadline())
				unschedulable++;
		}
		
		return unschedulable;
	}
	
	public int getWCRT(int i) {
		
		double ci = Math.ceil(tasks.get(i).getWCET()*WCETFactor);
		double interference, intSum, responseTime;
		interference = 0;
		do {
			intSum = 0;
			responseTime = interference + ci;
			for(int j = 0; j < i; j++) {
				intSum += Math.ceil(responseTime/tasks.get(j).getPeriod()) 
						* (tasks.get(j).getWCET()* WCETFactor);
			}
			interference = intSum;
		}while(interference + ci > responseTime);
		
		return (int) Math.ceil(responseTime);
		
	}
}
