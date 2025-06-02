package com.opensponsor.utils;

import com.opensponsor.entitys.Order;
import com.opensponsor.entitys.Tier;
import com.opensponsor.entitys.User;
import com.opensponsor.enums.E_ORDER_STATUS;
import com.opensponsor.enums.E_PAYMENT_METHOD;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

public class OrderTools {
    private static String generateOrderNo() {
        Instant instant = Instant.now();

        LocalDate currentDate = LocalDate.now();
        return String.join(
            "",
            String.valueOf(currentDate.getYear()),
            String.valueOf(currentDate.getMonth().getValue()),
            String.valueOf(currentDate.getDayOfMonth()),
            String.valueOf(instant.getNano()),
            String.valueOf(Math.random()).replace("0.", "").substring(0, 16)
        );
    }

    public static String persistOrder(Tier tier, E_PAYMENT_METHOD method, User user) {
        Optional<Order> hasOrder = Order.find("tier = ?1 AND user = ?2 AND status = ?3", tier, user, E_ORDER_STATUS.NEW).firstResultOptional();
        if(hasOrder.isPresent()) {
            return hasOrder.get().getOutTradeNo();
        } else {
            String tradeNo = generateOrderNo();
            Duration eightHours = Duration.ofHours(8);
            Order order = new Order();
            order.outTradeNo = tradeNo;
            order.payStatus = false;
            order.paymentMethod = method;
            order.user = user;
            order.status = E_ORDER_STATUS.NEW;
            order.currency = tier.currency;
            order.organization = tier.organization;
            order.tier = tier;
            order.whenExpires = Instant.now().plus(eightHours);
            order.totalAmount = BigDecimal.valueOf(tier.amount.longValue());
            order.persistAndFlush();

            return tradeNo;
        }
    }
}
