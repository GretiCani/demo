package com.test.demo;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.*;
import static java.util.Map.Entry.*;

@Service
public class LanguageService {

    private FileWriter fileWriter;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String fileExtension = "*.properties";


    public void addLanguage(String filePath, HashMap<String,String>langPair) throws IOException {

        fileWriter = new FileWriter(new File(filePath));
        printWriter = new PrintWriter(fileWriter,true);

        for (String key : langPair.keySet()){
            printWriter.append(key+"="+langPair.get(key)).append('\n');
        }
        System.err.println("File name :"+new File(filePath).getName());
        printWriter.close();
        fileWriter.close();
    }

    public void updateLanguage(String filePath,HashMap<String,String> langPair)throws IOException{

        fileWriter = new FileWriter(new File(filePath),true);
        bufferedWriter = new BufferedWriter(fileWriter);
        for(String key : langPair.keySet()){
            bufferedWriter.append(key+"="+langPair.get(key)).append('\n');
        }
        bufferedWriter.close();
        fileWriter.close();
    }

    public HashMap<String,String> uploadLanguage(String filePath) throws IOException{
        BufferedReader csvReader = new BufferedReader(new FileReader(filePath));
        HashMap<String,String> langPair = new HashMap<>();
        String row;
        String array[];
        while ((row = csvReader.readLine())!=null){
            array = row.split(",",2);
            langPair.put(array[0],array[1]);
        }
        return langPair;
       }

       public boolean deleteLanguage(String filePath)throws IOException{
        File file = new File(filePath);
        if(file.delete())
            return true;
        else
            return false;
       }


       public List<LanguageDTO> readProperties(String filePath)throws IOException{
        bufferedReader = new BufferedReader(new FileReader(filePath));
        HashMap<String,String> langPair = new HashMap<>();
        List<LanguageDTO> language = new ArrayList<>();
        Path path = Paths.get(filePath);

           List<String> lines = bufferedReader.lines().collect(Collectors.toList());
        String[] arrayVal;
        for (String line : lines){
            arrayVal = line.split("=");
            language.add(new LanguageDTO(path.getFileName().toString().replaceFirst("[.][^.]+$", ""),arrayVal[0],arrayVal[1]));
        }
        return language;
       }

       public HashMap<String,String> sortByValueASC(HashMap<String,String> map){
        HashMap<String,String> sortedMap = map.entrySet().stream().sorted(comparingByValue())
                .collect(toMap(e->e.getKey(),e->e.getValue(),(e1,e2)->e2, LinkedHashMap::new));
        return sortedMap;
       }

       public List<LanguageDTO> getLanguages(String dir)throws IOException{

           Path path = Paths.get(dir);
           FileSearchPattern fileSearchPattern = new FileSearchPattern(fileExtension);
           List<LanguageDTO>langPair = new ArrayList<>();

           Files.walkFileTree(path,fileSearchPattern);

           for(Path p : fileSearchPattern.getPaths()){
               langPair = readProperties(p.toString());
           }

           return langPair;

       }

       public List<LanguageDTO> findLanguageByFilename(String baseDir,String filename)throws IOException{

        String fName = filename+".properties";
        List<LanguageDTO> language = new ArrayList<>();
        Path path = Paths.get(baseDir);
        FileSearchPattern fileSearchPattern = new FileSearchPattern(fileExtension);

        Files.walkFileTree(path,fileSearchPattern);

        for(Path p : fileSearchPattern.getPaths()){
            if(p.getFileName().toString().equals(fName)){
               language = readProperties(p.toString());
            }
        }

        return language;

       }

       public List<Languages> languageNames(String dir) throws IOException{

           List<Languages> langNames = new ArrayList<>();
           Path path = Paths.get(dir);
           FileSearchPattern fileSearchPattern = new FileSearchPattern(fileExtension);

           Files.walkFileTree(path,fileSearchPattern);

           fileSearchPattern.getPaths().forEach(path1 -> {
                   langNames.add(new Languages(path1.getFileName().toString().replaceFirst("[.][^.]+$", "")));

           });
           return langNames;
       }

    }

