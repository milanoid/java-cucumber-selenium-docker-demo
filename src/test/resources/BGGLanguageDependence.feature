Feature: Is Language Dependence present on a game page?
  A game page on the BoardGameGeek contains most voted Language Dependence level

  Background: the user is logged in
    Given the user is successfully logged in
    And the user click its username to open menu

  @godfather
  Scenario: A game page contains language dependencies
    Given the user selects "Collection" from the menu
    And the user opens game title "The Godfather: Corleone's Empire"
    When the user opens polls and results for language dependence
    Then the slide out panel is displayed with items
      | No necessary in-game text                                        |
      | Some necessary text - easily memorized or small crib sheet       |
      | Moderate in-game text - needs crib sheet or paste ups            |
      | Extensive use of text - massive conversion needed to be playable |
      | Unplayable in another language                                   |
