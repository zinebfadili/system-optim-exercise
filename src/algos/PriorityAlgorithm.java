package algos;

import java.util.List;
import mcp.Task;
import java.util.Collections;
public class PriorityAlgorithm {
    List<Task> tasks;

    
    public List<Task> SetPriorities()
    {
        Collections.sort(tasks);
        int i=1;
        for (Task concreteTask : tasks) {
            concreteTask.setPriority(i);
            i++;
        }
        return tasks;
    }

    public PriorityAlgorithm(List<Task> tasks) {
        this.tasks = tasks;
    }
}