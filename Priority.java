import java.util.*;

class Process {
    int pid, at, bt, priority, wt, tat, ct;
    
    Process(int pid, int at, int bt, int priority) {
        this.pid = pid;
        this.at = at;        // arrival time
        this.bt = bt;        // burst time
        this.priority = priority;  // priority
    }
}

public class PriorityScheduling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        
        Process[] p = new Process[n];
        
        // Input arrival time, burst time and priority
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for P" + (i+1) + ": ");
            int at = sc.nextInt();
            System.out.print("Enter burst time for P" + (i+1) + ": ");
            int bt = sc.nextInt();
            System.out.print("Enter priority for P" + (i+1) + " (higher value = higher priority): ");
            int priority = sc.nextInt();
            p[i] = new Process(i+1, at, bt, priority);
        }
        
        // Sort by arrival time first, then by priority
        Arrays.sort(p, (a, b) -> {
            if (a.at != b.at) return a.at - b.at;      // Sort by arrival time
            if (a.priority != b.priority) return b.priority - a.priority;  // Then by priority
            return a.pid - b.pid;  // If same priority, use FCFS (process ID order)
        });
        
        // Calculate completion, waiting and turnaround times
        int currentTime = 0;
        for (int i = 0; i < n; i++) {
            if (currentTime < p[i].at) {
                currentTime = p[i].at;  // CPU idle until process arrives
            }
            p[i].ct = currentTime + p[i].bt;  // completion time
            p[i].tat = p[i].ct - p[i].at;     // turnaround time
            p[i].wt = p[i].tat - p[i].bt;     // waiting time
            currentTime = p[i].ct;
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