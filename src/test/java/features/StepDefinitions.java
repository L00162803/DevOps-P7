package features;

import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import revolut.PaymentService;
import revolut.Person;

import java.util.List;

public class StepDefinitions {

    private double topUpAmount;
    //private String topUpMethod;
    PaymentService topUpMethod;
    Person danny;
    Person mary;

    @Before//Before hooks run before the first step in each scenario
    public void setUp(){
        //We can use this to setup test data for each scenario
        danny = new Person("Danny");
        mary = new Person("Mary");
    }
    @Given("Danny has {double} euro in his euro Revolut account")
    public void dannyHasEuroInHisEuroRevolutAccount(double startingBalance) {
        danny.setAccountBalance(startingBalance);
    }

    @Given("Danny selects {double} euro as the topUp amount")
    public void danny_selects_euro_as_the_top_up_amount(double topUpAmount) {
        this.topUpAmount = topUpAmount;
    }

    @Given("Danny selects his {paymentService} as his topUp method")
    public void danny_selects_his_debit_card_as_his_top_up_method(PaymentService topUpSource) {
        topUpMethod = topUpSource;
    }

    @When("Danny tops up")
    public void danny_tops_up() {
        if(topUpMethod.getServiceStatus()=="Pass") {
            danny.getAccount("EUR").addFunds(topUpAmount);
        }
    }

    @Then("The new balance of his euro account should now be {double}")
    public void the_new_balance_of_his_euro_account_should_now_be(double newBalance) {
        //Arrange
        double expectedResult = newBalance;
        //Act
        double actualResult = danny.getAccount("EUR").getBalance();
        //Assert
        Assert.assertEquals(expectedResult, actualResult,0);
    }


    @Given("Danny has a starting balance of {double}")
    public void dannyHasAStartingBalanceOfStartBalance(double startingBalance) {
        danny.setAccountBalance(startingBalance);
    }

    @When("Danny now tops up by {double}")
    public void dannyNowTopsUpByTopUpAmount(Double topUpAmount) {
        this.topUpAmount = topUpAmount;
        danny.getAccount("EUR").addFunds(topUpAmount);
    }

    @Then("The balance in his euro account should be {double}")
    public void theBalanceInHisEuroAccountShouldBeNewBalance(double newBalance) {
        //Arrange
        double expectedResult = newBalance;
        //Act
        double actualResult = danny.getAccount("EUR").getBalance();
        //Assert
        Assert.assertEquals(expectedResult, actualResult,0);
    }

    @And("The Payment Service {word} the request")
    public void thePaymentServiceRejectsTheRequest(String requestStatus) {
        if(requestStatus.equals("Rejects")){
            topUpMethod.setServiceStatus("Fail");
        }
        if(requestStatus.equals("Accepts")) {
            topUpMethod.setServiceStatus("Pass");
        }
    }

    @Given("Mary has {double} euro in her euro Revolut account")
    public void maryHasEuroInHisEuroRevolutAccount(double startingBalance) {
        mary.setAccountBalance(startingBalance);
    }

    @When("Danny transfers {int} euro to Mary")
    public void dannyTransfersEuroToMary(int transferAmount) {
        danny.getAccount("EUR").subtractFunds(transferAmount);
        mary.getAccount("EUR").addFunds(transferAmount);
    }

    @And("The new balance of her euro account should now be {int}")
    public void theNewBalanceOfHerEuroAccountShouldNowBe(double newBalance) {
        //Arrange
        double expectedResult = newBalance;
        //Act
        double actualResult = mary.getAccount("EUR").getBalance();
        //Assert
        Assert.assertEquals(expectedResult, actualResult,0);
    }

    @When("They split a {double} euro payment")
    public void theySplitAEuroPayment(double paymentAmount) {
        double individualAmount = paymentAmount /2;
        danny.getAccount("EUR").subtractFunds(individualAmount);
        mary.getAccount("EUR").subtractFunds(individualAmount);
    }
}
