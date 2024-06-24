class Patient extends Thread {
    private DoctorOffice office;
    private int patientNumber;

    //constructor
    public Patient(DoctorOffice office, int patientNumber) {
        this.office = office;
        this.patientNumber = patientNumber;
    }

    @Override
    public void run() {
        try {
            int patientNumber = 1;
            while (true) {
                office.patientArrives(patientNumber);
                office.doctorExamines(patientNumber);
                office.patientLeaves(patientNumber);
                patientNumber++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}