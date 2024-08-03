import java.util.*;

public class SJF {

    ArrayList<Process> processesExecution;

    public SJF()
    {
        processesExecution = new ArrayList<Process>();
    }

    public void start(Vector <Process>p,int contextSwitching)
    {
        Process currentProcess = new Process();
        int n = p.size();

        int time=0, total=0,finish_time=0;
        float avg_waiting_time=0, avg_tta=0;

        int c=0, min=Integer.MAX_VALUE;

        boolean check=false;

        int rt[]=new int[n];

        for (int j=0;j<n;j++)
        {
            rt[j]=p.get(j).burstTime;
        }
        while(total!=n)
        {
            for (int i=0; i<n; i++)
            {
                if ((p.get(i).arrivalTime<=time) && (rt[i]>0) && (rt[i]<min))
                {
                    min=rt[i];
                    c=i;
                    check=true;
                }
            }

            if (check==false)
            {
                time++;
                continue;
            }

            rt[c]--;
            min=rt[c];
            if (min == 0)
            {min = Integer.MAX_VALUE;}

            if (p.get(c)!=currentProcess)
            {
                processesExecution.add(p.get(c));
                time+=1+contextSwitching;
            }
            else
            {
                time++;
            }

            if (rt[c]==0)
            {
                total++;
                check=false;
                finish_time=time;
                int wait =finish_time-(p.get(c).burstTime+p.get(c).arrivalTime);
                p.get(c).setWaiting_time(wait);

                if (p.get(c).getWaiting_time()<0)
                {
                    p.get(c).setWaiting_time(0);
                }
            }

            currentProcess = p.get(c);

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

        for(int i=0;i<n;i++) {
            int turn=p.get(i).burstTime+ p.get(i).getWaiting_time();
            p.get(i).setTurnaround(turn);
            avg_waiting_time+= p.get(i).getWaiting_time();
            avg_tta+= p.get(i).getTurnaround();
        }

        Formatter fmt = new Formatter();
        System.out.println("\n\nInformation of each Process");
        fmt.format("%12s %16s %16s\n", "Process Name", "TurnroundTime", "Waiting Time");
        for (int j = 0; j < n; j++) {
            fmt.format("%11s %16s %16s\n", p.get(j).process_name, p.get(j).getTurnaround(), p.get(j).getWaiting_time());
        }
        System.out.print(fmt);

        System.out.println ("\nAverage Turn Around Time is: "+(avg_tta/n));
        System.out.println ("Average Waiting Time is: "+(avg_waiting_time/n));


    }

}