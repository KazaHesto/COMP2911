import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceManager {

	/**
	 * method: save() -> saves the game
	 * 
	 * @param 	data			save data
	 * @param 	file			where you want to save the game
	 * @return	-
	 * @throws 	Exception		the game is not able to be saved
	 */
	public static void save(Serializable data, File file) throws Exception {
		// save the game to a file
		String filePath = file.getAbsolutePath();
		if (!filePath.endsWith(".save")) {
			filePath = filePath.concat(".save");
		}
		try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(filePath)))) {
			oos.writeObject(data);
		}
	}
	/**
	 * method: load() -> loads a game based on a save file
	 * 
	 * @param 	file			file that is to be loaded
	 * @return	-
	 * @throws 	Exception		if the game is not able to be saved
	 */

	public static Object load(File file) throws Exception {
		// load the save file
		try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(file.getAbsolutePath())))) {
			return ois.readObject();
		}
	}
}