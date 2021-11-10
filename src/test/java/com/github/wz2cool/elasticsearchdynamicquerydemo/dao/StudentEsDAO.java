package com.github.wz2cool.elasticsearchdynamicquerydemo.dao;

import com.github.wz2cool.elasticsearch.model.LogicPagingResult;
import com.github.wz2cool.elasticsearch.query.DynamicQuery;
import com.github.wz2cool.elasticsearch.query.LogicPagingQuery;
import com.github.wz2cool.elasticsearchdynamicquerydemo.mapper.StudentEsQueryMapper;
import com.github.wz2cool.elasticsearchdynamicquerydemo.model.StudentES;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StudentEsDAO {

    @Resource
    private StudentEsQueryMapper studentEsMapper;

    public void save(StudentES... students) {
        studentEsMapper.saveAll(Arrays.asList(students));
    }

    public void deleteAll() {
        studentEsMapper.deleteAll();
    }

    public void deleteByIds(Long... ids) {
        final List<StudentES> collect = Arrays.stream(ids).map(x -> {
            StudentES studentES = new StudentES();
            studentES.setId(x);
            return studentES;
        }).collect(Collectors.toList());
        studentEsMapper.deleteAll(collect);
    }

    public LogicPagingResult<StudentES> selectByLogicPaging(LogicPagingQuery<StudentES> logicPagingQuery) {
        return studentEsMapper.selectByLogicPaging(logicPagingQuery);
    }

    public List<StudentES> selectByDynamicQuery(DynamicQuery<StudentES> dynamicQuery) {
        return studentEsMapper.selectByDynamicQuery(dynamicQuery);
    }

    public void deleteByDynamicQuery(DynamicQuery<StudentES> dynamicQuery) {
        studentEsMapper.deleteByDynamicQuery(dynamicQuery);
    }
}
