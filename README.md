# Digital Image Processing

This project was developed as part of a Computer Vision course to study the implementation and behavior of fundamental digital image processing algorithms. The application provides a graphical user interface to visualize and apply various image processing techniques.

## Implemented Algorithms

### Geometric Operations
* **ZoomIn**: Increases the size of an image, generating new pixels through interpolation.
* **ZoomOut**: Reduces the size of an image by sampling or averaging pixels.

### Grayscale Conversions
* **Weighted Grayscale**: Converts an RGB image to grayscale using weighted channel contributions (typically 0.299R + 0.587G + 0.114B).
* **Simple Grayscale**: Converts an RGB image to grayscale using a simple average of RGB channels.

### Brightness and Contrast Operations
* **Percentage Increment**: Increases pixel values by a percentage of their current value.
* **Absolute Increment**: Adds a fixed value to all pixels.

### Color Operations
* **Color Channel Isolation**: Extracts and displays a single color channel (R, G, or B).

### Image Combination Operations
* **Addition**: Combines two images by adding their pixel values.
* **Simple Addition**: Combines images without normalization.
* **Weighted Addition**: Combines images with different weights.
* **Subtraction**: Subtracts pixel values of one image from another.

### Background Operations
* **Adaptive Background**: Implements adaptive background subtraction for motion detection.
* **Alpha Background**: Implements alpha blending between images.

### Image Enhancement
* **Negative**: Inverts the colors of an image.
* **Thresholding**: Converts an image to binary based on a threshold value.

### Convolution and Filtering
* **Convolution**: Applies a custom kernel to an image.
* **Sobel**: Edge detection using Sobel operators.
* **Robinson**: Edge detection using Robinson compass masks.
* **Roberts**: Edge detection using Roberts cross operators.

### Morphological Operations
* **Erosion**: Erodes shapes in binary images.
* **Dilation**: Expands shapes in binary images.
* **Opening**: Erosion followed by dilation, useful for removing noise.
* **Closing**: Dilation followed by erosion, useful for filling small holes.

### Analysis
* **Histogram**: Displays the distribution of pixel intensities in an image.

## Technical Details

This application was built using Java with Swing for the graphical user interface. The implementation focuses on understanding the algorithms rather than optimizing for performance.

## Running the Project

The project can be run directly using Java, bypassing Maven complications. Here's how to run it:

1. Ensure you have Java Development Kit (JDK) installed
2. Clone or download the repository
3. Extract the `bibliotecas.rar` file to a folder named `lib` in the project root directory
4. Compile the project from the command line:
   ```
   javac -d target/classes -cp "lib/*" $(find src -name "*.java")
   ```
5. Run the application:
   ```
   java -cp "target/classes:lib/*" br.univali.pdi.MainJFrame
   ```
   
Note: This project was originally developed in September 2018 for educational purposes to understand the principles of digital image processing algorithms. While the core image processing algorithms remain fully functional when processing images from files, some system-dependent features like webcam capture may require additional configuration on modern systems.
