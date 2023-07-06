package org.cloud.bi.dao.aspect;

import lombok.extern.log4j.Log4j2;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;

/**
 * 描述：事务切面类
 *
 * @author Tubetrue01@gmail.com by 2022/7/22
 */
@Log4j2
@Component
public class TransactionAspect {

    /**
     * 定义切点表达式
     */
    private static final String AOP_POINTCUT_EXPRESSION = "execution(* org.cloud.*.*.*.*Service.*(..))";

    @Autowired
    private TransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {
        var source = new NameMatchTransactionAttributeSource();

        // 只读事务管理器
        var readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);

        // 当前事务存在时，使用当前事务；当没有当前事务时，创建新事务
        var requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        var txMap = new HashMap<String, TransactionAttribute>(16);

        // 配置需要事务的方法前缀
        txMap.put("add*", requiredTx);
        txMap.put("save*", requiredTx);
        txMap.put("insert*", requiredTx);
        txMap.put("create*", requiredTx);
        txMap.put("batch*", requiredTx);
        txMap.put("update*", requiredTx);
        txMap.put("modify*", requiredTx);
        txMap.put("delete*", requiredTx);
        txMap.put("remove*", requiredTx);
        // 配置只读事务方法前缀
        txMap.put("get*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        txMap.put("find*", readOnlyTx);
        txMap.put("select*", readOnlyTx);
        // 除此之外的方法添加事务
        txMap.put("*", requiredTx);
        source.setNameMap(txMap);

        return new TransactionInterceptor(transactionManager, source);
    }

    /**
     * 事务注册
     */
    @Bean
    public Advisor txAdviceAdvisor(TransactionInterceptor txAdvice) {
        var pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice);
    }

}
