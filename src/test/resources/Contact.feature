Feature: Check CRUD operation for Contact

  Background: Authenticate the user with credentials
    Given I am the authorized user

  Scenario: Authorized user is able to add Contact
    When Create Contact
    Then Receive the status code of 201

  Scenario: Authorized user is able to fetch contact
    When Fetch Contact
    Then Fetch Receive the status code of 200