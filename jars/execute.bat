start javaw -jar port8080.jar 
start javaw -jar port8085ConAut.jar
start javaw -jar port9000ConAut.jar
timeout 10
start http://localhost:8080/
start http://localhost:8085/
start http://localhost:9000/