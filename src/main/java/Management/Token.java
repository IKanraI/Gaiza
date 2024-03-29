package Management;

import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Token 
{
	@Getter private static String token = "";
	
	public Token() {
		setToken();
	}
	@SneakyThrows
	static public void setToken() {
		token = Files.readAllLines(Paths.get("/home/kanra/projects/data/hidden/token")).get(0);
//		token = Files.readAllLines(Paths.get("C:\\Users\\joelm\\Documents\\JavaProjects\\Hidden\\Token.txt")).get(0);
	}
}
