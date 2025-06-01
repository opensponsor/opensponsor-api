package com.opensponsor.resources;

import com.alipay.api.AlipayApiException;
import com.opensponsor.entitys.Order;
import com.opensponsor.entitys.Tier;
import com.opensponsor.payload.ResultOfData;
import com.opensponsor.payload.TradePagePayBodyForAliPay;
import com.opensponsor.repositorys.UserRepository;
import com.opensponsor.utils.alipay.AliPayTrade;
import com.opensponsor.utils.wechat.WechatPayTrade;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.resteasy.annotations.Form;

import java.util.Optional;

@Path("/payment-wechat")
public class WechatPaymentResource {

    @Inject
    public UserRepository userRepository;

    @Inject
    public WechatPayTrade wechatPayTrade;

    @Transactional
    protected Order checkTrade(TradePagePayBodyForAliPay data)  throws AlipayApiException {
        Order t = null;
        if(AliPayTrade.queryTrade(data).isSuccess()) {
            if(AliPayTrade.queryTrade(data).getTradeStatus().equals("TRADE_SUCCESS")) {
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
    public Response pay(@Valid Tier tier) {
        return Response
            .status(Response.Status.OK.getStatusCode())
            .entity(new ResultOfData<>(
                wechatPayTrade.generateOrder( Tier.findById(tier.id), userRepository.authUser() )
            ))
            .build();
    }

    /**
     * 验证签名 https://pay.weixin.qq.com/doc/global/v3/zh/4012354989
     * @param data
     * @return String
     * @throws AlipayApiException
     */
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

    @APIResponse(
        responseCode = "200",
        description = "Query wechatpay order status",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ResultOfData.class,
                properties = {
                    @SchemaProperty(name = "data", type = SchemaType.BOOLEAN, implementation = Boolean.class),
                }
            )
        )
    )
    @GET
    @Path("queryOrder")
    @RolesAllowed({"User"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response queryOrder(@QueryParam("outTradeNo") String outTradeNo) {
        return Response.status(Response.Status.OK)
            .entity(new ResultOfData<>(wechatPayTrade.queryOrderForOutTradeNo(outTradeNo)))
            .build();
    }

}
