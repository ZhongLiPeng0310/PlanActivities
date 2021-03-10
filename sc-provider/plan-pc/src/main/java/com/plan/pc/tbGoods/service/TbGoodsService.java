package com.plan.pc.tbGoods.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.StringUtil;
import com.plan.pc.tbGoods.dao.TbGoodsDao;
import com.plan.pc.tbGoods.entity.TbGoodsEntity;
import com.plan.pc.tbGoods.entity.TbGoodsInfo;
import com.plan.pc.tbGoods.entity.TbGoodsVo;
import com.plan.pc.tbGoods.repository.TbGoodsRepository;
import com.plan.pc.tbUser.entity.TbUserInfo;
import com.plan.pc.tbUser.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 12533
 */
@Service
public class TbGoodsService {

    @Resource
    private TbGoodsDao tbGoodsDao;

    @Resource
    private TbGoodsRepository tbGoodsRepository;

    /**
     * 保存
     * @param tbGoodsInfo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveGoods(TbGoodsInfo tbGoodsInfo) {
        TbGoodsEntity entity = new TbGoodsEntity();
        BeanUtils.copyProperties(tbGoodsInfo,entity);
        if (entity.getId() == null){
            entity.setId(StringUtil.getCommonCode(2));
            //获取用户id
            String userId = SecurityUtils.getCurrentUserId();
            entity.setCreateName(userId);
            entity.setCreateTime(new Date());
            entity.setUpdateName(userId);
            entity.setUpdateTime(new Date());
        }else {
            TbGoodsEntity oldEntity = tbGoodsRepository.findById(tbGoodsInfo.getId());
            entity.setId(oldEntity.getId());
            //获取用户id
            String userId = SecurityUtils.getCurrentUserId();
            entity.setCreateName(oldEntity.getCreateName());
            entity.setCreateTime(oldEntity.getCreateTime());
            entity.setUpdateName(userId);
            entity.setUpdateTime(new Date());
        }
        entity.setIsDeleted(1);
        entity.setVersion(0);
        // 新增物资
        tbGoodsRepository.save(entity);
        return AppResponse.success("保存成功！");
    }

    /**
     * 分页
     * @param tbGoodsInfo
     * @return
     */
    public AppResponse listGoodsByPage(TbGoodsInfo tbGoodsInfo) {
        PageHelper.startPage(tbGoodsInfo.getPageNum(),tbGoodsInfo.getPageSize());
        List<TbGoodsInfo> goodsInfoList = tbGoodsDao.listGoodsByPage(tbGoodsInfo);
        //包装对象
        PageInfo<TbGoodsInfo> pageData = new PageInfo<TbGoodsInfo>(goodsInfoList);
        return AppResponse.success("查询成功",pageData);
    }

    /**
     * 查询详情
     * @param id
     * @return
     */
    public AppResponse getGoodsById(String id) {
        TbGoodsEntity tbGoodsEntity = tbGoodsRepository.findById(id);
        TbGoodsVo vo = new TbGoodsVo();
        BeanUtils.copyProperties(tbGoodsEntity,vo);
        return AppResponse.success("查询成功！",vo);
    }

    /**
     * 批量删除
     * @param id
     * @param userId
     * @return
     */
    public AppResponse deleteGoods(String id, String userId) {
        List<String> listId = Arrays.asList(id.split(","));
        List<TbGoodsEntity> oldList = new ArrayList<>();
        for (String strings : listId){
            TbGoodsEntity oldEntity = tbGoodsRepository.findById(strings);
            oldList.add(oldEntity);
        }
        for (TbGoodsEntity entitys : oldList){
            TbGoodsEntity entity = new TbGoodsEntity();
            BeanUtils.copyProperties(entitys,entity);
            entity.setUpdateName(userId);
            entity.setUpdateTime(new Date());
            entity.setIsDeleted(0);
            tbGoodsRepository.save(entity);
        }
        return AppResponse.success("删除成功！");
    }
}
