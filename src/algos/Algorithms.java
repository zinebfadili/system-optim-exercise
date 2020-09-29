package algos;

import mcp.Core;
import mcp.MCP;
import mcp.Parser;
import mcp.Task;

import java.util.Comparator;
import java.util.List;

import java.util.Collections;
public class Algorithms {
    public List<MCP> mcps;
    private int perfectLaxity = 0;

    void perfectLaxity() {
        for (MCP mcp : mcps) {
            for (Core core : mcp.getCores()) {
                for (Task task : core.getTasks()) {
                    perfectLaxity += task.getDeadline() - task.getWCET();
                }
            }
        }
    }
    //// calculation of cost
    double cost() // ---------not implemented yet
    {
        int totalTasks = 0;
        long totalLaxity = 0;
        int penalty = 5000;
        //double averageLaxity = 0;
        int unschedulable = 0;

        for(MCP mcp : mcps) {
            for (Core core : mcp.getCores()) {
                unschedulable += core.getUnschedulable();
                totalTasks += core.getTasks().size();
                totalLaxity += core.getLaxity();
            }
        }
        // verify this ...
        penalty *= unschedulable;
        //averageLaxity = 1/Double.valueOf(totalTasks) * totalLaxity + penalty;

        //averageLaxity = ((double)totalLaxity)/perfectLaxity;
        //System.out.println("number of unschedulable : "+ unschedulable);
        //System.out.println("average laxitiy: "+ averageLaxity);
        //Cost function will return a value between 0 and 1000
        //A bad solution will return 1000
        //the better a solution is, the closer the value is to 0

        //System.out.println("average laxity "+ averageLaxity);
        /*if(averageLaxity < 1000) {
        	return 1000; //maximum value;
        }
        else {
        	return (int) ((1000/averageLaxity)*1000);
        }*/
        return (1.0/totalTasks * (perfectLaxity-(totalLaxity-penalty)));
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
    }

    // exchange tasks between two cores
    /*Task[] exchangeRandomTasks(Core coreA, Core coreB) // -------- not implemented yet
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
	}*/

    //version 2 : just move a task to another :
    Task exchangeRandomTasks(Core coreA, Core coreB) // -------- not implemented yet
    {
        //Task[] exchangedTasks = new Task[2];
        Task taskA = coreA.getRandomTask();
        //Task taskB = coreB.swapRandomTask(taskA);
        //coreA.addTask(taskB);
        coreB.addTask(taskA);
        coreA.sortTasks();
        coreB.sortTasks();

        //exchangedTasks[0]=taskA;
        //exchangedTasks[1]=taskB;

        //return exchangedTasks;
        return taskA;
    }


    /*void undoExchange(Task[] tasks, Core coreA, Core coreB) {

        coreA.removeTaskById(tasks[1].getId());
        coreB.removeTaskById(tasks[0].getId());
        coreA.addTask(tasks[0]);
        coreB.addTask(tasks[1]);

        coreA.sortTasks();
        coreB.sortTasks();

    }*/

    //version 2: just re-adding the task to the core
    void undoExchange(Task task, Core coreA, Core coreB) {

        //coreA.removeTaskById(tasks[1].getId());
        coreB.removeTaskById(task.getId());
        coreA.addTask(task);
       // coreB.addTask(tasks[1]);

        coreA.sortTasks();
        coreB.sortTasks();

    }

    //take an mcp and return the core that has the lowest utilization rate
    Core lessUtil(int mcp)
    {
        double minUtil = 100;
        double util = 0;
        Core chosenCore = mcps.get(mcp).getCore(0);
        for(Core core : mcps.get(mcp).getCores()) {
            for (Task task : core.getTasks()) {
                util += (task.getWCET() * core.getWCETFactor()) / task.getPeriod();
            }
            if(util < minUtil){
                minUtil = util;
                chosenCore = core;
            }
            util = 0;
        }

        return chosenCore;
    }

    Core mostUtil(int mcp)
    {
        double maxUtil = 0;
        double util = 0;
        Core chosenCore = mcps.get(mcp).getCore(0);
        for(Core core : mcps.get(mcp).getCores()) {
            for (Task task : core.getTasks()) {
                util += (task.getWCET() * core.getWCETFactor()) / task.getPeriod();
            }
            if(util > maxUtil){
                maxUtil = util;
                chosenCore = core;
            }
            util = 0;
        }

        return chosenCore;
    }
    //

    // step and simulatedAnnealing are heavily inspired by peportier's algorithms on
    // his git repository as he was my teacher last year.

