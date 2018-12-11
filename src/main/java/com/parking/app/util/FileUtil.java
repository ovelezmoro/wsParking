package com.parking.app.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class FileUtil {

    public static void writeFile(File file, Boolean rewrite, String text) {
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            try {
                fw = new FileWriter(file, rewrite);
                pw = new PrintWriter(fw);
                pw.print(text);
            } catch (Exception e) {
                throw e;
            } finally {
                if (pw != null) {
                    pw.close();
                }
                if (fw != null) {
                    fw.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeFile(String name, Boolean rewrite, String text) {
        writeFile(new File(name), rewrite, text);
    }

    public static void writeFile(String name, String text) {
        writeFile(name, false, text);
    }

    public static void writeFile(File file, Boolean rewrite, String text, String charSet) {
        OutputStreamWriter osw = null;
        FileOutputStream fos = null;
        try {
            try {
                fos = new FileOutputStream(file, rewrite);
                osw = new OutputStreamWriter(fos, charSet);
                osw.append(text);
            } catch (Exception e) {
                throw e;
            } finally {
                if (osw != null) {
                    osw.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeFile(String name, Boolean rewrite, String text, String charSet) {
        writeFile(new File(name), rewrite, text, charSet);
    }

    public static void writeFile(String name, String text, String charSet) {
        writeFile(name, false, text, charSet);
    }

    public static String readFile(File file) {
        String data = "\r\n";
        FileReader fr = null;
        BufferedReader br = null;
        StringBuilder strBuilder = new StringBuilder(data);
        try {
            try {
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                String linea;
                while ((linea = br.readLine()) != null) {
                    strBuilder.append("\r\n");
                    strBuilder.append(linea);
                }
            } catch (Exception e) {
                throw e;
            } finally {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return strBuilder.toString().trim();
    }

    public static List<String> getFiles(File file) {
        List<String> files = new ArrayList<String>();
        FileReader fr = null;
        BufferedReader br = null;
        try {
            try {
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                String linea;
                while ((linea = br.readLine()) != null) {
                    files.add(linea);
                }
            } catch (Exception e) {
                throw e;
            } finally {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return files;
    }

    public static void fileMove(String sourceFile, String destinationFile) {
        File inFile;
        File outFile;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            try {
                inFile = new File(sourceFile);
                outFile = new File(destinationFile);
                fis = new FileInputStream(inFile);
                fos = new FileOutputStream(outFile);
                int c;
                while ((c = fis.read()) != -1) {
                    fos.write(c);
                }
                File file = new File(sourceFile);
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e) {
                throw e;
            } finally {
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void inputStreamToFile(InputStream is, File file) {
        OutputStream out = null;
        try {
            try {
                out = new FileOutputStream(file);
                byte buf[] = new byte[1024];
                int len;
                while ((len = is.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } catch (Exception e) {
                throw e;
            } finally {
                if (out != null) {
                    out.close();
                }
                is.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void readFileAndDo(File file) {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            try {
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                String linea;
                while ((linea = br.readLine()) != null) {
                    doMethod(linea);
                }
            } catch (Exception e) {
                throw e;
            } finally {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void doMethod(String line);

    public static void deleteFile(File file) {
        if (file.isDirectory()) {
            if (file.list().length == 0) {
                file.delete();
            } else {
                String files[] = file.list();
                for (String temp : files) {
                    File fileDelete = new File(file, temp);
                    deleteFile(fileDelete);
                }
                if (file.list().length == 0) {
                    file.delete();
                }
            }
        } else {
            file.delete();
        }
    }

    public static void copyFile(File source, File dest) {
        if (dest.getParentFile() != null && !dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        InputStream is = null;
        OutputStream os = null;
        try {
            try {
                is = new FileInputStream(source);
                os = new FileOutputStream(dest);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            } catch (Exception e) {
                throw e;
            } finally {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream getStream(Class clase, String dir) throws Exception {
        InputStream is = clase.getClassLoader().getResourceAsStream(dir);
        return is;
    }
}
