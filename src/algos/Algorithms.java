package algos;

public class Algorithms {

}
//// calculation of WCETFactor
//int WCETFactor() // ---------- not implemented yet
//{
//	//calculates the WCETFactor, this function is called by the function cost()
//}
//
//int WCRT() //--------- not implemented yet
//{
//
//
//	// R = C + I, longest response time = computation time + interference due to preemption by high priority tasks
//	//calculates the WRCT
//	//
//	/*
//
//	 * int numberNonSchedulable = 0;
//	 * for(each task in Tasks){
//	 * 		I=0
//	 //!!!!! Ci is WCET*WCET factor of the core !!!!!
//	 * 		do {
//	 * 			R=I+Ci; // Computation time of the task + interferences
//	 * 			if(R>Di) { // if the worst case response time is bigger than the deadline, it means that the set is not schedulable
//	 * 				numberNonSchedulable++;
//	 * 			}
//	 * 			I = sum(from j=1 to i-1) [(R/Tj)*Cj]; // otherwise we take into account the interferences
//	 * 		} while (I+Ci > R) // it means that the current R is not the actual worst case reponse time, so we continue calculating
//	   WCRT[task] = R;
//	 * }
//	 * return(numberNonSchedulable) // return number of tasks that are not schedulable
//	*/
//}
//
////calculation of cost
//int cost() // ---------not implemented yet
//{
//	// here we have to implement an algorithm that calculates the global cost of all the processors together, so that we can have a cost of a configuration
//	// meaning the global cost.
//	// the cost is going to be calculated for the global situation, so globally (for all processors etc) how is it?
//
//
//	//
//	/* Ntotal =0; //total number of non schedulable tasks
//	* call the WRCT function that is going to update the WRCT of each task for each core and do Ntotal += the result of each call
//    * cost = Ntotal;
//	*/
//}
//
//// print configuration
//void printConfig()// ----------not implemented yet
//{
//}
//
////
//
//// exchange two tasks
//void exchange(Task taskA, Task taskB) // -------- not implemented yet
//{
//    // !!! we need to sort the tasks (by decreasing period) is the cores once we've exchanged the tasks
//}
//
////step and simulatedAnnealing are heavily inspired by peportier's algorithms on his git repository as he was my teacher last year.
//
////step selects a neighbor configuration (a configuration where we have exchanged 2 tasks) and decides if the algorithm chooses them or not
//int step(int currentCost, float temperature)
//{
//	int newCost, costDifference, randomTaskA, randomTaskB;
//	randomTaskA = Math.random()%numberTasks;
//	randomTaskB = Math.random()%numberTasks;
//	while (randomTaskA == randomTaskB) // so we don't exchange the same task with itself
//	{
//		randomTaskA = Math.random()%N; //select another task
//	}
//	exchange(randomTaskA, randomTaskB); // we exchange the two tasks
//	newCost = cost(); // we calculate the cost of the new configuration
//	costDifference = newCost - currentCost; // the difference between the costs
//	if(costDifference < 0) // the new cost is lower than the current one, they we definitely make the move
//	{
//		currentCost = newCost;
//	} else // otherwise, it means that the new configuration has a higher cost, so we will go to it if the temperature permits it
//	{
//		if(Math.random() < Math.exp(-costDifferences/temperature) //if our random number is smaller than the temperature, we accept this poorer solution
//		{
//			currentCost = newCost;
//		} else { // if our random number is bigger than the temperature, we don't accept this new configuration, and we undo the change
//			exchange(randomTaskA, randomTaskB); //undoing the exchange is basically redoing it (re-exchanging them)
//		}
//
//	}
//
//	return currentCost;
//}
//
//}
//
//


