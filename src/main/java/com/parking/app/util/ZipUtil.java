package com.parking.app.util;

import java.io.*;
import java.util.zip.*;

public class ZipUtil {

    private static final int BUFFER = 1024;

    public static void uncompressZip(String file, String path) {
        try {
            BufferedOutputStream dest;
            FileInputStream fis = new FileInputStream(path + file);
            CheckedInputStream checksum = new CheckedInputStream(fis, new Adler32());
            ZipInputStream zis = new ZipInputStream(new BufferedInputStream(checksum));
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if (!entry.getName().equalsIgnoreCase("Thumbs.db")) {
                    if (entry.getName().endsWith("/")) {
                        File folder = new File(path + entry.getName());
                        if (!folder.exists()) {
                            folder.mkdirs();
                        }
                    } else {
                        int count;
                        byte data[] = new byte[BUFFER];
                        FileOutputStream fos = new FileOutputStream(path + entry.getName());
                        dest = new BufferedOutputStream(fos, BUFFER);
                        while ((count = zis.read(data, 0, BUFFER)) != -1) {
                            dest.write(data, 0, count);
                        }
                        dest.flush();
                        dest.close();
                        fos.close();
                    }
                }
            }
            zis.close();
            checksum.close();
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void compressZip(String[] fileNames, String fileName) {
        byte[] buf = new byte[1024];
        try {
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(fileName));
            for (String fName : fileNames) {
                FileInputStream in = new FileInputStream(new File(fName));
                File file = new File(fName);
                out.putNextEntry(new ZipEntry(file.getName()));
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.closeEntry();
                in.close();
            }
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
