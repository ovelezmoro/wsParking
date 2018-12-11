/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.parking.app.util;

import java.io.File;
import java.util.Date;
import java.util.List;

/**
 *
 * @author CPizarro
 */
public class PolizaUtil {

    private final List<File> files;
    private final String filename;
    private final String directory;

    public PolizaUtil(List<File> files, String filename, String directory) {
        this.files = files;
        this.filename = filename;
        this.directory = directory;
    }

    public File getPolizaZip() {
        File file = new File(directory, filename);
        String[] paths = new String[files.size()];
        for (int i = 0; i < files.size(); i++) {
            File aux = files.get(i);
            if (aux != null && aux.exists()) {
                paths[i] = aux.getAbsolutePath();
            }
        }
        ZipUtil.compressZip(paths, file.getAbsolutePath());
        FileUtil.copyFile(file, new File(directory, "bkp/Poliza" + StrUtil.getDate(new Date(), "yyyyMMddHHmm") + ".bkp"));
        for (String path : paths) {
            FileUtil.deleteFile(new File(path));
        }
        return file;
    }

}
