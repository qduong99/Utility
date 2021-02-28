package com.qduong.pdfFile;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * MergeMultiplePDF2One multiple PDF files to one using Apache pdfbox
 */
public class MergeMultiplePDF2One {

    public static void main(String[] args) throws IOException {
        String rootClassSlides = "C:\\workspace\\MIU\\EA\\slides\\Class Slides";
        String rootRefSlides = "C:\\workspace\\MIU\\EA\\slides\\Reference Slides";

        String eaFolder = "C:\\workspace\\MIU\\EA\\slides";
        String outFileClassSlide  = eaFolder + "/merged/mergedClassSlide.pdf";
        String outFileRefSlide  = eaFolder + "/merged/mergedRefSlide.pdf";
        merge(rootClassSlides, outFileClassSlide);
        merge(rootRefSlides, outFileRefSlide);
    }

    public static void merge(String rootFolder, String outFile) throws IOException {
        PDFMergerUtility ut = new PDFMergerUtility();
        try (Stream<Path> paths = Files.walk(Paths.get(rootFolder))) {
            paths.filter(Files::isRegularFile).forEach(p -> {
                try {
                    ut.addSource(p.toFile());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Setting the destination file
        ut.setDestinationFileName(outFile);
        MemoryUsageSetting memoryUsageSetting = MemoryUsageSetting.setupMainMemoryOnly();
        ut.mergeDocuments(memoryUsageSetting);
    }
}
