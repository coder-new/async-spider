package com.farmer.async.spider.save.dao;

import com.farmer.async.spider.save.entity.BloggerEntity;
import com.farmer.async.spider.save.mapper.BloggerMapper;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/1/18
 */
@Component
public class BloggerDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BloggerDao.class);

    @Autowired
    private BloggerMapper bloggerMapper;

    public void saveList(List<BloggerEntity> bloggerEntities) {

        List<BloggerEntity> entities = notExistBloggerList(bloggerEntities);

        if (entities.size() == 0) {
            return;
        }

        bloggerMapper.insertList(entities);
    }

    public void save(BloggerEntity bloggerEntity) {

        BloggerEntity entity = queryByBloggerName(bloggerEntity.getBloggerName());
        if (null == entity) {
            bloggerMapper.insert(bloggerEntity);
        }
    }

    public List<BloggerEntity> queryIsRelationZeroBlogger() {

        Example example = new Example(BloggerEntity.class);
        Example.Criteria criteria = example.createCriteria();

        criteria.andEqualTo("isRelation",0);

        return bloggerMapper.selectByExample(example);
    }

    public BloggerEntity queryOneRelationZeroBlogger() {

        Example example = new Example(BloggerEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isRelation",0);

        List<BloggerEntity> bloggerEntities
                =  bloggerMapper.selectByExampleAndRowBounds(example,new RowBounds(0, 1));

        if (bloggerEntities.size() > 0) {
            return bloggerEntities.get(0);
        }

        return null;
    }

    public List<BloggerEntity> queryUidNullBlogger() {

        Example example = new Example(BloggerEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIsNull("bloggerUid");

        return bloggerMapper.selectByExample(example);
    }

    public List<BloggerEntity> queryUidNotNullBlogger() {

        Example example = new Example(BloggerEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIsNotNull("bloggerUid");

        return bloggerMapper.selectByExample(example);
    }

    public BloggerEntity queryByBloggerName(String bloggerName) {

        Example example = new Example(BloggerEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bloggerName",bloggerName);

        List<BloggerEntity> bloggerEntities = bloggerMapper.selectByExample(example);
        if ((null == bloggerEntities) || bloggerEntities.size() == 0) {
            return null;
        }
        return bloggerEntities.get(0);
    }

    public void updateBloggerUid(String bloggerName,String bloggerUid) {

        Example example = new Example(BloggerEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("bloggerName",bloggerName);

        BloggerEntity bloggerEntity = new BloggerEntity();
        bloggerEntity.setBloggerUid(bloggerUid);

        bloggerMapper.updateByExampleSelective(bloggerEntity,example);
    }

    private List<BloggerEntity> notExistBloggerList(List<BloggerEntity> bloggerEntities) {

        List<BloggerEntity> entities = new ArrayList<>();
        for (BloggerEntity bloggerEntity : bloggerEntities) {

            Example example = new Example(BloggerEntity.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("bloggerName",bloggerEntity.getBloggerName());

            List<BloggerEntity> existEntities = bloggerMapper.selectByExample(example);
            if (existEntities.size() == 0) {
                entities.add(bloggerEntity);
            }
        }

        return entities;
    }

    public void updateBloggerEntity(BloggerEntity bloggerEntity,Integer id) {

        Example example = new Example(BloggerEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",id);

        bloggerMapper.updateByExampleSelective(bloggerEntity,example);
    }
}
