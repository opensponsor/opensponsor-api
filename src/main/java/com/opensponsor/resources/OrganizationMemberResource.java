package com.opensponsor.resources;


import com.opensponsor.entitys.Member;
import com.opensponsor.entitys.Organization;
import com.opensponsor.payload.ResultOfArray;
import com.opensponsor.repositorys.OrganizationRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

@Path("/organization-member/{slug}")
public class OrganizationMemberResource {
    @Inject
    OrganizationRepository orgRepo;

    @GET
    public Response members(@PathParam("slug") String slug) {
        Optional<Organization> org = this.orgRepo.find("slug = ?1", slug).firstResultOptional();
        if(org.isPresent()) {
            return Response
                .status(Response.Status.OK)
                .entity(new ResultOfArray<>(org.get().members.stream().toList()))
                .build();
        } else {
            return Response
                .status(Response.Status.NOT_FOUND)
                .build();
        }
    }

    @GET
    @Path("invite-member")
    public Response inviteMember(@PathParam("slug") String slug, @QueryParam("email") String email) {
        return Response
            .status(Response.Status.OK)
            .entity(email)
            .build();
    }

    @DELETE
    @RolesAllowed({"User"})
    public Response deleteMember(@PathParam("slug") String slug, @BeanParam Member member) {
        return Response
            .status(Response.Status.OK)
            .build();
    }
}
