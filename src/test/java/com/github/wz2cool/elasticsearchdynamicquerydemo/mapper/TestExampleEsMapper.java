package com.github.wz2cool.elasticsearchdynamicquerydemo.mapper;

import com.github.wz2cool.elasticsearch.repository.ElasticsearchExtRepository;
import com.github.wz2cool.elasticsearchdynamicquerydemo.model.TestExampleES;

public interface TestExampleEsMapper extends ElasticsearchExtRepository<TestExampleES, Long> {
}
