public class Berth{
    private String name = new String();
    private volatile int maxSpace;
    private volatile int curSpace;
    private volatile boolean shieldStatus;

    public Berth(String name){
        this.name = name;
        this.maxSpace = 1;
        this.curSpace = maxSpace;
        this.shieldStatus = false;
    }
    public synchronized void shieldActivate(){
        this.shieldStatus = true;
    }
    public synchronized void shieldDeactivate(){
        this.shieldStatus = false;
        notify();
    }
    public synchronized void dock(){
        while(curSpace < 1 || shieldStatus == true){
            try{
                wait();
            }
            catch (InterruptedException e){}
        }
        curSpace -= 1;
    }
    public synchronized void undock(){
        while(shieldStatus == true){
            try{
                wait();
            }
            catch (InterruptedException e){}
        }
        curSpace += 1;
        notify();
    }
}