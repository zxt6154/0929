package com.cqupt.annotation.component;

import cn.vcinema.pumpkin.activity.api.bean.OperateCode;
import cn.vcinema.pumpkin.activity.api.bean.TaskStatusEnum;
import cn.vcinema.pumpkin.activity.api.bean.TaskUserTypeRewardEnum;
import cn.vcinema.pumpkin.activity.api.exception.CommonException;
import cn.vcinema.pumpkin.activity.api.factory.WeekActivityFactory;
import cn.vcinema.pumpkin.activity.api.repository.entity.Act2022008WeekChallengerSatisfyUser;
import cn.vcinema.pumpkin.activity.api.repository.mapper.Act2022008WeekChallengerTaskRecordMapper;
import cn.vcinema.pumpkin.activity.api.server.api.Act2022008WeeklyApi;
import cn.vcinema.pumpkin.activity.api.server.model.ActivityUserCache;
import cn.vcinema.pumpkin.activity.api.server.model.Challenging;
import cn.vcinema.pumpkin.activity.api.server.model.Claiming;
import cn.vcinema.pumpkin.activity.api.service.Act2022008WeekChallengerInfoService;
import cn.vcinema.pumpkin.activity.api.service.Act2022008WeekChallengerSatisfyUserService;
import cn.vcinema.pumpkin.activity.api.service.Act2022008WeekChallengerTaskRecordService;
import cn.vcinema.pumpkin.activity.api.util.DateUtils;
import cn.vcinema.tool.cloud.aliyun.Redis;
import cn.vcinema.tool.pumpkin.RedisLockCache;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

//数据库多个表同时更新
@Component
public class TaskTransComponent {
    private static Logger logger = LoggerFactory.getLogger(TaskTransComponent.class);

    @Autowired
    private Act2022008WeekChallengerTaskRecordService act2022008WeekChallengerTaskRecordService;

    @Autowired
    private Act2022008WeekChallengerInfoService act2022008WeekChallengerInfoService;

    @Autowired
    private Act2022008WeekChallengerSatisfyUserService act2022008WeekChallengerSatisfyUserService;

    @Autowired
    private Act2022008WeeklyApi act2022008WeeklyApi;

    @Autowired
    Act2022008WeekChallengerTaskRecordMapper act2022008WeekChallengerTaskRecordMapper;

    @Autowired
    RedisLockCache redisLockCache;

    private String EXPIREDAY = "0";