    // step selects a neighbor configuration (a configuration where we have
    // exchanged 2 tasks) and decides if the algorithm chooses them or not
    //this algorithm is heavily inspired by peportier's git repository as he was one of the teammate's former teacher
    double step(double currentCost, double temperature) {
        //Task[] switchedTasks = new Task[2];
        Task switchedTask;
        int randomCoreA, randomCoreB, randomMCP1, randomMCP2;
        double newCost, costDifference;
        randomMCP1 = (int) (Math.random()*mcps.size()); //pick a random MCP
        randomMCP2 = (int) (Math.random()*mcps.size()); //pick a random MCP

        //System.out.println("MCP1 : "+randomMCP1);
        //System.out.println("MCP2 : "+randomMCP2);

        //version 2
        randomCoreA = (int) (Math.random()*mcps.get(randomMCP1).getCores().size()); //pick a random core
        randomCoreB = (int) (Math.random()*mcps.get(randomMCP2).getCores().size()); //pick a random core

       /* Core coreA = mostUtil(randomMCP1);
        Core coreB = lessUtil(randomMCP2);*/

       // System.out.println("C1 : "+randomCoreA);
       // System.out.println("C2 : "+randomCoreB);
        /*while ((randomMCP1==randomMCP2 && coreA.getId() == coreB.getId())
                || (coreA.getTasks().size() == 0)) // so we don't exchange the same task with itself*/
        while ((randomMCP1==randomMCP2 && randomCoreA  == randomCoreB)
                || (mcps.get(randomMCP1).getCore(randomCoreA).getTasks().size() == 0))
        {
            //System.out.println("stuck");
            //printConfig();
           /* System.out.println("MCP1 :" +randomMCP1);
            System.out.println("MCP2 :" +randomMCP2);
            System.out.println("CoreA :" +randomCoreA);
            System.out.println("CoreB :" +randomCoreB);*/
            randomMCP1 = (int) (Math.random()*mcps.size()); //pick a random MCP
            randomCoreA = (int) (Math.random()*mcps.get(randomMCP1).getCores().size()); // select another core
            //coreA = mostUtil(randomMCP1);
        }
        Core coreA = mcps.get(randomMCP1).getCore(randomCoreA);
        Core coreB = mcps.get(randomMCP2).getCore(randomCoreB);
        switchedTask = exchangeRandomTasks(coreA, coreB); // we exchange the two tasks
       // switchedTask = exchangeRandomTasks(coreA, coreB);
        newCost = cost(); // we calculate the cost of the new configuration
        double costDiff = newCost - currentCost; // the difference between the costs
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
                undoExchange(switchedTask,coreA, coreB); // undoing the exchange is basically redoing it (re-exchanging them)
            }

        }

        return currentCost;
    }

    // simulated annealing
    //this algorithm is heavily inspired by peportier's git repository as he was one of the teammate's former teacher
    void simulatedAnnealing(double T0, double BETA0, int MAXTIME, double BETA, double ALPHA) {

        boolean solutionFound = false; // the solution has not been found yet
        double currentCost = cost(); // the current cost is the one of the initial state
        double bestCost = currentCost; // the best cost is the current cost
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
        	System.out.format("%d, out of %d spent \n", elapsed, MAXTIME);
            bestCost = currentCost; // the best cost is the current cost
            while (timer != 0) { // we still have time at this temperature
                currentCost = step(currentCost, temperature); // we calculate the currentcost
                //System.out.println("current cost : "+ currentCost);
                if (currentCost == 0) { // if the cost calculated is equal to zero, it means that we have found the best
                                        // solution we can stop
                    bestCost = currentCost; // the best cost is 0
                    solutionFound = true; // the solution is found
                    System.out.println("Solution found");
                    System.out.println("best cost found"+bestCost);
                    int totalLaxity = 0;
                    for(MCP mcp : mcps) {
                        for (Core core : mcp.getCores()) {
                            totalLaxity += core.getLaxity();
                        }
                    }
                    System.out.println("total laxity : "+totalLaxity);
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
            //printConfig();
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

    void printLaxity()
    {
        int totalLaxity = 0;
        for(MCP mcp : mcps) {
            for (Core core : mcp.getCores()) {
                totalLaxity += core.getLaxity();
            }
        }
        System.out.println("total laxity : "+totalLaxity);
    }

    public static void main(String[] args)
    {
        Algorithms algo = new Algorithms();
        //call createTasksFromXml to read the tasks
        String path = "small.xml";
        List<Task> tasks = Parser.createTasksFromXml(path);
        // call createMCPsFromXml to read the MCPs
        algo.mcps = Parser.createMCPsFromXml(path);
        // assign the tasks to the MCPs
        algo.initialAssignation(tasks);
        algo.perfectLaxity();
        System.out.println("Initial configuration :");
        algo.printConfig();
        algo.printLaxity();
        // start simulated annealing
        System.out.println("Start of simulated annealing:");

        double T0=25;
        double ALPHA=0.95;
        double BETA=1.1;
        double BETA0=0.001;
        int MAXTIME=100000000;
        algo.simulatedAnnealing(T0, BETA0, MAXTIME, BETA, ALPHA);
        algo.printConfig();
        algo.printLaxity();

        System.out.println("end of algorithm");
    }
}
