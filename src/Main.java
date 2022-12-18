import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        List<String> pathlist = new ArrayList<>();
        GameProgress gamer1 = new GameProgress(100, 3, 20, 3.8);
        GameProgress gamer2 = new GameProgress(60, 4, 12, 15.3);
        GameProgress gamer3 = new GameProgress(120, 3, 24, 5.1);
        saveGame(gamer1, "F://Game/savegame/save1.dat", pathlist);
        saveGame(gamer2, "F://Game/savegame/save2.dat", pathlist);
        saveGame(gamer3, "F://Game/savegame/save3.dat", pathlist);
        System.out.println(pathlist);
        zipFiles("F://Game/savegame/zip.zip", pathlist);
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

    public static void zipFiles(String pathZip, List<String> path) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(pathZip))) {
            for (int i = 0; i < path.size(); i++) {
                System.out.println(path.get(i));
                FileInputStream fis = new FileInputStream(path.get(i));
                ZipEntry entry  = new ZipEntry("save" + (i + 1) + ".dat");
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                zout.closeEntry();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}