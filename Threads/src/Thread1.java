public class Thread1 extends Thread {
    int x;
    public Thread1(int x) {
        this.x = x;
        this.setDaemon(true);
        this.start();   
    }
    public void run() {
        int i = 0;
        while(i < 5) {
            System.out.println(++x);
            i++;
        }
    }
    public static void main(String args[]) {
        System.out.println("Start program: ");
        int x = 6;
        for(int i = 0; i < 10; i++) {
           System.out.println("Thread" + i + " :"); 
           new Thread1(x + i);
        }
    }
}