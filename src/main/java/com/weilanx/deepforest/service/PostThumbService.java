package com.weilanx.deepforest.service;

import com.weilanx.deepforest.model.entity.PostThumb;
import com.baomidou.mybatisplus.extension.service.IService;
import com.weilanx.deepforest.model.entity.User;

/**
 * 帖子点赞服务
 *
 * @author <a href="https://github.com/Azure12355">蔚蓝</a>
 * @from 
 */
public interface PostThumbService extends IService<PostThumb> {

    /**
     * 点赞
     *
     * @param postId
     * @param loginUser
     * @return
     */
    int doPostThumb(long postId, User loginUser);

    /**
     * 帖子点赞（内部服务）
     *
     * @param userId
     * @param postId
     * @return
     */
    int doPostThumbInner(long userId, long postId);
}
