package mr;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.imgrec.ImageRecognitionPlugin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

/**
 * Spring Controller for the money image recognition. Send an image file as multi part with the name="file"
 * and this controller will return application/json with the percentages for each coin type.
 *
 * NOTE: The neuroph-imgrec dependency is not available in maven. I added this as a local repository. Might not work
 * on your machine, if you don't add it to a local repos on your side.
 */

@Controller
@RequestMapping("/new")
public class MoneyImRecController {

	@RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Map<String, Double> recognize(@RequestParam("file") MultipartFile file) throws IOException {
        BufferedImage image = ImageIO.read(file.getInputStream());

        // load trained neural network saved with Neuroph Studio (specify some existing neural network file here)
        NeuralNetwork<?> nnet = NeuralNetwork.createFromFile(MoneyImRecController.class.getResource("/ann/26.nnet").getPath()); // load trained neural network saved with Neuroph Studio
        // get the image recognition plugin from neural network
        ImageRecognitionPlugin imageRecognition = (ImageRecognitionPlugin) nnet.getPlugin(ImageRecognitionPlugin.class); // get the image recognition plugin from neural network

        return imageRecognition.recognizeImage(image);
    }
}