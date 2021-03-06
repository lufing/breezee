/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.prj.sodexo.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.common.util.ContextUtil;
import com.breezee.pcm.api.domain.CategoryInfo;
import com.breezee.pcm.api.service.ICategoryService;
import com.breezee.prj.sodexo.domain.FoodLineInfo;
import com.breezee.prj.sodexo.entity.FoodLineEntity;
import com.breezee.prj.sodexo.repository.FoodLineRepository;
import com.breezee.prj.sodexo.service.IFoodLineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by Silence on 2016/2/13.
 */
@Service("foodLineService")
public class FoodLineServiceImpl implements IFoodLineService {

    @Autowired
    private FoodLineRepository foodLineRepository;

    @Resource
    private ICategoryService categoryService;

    @Override
    public FoodLineInfo saveInfo(FoodLineInfo foodLineInfo) {
        FoodLineEntity entity = foodLineRepository.findByCode(foodLineInfo.getCode());
        if (foodLineInfo.getId() == null && entity != null) {
            return ErrorInfo.build(foodLineInfo, ContextUtil.getMessage("duplicate.key", new String[]{foodLineInfo.getCode()}));
        }
        if (entity == null)
            entity = new FoodLineEntity();
        entity.parse(foodLineInfo);
        foodLineRepository.save(entity);
        //增加品类
        CategoryInfo categoryInfo = categoryService.findByCode(foodLineInfo.getCode());
        if(categoryInfo.getId()<0) {
            categoryInfo = new CategoryInfo();
            categoryInfo.setCode(foodLineInfo.getCode());
            categoryInfo.setRemark("根据服务线自动创建");
            categoryInfo.setParent(new CategoryInfo(1L));
        }
        categoryInfo.setName(foodLineInfo.getName());
        categoryService.saveInfo(categoryInfo);
        return SuccessInfo.build(FoodLineInfo.class);
    }

    @Override
    public FoodLineInfo deleteById(Long id) {
        foodLineRepository.delete(id);
        return SuccessInfo.build(FoodLineInfo.class);
    }

    @Override
    public FoodLineInfo findInfoById(Long id) {
        FoodLineEntity entity = foodLineRepository.findOne(id);
        if (entity == null)
            return ErrorInfo.build(FoodLineInfo.class);
        return entity.toInfo();
    }

    @Override
    public List<FoodLineInfo> listAll(Map<String, Object> m) {
        List<FoodLineEntity> l = foodLineRepository.findAll(DynamicSpecifications.createSpecification(m));
        return new InfoList<>(l, (Callback<FoodLineEntity, FoodLineInfo>) (FoodLineEntity, FoodLineInfo) -> FoodLineEntity.toInfo());
    }

    @Override
    public PageResult<FoodLineInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        Page<FoodLineEntity> page = foodLineRepository.findAll(DynamicSpecifications.createSpecification(m), pageInfo);
        return new PageResult<>(page, FoodLineInfo.class, (foodLineEntity, foodLineInfo) -> foodLineEntity.toInfo());
    }

    @Override
    public void updateStatus(Long id, int status) {
    }

    @Override
    public List<FoodLineInfo> findByMesshallId(Long messhallId) {
        return new InfoList<>(foodLineRepository.findByMesshallId(messhallId), (Callback<FoodLineEntity, FoodLineInfo>) (FoodLineEntity, FoodLineInfo) -> FoodLineEntity.toInfo());
    }

    @Override
    public List<FoodLineInfo> findBySite(String site) {
        return new InfoList<>(foodLineRepository.findBySite(site), (Callback<FoodLineEntity, FoodLineInfo>) (FoodLineEntity, FoodLineInfo) -> FoodLineEntity.toInfo());
    }
}
