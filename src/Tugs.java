public class Tugs{
    private volatile int maxNum;
    private volatile int curNum;
    //constructer
    public Tugs(int num){
        this.maxNum = num;
        this.curNum = maxNum;
    }
    public synchronized void getTugs(int num){
        while(curNum < num){
            try{
                wait();
            }
            catch (InterruptedException e) {}
        }
        curNum -= num;
    }
    public synchronized void returnTugs(int num){
        int temp = curNum + num;
        if(temp <= maxNum){
            curNum = temp;
            notify();
        }
    }
    public synchronized int getTugNum(){
        return curNum;
    }
}