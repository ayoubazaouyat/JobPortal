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
    void list_is_empty_when_student_is_created() {
        assertEquals(0, s1.getSkills().size());
    }

    @Test
    void list_is_incremented_when_skill_is_added() {
        assertEquals(0, s1.getSkills().size());
        s1.addSkill("Programming");
        assertEquals(1, s1.getSkills().size());
    }

    @Test
    void list_is_decremented_when_skill_is_deleted() {
        assertEquals(0, s1.getSkills().size());
        s1.addSkill("Programming");
        assertEquals(1, s1.getSkills().size());
        s1.deleteSkill("Programming");
        assertEquals(0, s1.getSkills().size());
    }

    @Test
    void skill_is_correctly_added() {
        s1.addSkill("Programming");
        assertEquals("Programming", s1.getSkills().get(0));
        s1.addSkill("Hacking");
        assertEquals("Hacking", s1.getSkills().get(1));
    }

    @Test
    void skill_is_correctly_deleted() {
        s1.addSkill("Programming");
        assertEquals("Programming", s1.getSkills().get(0));
        s1.addSkill("Hacking");
        assertEquals("Hacking", s1.getSkills().get(1));
        s1.deleteSkill("Programming");
        assertEquals("Hacking", s1.getSkills().get(0));
    }
}
