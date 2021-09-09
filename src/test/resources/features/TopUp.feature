Feature: TopUp Account
  This feature describes various scenarios for users adding funds to their revolut account(s)

  #As a user, I can top up my Revolut account using my debit card

  Scenario: Add money to Revolut account using debit card
    Given Danny has 10 euro in his euro Revolut account
    And Danny selects 100 euro as the topUp amount
    And  Danny selects his DebitCard as his topUp method
    #And  Danny selects his BankAccount as his topUp method
    When Danny tops up
    Then The new balance of his euro account should now be 110


  Scenario: Add money to Revolut account using bank account
    Given Danny has 20 euro in his euro Revolut account
    And Danny selects 230 euro as the topUp amount
    And  Danny selects his BankAccount as his topUp method
    #And  Danny selects his BankAccount as his topUp method
    When Danny tops up
    Then The new balance of his euro account should now be 250


  #ToDo implement the remaining scenarios listed below

  #To implement this scenario you will need to use data tables
    # https://cucumber.io/docs/cucumber/api/
  Scenario Outline: Add various amounts to Revolut account
    Given Danny has a starting balance of <startBalance>
    And Danny selects his DebitCard as his topUp method
    When Danny now tops up by <topUpAmount>
    Then The balance in his euro account should be <newBalance>
    Examples:
      | startBalance| topUpAmount | newBalance  |
      | 0           | 100         | 100         |
      | 14          | 20          | 34          |
      | 23          | 30          | 53          |

  Rule: The account balance shouldn't change if the topup payment request is rejected by the payment service

    #The scenarios below will need a payment service that accepts or rejects a request to add funds
    Scenario: Payment service rejects the request
      Given Danny has 50 euro in his euro Revolut account
      And Danny selects 100 euro as the topUp amount
      And  Danny selects his DebitCard as his topUp method
      And The Payment Service Rejects the request
      When Danny tops up
      Then The new balance of his euro account should now be 50

    Scenario: Payment service accepts the request
      Given Danny has 50 euro in his euro Revolut account
      And Danny selects 100 euro as the topUp amount
      And  Danny selects his DebitCard as his topUp method
      And The Payment Service Accepts the request
      When Danny tops up
      Then The new balance of his euro account should now be 150

    Scenario: Transfer money to another Revolt account
      Given Danny has 100 euro in his euro Revolut account
      And Mary has 50 euro in her euro Revolut account
      When Danny transfers 20 euro to Mary
      Then The new balance of his euro account should now be 80
      And The new balance of her euro account should now be 70

    Scenario: Split a payment between two cards
      Given Danny has 80 euro in his euro Revolut account
      And Mary has 100 euro in her euro Revolut account
      When They split a 90 euro payment
      Then The new balance of his euro account should now be 35
      And The new balance of her euro account should now be 55
