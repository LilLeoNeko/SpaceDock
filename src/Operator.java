public class Operator extends Thread{
    private Berth berth;

    Operator(Berth berth) {
        this.berth = berth;
    }

    public void run() {
        while (!isInterrupted()) {
            try {
                sleep(Params.debrisLapse());
                berth.shieldActivate();
                sleep(Params.DEBRIS_TIME);
                berth.shieldDeactivate();
            }
            catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}