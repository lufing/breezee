/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.prj.sodexo.service;

import com.breezee.common.IServiceLayer;
import com.breezee.prj.sodexo.domain.FoodLineInfo;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

/**
 * Created by Silence on 2016/2/13.
 */
@Path("/foodLine")
public interface IFoodLineService extends IServiceLayer<FoodLineInfo> {

    @Path("/messhallId/{messhallId}")
    @GET
    List<FoodLineInfo> findByMesshallId(@PathParam("messhallId") Long messhallId);

    @Path("/site/{site}")
    @GET
    List<FoodLineInfo> findBySite(@PathParam("site")String site);
}
