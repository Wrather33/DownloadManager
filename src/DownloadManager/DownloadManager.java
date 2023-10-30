package DownloadManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class DownloadManager {
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException, InterruptedException {
        while (true){
            String str = bufferedReader.readLine();
            String path = bufferedReader.readLine()+"\\";
            if (str.equals("exit")){
                return;
            }
            else {
                DownloadThread downloadThread = new DownloadThread(str,path);
                downloadThread.start();
            }

        }
    }
}
