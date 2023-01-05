 fetch("/file-available")
  .then(response => response.json()) // Parse the response as JSON
  .then(data => {
    // Check the value of the "available" property in the response data
    if (data.available) {
      // If the file is available, show the download button
      document.getElementById("download-button").style.display = "block";
    } else {
      // If the file is not available, hide the download button
      document.getElementById("download-button").style.display = "none";
    }
  })
   .catch(error => {
    console.log(error);
});
  // Define a function to check the file availability
  function checkFileAvailability() {
    // Make an HTTP GET request to the /file-available route on your server
    fetch("/file-available")
      .then(response => response.json()) // Parse the response as JSON
      .then(data => {
        // Check the value of the "available" property in the response data
        if (data.available) {
          // If the file is available, show the download button
          document.getElementById("download-button").style.display = "block";
        } else {
          // If the file is not available, hide the download button
          document.getElementById("download-button").style.display = "none";
        }
      })};