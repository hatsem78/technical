# technical-challenge
Technical Challenge

# Se crearon los siguientes enpoint .

    a) Creación de un cleinte

        http://0.0.0.0:8087/api/customer/add

    b) Update cliente
        http://0.0.0.0:8087/api/customer/update/1

    c) Find cliente
        http://0.0.0.0:8087/api/customer/find/4

    d) Cosnultar todos los clientes
        http://0.0.0.0:8087/api/customer/stadistic

    f) Delete cliente
        http://0.0.0.0:8087/api/customer/delete/2

    g) Estadostocas
        http://0.0.0.0:8087/api/customer/stadistic

    h) Cliente person_relationship_add
        http://0.0.0.0:8087/api/customer/persons/

    i) Cliente customer_person_relationship
        http://0.0.0.0:8087/api/customer/relationship/3/4



# Los enpoint de ejemplo estan
    estos se encuentran /technical-challenge/src/main/resources/technical-challenge.postman_collection.json

# Configuraciòn para la base de datos
    En el archivo application-mysql.properties

    Cambiar las iguientes propiedadees para la conceccion
    #Coneccion mysql application-mysql.properties
    EN el caso de crear una base de datos local o remoto
    spring.datasource.url=jdbc:mysql://localhost:3306/test?verifyServerCertificate=false&useSSL=true
    spring.datasource.username=root
    spring.datasource.password=root@2021
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    spring.jpa.hibernate.ddl-auto=create
    spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect

    si se deja esa propiedad en create, crea las tablas correspondeitntes
    spring.jpa.hibernate.ddl-auto=create
    para que no lo cree tiene que estar seteado en none
    spring.jpa.hibernate.ddl-auto=create

    Si se utiliza docker debe cambiar el archivo application-mysql.properties por:
    Para utilizar una base de datos en base a docker

    # database init, supports mysql too
    database=mysql
    spring.datasource.url=${MYSQL_URL:jdbc:mysql://localhost/petclinic}
    spring.datasource.username=${MYSQL_USER:petclinic}
    spring.datasource.password=${MYSQL_PASS:petclinic}
    # SQL is written to be idempotent so this is safe
    spring.datasource.initialization-mode=always

## Ejecuciòn del programa con docker-compose

    para ejecutar docker en formato develop:
    docker-compose -f docker-compose.dev.yaml up --build

    Ejecutar los test
    docker-compose -f docker-compose.test.yaml up --build