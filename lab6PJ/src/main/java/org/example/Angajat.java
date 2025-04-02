package org.example;

import java.time.LocalDate;

public class Angajat {
    private String nume;
    private String post;
    private LocalDate data_angajarii;
    private float salariul;

    public Angajat() {
    }

    public Angajat(String nume, String post, LocalDate data_angajarii, float salariul) {
        this.nume = nume;
        this.post = post;
        this.data_angajarii = data_angajarii;
        this.salariul = salariul;
    }

    public float getSalariul() {
        return salariul;
    }

    public void setSalariul(float salariul) {
        this.salariul = salariul;
    }

    public LocalDate getData_angajarii() {
        return data_angajarii;
    }

    public void setData_angajarii(LocalDate data_angajarii) {
        this.data_angajarii = data_angajarii;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "nume=" + nume + ", post=" + post + ", data_angajarii=" + data_angajarii + ", salariul: " + salariul;
    }

    public int compareTo(Angajat a2) {
        if (this.salariul < a2.salariul) {
            return 1;
        } else if (this.salariul == a2.salariul) return 0;
        else return -1;
    }
}

