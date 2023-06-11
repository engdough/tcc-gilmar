package solunit.mapper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;

import solunit.model.TimeTest;

public class MapperTimeTest {
	private HashMap<String, TimeTest> cacheTimeTest = new HashMap<>();
	private String filename;
	private static MapperTimeTest instancia;

	public MapperTimeTest(String filename) {
		this.filename = filename + ".dados";
		load();
	}

	public static MapperTimeTest getMapper(String filename) {
		if (instancia == null) {
			instancia = new MapperTimeTest(filename);
		}
		return instancia;
	}

	public void persist() {
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(filename);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

			objectOutputStream.writeObject(cacheTimeTest);

			objectOutputStream.flush();
			fileOutputStream.flush();

			objectOutputStream.close();
			fileOutputStream.close();

		} catch (FileNotFoundException ex) {
			System.out.println("ARQUIVO NAO ENCONTRADO");
		} catch (IOException ex) {
			System.out.println("ERRO AO ABRIR O ARQUIVO");
		}
	}

	public void load() {
		try {
			FileInputStream fileInputStream = new FileInputStream(filename);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

			this.cacheTimeTest = (HashMap<String, TimeTest>)objectInputStream.readObject();

			objectInputStream.close();
			fileInputStream.close();

		} catch (ClassNotFoundException ex) {
			System.out.println("ERRO AO ABRIR O ARQUIVO");
		} catch (FileNotFoundException ex) {
			System.out.println("ARQUIVO NAO ENCONTRADO");
            persist();
		} catch (IOException ex) {
			System.out.println("ERRO AO ABRIR O ARQUIVO");
		}
	}

	public void put(TimeTest timeTest) {
		this.cacheTimeTest.put(timeTest.getName(), timeTest);
		persist();
	}

	public TimeTest get(String nameTest) {
		load();
		return cacheTimeTest.get(nameTest);
	}

	public void remove(String nameTest) {
		cacheTimeTest.remove(nameTest);
		persist();
	}

	public Collection<TimeTest> getList() {
		load();
		return cacheTimeTest.values();
	}
}
