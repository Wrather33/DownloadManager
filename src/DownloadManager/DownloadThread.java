package DownloadManager;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import org.apache.commons.io.*;


public class DownloadThread extends Thread{
    private String url;
    private String pathName;
    public DownloadThread(String url, String pathName) {
        this.url = url;
        this.pathName = pathName;
    }

    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        synchronized (this) {
            try {
                URL address = new URL(getUrl());
                URLConnection connection = address.openConnection();
                InputStream inputStream = address.openStream();
                String fileName = new File(address.getFile()).getName();
                String type = Files.probeContentType(Path.of(address.getFile()));
                if (type == null){
                    throw new FileNotFoundException();
                }
                String pathName = getPathName();
                if (!Files.exists(Path.of(pathName))) {
                    throw new InvalidPathException(pathName, "Директории не существует");
                }
                FileOutputStream fileOutputStream = new FileOutputStream(pathName + fileName);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                int c;
                while ((c = bufferedInputStream.read()) != -1) {
                    bufferedOutputStream.write(c);
                }
                {
                }
                bufferedInputStream.close();
                bufferedOutputStream.close();
                System.out.println(String.format("%s downloaded", fileName));
            }
            catch (FileNotFoundException e){
                System.out.println("Файл не найден");
            }
            catch (MalformedURLException e) {
                System.out.println("Невердный адрес");
            } catch (InvalidPathException e) {
                System.out.println(e.getReason());
            } catch (IOException e) {
                System.out.println("Ошибка ввода");
            }
        }}


}
