package com.github.wz2cool.elasticsearchdynamicquerydemo.dao;

import com.github.wz2cool.elasticsearch.query.DynamicQuery;
import com.github.wz2cool.elasticsearchdynamicquerydemo.mapper.TestExampleEsMapper;
import com.github.wz2cool.elasticsearchdynamicquerydemo.model.TestExampleES;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class TestExampleEsDAO {

    @Resource
    private TestExampleEsMapper testExampleEsMapper;

    public List<TestExampleES> selectByDynamicQuery(DynamicQuery<TestExampleES> query) {
        return testExampleEsMapper.selectByDynamicQuery(query);
    }

    public void save(List<TestExampleES> testExampleESList) {
        testExampleEsMapper.saveAll(testExampleESList);
    }
}