    public Challenging refresh(Integer userId) {
        Challenging challenging = act2022008WeekChallengerTaskRecordService.challengeTaskRecord(Integer.valueOf(userId));
        //如果任务完成，info表加完成挑战记录, record表更新状态 - 完成未领取

        if(Objects.isNull(challenging)) {
            Integer integer = act2022008WeekChallengerTaskRecordService.updateLastRecord(userId);

            //如果当前周期的前一个周期状态是成功的
            if (integer == 2) {
                act2022008WeekChallengerInfoService.updateFnishTime(userId);
            }
        } else {
            if(Objects.equals(challenging.getFlag(), 1)) {
                //del cache
                act2022008WeeklyApi.delAct20022008Weekly(String.valueOf(userId));
                updateTaskStateAndFinishTime(challenging);
            }
        }

        return challenging;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateTaskStateAndFinishTime(Challenging challenging) {
            act2022008WeekChallengerTaskRecordService.updateCurTaskStatusById(challenging.getRecordId(), TaskStatusEnum.COMPLETEDUNCLAIM.getCode());
            act2022008WeeklyApi.delAct20022008Weekly(String.valueOf(challenging.getUserId()));
            act2022008WeekChallengerInfoService.updateFnishTime(challenging.getUserId());
    }

    /**
     *
     * @param userId
     * @return
     */
    private Redis.RedisLock getClaimLock(Integer userId) {
        Redis.RedisLock weeklyEachRewardLock = redisLockCache.getWeeklyClaimTaskLock(String.valueOf(userId), 5);
        boolean lock = weeklyEachRewardLock.lock();
        if (lock) {
            return weeklyEachRewardLock;
        } else {
            for (int i = 0; i < 3; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100 * (i + 1));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Redis.RedisLock redisLock = redisLockCache.getWeeklyClaimTaskLock(String.valueOf(userId), 5);
                boolean getLock = redisLock.lock();
                if (getLock) {
                    return redisLock;
                }
            }
        }
        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateTaskStateAndInfo(Integer userId, Integer prizeType, Integer userType, Integer challengeTime) {
        logger.info(String.valueOf(Thread.currentThread()));

        Redis.RedisLock claimLock = getClaimLock(userId);

        if (Objects.isNull(claimLock)) {
            throw new CommonException(OperateCode.CLICK_FREQUENTLY);
        }
        try {

            if(Objects.nonNull(act2022008WeekChallengerTaskRecordService.ifCurHaveRecord(userId))) {
                throw new CommonException(OperateCode.TASK_CLAIMED);
            }
            //获取用户类型
            Map<Integer, Integer>  userType_challengeTime = calChallegeTime(userId, challengeTime);
//            AtomicReference<Integer> userTypeTemp = null;
//            AtomicReference<Integer> challegeTimeCur = null;
            userType_challengeTime.forEach((key, value) -> {
                if(!key.equals(userType)) {
                    throw  new CommonException(OperateCode.USER_TYPE_NOT_MATCH);
                }

                //前端返回day/seedNum 获取用户领取的奖励
                Integer prizeValue = TaskUserTypeRewardEnum.parseType(key).parseDayOrSeedNum(prizeType);

                if (Objects.equals(value, -1)) {
                    throw new CommonException(OperateCode.CHALLEGE_TIME_UNMATCH);
                }

                //领取奖励 record表新增数据
                act2022008WeekChallengerTaskRecordService.claimChallenge(value, userId, userType, prizeType, prizeValue);
            });

            //info表更新数据
            act2022008WeekChallengerInfoService.addOrUpdateUserTaskInfo(userId);
        } finally {
            claimLock.unlock();
        }
        logger.info(String.valueOf(userId));
        EXPIREDAY = DateUtils.getBetweenDays(LocalDateTime.now(), DateUtils.getCurTaskEndTime(), -1);
        //加入缓存

        if(!act2022008WeeklyApi.addAct20022008Weekly(new ActivityUserCache(EXPIREDAY, userId.toString()))) {
            throw  new CommonException(OperateCode.REDIS_ADD_EXCEPTION);
        }
    }

    Map<Integer, Integer> calChallegeTime(Integer userId, Integer challengeTime) {
        Map<Integer, Integer> map = Maps.newHashMap();
            Act2022008WeekChallengerSatisfyUser act2022008WeekChallengerSatisfyUser = act2022008WeekChallengerSatisfyUserService.userTypeInfo(userId);

        //上个个性任务状态
        Integer lastPersonalityTask = act2022008WeekChallengerTaskRecordService.getLastPersonalityTask(userId);

        Integer userType = act2022008WeekChallengerSatisfyUser == null ? 1 : act2022008WeekChallengerSatisfyUser.getUserType();
        Integer watchTimeAvg = act2022008WeekChallengerSatisfyUser == null ? null : act2022008WeekChallengerSatisfyUser.getWatchTimeAvg();

        WeekActivityFactory weekActivityFactory = new WeekActivityFactory();

        //本次任务挑战时长
        Double curChallengeTime = weekActivityFactory.createTask(userType, watchTimeAvg, lastPersonalityTask);

        BigDecimal minChallengeTime = new BigDecimal(curChallengeTime / 60).setScale(0, BigDecimal.ROUND_HALF_UP);
        int intChallengeTime = minChallengeTime.intValue();

        map.put(userType, intChallengeTime);

        if (Objects.nonNull(challengeTime)) {
            if (Objects.equals(intChallengeTime, challengeTime)) {
                map.put(userType, new BigDecimal(curChallengeTime).setScale(0, BigDecimal.ROUND_HALF_UP).intValue());
                return map;
            } else {
                map.put(userType, -1);
                }
        }
        return map;
    }

    public Claiming combineClaiming(Integer userId) {

            Map<Integer, Integer> userType_challengeTime = calChallegeTime(userId, null);

            AtomicReference<Claiming> claiming = new AtomicReference<>();
            userType_challengeTime.forEach((key, value) -> {
                //用户类型 会员天数 南瓜籽数量
                TaskUserTypeRewardEnum taskUserTypeRewardEnum = TaskUserTypeRewardEnum.parseType(key);
                assert taskUserTypeRewardEnum != null;
                Claiming claiming1 = new Claiming(String.valueOf(userId), value, taskUserTypeRewardEnum);
                claiming.set(claiming1);

            });
            return claiming.get();
    }


    public void ifActEnd(Integer userId) {
        Integer actId = act2022008WeeklyApi.verifyAct(userId);
        if(!Objects.equals(actId, 1)) {
            throw new CommonException(OperateCode.ACT_CLOSE);
        }
    }

}
