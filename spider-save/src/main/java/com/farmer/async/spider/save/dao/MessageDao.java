package com.farmer.async.spider.save.dao;

import com.alibaba.fastjson.JSON;
import com.farmer.async.spider.message.core.ActiveMqMessageSend;
import com.farmer.async.spider.message.definition.BaseMessage;
import com.farmer.async.spider.message.persistence.IMessagePersistence;
import com.farmer.async.spider.save.entity.QueueMessageEntity;
import com.farmer.async.spider.save.mapper.QueueMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/22
 */
@Component
public class MessageDao implements IMessagePersistence {

    @Autowired
    private QueueMessageMapper queueMessageMapper;

    @Autowired
    private ActiveMqMessageSend activeMqMessageSend;

    @Override
    public void persistence(String id, String queueName, String messageStr) {

        QueueMessageEntity queueMessageEntity = new QueueMessageEntity();
        queueMessageEntity.setId(id);
        queueMessageEntity.setMessageStr(messageStr);
        queueMessageEntity.setQueueName(queueName);

        queueMessageMapper.insert(queueMessageEntity);
    }

    @Override
    public void delete(String id) {

        Example example = new Example(QueueMessageEntity.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",id);

        queueMessageMapper.deleteByExample(example);
    }

    @Override
    public void restore() {

        List<QueueMessageEntity> entities = queueMessageMapper.selectAll();
        for (QueueMessageEntity queueMessageEntity : entities) {
            activeMqMessageSend.sendMessageWithoutPersistence(queueMessageEntity.getMessageStr(),queueMessageEntity.getQueueName());
        }
    }
}
