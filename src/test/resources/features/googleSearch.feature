@google
Feature: Google search functionality

  Scenario: User search title verification
    Given User is on google search page
    When User searches for "tree"
    Then User should see "tree" in the title