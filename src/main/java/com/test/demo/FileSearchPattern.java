package com.test.demo;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.List;

public class FileSearchPattern extends SimpleFileVisitor<Path> {

    private final PathMatcher pathMatcher;
    private static int count;
    private List<Path> paths = new ArrayList<>();
    private Path path;
    public FileSearchPattern(String pattern) {
        this.pathMatcher = FileSystems.getDefault().getPathMatcher("glob:"+pattern);
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        if(pathMatcher.matches(dir.getFileName())){
            count++;
            paths.add(dir);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if(pathMatcher.matches(file.getFileName())){
            count++;
            paths.add(file);
        }
        return FileVisitResult.CONTINUE;
    }

    public static   void log(Object object){
        System.err.println(object);
    }

    public int getCount(){
        return count;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public void setPaths(List<Path> paths) {
        this.paths = paths;
    }
}
