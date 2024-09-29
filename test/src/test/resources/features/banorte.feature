Feature: Banorte

Scenario: Validar inicio de Banorte
    Given Abrir la pagina de Banorte
    When La pagina este cargada 
    Then Validar el pagina Inicio banorte
    And Cerrar el explorador
 
 Scenario: Validar Titulo
 				Given Abrir la pagina de Banorte
 				When La pagina este cargada
 				Then Validar Titulo Principal
 				And Cerrar el explorador
 				
Scenario: Buscar en pagina
 				Given Abrir la pagina de Banorte
 				When La pagina este cargada
 				Then Buscar en pagina Seguro de Auto
 				Then Validar resultado de busqueda
 				And Cerrar el explorador
				
Scenario: Abrir pagina seguros y validar que accesamos
 				Given Abrir la pagina de Banorte
 				When La pagina este cargada
 				Then Buscar en pagina Seguro de Auto
 				Then Validar resultado de busqueda
 				Then Validar pagina seguros
 				And Cerrar el explorador
				
Scenario: Validar Necesita ayuda
 				Given Abrir la pagina de Banorte
 				When La pagina este cargada
 				Then Buscar en pagina Seguro de Auto
 				Then Validar resultado de busqueda
 				Then Valida necesita ayuda
 				And Cerrar el explorador