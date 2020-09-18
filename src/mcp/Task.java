package mcp;

public class Task {

    private int id;
    private int deadline;
    private int period;
    private int wcet;

    public Task(int id, int deadline, int period, int wcet){
        this.id = id;
        this.deadline = deadline;
        this.period = period;
        this.wcet = wcet;
    }

    @Override
    public String toString(){
        return this.id + " " + this.deadline + " " + this.period + " " + this.wcet;
    }


}
