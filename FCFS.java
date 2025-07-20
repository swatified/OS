import java.util.*;

class Process {
    int pid, at, bt, wt, tat, ct;
    
    Process(int pid, int at, int bt) {
        this.pid = pid;
        this.at = at;  // arrival time
        this.bt = bt;  // burst time
    }
}

public class FCFS {
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
        
        // Sort by arrival time
        Arrays.sort(p, (a, b) -> a.at - b.at);
        
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