package eu.resulaj.pdf;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class PdfController {

    private static final Logger logger = LoggerFactory.getLogger(PdfController.class);

    // Create a directory to store the merged PDF files for each user's session
    static File f = new File("./pdfer");

    static {

        f.mkdirs();

    }

    @GetMapping("/")
    public String index(HttpSession session) {

        // Check if a file is available for the user to download
        File file = (File) session.getAttribute("file");
        if (file != null && file.exists()) {
            // Set a flag in the session to indicate that the file is available for download
            session.setAttribute("fileAvailable", true);
        } else {
            // Set the flag to false
            session.setAttribute("fileAvailable", false);
        }
        return "redirect:/index.html";
    }

    @PostMapping("/merge")
    public String mergePdf(@RequestParam("files") MultipartFile[] files, HttpSession session) {
        try {
            PDFMergerUtility mergePdf = new PDFMergerUtility();
            String fileName = session.getId().substring(session.getId().length() - 10) + ".pdf";
            File sessionDir = new File("./pdfer/" + session.getId());
            sessionDir.mkdirs();
            File file = new File(sessionDir  + fileName);
            mergePdf.setDestinationFileName(file.toString());
            for (MultipartFile f : files) {
                if (f.getOriginalFilename() != null && f.getOriginalFilename().endsWith(".pdf")) {
                    mergePdf.addSource(f.getInputStream());
                }else if (f.getOriginalFilename() == null) {
                    throw new IOException("Original filename not found, Please select at least two PDF files to merge.");
                }else{
                    throw new IOException("Invalid file type. Only PDFs are allowed.");
                }


            }
            if (files.length < 2) {
                throw new IOException("Please select at least two PDF files to merge.");
            }
            mergePdf.mergeDocuments(null);
            logger.info("Merging PDF files for session {}", session.getId());

            // Store the file in the user's session
            session.setAttribute("file", file);
            // Set the fileAvailable attribute to true
            session.setAttribute("fileAvailable", true);
            // Set the session timeout to 30 minutes
            session.setMaxInactiveInterval(1800);
        } catch (IOException e) {
            // Set the fileAvailable attribute to false
            session.setAttribute("fileAvailable", false);
            // Set the error message
            session.setAttribute("error", e.getMessage());
        }

        // Return the name of the view to redirect to
        return "redirect:/index.html";
    }

    @GetMapping("/file-available")
    public ResponseEntity<Map<String, Object>> fileAvailable(HttpSession session) {
        // Check if a file is available for the user to download
        File file = (File) session.getAttribute("file");
        Map<String, Object> response = new HashMap<>();
        // Set the "available" property in the response data to indicate whether the
        // file is available for download
        response.put("available", file != null && file.exists());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/download")
    public ResponseEntity<UrlResource> downloadFile(HttpSession session) throws MalformedURLException {
        // Get the file from the user's session
        File file = (File) session.getAttribute("file");

        File sessionDir = new File("./pdfer/" + session.getId());

        // Create a Resource object for the file
        UrlResource resource = new UrlResource(file.toURI());
        // Set the Content-Disposition header to tell the browser to download the file
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        // Create a ScheduledExecutorService
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        // Schedule a task to delete the file and directory after 30 seconds
        scheduler.schedule(() -> {
            try {
                Files.delete(file.toPath());
                Files.delete(sessionDir.toPath());
                session.invalidate();
            } catch (IOException e) {
                // handle exception
            }
        }, 30, TimeUnit.SECONDS);


        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);

    }

}
