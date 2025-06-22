package com.opensponsor.repositorys;

import com.opensponsor.entitys.Order;
import com.opensponsor.entitys.Tier;
import com.opensponsor.enums.E_ORGANIZATION_TYPE;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class OrderRepository extends RepositoryBase<Tier> {

    public PanacheQuery<Order> filter(Order params) {
        PanacheQuery<Order> query = Order.findAll();
        query.filter("order(=payStatus)", Parameters.with("payStatus", true));
        if(params.userId != null) {
            query.filter("order(=userId)", Parameters.with("userId", UUID.fromString(params.userId)));
        }
        if(params.organizationId != null) {
            query.filter("order(=organizationId)", Parameters.with("organizationId", UUID.fromString(params.organizationId)));
        }
        if(params.status != null) {
            query.filter("order(=status)", Parameters.with("status", params.status));
        }
        if(params.startDate != null && params.endDate != null) {
            query.filter("order(=inDateRange)", Parameters.with("startDate", params.startDate));
            query.filter("order(=inDateRange)", Parameters.with("endDate", params.endDate));
        }

        return query;
    }

}
