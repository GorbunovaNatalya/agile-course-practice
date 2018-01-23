package ru.unn.agile.assessmentsaccounting.model;

import org.junit.Test;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class StudentTests {

    @Test
    public void canCreateStudent() {
        Student student = new Student("Max Bespalov");
        assertNotNull(student);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotCreateUnnamedStudent() {
        new Student("");
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotSetEmptyName() {
        Student student = new Student("Max Bespalov");
        student.setName("");
    }

    @Test
    public void canAddSubject() {
        Student student = new Student("Max Bespalov");
        UUID subjectUuid = UUID.randomUUID();
        student.addSubject(subjectUuid);
        assertNotNull(student.getAssessments(subjectUuid));
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotAddExistingSubject() {
        Student student = new Student("Max Bespalov");
        UUID subjectUuid = UUID.randomUUID();
        student.addSubject(subjectUuid);
        student.addSubject(subjectUuid);
    }

    @Test
    public void canRemoveAddedSubject() {
        Student student = new Student("Max Bespalov");
        UUID subjectUuid = UUID.randomUUID();
        student.addSubject(subjectUuid);
        student.removeSubject(subjectUuid);
        assertFalse(student.isRegisteredForSubject(subjectUuid));
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotRemoveNotExistingSubject() {
        Student student = new Student("Max Bespalov");
        student.removeSubject(UUID.randomUUID());
    }

    @Test
    public void afterAddingSubjectStudentRegisteredForIt() {
        Student student = new Student("Max Bespalov");
        UUID subjectUuid = UUID.randomUUID();
        student.addSubject(subjectUuid);
        assertTrue(student.isRegisteredForSubject(subjectUuid));
    }

    @Test
    public void studentDoesNotHaveSubjectThatWasNotAdded() {
        Student student = new Student("Max Bespalov");
        assertFalse(student.isRegisteredForSubject(UUID.randomUUID()));
    }

    @Test
    public void canAddAssessmentForTheSubject() {
        Student student = new Student("Max Bespalov");
        UUID subjectUuid = UUID.randomUUID();
        student.addSubject(subjectUuid);
        Assessment assessment = Assessment.VeryBad;
        student.addAssessment(assessment, subjectUuid);
        assertEquals(assessment, student.getAssessments(subjectUuid).get(0));
    }

    @Test
    public void canRemoveAddedAssessment() {
        Student student = new Student("Max Bespalov");
        UUID subjectUuid = UUID.randomUUID();
        student.addSubject(subjectUuid);
        student.addAssessment(Assessment.Satisfactorily, subjectUuid);
        student.removeAssessmentAt(0, subjectUuid);
        assertEquals(student.getAssessments(subjectUuid).size(), 0);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotRemoveAlreadyRemovedAssessment() {
        Student student = new Student("Max Bespalov");
        UUID subjectUuid = UUID.randomUUID();
        student.addAssessment(Assessment.Bad, subjectUuid);
        student.removeAssessmentAt(0, subjectUuid);
        student.removeAssessmentAt(0, subjectUuid);
    }

    @Test
    public void canAddTwoAssessments() {
        Student student = new Student("Max Bespalov");
        UUID subjectUuid = UUID.randomUUID();
        student.addSubject(subjectUuid);
        student.addAssessment(Assessment.Good, subjectUuid);
        student.addAssessment(Assessment.Great, subjectUuid);
        assertEquals(student.getAssessments(subjectUuid).get(1), Assessment.Great);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotRemoveAssessmentFromIndexHigherOrEqualsAssessmentsSize() {
        Student student = new Student("Max Bespalov");
        UUID subjectUuid = UUID.randomUUID();
        student.addSubject(subjectUuid);
        student.removeAssessmentAt(0, subjectUuid);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotRemoveAssessmentOfRemovedSubject() {
        Student student = new Student("Max Bespalov");
        UUID subjectUuid = UUID.randomUUID();
        student.addAssessment(Assessment.Bad, subjectUuid);
        student.removeSubject(subjectUuid);
        student.removeAssessmentAt(0, subjectUuid);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotRemoveAssessmentAtNegativeIndex() {
        Student student = new Student("Max Bespalov");
        UUID subjectUuid = UUID.randomUUID();
        student.addSubject(subjectUuid);
        student.removeAssessmentAt(-1, subjectUuid);
    }

    @Test
    public void canGetEmptyAssessmentsOfTheStudent() {
        Student student = new Student("Max Bespalov");
        assertEquals(student.getAssessments().size(), 0);
    }

    @Test
    public void canGetAssessments() {
        Student student = new Student("Max Bespalov");
        UUID firstSubjectUuid = UUID.randomUUID();
        UUID secondSubjectUuid = UUID.randomUUID();
        UUID thirdSubjectUuid = UUID.randomUUID();

        student.addSubject(firstSubjectUuid);
        student.addSubject(secondSubjectUuid);
        student.addSubject(thirdSubjectUuid);
        student.addAssessment(Assessment.Good, firstSubjectUuid);
        student.addAssessment(Assessment.Bad, secondSubjectUuid);
        student.addAssessment(Assessment.Great, thirdSubjectUuid);

        List<Assessment> allAssessments = student.getAssessments();
        assertEquals(allAssessments.size(), 3);
        assertTrue(allAssessments.contains(Assessment.Good));
        assertTrue(allAssessments.contains(Assessment.Bad));
        assertTrue(allAssessments.contains(Assessment.Great));
    }
}
