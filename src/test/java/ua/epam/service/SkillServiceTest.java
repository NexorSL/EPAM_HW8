package ua.epam.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import ua.epam.model.Skill;

import static org.junit.Assert.assertEquals;

@Slf4j
public class SkillServiceTest {

    private SkillService skillService;
    private Long skillId;
    private String skillName;
    private Skill testSkillCreate;
    private Skill testSkillUpdate;

    @Before
    public void setUp() {
        skillId = 5L;
        skillName = "JS";
        skillService = new SkillService();
        testSkillCreate = new Skill(skillId, skillName);
        testSkillUpdate = new Skill(4L, skillName);
    }


    @Test
    public void testCreateSkill() {
        Skill created = testSkillCreate;
        skillService.create(testSkillCreate);
        Skill res = skillService.getById(skillId);
        assertEquals(res, created);
    }

    @Test
    public void testDeleteSkill() {
        boolean isDeleted = true;
        boolean res = skillService.delete(skillId);
        assertEquals(isDeleted, res);
    }

    @Test
    public void testUpdateSkill() {
        skillService.update(testSkillUpdate, 4L);
        Skill res = skillService.getById(4L);
        assertEquals(testSkillUpdate, res);
    }
}
