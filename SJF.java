import java.util.*;

class Process {
    int pid, at, bt, wt, tat, ct;
    boolean completed = false;
    
    Process(int pid, int at, int bt) {
        this.pid = pid;
        this.at = at;  // arrival time
        this.bt = bt;  // burst time
    }
}

public class SJF {
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
        
        int currentTime = 0;
        int completed = 0;
        
        // SJF (Shortest Job First) Algorithm
        while (completed < n) {
            int shortest = -1;
            int minBurst = Integer.MAX_VALUE;
            
            // Step 1: Find shortest job among all arrived processes
            for (int i = 0; i < n; i++) {
                if (!p[i].completed && p[i].at <= currentTime) {
                    if (p[i].bt < minBurst) {
                        minBurst = p[i].bt;
                        shortest = i;
                    }
                }
            }
            
            // Step 2: If no process has arrived yet, move time forward
            if (shortest == -1) {
                currentTime++;
                continue;
            }
            
            // Step 3: Execute the shortest process completely
            p[shortest].ct = currentTime + p[shortest].bt;      // completion time
            p[shortest].tat = p[shortest].ct - p[shortest].at;  // turnaround time  
            p[shortest].wt = p[shortest].tat - p[shortest].bt;  // waiting time
            p[shortest].completed = true;
            
            // Step 4: Update current time and completed count
            currentTime = p[shortest].ct;
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