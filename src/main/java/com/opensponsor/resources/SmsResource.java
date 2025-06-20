package com.opensponsor.resources;

import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.opensponsor.entitys.User;
import com.opensponsor.payload.ResultOfData;
import com.opensponsor.payload.SendCodeBody;
import com.opensponsor.payload.SendSmsResponseAliyun;
import com.opensponsor.repositorys.UserRepository;
import com.opensponsor.utils.SmsTools;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.media.SchemaProperty;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/sms")
public class SmsResource {
    @Inject
    SmsTools smsTools;

    @Inject
    UserRepository userRepository;

    @Tag(name = "sms", description = "send sms")
    @Operation(summary = "send verification code")
    @APIResponse(
        responseCode = "200",
        description = "send successful",
        content = @Content(
            mediaType = "application/json",
            schema = @Schema(
                implementation = ResultOfData.class,
                properties = {
                    @SchemaProperty(name = "data", type = SchemaType.OBJECT, implementation = SendSmsResponseAliyun.class),
                }
            )
        )
    )
    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("verifyCode")
    public Response verifyCode(@Valid SendCodeBody body) throws Exception {
        SendSmsResponse sendSmsResponse = smsTools.sendVerifyCode(body);

        int statusCode = Response.Status.OK.getStatusCode();
        String message = "发送成功, 请查收";
        if(sendSmsResponse.getBody().getCode().equals("isv.BUSINESS_LIMIT_CONTROL")) {
            message = "发送频繁, 请稍后发送";
            statusCode = Response.Status.TOO_MANY_REQUESTS.getStatusCode();
        } else if(!sendSmsResponse.getBody().getCode().equalsIgnoreCase("ok")) {
            message = "发送异常，" + sendSmsResponse.getBody().getCode();
            statusCode = Response.Status.BAD_REQUEST.getStatusCode();
        }

        return Response
                .status(sendSmsResponse.getStatusCode())
                .entity(new ResultOfData<>(sendSmsResponse).code(statusCode).message(message))
                .build();
    }


    @POST()
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("verifyCodeToSelf")
    @RolesAllowed({"User"})
    public Response verifyCodeToSelf() throws Exception {
        SendCodeBody body = new SendCodeBody();
        User u = this.userRepository.authUser(true);
        body.phoneNumber = u.phoneNumber;
        body.countryCode = u.countryCode;

        return this.verifyCode(body);
    }
}
