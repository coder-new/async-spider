package com.farmer.async.spider.save.dao;

import com.farmer.async.spider.save.entity.BloggerRelationEntity;
import com.farmer.async.spider.save.mapper.BloggerRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/4
 */
@Component
public class BloggerRelationDao {

    @Autowired
    private BloggerRelationMapper bloggerRelationMapper;

    public void saveList(List<BloggerRelationEntity> bloggerRelationEntities) {

        List<BloggerRelationEntity> notExistList = notExistelation(bloggerRelationEntities);
        if (null != notExistList && notExistList.size() > 0) {
            bloggerRelationMapper.insertList(notExistList);
        }
    }

    private List<BloggerRelationEntity> notExistelation(List<BloggerRelationEntity> bloggerRelationEntities) {

        return bloggerRelationEntities.stream()
                .filter(bloggerRelationEntity -> !relationExist(bloggerRelationEntity.getBloggerFollower(),bloggerRelationEntity.getBloggerFollowee()))
                .collect(Collectors.toList());

    }

    private boolean relationExist(String bloggerFollower,String bloggerFollowee) {

        List<BloggerRelationEntity> bloggerRelationEntities = getRelation(bloggerFollower,bloggerFollowee);

        if ((bloggerRelationEntities == null) || (bloggerRelationEntities.size() == 0)) {
            return false;
        }

        return true;
    }

    private List<BloggerRelationEntity> getRelation(String bloggerFollower,String bloggerFollowee) {

        Example example = new Example(BloggerRelationEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bloggerFollower",bloggerFollower);
        criteria.andEqualTo("bloggerFollowee",bloggerFollowee);

        return bloggerRelationMapper.selectByExample(example);
    }
}
