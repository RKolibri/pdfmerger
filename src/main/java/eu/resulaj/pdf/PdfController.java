package eu.resulaj.pdf;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

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

@Controller
public class PdfController {

	// Create a directory to store the merged PDF file
	static File f = new File("./pdfer");
	static {
		f.mkdirs();
	}

	@GetMapping("/")
	public String index(HttpSession session) {
		// Store an attribute in the session
		session.setAttribute("attributeName", "attributeValue");

		session.getAttribute("attributeName");

		// Remove an attribute from the session
		session.removeAttribute("attributeName");

		return "redirect:/index.html";
	}

	@PostMapping("/merge")
	public String mergePdf(@RequestParam("files") MultipartFile[] files) throws IOException {
		// Create a PDFMergerUtility object
		PDFMergerUtility mergePdf = new PDFMergerUtility();
		// Set the destination file
		mergePdf.setDestinationFileName(f + "/merged.pdf");
		for (MultipartFile file : files) {
			mergePdf.addSource(file.getInputStream());
		}
		try {
			mergePdf.mergeDocuments(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Return the name of the view to redirect to
		return "redirect:/download";
	}

	@GetMapping("/download")
	public ResponseEntity<UrlResource> downloadFile() throws MalformedURLException {
		// Get the file you want to download
		File file = new File(f + "/merged.pdf");
		// Create a Resource object for the file
		UrlResource resource = new UrlResource(file.toURI());
		// Set the Content-Disposition header to tell the browser to download the file
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=merged.pdf");
		// Schedule a task to delete the file after 10 seconds
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				file.delete();
			}
		}, 10000);
		// Return the file in a ResponseEntity
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);

	}

	public static String getCurrentTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
		Date date = new Date();
		return sdf.format(date);
	}
}
