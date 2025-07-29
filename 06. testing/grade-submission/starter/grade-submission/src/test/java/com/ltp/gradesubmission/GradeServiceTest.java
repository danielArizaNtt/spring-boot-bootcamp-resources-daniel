package com.ltp.gradesubmission;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.ltp.gradesubmission.pojo.Grade;
import com.ltp.gradesubmission.repository.GradeRepository;
import com.ltp.gradesubmission.service.GradeService;

@RunWith(MockitoJUnitRunner.class)
public class GradeServiceTest {
    
    @Mock
    private GradeRepository gradeRepository;

    @InjectMocks
    private GradeService gradeService;

    @Test
    public void getGradesFromRepoTest() {
        // Arrange
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(
            new Grade("Harry", "Potions", "C-"),
            new Grade("Hermione", "Arithmancy", "A+")
        ));
        
        // Act
        List<Grade> result = gradeService.getGrades();

        // Assert
        assertEquals("Harry", result.get(0).getName());
        assertEquals("Arithmancy", result.get(1).getSubject());
    }

    @Test
    public void getGradeIndexTest() {
        // Arrange
        Grade grade = new Grade("Harry", "Potions", "C-");

        when(gradeRepository.getGrade(0)).thenReturn(grade);

        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(
            grade
        ));

        int valid = gradeService.getGradeIndex(grade.getId());
        int notFound = gradeService.getGradeIndex("123");

        assertEquals(0, valid);
        assertEquals(Constants.NOT_FOUND, notFound);
    }

    @Test
    public void getGradeByIdTest() {
        // Test whether gradeService.getGradeById returns correct grade when given an ID

        // Arrange
        Grade grade = new Grade("Harry", "Potions", "C-");
        when(gradeRepository.getGrade(0)).thenReturn(grade);
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));

        //Act
        String gradeId = grade.getId();
        String randomId = "123";

        Grade validGradeById = gradeService.getGradeById(gradeId);
        Grade notFoundGradeById = gradeService.getGradeById(randomId); 

        // Assert
        assertEquals(grade, validGradeById);

        assertNotNull(notFoundGradeById);  // A new Grade should be returned
        assertNull(notFoundGradeById.getName());  // New Grade has no name set
        assertNull(notFoundGradeById.getSubject()); // No subject set
        assertNull(notFoundGradeById.getScore()); // No score set
    }

    @Test
    public void submitGradeTest() {
        // Test whether submit grade actually adds a grade

        // Arrange
        Grade grade = new Grade("Harry", "Potions", "C-");
        when(gradeRepository.getGrade(0)).thenReturn(grade);
        when(gradeRepository.getGrades()).thenReturn(Arrays.asList(grade));
        // when(gradeRepository.addGrade(grade))


        // Act
        Grade newGrade = new Grade("Hermione", "Arithmancy", "A+"); // POST
        grade.setScore("B"); // PUT

        gradeService.submitGrade(newGrade);
        gradeService.submitGrade(grade);

        // Assert
        verify(gradeRepository, times(1)).addGrade(newGrade);
        verify(gradeRepository, times(1)).updateGrade(grade, 0);
    }
}
