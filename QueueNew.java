import java.util.*;

class Process {
    int pid, at, bt, remainingBT, wt, tat, ct, currentQueue;
    boolean completed = false;
    
    Process(int pid, int at, int bt) {
        this.pid = pid;
        this.at = at;           // arrival time
        this.bt = bt;           // burst time
        this.remainingBT = bt;  // remaining burst time
        this.currentQueue = 1;  // start in Queue 1
    }
}

public class MultilevelFeedbackQueue {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        
        Process[] p = new Process[n];
        
        // Input arrival and burst times
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for P" + (i+1) + ": ");
            int at = sc.nextInt();
            System.out.print("Enter burst time for P" + (i+1) + ": ");
            int bt = sc.nextInt();
            p[i] = new Process(i+1, at, bt);
        }
        
        // Algorithm Implementation
        int currentTime = 0;
        int completed = 0;
        int timeQuantum1 = 2;  // Time quantum for Queue 1
        int timeQuantum2 = 4;  // Time quantum for Queue 2
        
        System.out.println("\nExecution Details:");
        
        // Step 1: Divide processes into queues based on priority (initially all in Queue 1)
        while (completed < n) {
            boolean processExecuted = false;
            
            // Step 2: Queue 1 - Schedule using FCFS with time quantum
            for (int i = 0; i < n; i++) {
                if (!p[i].completed && p[i].at <= currentTime && p[i].currentQueue == 1) {
                    System.out.print("P" + p[i].pid + " executing in Queue 1 from time " + currentTime);
                    
                    // Execute for time quantum or remaining burst time
                    int executeTime = Math.min(timeQuantum1, p[i].remainingBT);
                    currentTime += executeTime;
                    p[i].remainingBT -= executeTime;
                    
                    System.out.println(" to " + currentTime);
                    
                    if (p[i].remainingBT == 0) {
                        // Process completed
                        p[i].completed = true;
                        p[i].ct = currentTime;
                        p[i].tat = p[i].ct - p[i].at;
                        p[i].wt = p[i].tat - p[i].bt;
                        completed++;
                    } else {
                        // Move to Queue 2
                        p[i].currentQueue = 2;
                    }
                    processExecuted = true;
                    break;
                }
            }
            
            // Step 3: Queue 2 - Schedule using Round Robin with larger time quantum
            if (!processExecuted) {
                for (int i = 0; i < n; i++) {
                    if (!p[i].completed && p[i].at <= currentTime && p[i].currentQueue == 2) {
                        System.out.print("P" + p[i].pid + " executing in Queue 2 from time " + currentTime);
                        
                        // Execute for time quantum or remaining burst time
                        int executeTime = Math.min(timeQuantum2, p[i].remainingBT);
                        currentTime += executeTime;
                        p[i].remainingBT -= executeTime;
                        
                        System.out.println(" to " + currentTime);
                        
                        if (p[i].remainingBT == 0) {
                            // Process completed
                            p[i].completed = true;
                            p[i].ct = currentTime;
                            p[i].tat = p[i].ct - p[i].at;
                            p[i].wt = p[i].tat - p[i].bt;
                            completed++;
                        }
                        // Process stays in Queue 2 if not completed
                        processExecuted = true;
                        break;
                    }
                }
            }
            
            // If no process executed, increment time
            if (!processExecuted) {
                currentTime++;
            }
        }
        
        // Step 4: Print execution order, WT and TAT for each process
        System.out.println("\nProcess Details:");
        System.out.println("PID\tAT\tBT\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + p[i].pid + "\t" + p[i].at + "\t" + p[i].bt + "\t" + 
                             p[i].ct + "\t" + p[i].tat + "\t" + p[i].wt);
        }
        
        // Step 5: Calculate & display average WT & TAT for both queues
        double totalWT = 0, totalTAT = 0;
        for (int i = 0; i < n; i++) {
            totalWT += p[i].wt;
            totalTAT += p[i].tat;
        }
        
        System.out.println("\nOverall Results:");
        System.out.println("Average Waiting Time: " + (totalWT / n));
        System.out.println("Average Turnaround Time: " + (totalTAT / n));
        
        sc.close();
    }
}
