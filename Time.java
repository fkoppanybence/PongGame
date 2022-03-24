public class Time {
    private static double timeStarted = System.nanoTime();

    public static double getTime(){
        return (System.nanoTime() - timeStarted)*1E-9;
    }
}
