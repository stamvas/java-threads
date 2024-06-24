public class Main {
    public static void main(String[] args) {
        int numDoctors = 3; // αριθμός των γιατρών

        DoctorOffice office = new DoctorOffice(numDoctors);
        Doctor[] doctors = new Doctor[numDoctors];//δημιουργία γιατρών
        for (int i = 0; i < numDoctors; i++) {
            doctors[i] = new Doctor(office, i + 1);
            doctors[i].start();//έναρξη thread
        }

        int numPatients = 1;

        Patient[] patients = new Patient[numPatients];//δημιουργία ασθενών
        for (int i = 0; i < numPatients; i++) {
            patients[i] = new Patient(office, i+1);
            patients[i].start();//έναρξη thread
        }
    }
}