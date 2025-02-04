package com.expenses.resource;

import com.expenses.entity.CategoryEntity;
import com.expenses.service.CategoryService;
import com.expenses.dto.CategoryDTO;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/categories")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoryResource {
    @Inject
    CategoryService categoryService;

    @GET
    public List<CategoryEntity> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @POST
    @Path("/create")
    public Response addCategory(CategoryDTO categoryDTO) {
        categoryService.addCategory(categoryDTO.getname());
        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/delete")
    public Response deleteCategory(CategoryDTO categoryDTO) {
        categoryService.deleteCategory(categoryDTO.getname());
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}