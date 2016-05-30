package warmup.task;

import javax.inject.Singleton;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by vladimir on 5/26/16.
 */

@Singleton
public class ApiInterfaceImpl implements ApiInterface {
    private final ConcurrentHashMap<Long, Account> accounts;
    private final AtomicLong idGenerator;


    public ApiInterfaceImpl() {
        this.accounts    = new ConcurrentHashMap<>();
        this.idGenerator = new AtomicLong(1);
    }

    @Override
    public long userBalance(long uid) throws ProcessingException {
        Account account = accounts.get(uid);
        if (account != null) {
            long result;
            synchronized (account) {
                result = account.getMoney();
            }
            return  result;
        } else {
            throw new ProcessingException("unknown account id");
        }
    }

    public long createAccount(long initialAmount) throws ProcessingException {
        Account account = new Account(idGenerator.getAndIncrement(), initialAmount);
        accounts.put(account.id(), account);
        return account.id();
    }

    public void transfer(long srcAccountId, long destAccountId, long amount) throws ProcessingException{
        if (srcAccountId == destAccountId) {
            throw new ProcessingException("intra transfers are not supported");
        } else {
            Account srcAccount = accounts.get(srcAccountId);
            Account destAccount = accounts.get(destAccountId);

            if (srcAccount == null || destAccount == null) {
                throw new ProcessingException("illegal account id");
            } else {
                Object monitor1 = srcAccountId < destAccountId ? srcAccount : destAccount;
                Object monitor2 = srcAccountId < destAccountId ? destAccount : srcAccount;
                synchronized (monitor1) {
                    synchronized (monitor2) {
                        if (srcAccount.getMoney() - amount > 0) {
                            srcAccount.setMoney(srcAccount.getMoney() - amount);
                            destAccount.setMoney(destAccount.getMoney() + amount);
                        } else {
                            throw new ProcessingException("not enough money");
                        }
                    }
                }
            }
        }
    }

    @Override
    public Set<Long> userIds() {
        return new HashSet(Collections.list(this.accounts.keys()));
    }
}


