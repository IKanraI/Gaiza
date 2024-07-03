package management;

import lombok.Getter;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;

public class Token 
{
	@Getter private String token = "";
	
	public Token() {
		setToken();
	}
	@SneakyThrows
	public void setToken() {
		token = Files.readAllLines(Paths.get("/home/kanra/projects/data/hidden/token")).get(0);
//		token = Files.readAllLines(Paths.get("C:\\Users\\joelm\\Documents\\JavaProjects\\Hidden\\Token.txt")).get(0);
	}
}
