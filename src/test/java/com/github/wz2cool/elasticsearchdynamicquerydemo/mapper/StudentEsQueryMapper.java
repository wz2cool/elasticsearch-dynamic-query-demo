package com.github.wz2cool.elasticsearchdynamicquerydemo.mapper;

import com.github.wz2cool.elasticsearch.repository.ElasticsearchExtRepository;
import com.github.wz2cool.elasticsearchdynamicquerydemo.model.StudentES;

public interface StudentEsQueryMapper extends ElasticsearchExtRepository<StudentES, Long> {
}
