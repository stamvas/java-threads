import java.util.concurrent.Semaphore;

class DoctorOffice {
    private static final int MAX_PATIENTS = 10; //μέγιστος αριθμός θέσεων
    private Semaphore waitingRoomSemaphore = new Semaphore(MAX_PATIENTS);//// Σημαφόρος για τον χώρο αναμονής
    private Semaphore doctorSemaphore = new Semaphore(1);//// Σημαφόρος για τον γιατρό
    private Semaphore examinationRoomSemaphore;//// Σημαφόρος για το δωμάτιο εξέτασης

    private boolean patientPresent = false;// Μεταβλητή για να ελέγχουμε αν υπάρχει ασθενής στο ιατρείο


    public DoctorOffice(int numDoctors) {
        this.examinationRoomSemaphore = new Semaphore(numDoctors);// Αρχικοποίηση του σημαφόρου δωματίου εξέτασης με τον αριθμό των γιατρών
    }

    // ασθενής φτάνει στο ιατρείο
    public void patientArrives(int patientNumber) throws InterruptedException {
        waitingRoomSemaphore.acquire();// Μειώνει τις άδειες θέσεις στον χώρο αναμονής
        System.out.println("Patient " + patientNumber + " arrived. Waiting room available: " + waitingRoomSemaphore.availablePermits());
        examinationRoomSemaphore.acquire();// Απαιτείται ένας γιατρός στο ιατρείο
        doctorSemaphore.acquire();// Είσοδος στο ιατρείο
        patientPresent = true;// Ορίζουμε ότι υπάρχει ασθενής
        System.out.println("Patient " + patientNumber + " entering examination room.");
    }

    // ασθενής αποχωρεί απο το ιατρείο
    public void patientLeaves(int patientNumber) {
        waitingRoomSemaphore.release();// Αυξάνει τις άδειες θέσεις στον χώρο αναμονής
        System.out.println("Patient " + patientNumber + " leaving. Waiting room available: " + waitingRoomSemaphore.availablePermits());
        examinationRoomSemaphore.release();
        doctorSemaphore.release();
        patientPresent = false;// Ορίζουμε ότι δεν υπάρχει πλέον ασθενής
    }


    // γιατρός εξετάζει έναν ασθενή
    public void doctorExamines(int patientNumber) throws InterruptedException {
        if (patientPresent) {// Έλεγχος αν υπάρχει ασθενής προς εξέταση
            System.out.println("Doctor examining patient " + patientNumber + ".");
            Thread.sleep(5000);// Προσομοίωση χρόνου εξέτασης
            System.out.println("Examination completed for patient " + patientNumber + ". Doctor leaving examination room.");
        }
    }
}