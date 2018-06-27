public class Thread2 implements Runnable {   
    private int x;
    private Thread t;
    public Thread2(int x) {
        this.x = x;
        t = new Thread(this);
        // t.setDaemon(true);
        t.start();
    }
    
    public void run() {
        int i = 0;
        while(i < 5) {
            System.out.println(++x);
            i++;
        }
    }
    
    public static void main(String args[]) {
        int x = 6;
        System.out.println("Starting Threads... ");
        for(int i = 0; i < 10; i++) {
            System.out.println("Calling Thread" + i + ":");
            new Thread2(x + i);
        }
    }
}