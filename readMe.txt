ROUTE PLANNER (phase 3 using struts)
-------------------------------------------------------------------------------

SETUP FROM SCRATCH ON FRESH MACHINE:
- sudo apt-get install p7zip
- copy Route Planner directory onto machine

1) Java jdk 7 (incudes jre)
   Example location: /usr/local/ProgramFiles 
  
2) Download ANT 
   Example location: /usr/local/ProgramFiles 

3) Set environment variables for Java and Ant
   --> Open ~/.bashrc with Vi 
       export ANT_OPTS="-Xmx256M"
       export ANT_HOME=/usr/local/ProgramFiles/apache-ant-1.8.2
       export JAVA_HOME=/usr/lib/jvm/jdk1.7.0
       export PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:
                   /sbin:/bin:/usr/games:${ANT_HOME}/bin:${JAVA_HOME}/bin
   --> Close terminal and open a new one.
       Verify Java and Ant are set: ant -version

3) MySQL 5.1
   a) System->Administration->Synaptic Package
      --> MySQL client and server
      OR
      sudo apt-get install mysql-server
   b) Check it is running:
      sudo netstat -tap | grep mysql
      OR
      Restart:
      sudo /etc/init.d/mysql restart
   c) START MySQL FROM LINUX: mysql -u root -h localhost -p 
             [when prompted]: mysql root password
   d) Setup DDL and DML from Route Planner folder

4) Install Tomcat (could use wget 'http://[apache_download].tar.gz' and then 'tar xvzf [tarfile]')
   Example location: /usr/local/tomcat - (no env variables set here)
   Copy it from home/Downloads using: sudo mv tomcat.tar.gz /usr/local/tomcat 

5) Eclipse:
   --> Download eclipse
   --> Download Sysdeo Tomcat plugin for eclipse. Restart eclipse and verify Tomcat icons are showing
   --> Unzip the project into desired workspace
   --> Open up project in eclipse:
       File/Import/General/Existing Projects into Workspace
       OR (MAYBE)
       File/New/Other/Java/Tomcat Project
   --> Window/Preferences/Tomcat/[Advanced]
       Tomcat home/base: eg. /usr/local/tomcat
                        (keep base the same as home)
       Contexts directory: \usr\local\tomcat\webapps\RoutePlannerMVC_phase3_struts\conf
                           \Catalina\localhost
   --> Add Tomcat libraries (also check project buildpath)    
       [Right click on project] Tomcat project/add Tomcat libraries to project buildpath

DEPLOYMENT:
1) Switch on Tomcat
2) Run ant deploy from within project in source workspace (containing build.xml)
3) Run from browser
4) Switch off Tomcat
5) Remove project context file in tomcat/conf/Catalina/localhost
6) Remove line of code in LoginAction execute() that generates test file,
   and remove generated test file

BROWSER URL: http://localhost:8080/RoutePlannerMVC_phase3_struts
--> admin1/999
--> member1/999
--> guest1/999

-------------------------------------------------------------------------------

ISSUES ON SETTING UP ON DIFFERENT MACHINES:

1) Update build.xml parameters:
   --> tomcat
   --> local_war

2) Note where TEST_FILE is created by Tomcat on first run attempt
   a) Verify on WEB-INF/classes/java/MessageResources.properties:
      --> app root extension (usually not necessary)
   b) Verify log file path matches in logs.rp3log.properties:
      --> log4j.appender.R.File

3) Alter on WEB-INF/classes/java/MessageResources.properties: 
   --> Change DB params as appropriate for ORACLE OR MySQL

4) For Linux Ubuntu the path delimiters in MessageResources.properties were 
   changed from '\\' to '/'. This may not have been necessary, and should
   be changed back for Windows installations in any case.
       
-------------------------------------------------------------------------------

BUGS FIXED:
1) 01/02/12  com.fdm.db.AccessControl loadAccessProperties(String propFilePath)
             SOLUTION:  propFilePath.trim() 

-------------------------------------------------------------------------------
