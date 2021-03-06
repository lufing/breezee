/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.pcm.impl;

import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.common.util.ContextUtil;
import com.breezee.pcm.api.domain.CateAttrInfo;
import com.breezee.pcm.api.domain.CategoryInfo;
import com.breezee.pcm.api.service.ICategoryService;
import com.breezee.pcm.entity.AttributeEntity;
import com.breezee.pcm.entity.CateAttrEntity;
import com.breezee.pcm.entity.CategoryEntity;
import com.breezee.pcm.repository.AttributeRepository;
import com.breezee.pcm.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 品类服务实现类
 * Created by Silence on 2016/2/7.
 */
@Service("categoryService")
public class CategoryServiceImpl implements ICategoryService {

    @Qualifier("categoryRepository")
    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Override
    public List<CategoryInfo> findCategoryByParentId(Long id) {
        List<CategoryEntity> l = new ArrayList<>();
        if (id == -1) {
            l = categoryRepository.findTop();
        } else {
            CategoryEntity en = categoryRepository.findOne(id);
            if (en != null)
                l.addAll(en.getChildren());
        }
        List<CategoryInfo> _l = new ArrayList<>(l.size());
        l.forEach(a -> {
            _l.add(a.toInfo(false));
        });
        return _l;
    }

    @Override
    public void saveCateAttr(CategoryInfo categoryInfo) {
        CategoryEntity entity = categoryRepository.findOne(categoryInfo.getId());
        if(entity==null)
            return;
        entity.getCateAttrs().removeAll(entity.getCateAttrs());
        categoryRepository.save(entity);
        List<CateAttrInfo> l = categoryInfo.getCateAttrInfos();
        if(l!=null && l.size()>0){
            for (CateAttrInfo cateAttrInfo : l) {
                AttributeEntity attributeEntity = attributeRepository.findOne(cateAttrInfo.getAttrId());
                if(attributeEntity!=null) {
                    entity.getCateAttrs().add(new CateAttrEntity(entity,attributeEntity).parse(cateAttrInfo));
                }
            }
            categoryRepository.save(entity);
        }
    }

    @Override
    public List<CateAttrInfo> findCateAttrsByCateId(Long cateId) {
        CategoryEntity entity = categoryRepository.findOne(cateId);
        List<CateAttrInfo> l = new ArrayList<>();
        while (entity != null){
            l.addAll(new InfoList<>(entity.getCateAttrs(), (Callback<CateAttrEntity, CateAttrInfo>) (cateAttrEntity, info) -> cateAttrEntity.toInfo()));
            entity = entity.getParent();
        }
        return l;
    }

    @Override
    public CategoryInfo findByCode(String code) {
        CategoryEntity en = categoryRepository.findByCode(code);
        if(en==null)
            return ErrorInfo.build(CategoryInfo.class);
        return en.toInfo(false);
    }

    @Override
    public CategoryInfo saveInfo(CategoryInfo categoryInfo) {
        CategoryEntity entity = categoryRepository.findByCode(categoryInfo.getCode());
        if (categoryInfo.getId() == null && entity != null) {
            return ErrorInfo.build(categoryInfo, ContextUtil.getMessage("duplicate.key", new String[]{categoryInfo.getCode()}));
        }
        if (entity == null)
            entity = new CategoryEntity();
        entity.parse(categoryInfo);
        if (categoryInfo.getParent() != null && categoryInfo.getParent().getId() != null) {
            entity.setParent(categoryRepository.findOne(categoryInfo.getParent().getId()));
        }
        categoryRepository.save(entity);
        return SuccessInfo.build(CategoryInfo.class);

    }

    @Override
    public CategoryInfo deleteById(Long id) {
        return null;
    }

    @Override
    public CategoryInfo findInfoById(Long id) {
        CategoryEntity en = categoryRepository.findOne(id);
        if(en==null)
            return ErrorInfo.build(CategoryInfo.class);
        return en.toInfo(false);
    }

    @Override
    public List<CategoryInfo> listAll(Map<String, Object> m) {
        List<CategoryEntity> l = (List<CategoryEntity>) categoryRepository.findAll();
        return new InfoList<>(l, (Callback<CategoryEntity, CategoryInfo>) (categoryEntity, categoryInfo) -> categoryEntity.toInfo(false));
    }

    @Override
    public PageResult<CategoryInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        return null;
    }

    @Override
    public void updateStatus(Long id, int status) {

    }
}
