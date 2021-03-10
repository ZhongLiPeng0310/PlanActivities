package com.plan.pc.tbPictureShow.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.StringUtil;
import com.plan.pc.tbPictureShow.dao.TbPictureShowDao;
import com.plan.pc.tbPictureShow.entity.TbPictureShowEntity;
import com.plan.pc.tbPictureShow.entity.TbPictureShowInfo;
import com.plan.pc.tbPictureShow.entity.TbPictureShowVo;
import com.plan.pc.tbPictureShow.repository.TbPictureShowRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 12533
 */
@Service
public class TbPictureShowService {
    @Resource
    private TbPictureShowDao tbPictureShowDao;

    @Resource
    private TbPictureShowRepository tbPictureShowRepository;
    /**
     * 保存
     * @param tbPictureShowInfo
     * @return
     */
    public AppResponse saveTbPictureShow(TbPictureShowInfo tbPictureShowInfo) {
        TbPictureShowEntity entity = new TbPictureShowEntity();
        TbPictureShowEntity oldEntity = tbPictureShowRepository.findById(tbPictureShowInfo.getId());
        BeanUtils.copyProperties(tbPictureShowInfo,entity);
        if (entity.getId() == null){
            entity.setId(StringUtil.getCommonCode(2));
            //获取用户id
            String userId = SecurityUtils.getCurrentUserId();
            entity.setCreateName(userId);
            entity.setCreateTime(new Date());
            entity.setUpdateName(userId);
            entity.setUpdateTime(new Date());
        }else {
            entity.setId(entity.getId());
            //获取用户id
            String userId = SecurityUtils.getCurrentUserId();
            entity.setCreateName(oldEntity.getCreateName());
            entity.setCreateTime(oldEntity.getCreateTime());
            entity.setUpdateName(userId);
            entity.setUpdateTime(new Date());
        }
        entity.setIsDeleted(1);
        entity.setVersion(0);
        // 新增轮播图
        tbPictureShowRepository.save(entity);
        return AppResponse.success("保存成功！");
    }

    /**
     * 分页查询
     * @param tbPictureShowVo
     * @return
     */
    public AppResponse listTbPictureShowByPage(TbPictureShowVo tbPictureShowVo) {
        PageHelper.startPage(tbPictureShowVo.getPageNum(),tbPictureShowVo.getPageSize());
        List<TbPictureShowVo> tbPictureShowVoList = tbPictureShowDao.listTbPictureShowByPage(tbPictureShowVo);
        //包装对象
        PageInfo<TbPictureShowVo> pageData = new PageInfo<TbPictureShowVo>(tbPictureShowVoList);
        return AppResponse.success("查询成功",pageData);
    }

    /**
     * 根据id获取详情
     * @param id
     * @return
     */
    public AppResponse getTbPictureShowById(String id) {
        TbPictureShowEntity tbPictureShowEntity = tbPictureShowRepository.findById(id);
        TbPictureShowVo vo = new TbPictureShowVo();
        BeanUtils.copyProperties(tbPictureShowEntity,vo);
        return AppResponse.success("查询成功！",vo);
    }

    /**
     * 批量删除
     * @param id
     * @param userId
     * @return
     */
    public AppResponse deleteTbPictureShow(String id, String userId) {
        List<String> listId = Arrays.asList(id.split(","));
        List<TbPictureShowEntity> oldList = new ArrayList<>();
        for (String strings : listId){
            TbPictureShowEntity oldEntity = tbPictureShowRepository.findById(strings);
            oldList.add(oldEntity);
        }
        for (TbPictureShowEntity entitys : oldList){
            TbPictureShowEntity entity = new TbPictureShowEntity();
            BeanUtils.copyProperties(entitys,entity);
            entity.setUpdateName(userId);
            entity.setUpdateTime(new Date());
            entity.setIsDeleted(0);
            tbPictureShowRepository.save(entity);
        }
        return AppResponse.success("删除成功！");
    }
}
