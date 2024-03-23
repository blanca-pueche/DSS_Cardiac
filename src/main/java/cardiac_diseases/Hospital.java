package cardiac_diseases;

import java.util.LinkedList;
import java.util.Objects;

public class Hospital {
    private LinkedList<Patient> listOfPatients = new LinkedList<>();
    private String name;

    public Hospital(LinkedList<Patient> listOfPatients, String name) {
        this.listOfPatients = listOfPatients;
        this.name = name;
    }
    public Hospital (String name){
        this.name = name;
    }

    public LinkedList<Patient> getListOfPatients() {
        return listOfPatients;
    }

    public String getName(){
        return this.name;
    }

    public void setListOfPatients(LinkedList<Patient> listOfPatients) {
        this.listOfPatients = listOfPatients;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hospital)) return false;
        Hospital hospital = (Hospital) o;
        return Objects.equals(getListOfPatients(), hospital.getListOfPatients()) && Objects.equals(getName(), hospital.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getListOfPatients(), getName());
    }

    @Override
    public String toString() {
        return this.listOfPatients + "," + this.name ;
    }
}
