# Use a imagem base do OpenJDK 21
FROM openjdk:21-jdk-slim

# Define o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copia o arquivo pom.xml e o código fonte para o contêiner
COPY pom.xml .
COPY src ./src

# Instala o Maven e constrói o projeto
RUN apt-get update && apt-get install -y maven && mvn clean package -DskipTests

# Expõe a porta que a aplicação Spring vai rodar
EXPOSE 8080

#Copia o arquivo JAR gerado para o contêiner
COPY target/rede-0.0.1-SNAPSHOT.jar app.jar

# Comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]
