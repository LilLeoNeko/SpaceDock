public class Pilot extends Thread{
    //id for each pilot
    private int id;
    //work status for each pilot
    private Ship curShip;
    //arrivalZone and depart
    private WaitZone arrival;
    private WaitZone depart;
    //Required Tugs and Berth
    private Tugs tugs;
    private Berth berth;

    //pilot[i] = new Pilot(i, arrivalZone, departureZone, tugs, berth)
    Pilot(int id, WaitZone arrival, WaitZone depart, Tugs tugs, Berth berth){
        this.id = id;
        this.arrival = arrival;
        this.depart = depart;
        this.tugs = tugs;
        this.berth = berth;
    }
    //require an space ship from arrival zone
    private void getShip(){
        this.curShip = arrival.getCurShip();
        System.out.println("pilot " + id + " accquires " + curShip.toString());
        arrival.depart();
    }
    private void leaveShip(){
        System.out.println("pilot " + id + " releases " + curShip.toString());
        depart.arrive(curShip);
    }
    //require or return tugs.
    private void getDockTug(){
        System.out.println("pilot " + id + " accquires 3 tugs (" + tugs.getTugNum() +" available). ");
        tugs.getTugs(Params.DOCKING_TUGS);
    }
    private void returnDockTug(){
        tugs.returnTugs(Params.DOCKING_TUGS);
        System.out.println("pilot " + id + " releases 3 tugs (" + tugs.getTugNum() +" available). ");
    }
    private void getUndockTug(){
        tugs.getTugs(Params.UNDOCKING_TUGS);
        System.out.println("pilot " + id + " accquires 2 tugs (" + tugs.getTugNum() +" available). ");
    }
    private void returnUndockTug(){
        tugs.returnTugs(Params.UNDOCKING_TUGS);
        System.out.println("pilot " + id + " releases 2 tugs (" + tugs.getTugNum() +" available). ");
    }
    //require dock or undock
    private void dock(){
        berth.dock();
    }
    private void undock(){
        berth.undock();
    }
    public void run(){
        while(!isInterrupted()){
            try{
                getShip();
                getDockTug();
                sleep(Params.TRAVEL_TIME);
                //docking
                dock();
                sleep(Params.DOCKING_TIME);
                returnDockTug();
                //unloading
                sleep(Params.UNLOADING_TIME);
                // undocking
                getUndockTug();
                undock();
                sleep(Params.UNDOCKING_TIME);
                //to depart zone
                sleep(Params.TRAVEL_TIME);
                leaveShip();
                returnUndockTug();
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}