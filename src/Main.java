import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        List<String> pathList = new ArrayList<>();
        GameProgress gamer1 = new GameProgress(100, 3, 20, 3.8);
        GameProgress gamer2 = new GameProgress(60, 4, 12, 15.3);
        GameProgress gamer3 = new GameProgress(120, 3, 24, 5.1);
        saveGame(gamer1, "F://Game/savegame/save1.dat", pathList);
        saveGame(gamer2, "F://Game/savegame/save2.dat", pathList);
        saveGame(gamer3, "F://Game/savegame/save3.dat", pathList);
        zipFiles("F://Game/savegame/zip.zip", pathList);
    }

    public static void saveGame(GameProgress gamer, String path, List<String> pathList) {
        try (FileOutputStream fin = new FileOutputStream(path);
                ObjectOutputStream fos = new ObjectOutputStream(fin)) {
            fos.writeObject(gamer);
            pathList.add(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void zipFiles(String pathZip, List<String> pathList) {
        File file;
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathZip))) {
            for(String path : pathList) {
                FileInputStream fis = new FileInputStream(path);
                String[] parts = path.split("/");
                String fileName = parts[parts.length - 1];
                ZipEntry entry = new ZipEntry(fileName);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
                file = new File(path);
                file.delete();
                if (file.delete())
                    System.out.println(fileName + " добавлен в архив и удален");
                else
                    System.out.println("Ошибка, не могу удалить " + fileName);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}