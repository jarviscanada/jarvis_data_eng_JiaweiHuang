package ca.jrvs.apps.grep;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp {
    public static void main(String[] args) {
        if  (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }
        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);
        try {
            javaGrepLambdaImp.process();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void process() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getOutFile()))) {
            listFiles(getRootPath()).stream()
                    .flatMap(file -> readLinesAsStream(file))
                    .filter(this::containPattern)
                    .forEach(line -> {
                        try {
                            writer.write(line);
                            writer.newLine();
                        } catch (IOException e) {
                            throw new RuntimeException("Error writing to file", e);
                        }
                    });
        }
    }

    private Stream<String> readLinesAsStream(File inputFile) {
        try {
            // ISO_8859_1 to prevent MalformedInputException
            return Files.lines(inputFile.toPath(), StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + inputFile.getName(), e);
        }
    }

//    @Override
//    public List<String> readLines(File inputFile) {
//        try {
//            return Files.lines(inputFile.toPath()).collect(Collectors.toList());
//        }catch (IOException e){
//            e.printStackTrace();
//            return Collections.emptyList();
//        }
//    }

    @Override
    public List<File> listFiles(String rootDir){
        File dir = new File(rootDir);
        File[] files = dir.listFiles();
        return Arrays.stream(files).flatMap(file -> file.isDirectory()
                ? listFiles(file.getPath()).stream()
                : Stream.of(file))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> readLines(File inputFile) {
        return readLinesAsStream(inputFile).collect(Collectors.toList());
    }
}

