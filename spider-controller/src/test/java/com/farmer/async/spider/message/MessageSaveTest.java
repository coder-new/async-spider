package com.farmer.async.spider.message;

import com.farmer.async.spider.save.dao.MessageDao;
import com.farmer.async.spider.save.entity.QueueMessageEntity;
import com.farmer.async.spider.save.mapper.QueueMessageMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

/**
 * @Author farmer-coder
 * @Email aprimecoder@gmail.com
 * @Date Create at : 2018/2/22
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageSaveTest {

    @Autowired
    private QueueMessageMapper queueMessageMapper;

    @Autowired
    private MessageDao messageDao;

    @Test
    public void test() {

        QueueMessageEntity queueMessageEntity = new QueueMessageEntity();
        queueMessageEntity.setId(UUID.randomUUID().toString());
        queueMessageEntity.setQueueName("spider-save-queue");
        queueMessageEntity.setMessageStr("");

        queueMessageMapper.insert(queueMessageEntity);
    }

    @Test
    public void testDelete() {

        messageDao.delete("f3490d17-a8c1-4c06-9597-90d5fa855101");
    }
}
