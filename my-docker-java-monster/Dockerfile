# specify the Java
FROM eclipse-temurin:21-jdk

# specify the working directory inside the container
WORKDIR /app

# copy the source code into the directory
COPY . .

# compile and run the code
RUN javac *.java

# run the compiled code
CMD ["java", "Main"]



