Feature: Usuario
#Comentarios con almohadilla
Scenario: BDD vacia
	Given BDD iniciada sin datos
    When Yo busco un valor
    Then El resultado es vacio
    
Scenario Outline: Creando un usuario
	Given BDD iniciada sin datos
    When Yo creo un usuario con nick <key> y rol <rol>
    Then yo puedo encontrarlo con el nick <key> y el rol <rol>
    
    Examples: Ejemplo Usuarios
    | key | rol |
    | admin   | "administrador" |
    | val   | "validador" |
    

   
