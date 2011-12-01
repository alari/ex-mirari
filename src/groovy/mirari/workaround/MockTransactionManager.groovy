package mirari.workaround

import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.TransactionDefinition

/**
 * @author alari
 * @since 12/1/11 6:26 PM
 */
class MockTransactionManager implements PlatformTransactionManager{

    TransactionStatus getTransaction(TransactionDefinition transactionDefinition) {
 println "GetTransaction"
    return null  //To change body of implemented methods use File | Settings | File Templates.
}

    void commit(TransactionStatus transactionStatus) {
        println "commit"
        //To change body of implemented methods use File | Settings | File Templates.
    }

    void rollback(TransactionStatus transactionStatus) {
        println "rollback"
        //To change body of implemented methods use File | Settings | File Templates.
    }
}