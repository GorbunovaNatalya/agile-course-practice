package ru.unn.agile.assessmentsaccounting.model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class AssessmentTableTests {

    @Before
    public void init() {
        table = new AssessmentsTable();
    }

    @After
    public void clear() {
        table = null;
    }

    @Test
    public void canRemoveAddedSubject() {
        String subject = "TDD";
        table.addSubject(subject);
        table.removeSubject(subject);
        assertEquals(table.getSubjects().size(), 0);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotRemoveNotExistingSubject() {
        table.removeSubject("Some subject, that every student hates");
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotRemoveSubjectTwice() {
        String subject = "The worst subject ever";
        table.addSubject(subject);
        table.removeSubject(subject);
        table.removeSubject(subject);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotAddExistingSubject() {
        String subject = "The best subject ever";
        table.addSubject(subject);
        table.addSubject(subject);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotGetAssessmentsOfRemovedSubject() {
        String subject = "Math Analysis";
        table.addSubject(subject);
        table.removeSubject(subject);
        table.getAssessments(subject);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotGetAssessmentsOfNotExistingSubject() {
        table.getAssessments("History");
    }

    @Test
    public void canRenameSubject() {
        String subject = "Agile course";
        table.addSubject(subject);
        String newName = "Very important subject";
        table.renameSubject(subject, newName);
        assertTrue(table.getSubjects().get(0).equals(newName));
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotRenameSubjectIntoSameName() {
        String subject = "Making bugs";
        String student = "Max Bespalov";

        table.addStudent(student);
        table.addSubject(subject);
        table.renameSubject(subject, subject);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotRenameNotExistingSubject() {
        table.renameSubject("Game development", "Best subject");
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotRenameSubjectToEmptyName() {
        String subject = "Very short course";
        table.addSubject(subject);
        table.renameSubject(subject, "");
    }

    @Test
    public void afterRenamingSubjectStudentSubjectsGetRenamed() {
        String student = "Max Bespalov";
        String subject = "Agile course";
        Assessment assessment = Assessment.VeryBad;

        table.addSubject(subject);
        table.addStudent(student);
        table.addAssessment(assessment, student, subject);

        String newName = "Very important subject";
        table.renameSubject(subject, newName);

        assertEquals(table.getAssessmentsForStudent(newName, student).get(0), assessment);
    }

    @Test
    public void canAddStudent() {
        String name = "Max Bespalov";
        table.addStudent(name);

        Set<Student> students = table.getStudents();
        Iterator<Student> iterator = students.iterator();
        assertEquals(students.size(), 1);
        assertTrue(iterator.next().getName().equals(name));
    }

    @Test
    public void canNotAddExistingStudent() {
        String name = "Max Bespalov";
        table.addStudent(name);
        table.addStudent(name);
        assertEquals(table.getStudents().size(), 1);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotAddUnnamedStudent() {
        table.addStudent("");
    }

    @Test
    public void canRemoveAddedStudent() {
        String name = "Max Bespalov";
        table.addStudent(name);
        table.removeStudent(name);
        assertEquals(table.getStudents().size(), 0);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotRemoveNotExistingStudent() {
        table.removeStudent("Albert Einstein");
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotRemoveStudentTwice() {
        String name = "Max Bespalov";
        table.addStudent(name);
        table.removeStudent(name);
        table.removeStudent(name);
    }

    @Test
    public void canRenameStudent() {
        String name = "Max Bespalov";
        table.addStudent(name);
        String newName = "Very lazy student";
        table.renameStudent(name, newName);
        Iterator<Student> iterator = table.getStudents().iterator();
        assertTrue(iterator.next().getName().equals(newName));
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotRenameNotExistingStudent() {
        table.renameStudent("Albert Einstein", "This student was Albert Einstein");
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotRenameStudentToEmptyName() {
        String name = "Max Bespalov";
        table.addStudent(name);
        table.renameStudent(name, "");
    }

    @Test
    public void canAddAssessmentToStudent() {
        String subject = "Agile course";
        String studentName = "Max Bespalov";
        Assessment assessment = Assessment.VeryBad;

        table.addSubject(subject);
        table.addStudent(studentName);
        table.addAssessment(assessment, studentName, subject);

        assertEquals(table.getAssessmentsForStudent(subject, studentName).get(0), assessment);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotAddAssessmentForNotExistingStudent() {
        String subject = "Agile course";
        table.addAssessment(Assessment.VeryBad, "Mystical student", subject);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotAddAssessmentForNotExistingSubject() {
        String studentName = "Max Bespalov";

        table.addStudent(studentName);
        table.addAssessment(Assessment.Great, studentName, "Game development");
    }

    @Test
    public void canAddAssessmentsToTwoStudents() {
        String subject = "Agile course";
        Assessment firstAssessment = Assessment.Bad;
        Assessment secondAssessment = Assessment.VeryGood;
        Assessment thirdAssessment = Assessment.Satisfactorily;

        table.addSubject(subject);
        table.addStudent("Max");
        table.addStudent("Ivan");
        table.addAssessment(firstAssessment, "Max", subject);
        table.addAssessment(secondAssessment, "Ivan", subject);
        table.addAssessment(thirdAssessment, "Ivan", subject);

        assertEquals(table.getAssessmentsForStudent(subject, "Max").get(0), firstAssessment);
        assertEquals(table.getAssessmentsForStudent(subject, "Ivan").get(0), secondAssessment);
        assertEquals(table.getAssessmentsForStudent(subject, "Ivan").get(1), thirdAssessment);
    }

    @Test
    public void canAddAssessmentsOnTwoSubjects() {
        String firstSubject = "Writing usefull tests";
        String secondSubject = "Writing bad code";
        String studentName = "Max Bespalov";
        Assessment firstAssessment = Assessment.VeryBad;
        Assessment secondAssessment = Assessment.Great;

        table.addSubject(firstSubject);
        table.addSubject(secondSubject);
        table.addStudent(studentName);
        table.addAssessment(firstAssessment, studentName, firstSubject);
        table.addAssessment(secondAssessment, studentName, secondSubject);

        assertEquals(table.getAssessmentsForStudent(firstSubject, studentName).get(0),
                firstAssessment);
        assertEquals(table.getAssessmentsForStudent(secondSubject, studentName).get(0),
                secondAssessment);
    }

    @Test
    public void canRemoveAssessment() {
        String subject = "Agile course";
        String studentName = "Max Bespalov";
        Assessment assessment = Assessment.Great;

        table.addSubject(subject);
        table.addStudent(studentName);
        table.addAssessment(assessment, studentName, subject);
        table.removeAssessment(0, studentName, subject);

        assertEquals(table.getAssessmentsForStudent(subject, studentName).size(), 0);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotRemoveAssessmentTwice() {
        String subject = "Java development";
        String studentName = "Max Bespalov";
        Assessment assessment = Assessment.VeryGood;

        table.addSubject(subject);
        table.addStudent(studentName);
        table.addAssessment(assessment, studentName, subject);
        table.removeAssessment(0, studentName, subject);
        table.removeAssessment(0, studentName, subject);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotRemoveNotExistingAssessment() {
        String subject = "Haskell development";
        String studentName = "Max Bespalov";
        Assessment assessment = Assessment.Great;

        table.addSubject(subject);
        table.addStudent(studentName);
        table.removeAssessment(0, studentName, subject);
    }

    @Test
    public void canChangeAssessment() {
        String subject = "Agile course";
        String studentName = "Max Bespalov";
        Assessment assessment = Assessment.Great;
        Assessment newAssessment = Assessment.VeryBad;

        table.addSubject(subject);
        table.addStudent(studentName);
        table.addAssessment(assessment, studentName, subject);
        table.changeAsessment(0, newAssessment, studentName, subject);

        assertEquals(table.getAssessmentsForStudent(subject, studentName).get(0),
                newAssessment);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotChangeNotExistingAssessment() {
        String subject = "Code reviewong";
        String studentName = "Max Bespalov";

        table.addSubject(subject);
        table.addStudent(studentName);
        table.changeAsessment(0, Assessment.VeryBad, studentName, subject);
    }

    @Test
    public void canChangeAssessmentTwice() {
        String subject = "Java understanding";
        String studentName = "Max Bespalov";
        Assessment assessment = Assessment.VeryBad;
        Assessment secondAssessment = Assessment.Great;
        Assessment thirdAssessment = Assessment.Bad;


        table.addSubject(subject);
        table.addStudent(studentName);
        table.addAssessment(assessment, studentName, subject);
        table.changeAsessment(0, secondAssessment, studentName, subject);
        table.changeAsessment(0, thirdAssessment, studentName, subject);

        assertEquals(table.getAssessmentsForStudent(subject, studentName).get(0),
                thirdAssessment);
    }

    @Test
    public void canAddAssessmentToGroupOfStudents() {
        String subject = "Agile course";
        String firstStudentName = "Max Bespalov";
        String secondStudentName = "Never seen student";
        List<String> students = new ArrayList<String>();
        students.add(firstStudentName);
        students.add(secondStudentName);
        Assessment assessment = Assessment.VeryBad;

        table.addSubject(subject);
        table.addStudent(firstStudentName);
        table.addStudent(secondStudentName);
        table.addAssessment(assessment, students, subject);

        assertEquals(table.getAssessmentsForStudent(subject, firstStudentName).get(0),
                assessment);
        assertEquals(table.getAssessmentsForStudent(subject, secondStudentName).get(0),
                assessment);
    }

    @Test
    public void canGetEmptySubjectAssessments() {
        String subject = "Agile course";
        table.addSubject(subject);
        assertNotNull(table.getAssessments(subject));
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotGetAssessmentsForNotExistingSubject() {
        table.getAssessments("Game development");
    }

    @Test
    public void canGetAssessmentForSubject() {
        String subject = "Agile course";
        String firstStudentName = "Max Bespalov";
        String secondStudentName = "Ivan Ivanov";
        Assessment firstAssessment = Assessment.VeryBad;
        Assessment secondAssessment = Assessment.Great;

        table.addSubject(subject);
        table.addStudent(firstStudentName);
        table.addStudent(secondStudentName);
        table.addAssessment(firstAssessment, firstStudentName, subject);
        table.addAssessment(secondAssessment, secondStudentName, subject);

        List<Assessment> assessments = table.getAssessments(subject);
        assertEquals(assessments.size(), 2);
        assertTrue(assessments.contains(firstAssessment));
        assertTrue(assessments.contains(secondAssessment));
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotGetAssessmentsForStudentThatDoesNotHaveAnyForThisSubject() {
        String subject = "Agile course";
        String student = "Max Bespalov";
        table.addSubject(subject);
        table.addStudent(student);
        table.getAssessmentsForStudent(subject, student);
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotGetAssessmentsOfNotExistingStudent() {
        String subject = "Theory of relativity";
        table.addSubject(subject);
        table.getAssessmentsForStudent(subject, "Albert Einstein");
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotGetStudentAssessmentsForNotExistingSubject() {
        String student = "Max Bespalov";
        table.addStudent(student);
        table.getAssessmentsForStudent("Game development", student);
    }

    @Test
    public void canGetStudentAssessments() {
        String subject = "Writing hundreds of test code rows";
        String student = "Max Bespalov";
        Assessment firstAssessment = Assessment.VeryGood;
        Assessment secondAssessment = Assessment.Perfect;

        table.addStudent(student);
        table.addSubject(subject);
        table.addAssessment(firstAssessment, student, subject);
        table.addAssessment(secondAssessment, student, subject);

        List<Assessment> assessments = table.getAssessmentsForStudent(subject, student);
        assertEquals(assessments.size(), 2);
        assertTrue(assessments.contains(firstAssessment));
        assertTrue(assessments.contains(secondAssessment));
    }

    @Test
    public void canGetAverageAssessmentForStudentOnSubject() {
        String subject = "Agile Course";
        String student = "Max Bespalov";
        Assessment firstAssessment = Assessment.VeryBad;
        Assessment secondAssessment = Assessment.Bad;
        double average = 1.5;

        table.addStudent(student);
        table.addSubject(subject);
        table.addAssessment(firstAssessment, student, subject);
        table.addAssessment(secondAssessment, student, subject);

        assertTrue(table.getAverageAssessment(student, subject) == average);
    }

    @Test
    public void canGetAverageAssessmentForStudentOnSubjectWithSingleAssessment() {
        String subject = "Reaching the end of tests";
        String student = "Max Bespalov";
        Assessment assessment = Assessment.VeryGood;

        table.addStudent(student);
        table.addSubject(subject);
        table.addAssessment(assessment, student, subject);

        assertTrue(table.getAverageAssessment(student, subject) == assessment.getMark());
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotGetAverageAssessmentForStudentOnSubjectsWithoutAssessments() {
        String subject = "Giving up";
        String student = "Max Bespalov";

        table.addStudent(student);
        table.addSubject(subject);
        table.getAverageAssessment(student, subject);
    }

    @Test
    public void canGetAverageAssessmentForStudent() {
        String firstSubject = "Letting you down";
        String secondSubject = "Running around";
        String student = "Max Bespalov";
        Assessment firstAssessment = Assessment.VeryGood;
        Assessment secondAssessment = Assessment.Perfect;
        double average = 6;

        table.addStudent(student);
        table.addSubject(firstSubject);
        table.addSubject(secondSubject);
        table.addAssessment(firstAssessment, student, firstSubject);
        table.addAssessment(secondAssessment, student, secondSubject);

        assertTrue(table.getAverageAssessmentForStudent(student) == average);
    }

    @Test
    public void canGetAverageAssessmentForStudentWithSingleAssessment() {
        String subject = "Deserting you";
        String student = "Max Bespalov";
        Assessment assessment = Assessment.Good;

        table.addStudent(student);
        table.addSubject(subject);
        table.addAssessment(assessment, student, subject);

        assertTrue(table.getAverageAssessmentForStudent(student) == assessment.getMark());
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotGetAverageAssessmentForStudentWithoutAssessments() {
        String student = "Max Bespalov";

        table.addStudent(student);
        table.getAverageAssessmentForStudent(student);
    }

    @Test
    public void canGetAverageAssessmentForSubject() {
        String subject = "Making you cry";
        String firstStudent = "Max Bespalov";
        String secondStudent = "Ivan Ivanov";
        Assessment firstAssessment = Assessment.Perfect;
        Assessment secondAssessment = Assessment.VeryBad;
        double average = 4;

        table.addStudent(firstStudent);
        table.addStudent(secondStudent);
        table.addSubject(subject);
        table.addAssessment(firstAssessment, firstStudent, subject);
        table.addAssessment(secondAssessment, secondStudent, subject);

        assertTrue(table.getAverageAssessmentForSubject(subject) == average);
    }

    @Test
    public void canGetAverageAssessmentForSubjectWithSingleAssessment() {
        String subject = "Saying goodbye";
        String student = "Max Bespalov";
        Assessment assessment = Assessment.Perfect;

        table.addStudent(student);
        table.addSubject(subject);
        table.addAssessment(assessment, student, subject);

        assertTrue(table.getAverageAssessmentForSubject(subject) == assessment.getMark());
    }

    @Test(expected = InvalidParameterException.class)
    public void canNotGetAverageAssessmentForSubjectWithoutAssessments() {
        String subject = "Going to tell a lie and hurting you";

        table.addSubject(subject);
        table.getAverageAssessmentForSubject(subject);
    }

    @Test
    public void canRemoveSubjectAfterAddingNewStudent() {
        String student = "Max Bespalov";
        String subject = "Some new exciting subject";

        table.addStudent(student);
        table.addSubject(subject);
        UUID subjectUuid = table.getSubjectUUID(subject);
        table.addAssessment(Assessment.Good, student, subject);

        String newStudent = "Excited student";
        table.addStudent(newStudent);

        table.removeSubject(subject);

        Iterator<Student> iterator = table.getStudents().iterator();
        assertFalse(iterator.next().isRegisteredForSubject(subjectUuid));
        assertFalse(iterator.next().isRegisteredForSubject(subjectUuid));
    }

    private AssessmentsTable table;
}
