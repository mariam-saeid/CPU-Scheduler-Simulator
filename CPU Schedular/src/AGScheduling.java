import java.util.*;
import java.lang.Math;

class AGScheduling
{
    Process processes[];
    ArrayList<Process> queue;
    ArrayList<Process> processesExecution;
    int num_pros;
    public AGScheduling(Process processes[],int num_pros)
    {
        this.processes=processes;
        this.num_pros=num_pros;
        queue = new ArrayList<Process>();
        processesExecution = new ArrayList<Process>();
    }

    public void print(ArrayList<Process> q)
    {
        System.out.print("q = ");
        for (int i=0;i<q.size();i++)
        {
            System.out.print(q.get(i).process_name+" ");
        }
        System.out.println();
    }

    public void printHistory(Process currentProcess,Process prevProcess) {
        System.out.print("Quantum (");
        for (int i = 0; i < num_pros; i++)
        {
            if (processes[i].process_name.equals(prevProcess.process_name) && processes[i].quantum!=0)
            {
                System.out.print(prevProcess.quantum+"+"+(processes[i].quantum-prevProcess.quantum));
            }
            else
            {
                System.out.print(processes[i].quantum);
            }
            if (i==num_pros-1)
            {
                System.out.print(")");
            }
            else
                System.out.print(",");
        }


        System.out.print("-> ceil(25%) = ( ");

        for (int i = 0; i < num_pros; i++)
        {
            if (processes[i]==currentProcess)
            {
                System.out.print((int) Math.ceil((currentProcess.quantum) * 0.25));
            }
            else
                System.out.print("-");
            if (i!=num_pros-1)
            {
                System.out.print(",");
            }
        }

        System.out.print(") && ceil(50%) = ( ");

        for (int i = 0; i < num_pros; i++)
        {
            if (processes[i]==currentProcess)
            {
                System.out.print((int) Math.ceil((currentProcess.quantum) * 0.50));
            }
            else
                System.out.print("-");
            if (i!=num_pros-1)
            {
                System.out.print(",");
            }
        }

        System.out.println(")");
    }
    public void start()
    {
        Formatter fmt = new Formatter();

        Arrays.sort(processes, new sortbyarrival());
        int i = 1;
        int time = 0;
        int jobCount;
        boolean finished = false;
        Process currentProcess = new Process();
        currentProcess=processes[0];
        Process tempProcess = new Process();
        Process prevProcess =new Process();
        int counter = 0;

        while (finished==false)
        {
            counter++;
            processesExecution.add(currentProcess);
            if (currentProcess.begin_time==-1)
                currentProcess.begin_time = time;
            jobCount = (int) Math.ceil(currentProcess.quantum * 0.25);

            // print history
            printHistory(currentProcess,prevProcess);
            prevProcess.process_name = currentProcess.process_name;
            prevProcess.quantum=currentProcess.quantum;

            // add to queue
            while (i<num_pros)
            {
                if (processes[i].arrivalTime <= time+jobCount)
                {
                    queue.add(processes[i]);
                    i++;
                }
                else
                    break;
            }


            // finished
            if (currentProcess.burstTime-jobCount<=0)
            {
                currentProcess.quantum = 0;
                time += currentProcess.burstTime;
                currentProcess.burstTime = 0;
                currentProcess.end_time = time;
                if (queue.size()!=0)
                    currentProcess=queue.remove(0);
            }
            else
            {
                //check priority
                int priorityInd = -1;
                tempProcess = currentProcess;
                for (int j = 0; j < queue.size(); j++) {
                    if (queue.get(j).priority < tempProcess.priority) {
                        tempProcess = queue.get(j);
                        priorityInd = j;
                    }
                }

                // Priority not found
                if (priorityInd == -1)
                {
                    jobCount = (int) Math.ceil(currentProcess.quantum * 0.50);

                    // add to queue
                    while (i < num_pros) {
                        if (processes[i].arrivalTime <= time+jobCount) {
                            queue.add(processes[i]);
                            i++;
                        } else
                            break;
                    }


                    // finished
                    if (currentProcess.burstTime-jobCount<=0)
                    {

                        currentProcess.quantum = 0;
                        time += currentProcess.burstTime;
                        currentProcess.burstTime = 0;
                        currentProcess.end_time = time;
                        if (queue.size()!=0)
                            currentProcess=queue.remove(0);
                    }

                    else {

                        // check SJF
                        int SJFInd = -1;
                        tempProcess = currentProcess;
                        while (SJFInd == -1)
                        {
                            for (int j = 0; j < queue.size(); j++)
                            {
                                if (queue.get(j).burstTime < (tempProcess.burstTime - jobCount)) {
                                    tempProcess = queue.get(j);
                                    SJFInd = j;
                                }
                            }
                            if (SJFInd==-1)
                            {
                                if (currentProcess.burstTime-jobCount==0)
                                {
                                    break;
                                }
                                jobCount += 1;
                                if (jobCount > currentProcess.quantum)
                                {
                                    break;
                                }
                                else
                                {
                                    // add to queue
                                    while (i < num_pros)
                                    {
                                        if (processes[i].arrivalTime <= time + jobCount)
                                        {
                                            queue.add(processes[i]);
                                            i++;
                                        } else
                                            break;
                                    }
                                }
                            }
                        }


                        // SJF not found
                        if (SJFInd == -1)
                        {

                            // finished
                            if (currentProcess.burstTime-jobCount<=0)
                            {
                                if (jobCount<currentProcess.quantum)
                                    currentProcess.quantum = 0;

                                time += currentProcess.burstTime;
                                currentProcess.burstTime = 0;
                                currentProcess.end_time = time;
                                if (queue.size()!=0)
                                    currentProcess=queue.remove(0);
                            }

                            if (jobCount>currentProcess.quantum)
                            {
                                jobCount-=1;
                                currentProcess.quantum = currentProcess.quantum + 2;
                                currentProcess.burstTime -= jobCount;
                                time = time+jobCount;
                                queue.add(currentProcess);
                                currentProcess = queue.remove(0);
                            }
                        }

                        // SJF found
                        else {
                            currentProcess.quantum = currentProcess.quantum + (currentProcess.quantum - jobCount);
                            currentProcess.burstTime -= jobCount;
                            time = time+jobCount;
                            queue.add(currentProcess);
                            currentProcess = queue.remove(SJFInd);
                        }
                    }
                }

                // Priority found
                else
                {
                    currentProcess.quantum = currentProcess.quantum + (int) Math.ceil((double)(currentProcess.quantum - jobCount) / 2);
                    currentProcess.burstTime -= jobCount;
                    time = time+jobCount;
                    queue.add(currentProcess);
                    currentProcess = queue.remove(priorityInd);

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