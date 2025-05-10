package com.opensponsor.resources;

import com.alipay.api.AlipayApiException;
import com.opensponsor.entitys.Order;
import com.opensponsor.entitys.Tier;
import com.opensponsor.payload.ResultOfData;
import com.opensponsor.payload.TradePagePayBodyForAliPay;
import com.opensponsor.repositorys.UserRepository;
import com.opensponsor.utils.alipay.AlipayTradePay;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.annotations.Form;

import java.util.Optional;

@Path("/payment")
public class AliPaymentResource {

    @Inject
    public UserRepository userRepository;

    @Inject
    public AlipayTradePay alipayTradePay;

    @Transactional
    protected Order checkTrade(TradePagePayBodyForAliPay data)  throws AlipayApiException {
        Order t = null;
        if(AlipayTradePay.queryTrade(data).isSuccess()) {
            if(AlipayTradePay.queryTrade(data).getTradeStatus().equals("TRADE_SUCCESS")) {
                Optional<Order> trade = Order.find("tradeNo", data.out_trade_no).firstResultOptional();
                if(trade.isPresent() && !trade.get().payStatus) {
                    t = trade.get();

                    if(String.valueOf(t.totalAmount).equals(data.total_amount)) {
                        t.payStatus = true;
                        t.persistAndFlush();
                    }
                } else if(trade.isPresent()) {
                    t = trade.get();
                }
            }
        }

        return t;
    }

    @APIResponse(
        responseCode = "200",
        description = "Example List",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ResultOfData.class,
                properties = {
                    @SchemaProperty(name = "data", type = SchemaType.OBJECT, implementation = String.class),
                }
            )
        )
    )
    @POST()
    @Transactional
    @RolesAllowed({"User"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response pay(@Valid Tier body) throws AlipayApiException {
        return Response
            .status(Response.Status.OK.getStatusCode())
            .entity(new ResultOfData<>(alipayTradePay.generateOrder( String.valueOf(body.amount), userRepository.authUser() )))
            .build();
    }

    @APIResponse(
        responseCode = "200",
        description = "Example List",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ResultOfData.class,
                properties = {
                    @SchemaProperty(name = "data", type = SchemaType.OBJECT, implementation = Order.class),
                }
            )
        )
    )
    @POST()
    @Path("callback")
    @Produces(MediaType.APPLICATION_JSON)
    public Response callback(@Valid TradePagePayBodyForAliPay data) throws AlipayApiException {
        Order t = checkTrade(data);

        return Response
            .status(Response.Status.OK.getStatusCode())
            .entity(new ResultOfData<>(t))
            .build();
    }

    @POST()
    @Path("notify")
    @Consumes({MediaType.APPLICATION_FORM_URLENCODED})
    @Produces(MediaType.TEXT_PLAIN)
    public String notify(@Form TradePagePayBodyForAliPay data) throws AlipayApiException {
        Order t = checkTrade(data);

        if(t != null && t.payStatus) {
            return "success";
        } else {
            return "fail";
        }
    }
}
