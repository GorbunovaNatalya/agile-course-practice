package ru.unn.agile.AssessmentsAccounting.model;

import java.util.UUID;

public class Student {

    public UUID getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Student(final UUID studentId, final String studentName) {
        if (studentId == null) {
            throw new NullPointerException("Student ID is null");
        }
        this.validateName(studentName);
        this.id = studentId;
        this.name = studentName;
    }

    public void rename(final String newStudentName) {
        this.validateName(newStudentName);
        this.name = newStudentName;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof Student) {
            return this.getId().equals(((Student) object).getId());
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    private String name;

    private UUID id;

    private void validateName(final String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Student must have not empty name");
        }
    }
}
