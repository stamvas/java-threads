class Doctor extends Thread {
    private DoctorOffice office;
    private int doctorNumber;

    //constructor
    public Doctor(DoctorOffice office, int doctorNumber) {
        this.office = office;
        this.doctorNumber = doctorNumber;
    }

    @Override
    public void run() {
        try {
            while (true) {
                office.doctorExamines(doctorNumber);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}