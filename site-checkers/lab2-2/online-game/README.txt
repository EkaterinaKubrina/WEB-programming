Для того чтобы запустить проект нужно открыть командную строку в папке проекта, и ввести команду - "mvnw spring-boot:run"

Для того чтобы собрать/пересобрать .jar файл нужно ввести команду - "mvnw clean package"

Для запуска .jar файла нужно в командной строке папки проекта ввести команду -
"java -Dserver.port=<port> -Dserver.servlet.contextPath=/<path> -jar  target\online-game-0.0.1-SNAPSHOT.jar"
или
"java -jar target\online-game-0.0.1-SNAPSHOT.jar --server.port=<port> --server.servlet.contextPath=/<path>"

и открыть в браузере адрес http://localhost:<port>/<path>/