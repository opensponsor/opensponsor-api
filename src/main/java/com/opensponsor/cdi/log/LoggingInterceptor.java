package com.opensponsor.cdi.log;

import com.opensponsor.entitys.UserToken;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.SecurityContext;
import org.jboss.logging.Logger;


@Logged()
@Priority(2020)
@Interceptor
public class LoggingInterceptor {

    @Inject
    SecurityContext ctx;


    @Inject
    HttpHeaders headers;

    @Inject
    Logger logger;

    @AroundInvoke
    Object logInvocation(InvocationContext context) throws Exception {
        // ...log before
        Object ret = context.proceed();
        // return Response
        //     .status(200)
        //     .entity("fdsfdsfd")
        //     .build();
        // ...log after
        logger.info(context.getMethod());
        logger.info(ctx.getUserPrincipal());
        logger.info("\n");
        String token = headers.getHeaderString("Authorization").replace(ctx.getAuthenticationScheme(), "").trim();
        logger.info(token);
        System.out.println(UserToken.find("token", token).firstResultOptional().isPresent());

        return ret;
    }

}
