
# Introduction
The Java Grep application is a command-line tool designed to emulate the basic functionality of the
Linux `grep` command. It searches for text patterns within a directory tree and outputs matching lines to a specified
file. I developed two versions: a core **Java Implementation** using traditional loops and recursion, and a **Lambda 
Implementation** utilizing Java 8+ Streams and NIO for more functional, concise code. The project was built using Java 1.8, 
Maven for dependency management (SLF4J/Log4j for logging), and IntelliJ IDEA as the primary development environment.
# Quick Start
Note: an uber/far jar file is required for running the app
````
mvn clean package

# standard implementation
java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp .*Romeo.*Juliet.* ./data ./out/grep.txt 

# lambda implementation
java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepLambdaImp .*Romeo.*Juliet.* ./data ./out/grep.txt
````


# Implemenation
## Pseudocode
Process Method
````
matchedLines = []
for file in listFilesRecursively(rootDir)
for line in readLines(file)
if containsPattern(line)
matchedLines.add(line)
writeToFile(matchedLines)
````
listFiles
````
Files = []
for file in rootdir;
if file is directory;
listFile(file);
else Files add file;
Return Files;
````
readLines
````
lines = [];
while scanner has next line;
lines add nextline;
return lines;
````
containsPattern
````
return if line matches regex;
````
writeToFile
````
for line in lines;
    write line to output file;
````



## Performance Issue
The `OutOfMemoryError` may occur when the available heap memory is insufficient to process large files. For instance, if 
the heap memory size is set to 5MB and a text file, such as shakespeare.txt, exceeds 5.3MB, the program will throw an 
`OutOfMemoryError` while reading the file. To address this performance issue, one solution is to divide the data files 
into smaller chunks and read them separately using the Stream API and BufferedReader. Another solution is to increase 
the heap memory size using the `Java -Xmx` flag while running the program.

# Test
Manual testing was conducted using the following steps:

- A shakespeare.txt file located in [./data/txt/]() was used as sample data.

- The application was run via terminal using known strings (e.g., .*Romeo.*Juliet.*).

- Checked if the output file was created in the /out directory. 
- Compared the line count of the output 
file against the actual Linux grep command output. 

  
# Deployment
The app was dockerized for easier distribution.

**eclipse-temurin:8-jre** image was used for running **mvn clean package**
````
cat > Dockerfile << EOF
FROM eclipse-temurin:8-jre
COPY target/grep*.jar /usr/local/app/grep/lib/grep.jar
ENTRYPOINT ["java","-jar","/usr/local/app/grep/lib/grep.jar"]
EOF

mvn clean package
````

# Improvement
- Modify the output format to include the filename and line number for each match (e.g., file.txt:line_number: matched_text).
- Refactor the code to return Stream<String> instead of List<String> to handle files larger than the available RAM.
- Conduct unit testing of the functions within the grep app using JUnit4.
