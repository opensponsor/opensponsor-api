package com.opensponsor.payload;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;

import java.util.List;

public class ResultOfPaging {

    public long currentPageNumber;
    public long lastPageNumber;
    public long pageSize;
    public long totalRecords;
    public String message = "ok";

    public Long code = 0L;
    public List<PanacheEntityBase> records;

    public ResultOfPaging(PanacheQuery<PanacheEntityBase> panacheQuery, Page page) {
        PanacheQuery<PanacheEntityBase> query = panacheQuery.page(page);
        currentPageNumber = page.index + 1;
        lastPageNumber = query.pageCount();
        pageSize = query.count();
        totalRecords = panacheQuery.count();
        records = query.list();
    }

    public ResultOfPaging code(long code) {
        this.code = code;
        return this;
    }

    public ResultOfPaging message(String message) {
        this.message = message;
        return this;
    }
}
