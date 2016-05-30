package warmup.task;


import javax.annotation.concurrent.NotThreadSafe;

/**
 * Created by vladimir on 5/26/16.
 */

@NotThreadSafe
public class Account {
    private final long id;
    private long money;

    public Account(long id, long initialMoney) {
        this.id = id;
        this.money = initialMoney;
    }

    public long id(){
        return id;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
    }
}
