import java.util.Comparator;

public class sortbyarrival implements Comparator<Process> {
    public int compare() {
        return compare(null, null);
    }

    public int compare(Process a, Process b)
    {
        return a.arrivalTime-b.arrivalTime;
    }
}