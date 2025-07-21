import java.util.*;

class Process {
    int pid, at, bt, remainingBT, wt, tat, et, queue = 1;
    boolean completed = false;
    
    Process(int pid, int at, int bt) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
        this.remainingBT = bt;
    }
}

public class MultilevelQueue {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();
        Process[] p = new Process[n];
        
        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for P" + (i+1) + ": ");
            int at = sc.nextInt();
            System.out.print("Enter burst time for P" + (i+1) + ": ");
            int bt = sc.nextInt();
            p[i] = new Process(i+1, at, bt);
        }
        
        int currentTime = 0, completed = 0;
        
        while (completed < n) {
            boolean found = false;
            
            // Queue 1 (currentTime quantum = 2)
            for (int i = 0; i < n; i++) {
                if (!p[i].completed && p[i].at <= currentTime && p[i].queue == 1) {
                    int exec = Math.min(2, p[i].remainingBT);
                    currentTime += exec;
                    p[i].remainingBT -= exec;
                    
                    if (p[i].remainingBT == 0) {
                        p[i].completed = true;
                        p[i].et = currentTime;
                        p[i].tat = p[i].et - p[i].at;
                        p[i].wt = p[i].tat - p[i].bt;
                        completed++;
                    } else {
                        p[i].queue = 2;  // Move to Queue 2
                    }
                    found = true;
                    break;
                }
            }
            
            // Queue 2 (currentTime quantum = 4)
            if (!found) {
                for (int i = 0; i < n; i++) {
                    if (!p[i].completed && p[i].at <= currentTime && p[i].queue == 2) {
                        int exec = Math.min(4, p[i].remainingBT);
                        currentTime += exec;
                        p[i].remainingBT -= exec;
                        
                        if (p[i].remainingBT == 0) {
                            p[i].completed = true;
                            p[i].et = currentTime;
                            p[i].tat = p[i].et - p[i].at;
                            p[i].wt = p[i].tat - p[i].bt;
                            completed++;
                        }
                        found = true;
                        break;
                    }
                }
            }
            
            if (!found) currentTime++;
        }
        
        double avgWT = 0, avgTAT = 0;
        for (int i = 0; i < n; i++) {
            avgWT += p[i].wt;
            avgTAT += p[i].tat;
        }
        
        System.out.println("Average Waiting currentTime: " + (avgWT / n));
        System.out.println("Average Turnaround currentTime: " + (avgTAT / n));
        
        sc.close();
    }
}