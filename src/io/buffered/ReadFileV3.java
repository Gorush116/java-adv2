package io.buffered;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import static io.buffered.BufferedConst.BUFFER_SIZE;
import static io.buffered.BufferedConst.FILE_NAME;

public class ReadFileV3 {

    public static void main(String[] args) throws IOException {
        FileInputStream fis = new FileInputStream(FILE_NAME);
        BufferedInputStream bis = new BufferedInputStream(fis, BUFFER_SIZE);
        long startTime = System.currentTimeMillis();

        // 1. 버퍼에 우선 버퍼사이즈만큼 담아두고
        // 2. read()를 호출할 때마다 반환
        // 3. read() 호출 시 비어있으면 다시 버퍼사이즈만큼 담는다
        int fileSize = 0;
        int data;
        while ((data = bis.read()) != -1) {
            fileSize++;
        }
        bis.close();

        long endTime = System.currentTimeMillis();

        System.out.println("File Created in " + (endTime - startTime) + " ms");
        System.out.println("File Size: " + fileSize / 1024 / 1024 + "MB");
    }
}
