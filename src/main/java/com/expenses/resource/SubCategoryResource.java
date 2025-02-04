package com.expenses.resource;

import com.expenses.entity.SubCategoryEntity;
import com.expenses.service.SubCategoryService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/sub-categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SubCategoryResource {
    @Inject
    SubCategoryService subCategoryService;

    @GET
    @Path("/{categoryId}")
    public List<SubCategoryEntity> getSubCategoriesByCategory(@PathParam("categoryId") Long categoryId) {
        return subCategoryService.getSubCategoriesByCategory(categoryId);
    }

    @POST
    public Response addSubCategory(Long categoryId, String name) {
        SubCategoryEntity subCategory = subCategoryService.addSubCategory(categoryId, name);
        return Response.ok(subCategory).build();
    }
}