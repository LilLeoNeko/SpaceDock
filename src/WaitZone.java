public class WaitZone{
    private String name = new String();
    private volatile int maxSpace;
    private volatile int curSpace;
    private volatile Ship curShip;

    public WaitZone(String name){
        this.name = name;
        this.maxSpace = 1;
        this.curSpace = maxSpace;
    }
    public synchronized void arrive(Ship ship){
        while(curSpace < 1){
            try{
                wait();
            }
            catch(InterruptedException e){}
        }
        System.out.println(ship.toString() + " arrives at " + name + " zone");
        this.curShip = ship;
        curSpace -= 1;
        notify();
    }
    public synchronized void depart(){
        int temp = curSpace + 1;
        if (temp <= maxSpace) {
            System.out.println(curShip.toString() + " departs " + name + " zone");
            curSpace = temp;
            notify();
        }
    }
    public synchronized Ship getCurShip(){
        while (curSpace == maxSpace){
            try{
                wait();
            }catch(InterruptedException e){}
        }
        return curShip;
    }
}