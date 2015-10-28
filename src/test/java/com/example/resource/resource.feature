Feature: Resource
#Comentarios con almohadilla
Scenario: Storage empty
	Given A empty resource store
    When I search all resources
    Then The result its empty
    
Scenario Outline: Create a resource
	Given A empty resource store
    When I create a resource with key <key> and value <value>
    Then I can find it in the store with key <key> and value <value>
    
    Examples: Example Resources
    | key | value |
    | 1   | "value 1" |
    | 2   | "value 2" |
    

Scenario: Create a duplicated resource
	Given A empty resource store
    Then I create a duplicated resource with key 1 and value value 1
