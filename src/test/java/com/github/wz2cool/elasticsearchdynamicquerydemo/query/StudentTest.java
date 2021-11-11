package com.github.wz2cool.elasticsearchdynamicquerydemo.query;

import com.github.wz2cool.elasticsearch.query.DynamicQuery;
import com.github.wz2cool.elasticsearchdynamicquerydemo.TestApplication;
import com.github.wz2cool.elasticsearchdynamicquerydemo.dao.StudentEsDAO;
import com.github.wz2cool.elasticsearchdynamicquerydemo.model.ClassroomES;
import com.github.wz2cool.elasticsearchdynamicquerydemo.model.StudentES;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.wz2cool.elasticsearch.helper.BuilderHelper.asc;
import static com.github.wz2cool.elasticsearch.helper.BuilderHelper.mustNot;
import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestApplication.class)
public class StudentTest {

    private static final Logger LOG = LoggerFactory.getLogger(StudentTest.class);

    @Resource
    private StudentEsDAO studentEsDAO;

    @Before
    public void init() {

        mockData();
    }

    private void mockData() {
        List<StudentES> data = new ArrayList<>();

        ClassroomES classroomES1 = new ClassroomES();
        classroomES1.setId(1L);
        classroomES1.setName("classroom1");

        ClassroomES classroomES2 = new ClassroomES();
        classroomES2.setId(2L);
        classroomES2.setName("classroom2");

        for (int i = 1; i < 100; i++) {
            StudentES studentES = new StudentES();
            studentES.setId((long) i);
            studentES.setName("student" + i);
            studentES.setAge(10);
            if (i % 2 == 0) {
                studentES.setClassroom(classroomES1);
            } else {
                studentES.setClassroom(classroomES2);
            }
            data.add(studentES);
        }
        studentEsDAO.save(data.toArray(new StudentES[0]));
    }

    @Test
    public void testTerm() {
        DynamicQuery<StudentES> filterQuery = DynamicQuery.createQuery(StudentES.class)
                .and(StudentES::getId, o -> o.terms(1L, 3L, 5L))
                .orderBy(StudentES::getId, asc());
        final List<StudentES> studentES = studentEsDAO.selectByDynamicQuery(filterQuery);
        assertEquals(3, studentES.size());
        assertEquals(Long.valueOf(1), studentES.get(0).getId());
        assertEquals(Long.valueOf(3), studentES.get(1).getId());
        assertEquals(Long.valueOf(5), studentES.get(2).getId());
    }

    @Test
    public void testMustNot() {
        DynamicQuery<StudentES> filterQuery = DynamicQuery.createQuery(StudentES.class)
                .and(StudentES::getId, o -> o.terms(1L, 3L, 5L))
                .orderBy(StudentES::getId, asc());
        final List<StudentES> studentES = studentEsDAO.selectByDynamicQuery(filterQuery);
        assertEquals(3, studentES.size());

        DynamicQuery<StudentES> mustNotQuery = DynamicQuery.createQuery(StudentES.class)
                .and(mustNot(), StudentES::getId, o -> o.terms(1L, 3L, 5L))
                .orderBy(StudentES::getId, asc());
        final List<StudentES> studentES1 = studentEsDAO.selectByDynamicQuery(mustNotQuery);
        for (StudentES es : studentES1) {
            assertFalse(Arrays.asList(1L, 3L, 5L).contains(es.getId()));
        }
    }

    @Test
    public void testObject() {
        final boolean debugEnabled = LOG.isDebugEnabled();
        if (debugEnabled) {
            LOG.debug("1111");
        }
        DynamicQuery<StudentES> query = DynamicQuery.createQuery(StudentES.class)
                .and(StudentES::getClassroom, ClassroomES::getId, o -> o.term(1L));
        final List<StudentES> studentESList = studentEsDAO.selectByDynamicQuery(query);
        assertTrue(studentESList.size() > 0);
        for (StudentES studentES : studentESList) {
            assertEquals(Long.valueOf(1), studentES.getClassroom().getId());
        }
    }

    @Test
    public void testNested() {
        DynamicQuery<StudentES> query = DynamicQuery.createQuery(StudentES.class)
                .and(StudentES::getName, o -> o.term("student1"))
                .and("student1", o -> o.multiMatch(StudentES::getName, StudentES::getNameWide))
                .highlightMapping(StudentES::getName, StudentES::setNameHit)
                .highlightMapping(StudentES::getNameWide, StudentES::setNameWideHit);
        final List<StudentES> studentESList = studentEsDAO.selectByDynamicQuery(query);
        assertEquals(1, studentESList.size());
        assertEquals(Long.valueOf(1), studentESList.get(0).getId());
    }
}
