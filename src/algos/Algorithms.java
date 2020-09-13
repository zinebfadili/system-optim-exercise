
// calculation of WCETFactor
int WCETFactor() // ---------- not implemented yet
{
	//calculates the WCETFactor, this function is called by the function cost()
}

int WCRT() //--------- not implemented yet
{
	//calculates the WRCT
	//
	/*
	 * for(each task in Tasks){
	 * 		I=0
	 * 		do {
	 * 			R=I+Ci;
	 * 			if(R>Di) { return(unschedulable) }
	 * 			I = sum(from j=1 to i-1) [(R/Tj)*Cj]s
	 * 		} while (I+Ci > R)
	 * }
	 * return(schedulable)
	*/
}

//calculation of cost
int cost() // ---------not implemented yet
{
	// here we have to implement an algorithm that calculates the global cost of all the processors together, so that we can have a cost of a configuration
	// meaning the global cost. 
	// the cost is going to be calculated for the global situation, so globally (for all processors etc) how is it?
	// = penalty*(WRCT-deadline) - averageWCETFactor
}

// print configuration
void printConfig()// ----------not implemented yet
{
}

// 

// exchange two tasks
void exchange(Task taskA, Task taskB) // -------- not implemented yet
{
}

//step selects a neighbor configuration (a configuration where we have exchanged 2 tasks) and decides if the algorithm chooses them or not
int step(int currentCost, float temperature)
{
	int newCost, costDifference, randomTaskA, randomTaskB;
	randomTaskA = Math.random()%numberTasks;
	randomTaskB = Math.random()%numberTasks; 
	while (randomTaskA == randomTaskB) // so we don't exchange the same task with itself
	{
		randomTaskA = Math.random()%N; //select another task
	}
	exchange(randomTaskA, randomTaskB); // we exchange the two tasks
	newCost = cost(); // we calculate the cost of the new configuration
	costDifference = newCost - currentCost; // the difference between the costs
	if(costDifference < 0) // the new cost is lower than the current one, they we definitely make the move
	{
		currentCost = newCost;
	} else // otherwise, it means that the new configuration has a higher cost, so we will go to it if the temperature permits it
	{
		if(Math.random() < Math.exp(-costDifferences/temperature) //if our random number is smaller than the temperature, we accept this poorer solution
		{
			currentCost = newCost;
		} else { // if our random number is bigger than the temperature, we don't accept this new configuration, and we undo the change
			exchange(randomTaskA, randomTaskB); //undoing the exchange is basically redoing it (re-exchanging them)
		}
			
	}
	
	return currentCost;
}

//simulated annealing
void simulatedAnnealing() {
	
	boolean solutionFound= false;  // the solution has not been found yet
	int currentCost = cost(); // the current cost is the one of the initial state
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
			currentCost = step(currentCost, temperature); // we calculate the currentcost
			if(currentCost == 0) { // if the cost calculated is equal to zero, it means that we have found the best solution we can stop
				bestCost = curentCost;  // the best cost is 0
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
		spent = (int) floor(BETA*spent); // we spend more time at a lower temperature (BETA>1)
		timer = spent;
		temperature = temperature * ALPHA; // we decrease the temperature (ALPHA<1)
	}

}

