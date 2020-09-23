package algos;

import mcp.Core;
import mcp.MCP;
import mcp.Parser;
import mcp.Task;

import java.util.Comparator;
import java.util.List;

import java.util.Collections;
public class Algorithms {
    private List<MCP> mcps;

    //// calculation of cost
    int cost() // ---------not implemented yet
    {
        int totalTasks = 0;
        int unschedulable = 0;
        for(MCP mcp : mcps)
        for (Core core : mcp.getCores()) {
            unschedulable += core.getUnschedulable();
            totalTasks += core.getTasks().size();
        }
        // here we have to implement an algorithm that calculates the global cost of all
        // the processors together, so that we can have a cost of a configuration
        // meaning the global cost.
        // the cost is going to be calculated for the global situation, so globally (for
        // all processors etc) how is it?

        //
        /*
         * Ntotal =0; //total number of non schedulable tasks call the WRCT function
         * that is going to update the WRCT of each task for each core and do Ntotal +=
         * the result of each call cost = Ntotal;
         */
        System.out.println("number of unschedulable : "+ unschedulable);
        return unschedulable / totalTasks;
    }

    // exchange tasks between two cores
    Task[] exchangeRandomTasks(Core coreA, Core coreB) // -------- not implemented yet
	{
		Task[] exchangedTasks = new Task[2];
		Task taskA = coreA.getRandomTask();
		Task taskB = coreB.swapRandomTask(taskA);
		coreA.addTask(taskB);
		coreA.sortTasks();
		coreB.sortTasks();
		
		exchangedTasks[0]=taskA;
		exchangedTasks[1]=taskB;
		
		return exchangedTasks;	
	}

    void undoExchange(Task[] tasks, Core coreA, Core coreB) {

        coreA.removeTaskById(tasks[1].getId());
        coreB.removeTaskById(tasks[0].getId());
        coreA.addTask(tasks[0]);
        coreB.addTask(tasks[1]);

        coreA.sortTasks();
        coreB.sortTasks();

    }

    //

    // step and simulatedAnnealing are heavily inspired by peportier's algorithms on
    // his git repository as he was my teacher last year.

    // step selects a neighbor configuration (a configuration where we have
    // exchanged 2 tasks) and decides if the algorithm chooses them or not
    //this algorithm is heavily inspired by peportier's git repository as he was one of the teammate's former teacher
    int step(int currentCost, double temperature) {
        Task[] switchedTasks = new Task[2];
        int newCost, costDifference, randomCoreA, randomCoreB, randomMCP1, randomMCP2;
        randomMCP1 = (int) (Math.random()*mcps.size());
        randomMCP2 = (int) (Math.random()*mcps.size());

        randomCoreA = (int) (Math.random()*mcps.get(randomMCP1).getCores().size());
        randomCoreB = (int) (Math.random()*mcps.get(randomMCP2).getCores().size());
        while (randomCoreA == randomCoreB) // so we don't exchange the same task with itself
        {
            randomCoreA = (int) (Math.random()*mcps.get(randomMCP1).getCores().size()); // select another task
        }
        Core coreA = mcps.get(randomMCP1).getCore(randomCoreA);
        Core coreB = mcps.get(randomMCP2).getCore(randomCoreB);
        switchedTasks = exchangeRandomTasks(coreA, coreB); // we exchange the two tasks
        newCost = cost(); // we calculate the cost of the new configuration
        int costDiff = newCost - currentCost; // the difference between the costs
        if (costDiff < 0) // the new cost is lower than the current one, they we definitely make the move
        {
            currentCost = newCost;
        } else // otherwise, it means that the new configuration has a higher cost, so we will
               // go to it if the temperature permits it
        {
            if (Math.random() < Math.exp(-costDiff / temperature)) // if our random number is smaller than the
                                                                   // temperature, we accept this poorer solution
            {
                currentCost = newCost;
            } else { // if our random number is bigger than the temperature, we don't accept this new
                     // configuration, and we undo the change
                undoExchange(switchedTasks,coreA, coreB); // undoing the exchange is basically redoing it (re-exchanging them)
            }

        }

        return currentCost;
    }

