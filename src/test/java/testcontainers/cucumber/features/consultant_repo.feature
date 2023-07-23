Feature: Consultant Repository Test
  Scenario: A consultant is being saved
    Given A consultant named "Harout" is saved
    When I ask whether "Harout" has been saved
    Then It should have saved "Harout"

  #Scenario: Consultant "Harout" is being deleted
  #  Given A consultant named "Harout" is deleted
  #  When I ask whether "Harout" has been deleted
  #  Then It should have deleted