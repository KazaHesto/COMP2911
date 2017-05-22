import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceManager {

	public static void save(Serializable data, File file) throws Exception {
		String filePath = file.getAbsolutePath();
		if (!filePath.endsWith(".save")) {
		System.out.println(filePath);
			filePath = filePath.concat(".save");
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(filePath)))) {
			oos.writeObject(data);
		}
	}

	public static Object load(File file) throws Exception {
		System.out.println(file.getAbsolutePath());
		try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(file.getAbsolutePath())))) {
			return ois.readObject();
		}
	}
}