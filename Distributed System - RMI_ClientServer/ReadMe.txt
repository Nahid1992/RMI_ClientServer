.......
To Run (Windows):

1. (rmiregistry) Run runRMI.bat from cmd with one parameter (port)
example: runRMI 1099

2. (server) Run runServer.bat from cmd.
example: runServer

3. (clients) Run runClient.bat from cmd with 5 parameters (hostname, username, locationX, locationY, age)
example: runClient localhost:2236 Nahid 20 -30 23

*repeat 3 multiple times for multiple clients

........

Note: In ServerDriver.java, the PORT is set as "1099" (default port). To use different PORT while running rmiregistry (step 1),
please set the same port in ServerDriver.java. 