package algos;

import java.util.List;

import mcp.ConcreteTask;
import mcp.Task;
import java.util.Collections;
public class PriorityAlgorithm {
    List<ConcreteTask> tasks;

    
    public List<ConcreteTask> SetPriorities()
    {
        Collections.sort(tasks);
        int i=1;
        for (ConcreteTask concreteTask : tasks) {
            concreteTask.setPriority(i);
            i++;
        }
        return tasks;
    }

    public PriorityAlgorithm(List<ConcreteTask> tasks) {
        this.tasks = tasks;
    }
}