## API de treinamento para conceitos de POO e CRUD básico 

Trata-se de uma API em Java com Spring Framework, em que busco exercitar conceitos de REST e princípios do SOLID 
através da implementação de um CRUD de Customers e Addresses. 

Quanto às tecnologias utilizadas, o armzenamento foi feito em banco de dados relacional (MySQL) e foram implementados 
testes para a entidade de Customer. Ainda, há o consumo da API externa do viacep para busca e validação de dados de 
endereço após envio de um CEP via requisição. 

Portanto, trata-se de um CRUD básico que poderia ser resumido nas seguintes features:

### Features e conceitos trabalhados

* Aplicação em Java 17 utilizando Spring Boot;
* Armazenamento em banco de dados relacional (MySQL);
* Versionamento através de Git;
* Validações feitas para os campos de Customer e Address;
* Implementações de filtros em endpoints de GET;
* Testes unitários realizados com JUnit5 e Mockito;
* Consumo de API externa do [ViaCep](https://viacep.com.br/);
* Utilização de ModelMapper para conversão de objetos;

**UNDER CONSTRUCTION**