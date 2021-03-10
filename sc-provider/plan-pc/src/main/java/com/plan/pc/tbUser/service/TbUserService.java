package com.plan.pc.tbUser.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neusoft.core.restful.AppResponse;
import com.neusoft.security.client.utils.SecurityUtils;
import com.neusoft.util.StringUtil;
import com.plan.pc.tbUser.dao.TbUserDao;
import com.plan.pc.tbUser.entity.TbUserInfo;
import com.plan.pc.tbUser.entity.TbUserVo;
import com.plan.pc.tbUser.entity.UserEntity;
import com.plan.pc.tbUser.repository.UserRepository;
import com.plan.pc.user.entity.UserInfo;
import com.plan.pc.util.PasswordUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author 12533
 */
@Service
public class TbUserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private TbUserDao tbUserDao;

    /**
     * 分页
     * @param userInfo
     * @return
     */
    public AppResponse listUsersByPage(TbUserInfo userInfo) {
        PageHelper.startPage(userInfo.getPageNum(),userInfo.getPageSize());
        List<TbUserInfo> userInfoList = tbUserDao.listUsersByPage(userInfo);
        //包装对象
        PageInfo<TbUserInfo> pageData = new PageInfo<TbUserInfo>(userInfoList);
        return AppResponse.success("查询成功",pageData);
    }

    /**
     * 保存
     * @param userInfo
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public AppResponse saveUser(TbUserInfo userInfo) {
         //校验账号和手机号码是否存在
        List<UserEntity> countUsers = userRepository.findByUserAcctOrPhone(userInfo.getUserAcct(),userInfo.getPhone());
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(userInfo,entity);
        if (entity.getId() == null){
            //新增时------校验账号和手机号码是否存在
            if(countUsers.size() != 0) {
                return AppResponse.bizError("用户账号或手机号已存在，请重新输入！");
            }
            entity.setId(StringUtil.getCommonCode(2));
            //获取用户id
            String userId = SecurityUtils.getCurrentUserId();
            //密码加密
            String pwd = PasswordUtils.generatePassword(userInfo.getUserPassword());
            entity.setUserPassword(pwd);
            entity.setCreateName(userId);
            entity.setCreateTime(new Date());
            entity.setUpdateName(userId);
            entity.setUpdateTime(new Date());
        }else {
            //旧数据
            UserEntity oldEntity = userRepository.findById(userInfo.getId());
            //修改时------校验账号和手机号是否存在
            if (!oldEntity.getUserAcct().equals(entity.getUserAcct())  || !oldEntity.getPhone().equals(entity.getPhone())){
                if(countUsers.size() != 0) {
                    return AppResponse.bizError("用户账号或手机号已存在，请重新输入！");
                }
            }
            entity.setId(entity.getId());
            //获取用户id
            String userId = SecurityUtils.getCurrentUserId();
            //查出数据库里原加密密码，如果不修改密码，密码无需再次加密
            UserEntity getPwdEntity = userRepository.findById(entity.getId());
            if (!entity.getUserPassword().equals(getPwdEntity.getUserPassword())){
                String pwd = PasswordUtils.generatePassword(userInfo.getUserPassword());
                entity.setUserPassword(pwd);
            }
            entity.setCreateName(oldEntity.getCreateName());
            entity.setCreateTime(oldEntity.getCreateTime());
            entity.setUpdateName(userId);
            entity.setUpdateTime(new Date());
        }
        entity.setIsDeleted(1);
        entity.setVersion(0);
        // 新增用户
        userRepository.save(entity);
        return AppResponse.success("保存成功！");
    }

    /**
     * 根据id查询详情信息
     * @param id
     * @return
     */
    public AppResponse getUserByUserCode(String id) {
        UserEntity userEntity = userRepository.findById(id);
        TbUserVo vo = new TbUserVo();
        BeanUtils.copyProperties(userEntity,vo);
        return AppResponse.success("查询成功！",vo);
    }

    /**
     * 删除
     * @param id
     * @param userId
     * @return
     */
    public AppResponse deleteUser(String id, String userId) {
        List<String> listId = Arrays.asList(id.split(","));
        List<UserEntity> oldList = new ArrayList<>();
        for (String strings : listId){
            UserEntity oldEntity = userRepository.findById(strings);
            oldList.add(oldEntity);
        }
        for (UserEntity entitys : oldList){
            UserEntity entity = new UserEntity();
            BeanUtils.copyProperties(entitys,entity);
            entity.setUpdateName(userId);
            entity.setUpdateTime(new Date());
            entity.setIsDeleted(0);
            userRepository.save(entity);
        }
        return AppResponse.success("删除成功！");
    }

    public AppResponse getUserUrl(UserInfo userInfo) {
        return null;
    }
}
