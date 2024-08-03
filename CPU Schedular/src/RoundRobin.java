import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;

public class RoundRobin {
    Process processes[];
    int num_pros;
    int QuantumTime;
    int ContextSwitching;
    ArrayList<Process> processesExecution;
    public RoundRobin(Process processes[],int num_pros,int QuantumTime,int ContextSwitching)
    {
        this.processes=processes;
        this.num_pros=num_pros;
        this.QuantumTime= QuantumTime;
        this.ContextSwitching=ContextSwitching;
        processesExecution = new ArrayList<Process>();
    }
    public void start()
    {
        Formatter fmt = new Formatter();
        //sort list of process based on arrival time
        Arrays.sort(processes, new sortbyarrival());
        System.out.print("\n");
        int counter=0;
        int CStime=0;
        boolean finished=false;
        while(finished!=true) {
            for (int i = 0; i < num_pros; i++) {
                if (processes[i].burstTime != 0) {
                    if(processes[i].begin_time==-1)
                    {
                        if(processes[i].arrivalTime==counter&&counter==0)
                        {
                            processes[i].begin_time = counter;
                            processes[i].copy_execution = processes[i].burstTime;
                        }
                        if(processes[i].arrivalTime<counter-ContextSwitching) {
                            processes[i].begin_time = counter;
                            processes[i].copy_execution = processes[i].burstTime;
                        }
                        else {

                            break;
                        }
                    }
                    if (processes[i].burstTime >= QuantumTime) {
                        processes[i].burstTime -= QuantumTime;
                        CStime+=ContextSwitching;
                        counter +=QuantumTime;
                        counter +=ContextSwitching;
                        processesExecution.add(processes[i]);
                    }
                    else
                    {
                        CStime+=ContextSwitching;
                        counter +=processes[i].burstTime ;
                        counter +=ContextSwitching;
                        processes[i].burstTime = 0;
                        processesExecution.add(processes[i]);
                    }

                    if (processes[i].burstTime == 0) {
                        processes[i].end_time = counter;
                    }
                }

            }
            finished = true;
            for (int j = 0; j < num_pros; j++)
            {
                if (processes[j].burstTime!=0)
                {
                    finished=false;
                    break;
                }
            }
        }

        System.out.println("\nProcesses execution order");
        for (int j=0;j<processesExecution.size();j++)
        {
            System.out.print(processesExecution.get(j).process_name);
            if (j!=processesExecution.size()-1)
            {
                System.out.print(", ");
            }
        }

        System.out.println("\n\nInformation of each Process");
        System.out.print("number of context switching: ");
        System.out.println(CStime);
        fmt.format("%12s %16s %16s %16s\n", "Process Name", "Completion Time", "TurnroundTime", "Waiting Time");
        for (int j = 0; j < num_pros; j++) {
            fmt.format("%11s %16s %16s %16s\n", processes[j].process_name,
                    processes[j].CompletionTime(), processes[j].TurnroundTime(), processes[j].WaitingTime());
        }
        System.out.print(fmt);
    }
}