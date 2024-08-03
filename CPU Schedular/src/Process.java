public class Process {
    public String process_name;
    public int burstTime;
    public int arrivalTime;
    public int priority;
    public int quantum;
    public int turnaround = 0;
    public int waitingTime = 0;
    public boolean accessState = false;

    public int begin_time=-1;
    public int end_time=0;
    public int copy_execution;
    private int starvation=0;


    public Process() {
    }

    public int CompletionTime()
    {
        return end_time;
    }
    public int TurnroundTime()
    {
        return WaitingTime()+copy_execution;
    }
    public int WaitingTime()
    {
        return end_time-(arrivalTime+copy_execution);
    }

    public int getTurnaround() {
        return turnaround;
    }

    public void setTurnaround(int turnaround) {
        this.turnaround = turnaround;
    }

    public int getWaiting_time() {
        return waitingTime;
    }

    public void setWaiting_time(int waiting_time) {
        waitingTime = waiting_time;
    }
    public int ResponseTime()
    {
        return begin_time;
    }
    public void Starvation()
    {
        starvation++;
        if (starvation==3) {
            priority = priority - 1;
            starvation = 0;
        }
    }
}