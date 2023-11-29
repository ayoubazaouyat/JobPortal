package teapot.collat_hbrs;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import teapot.collat_hbrs.backend.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class StudentTest {

    Student s1;
    String skill1 = "Programming";
    String skill2 = "Hacking";

    @BeforeEach
    public void setup() {
        s1 = new Student("mmuster", "a@example.com", "Musterman", "Max",
                "Musterstrasse 41a 53757 Sankt Augustin", "01513142271",
                "Computer Science");
    }

    @AfterEach
    public void teardown() {
        s1 = null;
    }

    /*
    We are not testing getters and setters here, only list functionality so far
     */

    @Test
    void listIsEmptyWhenStudentIsCreated() {
        assertEquals(0, s1.getSkills().size());
    }

    @Test
    void listIsIncrementedWhenSkillIsAdded() {
        assertEquals(0, s1.getSkills().size());
        s1.addSkill(skill1);
        assertEquals(1, s1.getSkills().size());
    }

    @Test
    void listIsDecrementedWhenSkillIsDeleted() {
        assertEquals(0, s1.getSkills().size());
        s1.addSkill(skill1);
        assertEquals(1, s1.getSkills().size());
        s1.deleteSkill(skill1);
        assertEquals(0, s1.getSkills().size());
    }

    @Test
    void skillIsCorrectlyAdded() {
        s1.addSkill(skill1);
        assertEquals(skill1, s1.getSkills().get(0));
        s1.addSkill(skill2);
        assertEquals(skill2, s1.getSkills().get(1));
    }

    @Test
    void skillIsCorrectlyDeleted() {
        s1.addSkill(skill1);
        assertEquals(skill1, s1.getSkills().get(0));
        s1.addSkill(skill2);
        assertEquals(skill2, s1.getSkills().get(1));
        s1.deleteSkill(skill1);
        assertEquals(skill2, s1.getSkills().get(0));
    }
}
