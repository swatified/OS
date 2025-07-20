import java.util.*;

class Process {
    int pid, at, bt, wt, tat, ct, queueLevel;
    boolean completed = false;
    
    Process(int pid, int at, int bt, int queueLevel) {
        this.pid = pid;
        this.at = at;           // arrival time
        this.bt = bt;           // burst time
        this.queueLevel = queueLevel;  // queue level (0=highest priority)
    }
}

public class MultilevelQueue {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        
        Process[] p = new Process[n];
        
        // Input arrival time, burst time, and queue level
        System.out.println("Queue Levels: 0=System, 1=Interactive, 2=Batch");
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for P" + (i+1) + ": ");
            int at = sc.nextInt();
            System.out.print("Enter burst time for P" + (i+1) + ": ");
            int bt = sc.nextInt();
            System.out.print("Enter queue level for P" + (i+1) + " (0/1/2): ");
            int queueLevel = sc.nextInt();
            p[i] = new Process(i+1, at, bt, queueLevel);
        }
        
        int currentTime = 0;
        int completed = 0;
        
        // Multilevel Queue Scheduling Algorithm
        while (completed < n) {
            int selectedProcess = -1;
            int highestQueueLevel = Integer.MAX_VALUE;
            
            // Step 1: Find process from highest priority queue among arrived processes
            for (int i = 0; i < n; i++) {
                if (!p[i].completed && p[i].at <= currentTime) {
                    if (p[i].queueLevel < highestQueueLevel) {
                        highestQueueLevel = p[i].queueLevel;
                        selectedProcess = i;
                    }
                    // If same queue level, use FCFS (first arrival)
                    else if (p[i].queueLevel == highestQueueLevel && p[i].at < p[selectedProcess].at) {
                        selectedProcess = i;
                    }
                }
            }
            
            // Step 2: If no process has arrived yet, move time forward
            if (selectedProcess == -1) {
                currentTime++;
                continue;
            }
            
            // Step 3: Execute the selected process completely
            p[selectedProcess].ct = currentTime + p[selectedProcess].bt;      // completion time
            p[selectedProcess].tat = p[selectedProcess].ct - p[selectedProcess].at;  // turnaround time
            p[selectedProcess].wt = p[selectedProcess].tat - p[selectedProcess].bt;  // waiting time
            p[selectedProcess].completed = true;
            
            // Step 4: Update current time and completed count
            currentTime = p[selectedProcess].ct;
            completed++;
        }
        
        // Calculate averages
        double avgWT = 0, avgTAT = 0;
        for (int i = 0; i < n; i++) {
            avgWT += p[i].wt;
            avgTAT += p[i].tat;
        }
        avgWT /= n;
        avgTAT /= n;
        
        // Output results
        System.out.println("\nAverage Waiting Time: " + avgWT);
        System.out.println("Average Turnaround Time: " + avgTAT);
        
        // Show queue-wise execution
        System.out.println("\nExecution Order by Queue:");
        Arrays.sort(p, (a, b) -> a.ct - b.ct);  // Sort by completion time
        for (Process proc : p) {
            String queueName = (proc.queueLevel == 0) ? "System" : 
                              (proc.queueLevel == 1) ? "Interactive" : "Batch";
            System.out.println("P" + proc.pid + " (Queue: " + queueName + ")");
        }
        
        sc.close();
    }
}