/*
 * Copyright (c) 2016.
 * Breezee.com All Rights Reserved.
 */

package com.breezee.oms.impl;

import com.breezee.bpm.api.domain.ProcsInsInfo;
import com.breezee.bpm.api.service.ITaskService;
import com.breezee.bpm.api.service.IWorkflowService;
import com.breezee.common.*;
import com.breezee.common.util.Callback;
import com.breezee.common.DynamicSpecifications;
import com.breezee.oms.api.domain.OrderInfo;
import com.breezee.oms.entity.OrderEntity;
import com.breezee.oms.repository.OrderRepository;
import com.breezee.oms.api.service.IOrderService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Silence on 2016/2/12.
 */
@Service("orderService")
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Resource
    private IWorkflowService workflowService;

    @Override
    public OrderInfo saveInfo(OrderInfo orderInfo) {
        OrderEntity entity = new OrderEntity().parse(orderInfo);
        entity.setIssueDate(new Date());
        orderRepository.save(entity);
        //启动流程
       /*if(orderInfo.getTaskId()==null || orderInfo.getTaskId()<0) {
            Map<String, Object> m = new HashMap<>();
//            m.put("dueDate", entity.getIssueDate());
            m.put("creator", entity.getCreator());
            m.put("storeName",orderInfo.getStoreName());//获取选择的餐线，做为角色名称传入到流程中去
            ProcsInsInfo prcsInsInfo = workflowService.
                    startProcessInstanceById(orderInfo.getProcDefId(), entity.getId().toString());
            taskService.autoComplete(Long.parseLong(prcsInsInfo.getProcessInstanceId()), m);
        } else {
            taskService.complete(orderInfo.getTaskId().toString());
        }*/
        if(orderInfo.getTaskId()==null || orderInfo.getTaskId()<0) {
            Map<String,Object> vars = Maps.newConcurrentMap();
            //注意第一次保存启动流程orderInfo的ProcDefId和code一定要有值
            vars.put("foodLineRole",orderInfo.getStoreName());
            workflowService.startProcessInstanceById(orderInfo.getProcDefId(), entity.getCode(),vars);
        }

        //TODO:减库存
        return SuccessInfo.build(OrderInfo.class);
    }

    @Override
    public OrderInfo deleteById(Long id) {
        return null;
    }

    @Override
    public OrderInfo findInfoById(Long id) {
        OrderEntity entity = orderRepository.findOne(id);
        if(entity==null)
            return ErrorInfo.build(OrderInfo.class);
        return entity.toInfo();
    }

    @Override
    public List<OrderInfo> listAll(Map<String, Object> m) {
        List<OrderEntity> l = orderRepository.findAll(DynamicSpecifications.createSpecification(m));
        return new InfoList<>(l, (Callback<OrderEntity, OrderInfo>) (orderEntity, orderInfo) -> orderEntity.toInfo());
    }

    @Override
    public PageResult<OrderInfo> pageAll(Map<String, Object> m, PageInfo pageInfo) {
        if(m.get("username") != null){
            m.remove("username");
        }
        Page<OrderEntity> page = orderRepository.findAll(DynamicSpecifications.createSpecification(m),pageInfo);
        return new PageResult<>(page, OrderInfo.class, (orderEntity, orderInfo) -> orderEntity.toInfo());
    }




    @Override
    public OrderInfo findOrderInfoByCode(String code){
        OrderEntity entity;
        List<OrderEntity> entityList = orderRepository.findByCode(code);
        if(entityList==null){
            return ErrorInfo.build(OrderInfo.class);
        }else{
            entity = entityList.get(0);
        }
        return entity.toInfo();
    }

    @Override
    public List<OrderInfo> findOrderInfoListByCodes(Collection<String> codes){
        List<OrderInfo> orderInfoList = Lists.newArrayList();
        List<OrderEntity> entityList = orderRepository.findByCodeIn(codes);
        if(entityList==null){
            orderInfoList.add(ErrorInfo.build(OrderInfo.class));
        }else{
            for(OrderEntity item : entityList){
                orderInfoList.add(item.toInfo());
            }
        }
        return orderInfoList;
    }


}
