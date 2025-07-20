import java.util.*;

class Process {
    int pid, at, bt, priority, wt, tat, ct;
    boolean completed = false;
    
    Process(int pid, int at, int bt, int priority) {
        this.pid = pid;
        this.at = at;        // arrival time
        this.bt = bt;        // burst time
        this.priority = priority;  // priority (lower number = higher priority)
    }
}

public class PriorityScheduling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        
        Process[] p = new Process[n];
        
        // Input arrival time, burst time, and priority
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for P" + (i+1) + ": ");
            int at = sc.nextInt();
            System.out.print("Enter burst time for P" + (i+1) + ": ");
            int bt = sc.nextInt();
            System.out.print("Enter priority for P" + (i+1) + " (lower = higher priority): ");
            int priority = sc.nextInt();
            p[i] = new Process(i+1, at, bt, priority);
        }
        
        int currentTime = 0;
        int completed = 0;
        
        // Priority Scheduling Algorithm (Non-preemptive)
        while (completed < n) {
            int hp = -1;
            int minPriority = Integer.MAX_VALUE;
            
            // Step 1: Find highest priority process among all arrived processes
            for (int i = 0; i < n; i++) {
                if (!p[i].completed && p[i].at <= currentTime) {
                    if (p[i].priority < minPriority) {
                        minPriority = p[i].priority;
                        hp = i;
                    }
                    // If same priority, choose first arrival (FCFS for tie-breaking)
                    else if (p[i].priority == minPriority && p[i].at < p[hp].at) {
                        hp = i;
                    }
                }
            }
            
            // Step 2: If no process has arrived yet, move time forward
            if (hp == -1) {
                currentTime++;
                continue;
            }
            
            // Step 3: Execute the highest priority process completely
            p[hp].ct = currentTime + p[hp].bt;      // completion time
            p[hp].tat = p[hp].ct - p[hp].at;  // turnaround time
            p[hp].wt = p[hp].tat - p[hp].bt;  // waiting time
            p[hp].completed = true;
            
            // Step 4: Update current time and completed count
            currentTime = p[hp].ct;
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
        
        sc.close();
    }
}