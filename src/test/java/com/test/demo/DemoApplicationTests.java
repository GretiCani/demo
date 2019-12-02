package com.test.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;


@SpringBootTest
class DemoApplicationTests {

	private final LanguageService languageService = new LanguageService();


	public static String fileName ="src/test/resources/test_lang.properties";
    public static String shqip ="src/test/resources/shqip_lang.properties";
	public static String baseDir ="src/test/resources";



	@Test
	void contextLoads() throws IOException {
		Path newFilePath = Paths.get("src/test/resources/test_lang.properties");
		Files.createFile(newFilePath);

	}

	@Test
	void whenLanguageIsAdded_thenCorrect() throws IOException{
		HashMap<String,String> langPair = new HashMap<>();
		langPair.put("prop.one","Property 1");
		langPair.put("prop.two","Property 2");
		langPair.put("prop.three","Property 3");
        languageService.addLanguage(shqip,langPair);
	}


	@Test
	void whenEditingFile_thenCorrect() throws IOException{
		HashMap<String,String> langPair = new HashMap<>();
		langPair.put("prop.four","Property 4");
		langPair.put("prop.five","Property 5");
		langPair.put("prop.six","Property 6");
		languageService.updateLanguage(shqip,langPair);

	}



	@Test
    void whenAddLanguageFromCsv_thenCorrect()throws IOException{
	  HashMap<String,String>  uploadedLang = languageService.uploadLanguage(fileName);
	  languageService.addLanguage(shqip,uploadedLang);
	  Assertions.assertEquals(uploadedLang.size(),4);
    }

    @Test
    void whenUpdateLanguageFromCsv_thenCorrect()throws IOException{
        HashMap<String,String>  uploadedLang = languageService.uploadLanguage(fileName);
        languageService.updateLanguage(shqip,uploadedLang);
        Assertions.assertEquals(uploadedLang.size(),4);
    }

    @Test
	void whenDeleteFile_thenCorrect()throws IOException{
    	boolean isDeleted = languageService.deleteLanguage(fileName);
    	Assertions.assertEquals(true,isDeleted,"The except value does not match actual value");
	}

//	@Test
//	void whenHashMapIsSorted_thenCorrect() throws IOException{
//        HashMap<String,String> langPair = languageService.readProperties(shqip);
//        System.err.println("Map before sorting => "+langPair);
//        HashMap<String,String> sortedByValue = languageService.sortByValueASC(langPair);
//		System.err.println("Map after sorting => "+sortedByValue);
//
//	}

	@Test
	void whenSearchingCompleated_thenCorrect()throws IOException{
		languageService.getLanguages(baseDir);
	}

	@Test
	void whenReadAllFilesContent_thenCorrect()throws  IOException{
//		List<Path> paths = languageService.getLanguages(baseDir);
//		HashMap<String,HashMap<String,String >> languageContent = new HashMap<>();
//
//		paths.forEach(path ->
//		{
//			try {
//				languageContent.put(path.getFileName().toString().replaceFirst("[_][^_]+$", ""),languageService.readProperties(path.toString()));
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		});
//
//		languageContent.forEach((k,v)->{
//			System.err.println(k);
//			System.out.println(v);
//		});

//		Assertions.assertEquals(3,languageContent.size());
	}
}
