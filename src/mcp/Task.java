package mcp;

public class Task implements Comparable<Task>{

private Long deadline;
private Long period;
private Integer id;
private Integer WCET;
private Integer priority;

    public Task(Integer id, Long deadline, Integer WCET, Long period){
        this.id = id;
        this.deadline = deadline;
        this.period = period;
        this.WCET = WCET;
    }

    @Override
    public int compareTo(Task t) {
        int c1 = this.getPeriod().compareTo(t.getPeriod());
        if(c1!=0)
        return c1;
        return this.getId().compareTo(t.getId());
    }

    public Long getDeadline() {
        return deadline;
    }

    public void setDeadline(Long deadline) {
        this.deadline = deadline;
    }

    public Long getPeriod() {
        return period;
    }

    public void setPeriod(Long period) {
        this.period = period;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWCET() {
        return WCET;
    }

    public void setWCET(Integer wCET) {
        WCET = wCET;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public String toString(){
        return this.id + " " + this.deadline + " " + this.period + " " + this.WCET;
    }

}