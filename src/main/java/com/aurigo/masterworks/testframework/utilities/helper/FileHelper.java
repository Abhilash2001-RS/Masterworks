package com.aurigo.masterworks.testframework.utilities.helper;

import com.aurigo.masterworks.testframework.BaseFramework;
import com.aurigo.masterworks.testframework.utilities.TestDataUtil;
import com.aurigo.masterworks.testframework.webUI.constants.Constants;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.FluentWait;
import org.testng.util.Strings;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileHelper extends BaseFramework {

    private static final Logger logger = LogManager.getLogger(FileHelper.class);
    private static final String folderPath = Paths.get(userDir, Constants.DEFAULT_DOWNLOAD_PATH).toString();
    private static final boolean isWindows = System.getProperty("os.name").contains("Windows");

    /**
     * Creates a folder in the passed in location.
     *
     * @param fileName        - Path of folder to create
     * @param deleteIfPresent - If true will delete the existing folder so it can be recreated
     * @return true if folder was successfully created
     */
    public static File createFile(String fileName, boolean deleteIfPresent) {
        try {
            File file = new File(Paths.get(folderPath, fileName).toString());
            if (file.exists()) {
                if (deleteIfPresent) {
                    file.delete();
                } else {
                    return null;
                }
            }

            return createFileWithRandomData(fileName, TestDataUtil.getRandomName(100));
        } catch (Exception e) {
            logger.error("Error:" + e.getMessage());
            return null;
        }
    }

    /**
     * Creates a folder in the passed in location.
     *
     * @param folderName      - Path of folder to create
     * @param deleteIfPresent - If true will delete the existing folder so it can be recreated
     * @return true if folder was successfully created
     */
    public static File createFolder(String folderName, boolean deleteIfPresent) {
        File folder = new File(Paths.get(folderName).toString());
        try {
            if (folder.exists()) {
                if (deleteIfPresent) {
                    folder.delete();
                } else {
                    return null;
                }
            }
            folder.mkdirs();
            return folder;
        } catch (Exception e) {
            logger.error("Error:" + e.getMessage());
            return null;
        }
    }

    /**
     * Method to delete a directory
     *
     * @param dir path of the directory to be deleted
     * @return true if the directory is successfully deleted
     */
    public static boolean deleteDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (File child : children) {
                boolean success = deleteDirectory(child);
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * Zip the directory that is being passed.
     *
     * @param dir -   Directory path.
     * @return -   Path of the zipped folder.
     */
    public static String zipDirectory(File dir) {
        try {
            File file = Arrays.stream(dir.listFiles()).filter(f -> f.getName().equals("AutomationReport.html")).findAny().orElse(null);
            File log = Arrays.stream(dir.getParentFile().listFiles()).filter(f -> f.getName().endsWith(".log")).
                    max(Comparator.comparingLong(f -> f.lastModified())).orElse(null);
            if (file == null)
                return null;

            // Create ZipOutputStream to write to the zip file.
            String zipPath = dir.getAbsolutePath() + ".zip";
            FileOutputStream fos = new FileOutputStream(zipPath);
            ZipOutputStream zos = new ZipOutputStream(fos);

            // For ZipEntry we need to keep only relative file path, so we used substring on absolute path
            logger.info("Zipping " + file.getAbsolutePath());
            ZipEntry zeFile = new ZipEntry(file.getAbsolutePath().substring(dir.getAbsolutePath().length() + 1));
            zos.putNextEntry(zeFile);

            // Read the file and write to ZipOutputStream
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            zos.closeEntry();
            fis.close();

            if (log != null) {
                logger.info("Zipping " + log.getAbsolutePath());
                ZipEntry zeLog = new ZipEntry(log.getAbsolutePath().substring(dir.getParentFile().getAbsolutePath().length() + 1));
                zos.putNextEntry(zeLog);
                fis = new FileInputStream(log.getAbsolutePath());
                buffer = new byte[1024];
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                zos.closeEntry();
                fis.close();
            }

            zos.close();
            fos.close();

            return zipPath;
        } catch (IOException e) {
            logger.error("Error:" + e.getMessage());
        }

        return null;
    }

    /**
     * Method too generate a File from an input stream
     *
     * @param inputStream Input stream to convert
     * @return a File
     */
    public static File getFileFromInputStream(InputStream inputStream) {
        File tempFile = null;
        FileOutputStream outputStream = null;
        try {
            tempFile = File.createTempFile("temp", "file");
            tempFile.deleteOnExit();
            outputStream = new FileOutputStream(tempFile);
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            logger.error("Error:" + e.getMessage());
        }
        return tempFile;
    }

    /**
     * Delete the file passed.
     * Looks in the downloaded path.
     *
     * @param fileName -   File which needs to be deleted form default download path.
     * @param node     -   Node.
     */
    public static void deleteFile(String fileName, Host node) {
        deleteFile(fileName, node, fileDownloadPath);
    }

    /**
     * Delete the file passed.
     * Looks in the downloaded path.
     *
     * @param fileName      -   File which needs to be deleted form default download path.
     * @param node          -   Node.
     * @param pathToTheFile -   Path to the file.
     */
    public static void deleteFile(String fileName, Host node, String pathToTheFile) {
        File file = getFile(fileName, node, pathToTheFile);

        if (file.exists()) {
            logger().info("Deleting File : " + file.getName());
            file.delete();
        }
    }

    /**
     * Verify the file content.
     *
     * @param fileName        -   File name in which the verification is needed.
     * @param expectedContent -   Expected content.
     * @param node            -   Node
     * @return -   True if content exists, else false.
     */
    public static boolean verifyFileContent(String fileName, String expectedContent, Host node) {
        return verifyFileContent(fileName, expectedContent, node, fileDownloadPath);
    }

    /**
     * Verify the file content.
     *
     * @param fileName        -   File name in which the verification is needed.
     * @param expectedContent -   Expected content.
     * @param node            -   Node
     * @param pathToTheFile   -   Path to the file.
     * @return -   True if content exists, else false.
     */
    public static boolean verifyFileContent(String fileName, String expectedContent, Host node, String pathToTheFile) {
        File file = getFile(fileName, node, pathToTheFile);

        if (file.exists()) {
            logger().info(String.format("File exists: %s", fileName));

            try {
                if (file.getName().endsWith(".txt")) {
                    InputStream in = new FileInputStream(file);
                    String actContents = IOUtils.toString(in, StandardCharsets.UTF_8);
                    in.close();
                    return actContents.contains(expectedContent);
                } else if (file.getName().endsWith(".xls")) {
                    Map<Integer, List<String>> data = ExcelUtil.readExcel(fileName, node, pathToTheFile);
                    return data.values().stream().filter(d -> d.contains(expectedContent)).count() > 0;
                }
            } catch (IOException e) {
                logger().info(String.format("Verify File Content error: %s", e.getMessage()));
                logger.error("Error:" + e.getMessage());
            }
        } else {
            logger().info(String.format("File doesn't exists: %s", fileName));
        }

        return false;
    }

    /**
     * Creates a file under target folder with random data.
     *
     * @param fileName   - Name of the file to be created.
     * @param randomData - Data to be appended to the created file.
     * @return Return file if created, else null.
     */
    public static File createFileWithRandomData(String fileName, String randomData) {
        try {
            File file = createFile(fileName);
            FileWriter fr = null;
            try {
                fr = new FileWriter(file);
                fr.write(randomData);
            } catch (IOException e) {
                logger.error("Error:" + e.getMessage());
            } finally {
                // close resources
                try {
                    fr.close();
                } catch (IOException e) {
                    logger.error("Error:" + e.getMessage());
                }
            }

            return file;
        } catch (Exception e) {
            logger.error("Error:" + e.getMessage());
            return null;
        }
    }

    /**
     * Waits for file to be available.
     *
     * @param fileName                 Name of the file
     * @param maxWaitInSeconds         maximum waiting limit in seconds
     * @param pollingIntervalInSeconds polling interval in seconds
     * @param node                     Node
     * @return true if file is available within the limit
     */
    public static boolean waitForFileToBeAvailable(String fileName, int maxWaitInSeconds, int pollingIntervalInSeconds, Host node) {
        return waitForFileToBeAvailable(fileName, maxWaitInSeconds, pollingIntervalInSeconds, node, fileDownloadPath);
    }

    /**
     * Waits for file to be available.
     *
     * @param fileName                 Name of the file.
     * @param maxWaitInSeconds         maximum waiting limit in seconds.
     * @param pollingIntervalInSeconds polling interval in seconds.
     * @param node                     Node.
     * @param pathToTheFile            Path to the file.
     * @return true if file is available within the limit
     */
    public static boolean waitForFileToBeAvailable(String fileName, int maxWaitInSeconds, int pollingIntervalInSeconds, Host node, String pathToTheFile) {
        boolean isFileAvailable;
        String completePath = getCompleteFilePath(node, pathToTheFile, fileName);

        FluentWait<String> fluentWait = new FluentWait<>(completePath);
        fluentWait.withTimeout(Duration.ofSeconds(maxWaitInSeconds)).pollingEvery(Duration.ofSeconds(pollingIntervalInSeconds));

        try {
            isFileAvailable = fluentWait.until(arg -> {
                File file = new File(arg);
                return file.exists();
            });
        } catch (TimeoutException e) {
            logger.error("Timeout exception occurred =>" + e.getMessage());
            return false;
        }

        return isFileAvailable;
    }

    /**
     * Get file based on the parallel execution flag
     *
     * @param fileName -   File name.
     * @param node     -   Node details.
     * @return -   File.
     */
    public static File getFile(String fileName, Host node) {
        return getFileGeneric(fileName, node, fileDownloadPath);
    }

    /**
     * Get file based on the parallel execution flag
     *
     * @param fileName -   File name.
     * @param node     -   Node details.
     * @param filePath -   File path.
     * @return -   File.
     */
    public static File getFile(String fileName, Host node, String filePath) {
        return getFileGeneric(fileName, node, filePath);
    }

    /**
     * Check if file exists.
     * Looks in the downloaded path.
     *
     * @param fileName -   File which needs to be checked form default download path.
     * @param node     -   Node.
     */
    public static boolean isFileExists(String fileName, Host node) {
        return isFileExists(fileName, node, fileDownloadPath);
    }

    /**
     * Check if file exists.
     * Looks in the downloaded path.
     *
     * @param fileName     -   File which needs to be checked form default download path.
     * @param node         -   Node.
     * @param downloadPath - DownloadPath
     */
    public static boolean isFileExists(String fileName, Host node, String downloadPath) {
        File file = getFile(fileName, node, downloadPath);
        return file.exists();
    }

    /**
     * Create File.
     *
     * @param fileName - Name of the file.
     * @return File object.
     */
    public static File createFile(String fileName) {
        File file = new File(Paths.get(folderPath, fileName).toString());
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            return file;
        } catch (Exception e) {
            logger.error("Error:" + e.getMessage());
            return null;
        }
    }

    /**
     * Get file based on the parallel execution flag
     *
     * @param fileName -   File name.
     * @param node     -   Node details.
     * @return -   File.
     */
    public static File getLibraryFile(String fileName, Host node) {
        return getFileGeneric(fileName, node, Paths.get(userDir, Constants.DEFAULT_LIBRARY_PATH).toString());
    }

    /**
     * Get Test data file
     *
     * @param fileName -   File name.
     * @param node     -   Node details.
     * @return -   File.
     */
    public static File getTestDataFile(String fileName, Host node) {
        return getFileGeneric(fileName, node, Paths.get(userDir, Constants.DEFAULT_TESTDATA_PATH).toString());
    }

    /**
     * Wrapper to access files
     *
     * @param fileName -  file name to be fetched
     * @param node     - Selenium grid NODE
     * @param filePath - File path to look into
     * @return the file
     */
    private static File getFileGeneric(String fileName, Host node, String filePath) {
        String completePath = getCompleteFilePath(node, filePath, fileName);

        return new File(completePath);
    }

    /**
     * Get complete file path.
     *
     * @param node     -   Node on which the current test is running.
     * @param filePath -   Path of the file to get.
     * @param fileName -   Name of the file.
     * @return -   Complete file path.
     */
    private static String getCompleteFilePath(Host node, String filePath, String fileName) {
        String completeFilePath = null;
        logger().info(String.format("filePath : %s", filePath));
        logger().info(String.format("fileName : %s", fileName));
        if (isParallelExecutionEnabled && node != null) {
            String ip = node.getIpAddress();
            if (isWindows) {
                logger().info(String.format("user.dir Path: %s", userDir));
                logger().info(String.format("actualPath : %s", filePath.substring(3)));
                completeFilePath = Strings.isNotNullAndNotEmpty(fileName) ? "\\\\" + Paths.get(ip, filePath.substring(3), fileName) : "\\\\" + Paths.get(ip, filePath.substring(3));

            } else {
                // Docker setup.
                String dockerVolumePath = Paths.get("/nodes", ip).toString();
                logger().info(String.format("dockerVolumePath : %s", dockerVolumePath));
                completeFilePath = Strings.isNotNullAndNotEmpty(fileName) ? Paths.get(dockerVolumePath, filePath.replace("package/", ""), fileName).toString() :
                        Paths.get(dockerVolumePath, filePath.replace("package/", "")).toString();
            }
        } else {
            completeFilePath = Strings.isNotNullAndNotEmpty(fileName) ? Paths.get(filePath, fileName).toString() : Paths.get(filePath).toString();
        }
        logger().info(String.format("getFileCompletePath: %s", completeFilePath));
        return completeFilePath;
    }

    /**
     * Get File name based on Starting name
     *
     * @param name Stating name
     * @param node The node in which we are working on for the IP
     * @return The file name
     */
    public static String getFileNameBasedOnStartingName(String name, Host node, String filePath) {
        String completePath;
        logger().info("File Download Path " + fileDownloadPath);
        logger().info("Incoming file Path " + filePath);
        completePath = getCompleteFilePath(node, filePath, null);
        logger().info(String.format("complete Path : %s", completePath));

        File folder;
        File[] listOfFiles = null;
        int i = 0;
        while(i<3&&listOfFiles==null){
            folder = new File(completePath);
            listOfFiles = folder.listFiles();
            i++;
        }

        String fileName = null;
        for (File listOfFile : listOfFiles) {
            if (listOfFile.getName().startsWith(name)) {
                fileName = listOfFile.getName();
            }
        }
        logger().info(String.format("File Name : %s", fileName));
        return fileName;
    }

    /**
     * Creates a File With Desired Size
     *
     * @param fileName    - Path of folder to create
     * @param sizeInBytes - Specify the size of the file to be created
     * @return created file will be returned
     */
    public static File createFileWithDesiredSize(String fileName, int sizeInBytes) {
        File file = new File(fileName);
        try {
            file.createNewFile();
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            raf.setLength(sizeInBytes * (1024 * 1024));
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * Delete File  based on Starting name
     *
     * @param name Stating name
     * @param node The node in which we are working on for the IP
     * @param filePath PAth of the file to check and delete
     */
    public static void deleteFileBasedOnStartingName(String name, Host node, String filePath) {
        String completePath;
        logger().info("File Download Path " + fileDownloadPath);
        logger().info("Incoming file Path " + filePath);
        completePath = getCompleteFilePath(node, filePath, null);
        logger().info(String.format("complete Path : %s", completePath));

        File folder = new File(completePath);
        File[] listOfFiles = folder.listFiles();
        String fileName = null;
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].getName().contains(name)) {
                fileName = listOfFiles[i].getName();
                deleteFile(fileName,node);
            }
        }
    }

}
