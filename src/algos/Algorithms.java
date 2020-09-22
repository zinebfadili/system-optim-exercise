package algos;

import java.util.List;

import mcp.Task;
import mcp.Core;
import mcp.MCP;
import java.util.Collections;
public class Algorithms {
private MCP mcp;
////calculation of cost
int cost(MCP mcp) // ---------not implemented yet
{
    int totalTasks = 0;
    int unschedulable = 0;
    for (Core core : mcp.getCores()) {
        unschedulable+=core.getUnschedulable();
        totalTasks+=core.getTasks().size();
    }
	// here we have to implement an algorithm that calculates the global cost of all the processors together, so that we can have a cost of a configuration
	// meaning the global cost. 
	// the cost is going to be calculated for the global situation, so globally (for all processors etc) how is it?
	
	
	// 
	/* Ntotal =0; //total number of non schedulable tasks
	* call the WRCT function that is going to update the WRCT of each task for each core and do Ntotal += the result of each call
   * cost = Ntotal;
    */
    return unschedulable/totalTasks;
}

	// print configuration
	void printConfig()// ----------not implemented yet
	{
	}

	//

	// exchange tasks between two cores
	void exchangeRandomTasks(Core coreA, Core coreB) // -------- not implemented yet
	{
		//TO DO: store which Tasks were exchanged in order to undo it if the new config is bad
		Task taskA = coreA.getRandomTask();
		Task taskB = coreB.swapRandomTask(taskA);
		coreA.addTask(taskB);
		coreA.sortTasks();
		coreB.sortTasks();
		
	}

	//step and simulatedAnnealing are heavily inspired by peportier's algorithms on his git repository as he was my teacher last year.

	//step selects a neighbor configuration (a configuration where we have exchanged 2 tasks) and decides if the algorithm chooses them or not
	int step(int currentCost, float temperature, int nbCores)
	{
		int newCost, costDifference, randomCoreA, randomCoreB;
		randomCoreA = (int) Math.random()%nbCores;
		randomCoreB = (int) Math.random()%nbCores; 
		while (randomCoreA == randomCoreB) // so we don't exchange the same task with itself
		{
			randomCoreA = (int) Math.random()%nbCores; //select another task
        }
        Core coreA = mcp.getCore(randomCoreA);
        Core coreB = mcp.getCore(randomCoreB);
		exchangeRandomTasks(coreA, coreB); // we exchange the two tasks
		newCost = cost(mcp); // we calculate the cost of the new configuration
		int costDiff = newCost - currentCost; // the difference between the costs
		if(costDiff < 0) // the new cost is lower than the current one, they we definitely make the move
		{
			currentCost = newCost;
		} else // otherwise, it means that the new configuration has a higher cost, so we will go to it if the temperature permits it
		{
			if(Math.random() < Math.exp(-costDiff/temperature)) //if our random number is smaller than the temperature, we accept this poorer solution
			{
				currentCost = newCost;
			} else { // if our random number is bigger than the temperature, we don't accept this new configuration, and we undo the change
				exchangeRandomTasks(coreA, coreB); //undoing the exchange is basically redoing it (re-exchanging them)
			}
				
		}
		
		return currentCost;
	}

	//simulated annealing
	void simulatedAnnealing(float T0, int TIMEINIT, int MAXTIME, int nbCores, int BETA, int ALPHA) {
		
		boolean solutionFound= false;  // the solution has not been found yet
		int currentCost = cost(mcp); // the current cost is the one of the initial state
		int bestCost = currentCost; // the best cost is the current cost
		float temperature = T0; // the current temperature is the minimum temperature, the one entered T0
		int elapsed = 0; // the time elapsed is at 0
		
		// spent is the amount of time allowed to be spent at current temperature 
		int spent = (int) Math.floor(TIMEINIT*MAXTIME);  // TIMEINIT<1, we spend a fraction of the maximum time for our entire simulation at the begining 
		//(because we're at high temperature)
		int timer = spent; //our timer
	  
		while (elapsed<MAXTIME && !solutionFound) // while we haven't spent the whole time we allow ourselves (MAXTIME), and the solution hasn't been found
		{ 
			bestCost = currentCost; // the best cost is the current cost
			while (timer!=0) { // we still have time at this temperature
				currentCost = step(currentCost, temperature, nbCores); // we calculate the currentcost
				if(currentCost == 0) { // if the cost calculated is equal to zero, it means that we have found the best solution we can stop
					bestCost = currentCost;  // the best cost is 0
					solutionFound=true; // the solution is found
					break; // we stop
				} 
				else if(currentCost < bestCost)
				{
					bestCost = currentCost; // we have found a solution for which the cost is lowest to this point so we keep it as best cost
				}
				timer -= 1; // decrease the timer by 1
			}
			elapsed += spent; // elapsed is the total time spent, so we add to it the time we just spent at the possible solution
			System.out.println("At temperature T=" + temperature +", time spent : "+ spent +" out of "+ MAXTIME +". Total time spent so far :"+ elapsed +
				". Best cost so far :" + bestCost +"\n");
			spent = (int) Math.floor(BETA*spent); // we spend more time at a lower temperature (BETA>1)
			timer = spent;
			temperature = temperature * ALPHA; // we decrease the temperature (ALPHA<1)
		}

	}
}
