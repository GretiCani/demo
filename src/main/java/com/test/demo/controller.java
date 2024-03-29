package com.test.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class controller {

    @Autowired
    LanguageService languageService;
    public static String baseDir ="src/main/resources/messages";

    @GetMapping("/languages")
    public ResponseEntity<List<LanguageDTO>> getLanguages()throws IOException {

       return ResponseEntity.ok(languageService.getLanguages(baseDir));
    }

    @GetMapping("/languageNames")
    private ResponseEntity<List>getLanguageNames() throws IOException{
        return ResponseEntity.ok(languageService.languageNames(baseDir)) ;
    }

    @GetMapping("/language")
    public ResponseEntity<List<LanguageDTO>> getLanguage(@RequestParam String filename)throws IOException{
        return ResponseEntity.ok(languageService.findLanguageByFilename(baseDir,filename));

    }

}
