package tests.entities;

import entity.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AccountTests {

    @Test
    public void depositShouldIncreaseBalanceWhenPositiveAmount() {

        Double amount = 200.0;
        Double expectedValue = 196.0;

        Account account = new Account(1L,0.0);

        account.deposit(amount);

        Assertions.assertEquals(expectedValue, account.getBalance());
    }
}
