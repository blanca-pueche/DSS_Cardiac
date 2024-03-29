package cardiac_diseases;

import java.util.LinkedList;
import java.util.Objects;

public class Patient {

    private String name;
    private String surname;
    private int age;
    private Disease disease;
    private LinkedList<Symptom> symptoms = new LinkedList<>();

    public Patient(){
        this.name = null;
        this.surname = null;
        this.age = 0;
        this.symptoms = new LinkedList<>();
        this.disease = null;
    }
    public Patient(String name, String surname, int age, LinkedList<Symptom> listOfSymptoms){
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.symptoms = listOfSymptoms;
    }
    public Patient(String name, String surname, int age, LinkedList<Symptom> listOfSymptoms, Disease disease){
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.symptoms = listOfSymptoms;
        this.disease = disease;
    }

    public Patient(String name, String surname, int age){
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.symptoms = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public int getAge(){
        return this.age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public LinkedList<Symptom> getSymptoms() {
        return symptoms;

    }

    public void setSymptoms(LinkedList<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Patient)) return false;
        Patient patient = (Patient) o;
        return age == patient.age && Objects.equals(getName(), patient.getName()) && Objects.equals(getSurname(), patient.getSurname()) && getDisease() == patient.getDisease() && Objects.equals(getSymptoms(), patient.getSymptoms());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), age, getDisease(), getSymptoms());
    }

    @Override
    public String toString() {
        return "Patient{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", disease=" + disease +
                ", symptoms=" + symptoms +
                '}';
    }
}
