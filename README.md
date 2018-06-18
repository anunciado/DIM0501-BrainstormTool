# Brainstorm Tool

Brainstorming is a technique by which a group attempts to find a solution(s) to a specific problem by amassing ideas spontaneously. In this case, this tool works in four phases for this: welcome, brainstorm, voting and rank. First, the owner of the brainstorming session will create a session and enter the welcome phase, from this, the owner will wait for requests (such as a server) from users (such as a client) of the tool. After, comes the brainstorm session, where each user can give an idea for the session. After, comes the voting session, where each user can vote on ideas given in the previous session up to the limit of votes per user of the session (by default this value is 3). After, comes the rank session, where will be listed all ideas with at least one vote in descending order.

### Prerequisites

You will need to install the modules below to run the program: 
* [java 8.0 or greater](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

Besides that, without a keystore, both the server and client will fail. This video show how to generate keystore using keytool program. Then run server and client with keystore and password.
Type the following command in your command window to create a keystore named examplestore and to generate keys:

```
$ keytool -genkey -alias signFiles -keystore examplestore
```

You will be prompted to enter passwords for the key and keystore. The password in this example is "123456".

### Running

Before running, you will have to modify part of the code to enter your key used by SSL. In method main of OwnerInterface, you will need to modify the bellow lines of code:
```java
System.setProperty("javax.net.ssl.keyStore",path to key);
System.setProperty("javax.net.ssl.keyStorePassword", password);
```
An example would be:
```java
System.setProperty("javax.net.ssl.keyStore","BrainstormTool/src/gui/examplestore");
System.setProperty("javax.net.ssl.keyStorePassword","123456");
```
In method main of UserInterface, you will need to modify the bellow lines of code:
```java
System.setProperty("javax.net.ssl.trustStore",path to key);
System.setProperty("javax.net.ssl.trustStorePassword",password);
```
An example would be:
```java
System.setProperty("javax.net.ssl.trustStore","BrainstormTool/src/gui/examplestore");
System.setProperty("javax.net.ssl.trustStorePassword","123456");
```
There are two ways to run this software:
* Compile the IDE (Eclipse - Java IDE):
1. Just open the IDE
2. Import the project folder as a Java Project
3. Click on the OwnerInterface class and then in the "Run" button.
4. Click on the UserInterface class and then in the "Run" button.
* Compile by terminal:
1. Enter the src folder and compile all the .java files with the following command:
```
$ java * .java -d <target_address_name>
```
2. Enter the chosen destination directory and run the following command:
For OwnerInterface:
```
$ java gui.OwnerInterface
```
For UserInterface:
```
$ java gui.UserInterface
```
3. From this it only interacts with the system.

## Built With

* [Eclipse](https://www.eclipse.org/) - A IDE used

## Authors
### Developers: 
* **Luís Eduardo Anunciado Silva ([cruxiu@ufrn.edu.br](mailto:cruxiu@ufrn.edu.br))** 
### Project Advisor: 
* **Andre Mauricio Cunha Campos ([augusto@dimap.ufrn.br](mailto:andre@dimap.ufrn.br))** 

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the GPL 3.0 - see the [LICENSE](LICENSE) file for details

