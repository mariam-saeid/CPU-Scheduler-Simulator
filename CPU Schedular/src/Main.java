import java.util.Scanner;
import java.util.Vector;

public class Main
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Scheduler Menu");
        System.out.println("--------------");
        System.out.println("1) Shortest Job First (SJF)");
        System.out.println("2) Round Robin (RR)");
        System.out.println("3) Priority");
        System.out.println("4) AG Scheduling");
        System.out.println("0) Exit");
        System.out.print("Enter number of which scheduler you want: ");
        int menuNum = input.nextInt();

        while (menuNum != 0)
        {
            // Shortest Job First Input
            if (menuNum == 1)
            {
                System.out.println("\nShortest Job First (SJF)");
                System.out.println("---------------------------\n");

                Vector<Process> processes = new Vector<Process>();

                System.out.print("Enter number of processes: ");
                int num = input.nextInt();
                System.out.print("Enter Context switching: ");
                int contextSwitching = input.nextInt();

                System.out.println();

                for (int i = 0; i < num; i++)
                {
                    Process process = new Process();
                    System.out.print("Enter process " + (i + 1) + " name: ");
                    String processName = input.next();
                    System.out.print("Enter burst time for process " + (i + 1) + ": ");
                    int burstTime = input.nextInt();
                    System.out.print("Enter arrival time for process " + (i + 1) + ": ");
                    int arrivalTime = input.nextInt();

                    process.process_name = processName;
                    process.arrivalTime = arrivalTime;
                    process.burstTime = burstTime;
                    process.copy_execution = burstTime;

                    processes.add(process);
                    System.out.println();
                }

                SJF sjf = new SJF();
                sjf.start(processes, contextSwitching);
            }

            // Round Robin Input
            else if (menuNum == 2)
            {
                System.out.println("\nRound Robin (RR)");
                System.out.println("-------------------\n");

                System.out.print("Enter number of processes: ");
                int num = input.nextInt();
                System.out.print("Enter Round robin Time Quantum: ");
                int quantumTime = input.nextInt();
                System.out.print("Enter Context switching: ");
                int contextSwitching = input.nextInt();

                System.out.println();

                Process processes[] = new Process[num];
                for (int i = 0; i < num; i++)
                {
                    Process process = new Process();
                    System.out.print("Enter process " + (i + 1) + " name: ");
                    String processName = input.next();
                    System.out.print("Enter burst time for process " + (i + 1) + ": ");
                    int burstTime = input.nextInt();
                    System.out.print("Enter arrival time for process " + (i + 1) + ": ");
                    int arrivalTime = input.nextInt();

                    process.process_name = processName;
                    process.arrivalTime = arrivalTime;
                    process.burstTime = burstTime;
                    process.copy_execution = burstTime;

                    processes[i] = process;
                    System.out.println();
                }

                RoundRobin obj = new RoundRobin(processes, num, quantumTime, contextSwitching);
                obj.start();
            }

            // Priority Input
            else if (menuNum == 3)
            {
                System.out.println("\nPriority");
                System.out.println("-----------\n");

                System.out.print("Enter number of processes: ");
                int num = input.nextInt();
                System.out.println();

                Process processes[] = new Process[num];
                for (int i = 0; i < num; i++)
                {
                    Process process = new Process();
                    System.out.print("Enter process " + (i + 1) + " name: ");
                    String processName = input.next();
                    System.out.print("Enter burst time for process " + (i + 1) + ": ");
                    int burstTime = input.nextInt();
                    System.out.print("Enter arrival time for process " + (i + 1) + ": ");
                    int arrivalTime = input.nextInt();
                    System.out.print("Enter priority for process " + (i + 1) + ": ");
                    int priority = input.nextInt();

                    process.process_name = processName;
                    process.arrivalTime = arrivalTime;
                    process.burstTime = burstTime;
                    process.copy_execution = burstTime;
                    process.priority = priority;

                    processes[i] = process;
                    System.out.println();
                }

                Priority priority = new Priority(processes, num);
                priority.start();
            }

            // AG Scheduling Input
            else if (menuNum == 4)
            {
                System.out.println("\nAG Scheduling");

                System.out.print("Enter number of processes: ");
                int num = input.nextInt();

                System.out.println();

                Process processes[] = new Process[num];
                for (int i = 0; i < num; i++)
                {
                    Process process = new Process();
                    System.out.print("Enter process " + (i + 1) + " name: ");
                    String processName = input.next();
                    System.out.print("Enter burst time for process " + (i + 1) + ": ");
                    int burstTime = input.nextInt();
                    System.out.print("Enter arrival time for process " + (i + 1) + ": ");
                    int arrivalTime = input.nextInt();
                    System.out.print("Enter priority for process " + (i + 1) + ": ");
                    int priority = input.nextInt();
                    System.out.print("Enter quantum for process " + (i + 1) + ": ");
                    int quantum = input.nextInt();

                    process.process_name = processName;
                    process.arrivalTime = arrivalTime;
                    process.burstTime = burstTime;
                    process.copy_execution = burstTime;
                    process.priority = priority;
                    process.quantum = quantum;

                    processes[i] = process;
                    System.out.println();
                }

                AGScheduling agScheduling = new AGScheduling(processes,num);
                agScheduling.start();
            }

            System.out.println("\nScheduler Menu");
            System.out.println("--------------");
            System.out.println("1) Shortest Job First (SJF)");
            System.out.println("2) Round Robin (RR)");
            System.out.println("3) Priority");
            System.out.println("4) AG Scheduling");
            System.out.println("0) Exit");
            System.out.print("Enter number of which scheduler you want: ");
            menuNum = input.nextInt();

        }

    }
}