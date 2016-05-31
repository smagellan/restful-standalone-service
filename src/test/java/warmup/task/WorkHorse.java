package warmup.task;

/**
 * Created by vladimir on 5/30/16.
 */
class WorkHorse {
    private final ApiInterface apiInterface;
    public WorkHorse(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }


    public void transferNotEnoughMoney() throws Exception {
        long firstAccount = apiInterface.createAccount(1000);
        long secondAccount = apiInterface.createAccount(2000);
        apiInterface.transfer(firstAccount, secondAccount, 2000);
    }


    public void transferIllegalAccountId() throws Exception {
        long firstAccount = apiInterface.createAccount(1000);
        long secondAccount = apiInterface.createAccount(2000);
        apiInterface.transfer(firstAccount + secondAccount, secondAccount, 2000);
    }

    public void transferIntraTransfer() throws Exception {
        long firstAccount = apiInterface.createAccount(1000);
        apiInterface.transfer(firstAccount, firstAccount, 2000);
    }
}
