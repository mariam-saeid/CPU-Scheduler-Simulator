import java.util.Arrays;
import java.util.Formatter;
import java.util.Vector;
public class Priority
{
    Process processes[];
    int num_pros;
    public Priority(Process processes[],int num_pros)
    {
        this.processes=processes;
        this.num_pros=num_pros;
    }
    public void start() {
        // to formate outbut
        Formatter fmt = new Formatter();
        //sort list of process based on arrival time
        Arrays.sort(processes, new sortbyarrival());
        System.out.print("\n");
        // ready list to sort process that are ready to run
        Vector<Process> ready = new Vector<Process>();
        // contain order of processes
        Vector<Process> buff = new Vector<Process>();
        // for loop begin from the least Arrival time to the biggest arrival time +it's execution time
        int i = processes[0].arrivalTime;
        boolean c = true;
        while (c) {
            // if there aren't processes in buffer add first process
            if (buff.size() == 0) {
                buff.add(processes[0]);
                processes[0].begin_time = i;
            } else {
                //check processes that are ready to run and add them to ready list
                for (int j = 0; j < num_pros; j++) {
                    if (processes[j].arrivalTime <= i) {
                        if (buff.lastElement().priority > processes[j].priority || buff.lastElement().burstTime == 0) {
                            if (processes[j].burstTime != 0)
                                ready.add(processes[j]);
                        }
                    }
                    else
                        break;
                }
                // choose which process will run
                if (ready.size() != 0) {
                    Process max_prio = ready.get(0);
                    for (int j = 0; j < ready.size(); j++) {
                        if (ready.get(j).priority < max_prio.priority)
                            max_prio = ready.get(j);
                    }
                    if (max_prio.begin_time == -1)
                    {
                        max_prio.begin_time = i;
                    }
                    for (int k=0;k<num_pros;k++)
                    {
                        if (processes[k].burstTime!=0 && processes[k].arrivalTime<=i && !(processes[k].process_name.equals(max_prio.process_name)))
                        {
                            processes[k].Starvation();
                        }
                    }
                    buff.add(max_prio);
                }
                ready.clear();
            }
            i++;
            // decrease execution time of process for each second
            if (buff.size() != 0) {
                buff.lastElement().burstTime = buff.lastElement().burstTime - 1;
                if (buff.lastElement().burstTime == 0) {
                    buff.lastElement().end_time = i;
                }
            }
            //check if there are processes not finish
            for (int j = 0; j < num_pros; j++) {
                if (processes[j].burstTime == 0)
                    c = false;
                else {
                    c = true;
                    break;
                }
            }
        }
        System.out.println("\nOrder of Processes in Buffer ");
        for (int j = 0; j < buff.size(); j++) {
            System.out.print(buff.get(j).process_name);
            System.out.print(" ");
        }
        System.out.println("\n\nInformation of each Process");
        fmt.format("%12s %16s %16s %16s %16s\n", "Process Name", "Completion Time", "TurnroundTime", "Waiting Time", "ResponseTime");
        for (int j = 0; j < num_pros; j++) {
            fmt.format("%11s %16s %16s %16s %16s\n", processes[j].process_name, processes[j].CompletionTime(), processes[j].TurnroundTime(), processes[j].WaitingTime(), processes[j].ResponseTime());
        }
        System.out.print(fmt);

        double avgWaitingTime=0;
        double avgTurnroundTime=0;

        for (int j=0;j<num_pros;j++)
        {
            avgWaitingTime+=processes[j].WaitingTime();
            avgTurnroundTime+=processes[j].TurnroundTime();
        }

        System.out.println("\nAverage Waiting Time: "+avgWaitingTime/num_pros);
        System.out.println("Average Turnaround Time: "+avgTurnroundTime/num_pros);
    }

}