    // simulated annealing
    //this algorithm is heavily inspired by peportier's git repository as he was one of the teammate's former teacher
    void simulatedAnnealing(double T0, double BETA0, int MAXTIME, double BETA, double ALPHA) {

        boolean solutionFound = false; // the solution has not been found yet
        int currentCost = cost(); // the current cost is the one of the initial state
        int bestCost = currentCost; // the best cost is the current cost
        double temperature = T0; // the current temperature is the minimum temperature, the one entered T0
        int elapsed = 0; // the time elapsed is at 0

        // spent is the amount of time allowed to be spent at current temperature
        int spent = (int) Math.floor(BETA0 * MAXTIME); // BETA0<1, we spend a fraction of the maximum time for our
                                                          // entire simulation at the begining
        // (because we're at high temperature)
        int timer = spent; // our timer

        while (elapsed < MAXTIME && !solutionFound) // while we haven't spent the whole time we allow ourselves
                                                    // (MAXTIME), and the solution hasn't been found
        {
            bestCost = currentCost; // the best cost is the current cost
            while (timer != 0) { // we still have time at this temperature
                currentCost = step(currentCost, temperature); // we calculate the currentcost
                if (currentCost == 0) { // if the cost calculated is equal to zero, it means that we have found the best
                                        // solution we can stop
                    bestCost = currentCost; // the best cost is 0
                    solutionFound = true; // the solution is found
                    System.out.println("Solution found");
                    break; // we stop
                } else if (currentCost < bestCost) {
                    bestCost = currentCost; // we have found a solution for which the cost is lowest to this point so we
                                            // keep it as best cost
                }
                timer -= 1; // decrease the timer by 1
            }
            elapsed += spent; // elapsed is the total time spent, so we add to it the time we just spent at
                              // the possible solution
            System.out.println("At temperature T=" + temperature + ", time spent : " + spent + " out of " + MAXTIME
                    + ". Total time spent so far :" + elapsed + ". Best cost so far :" + bestCost + "\n");
            spent = (int) Math.floor(BETA * spent); // we spend more time at a lower temperature (BETA>1)
            timer = spent;
            temperature = temperature * ALPHA; // we decrease the temperature (ALPHA<1)
        }

    }

    // print configuration
    void printConfig()
    {
        Core currentCore;
        List<Task> currentTaskList;
        System.out.println("-----------------------");
        for (int i=0; i<mcps.size(); i++)
        {
            System.out.println("MCP n°"+i);
            for(int j=0; j<mcps.get(i).getCores().size(); j++)
            {
                System.out.print("Core n°"+j+" : ");
                currentCore = mcps.get(i).getCores().get(j);
                currentTaskList = currentCore.getTasks();
                for (int k=0; k<currentTaskList.size(); k++)
                {
                    System.out.print(currentTaskList.get(k).getId()+" ");
                }
                System.out.println("");
            }
            System.out.println("");
        }
    }


    void initialAssignation(List<Task> tasks)
    {
        // sort the tasks by priority
        Comparator<Task> byPriority = Comparator.comparing(Task::getPriority);
        tasks.sort(byPriority);
        int randomMCP;
        int randomCore;
        int coreId;
        for (int i=0; i<tasks.size(); i++) {
            // pick a randomMCP
            randomMCP = (int) (Math.random()*mcps.size());
            //pick a random core from that MCP
            randomCore = (int) (Math.random()*mcps.get(randomMCP).getCores().size());
            //assign the task to the core
            mcps.get(randomMCP).getCores().get(randomCore).addTask(tasks.get(i));
        }

    }

    public static void main(String[] args)
    {
        Algorithms algo = new Algorithms();
        //call createTasksFromXml to read the tasks
        String path = "medium.xml";
        List<Task> tasks = Parser.createTasksFromXml(path);
        // call createMCPsFromXml to read the MCPs
        algo.mcps = Parser.createMCPsFromXml(path);
        // assign the tasks to the MCPs
        algo.initialAssignation(tasks);
        System.out.println("Initial configuration :");
        algo.printConfig();
        // start simulated annealing
        System.out.println("Start of simulated annealing:");

        double T0=25;
        double ALPHA=0.93;
        double BETA=1.05;
        double BETA0=0.001;
        int MAXTIME=100000000;
        algo.simulatedAnnealing(T0, BETA0, MAXTIME, BETA, ALPHA);
        algo.printConfig();
    }
}
