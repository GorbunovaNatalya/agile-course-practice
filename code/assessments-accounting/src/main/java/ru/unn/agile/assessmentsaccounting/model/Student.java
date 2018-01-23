package ru.unn.agile.assessmentsaccounting.model;

import java.security.InvalidParameterException;
import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

public class Student {

    protected Student(final String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidParameterException("Name is null or empty");
        }
        this.name = name;
        this.assessments = new HashMap<UUID, List<Assessment>>();
    }

    @Override
    public boolean equals(final Object o) {
        return (o instanceof Student) && (((Student) o).getName()).equals(this.getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        if (name == null || name.isEmpty()) {
            throw new InvalidParameterException("Name is null or empty");
        }
        if (name == this.name) {
            return;
        }
        this.name = name;
    }

    public void addSubject(final UUID subjectUuid) {
        if (isRegisteredForSubject(subjectUuid)) {
            throw new InvalidParameterException("Student already registered for "
                    + "subject with uuid - " + subjectUuid.toString());
        }
        assessments.put(subjectUuid, new LinkedList<Assessment>());
    }

    public void removeSubject(final UUID subjectUuid) {
        if (!isRegisteredForSubject(subjectUuid)) {
            throw new InvalidParameterException("Student isn't registered for "
                    + "subject with uuid - " + subjectUuid.toString());
        }
        assessments.remove(subjectUuid);
    }

    public boolean isRegisteredForSubject(final UUID subjectUuid) {
        return assessments.containsKey(subjectUuid);
    }

    public void addAssessment(final Assessment assessment, final UUID subjectUuid) {
        if (!isRegisteredForSubject(subjectUuid)) {
            throw new InvalidParameterException("Student isn't registered for "
                    + "subject with uuid - " + subjectUuid.toString());
        }
        List<Assessment> subjectAssessments = assessments.get(subjectUuid);
        subjectAssessments.add(assessment);
        assessments.put(subjectUuid, subjectAssessments);
    }

    public void removeAssessmentAt(final int index, final UUID subjectUuid) {
        checkAssessmentsIndex(index, subjectUuid);
        List<Assessment> subjectAssessments = assessments.get(subjectUuid);
        subjectAssessments.remove(index);
        assessments.put(subjectUuid, subjectAssessments);
    }

    public void changeAssessmentAt(final int index,
                                   final Assessment assessment,
                                   final UUID subjectUuid) {

        checkAssessmentsIndex(index, subjectUuid);
        List<Assessment> subjectAssessments = assessments.get(subjectUuid);
        subjectAssessments.set(index, assessment);
        assessments.put(subjectUuid, subjectAssessments);
    }

    public List<Assessment> getAssessments() {
        List<Assessment> allAssessments = new LinkedList<Assessment>();
        for (Map.Entry<UUID, List<Assessment>> entry : assessments.entrySet()) {
            allAssessments.addAll(entry.getValue());
        }
        return allAssessments;
    }

    public List<Assessment> getAssessments(final UUID subjectUuid) {
        if (!isRegisteredForSubject(subjectUuid)) {
            throw new InvalidParameterException("Student isn't registered for "
                    + "subject with uuid - " + subjectUuid.toString());
        }
        return assessments.get(subjectUuid);
    }

    private void checkAssessmentsIndex(final int index, final UUID subjectUuid) {
        if (!assessments.containsKey(subjectUuid)) {
            throw new InvalidParameterException("Student isn't registered for "
                    + "subject with uuid - " + subjectUuid.toString());
        }
        if (index < 0 || index >= assessments.get(subjectUuid).size()) {
            throw new InvalidParameterException("Index out of range for "
                    + "subject with uuid - " + subjectUuid.toString());
        }
    }

    private String name;
    private Map<UUID, List<Assessment>> assessments;
}
