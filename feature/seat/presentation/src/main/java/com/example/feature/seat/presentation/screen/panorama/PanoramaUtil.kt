package com.example.feature.seat.presentation.screen.panorama

object PanoramaUtil {
    const val IMAGE_URL =
        "https://media.istockphoto.com/id/1435087141/photo/movie-theater-grey-seats-in-a-modern-cinema.jpg?s=612x612&w=0&k=20&c=4pS9Gcqnxg8a2OSbOen1T61VNm2aj-sfdG8ogho49dQ="

    const val HTML = """
            <!DOCTYPE html>
            <html>
            <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <style>
                    body, html { margin: 0; padding: 0; height: 100%; overflow: hidden; }
                    #panorama { width: 100%; height: 100%; }
                    .error-message { color: red; padding: 20px; text-align: center; }
                </style>
                <script src="https://cdn.jsdelivr.net/npm/pannellum@2.5.6/build/pannellum.min.js"></script>
                <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/pannellum@2.5.6/build/pannellum.css">
            </head>
            <body>
                <div id="panorama"></div>
                <script>
                    try {
                        pannellum.viewer('panorama', {
                            type: "equirectangular",
                            panorama: "$IMAGE_URL",
                            autoLoad: true,
                            showControls: true,
                            showFullscreenCtrl: true,
                            showZoomCtrl: true,
                            hfov: 100,
                            autoRotate: -2,
                            compass: true,
                            onerror: function(message) {
                                console.error("Pannellum error:", message);
                                document.getElementById('panorama').innerHTML = '<div class="error-message">Error loading panorama: ' + message + '</div>';
                            }
                        });
                    } catch(e) {
                        console.error("Error initializing panorama:", e);
                        document.getElementById('panorama').innerHTML = '<div class="error-message">Error initializing panorama viewer</div>';
                    }
                </script>
            </body>
            </html>
        """
}