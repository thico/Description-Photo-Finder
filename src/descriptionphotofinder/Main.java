/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package descriptionphotofinder;

import com.hp.hpl.jena.rdf.model.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
//import java.util.logging.Level;
//import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author thiago
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        Model model = null;
        //Model modeldb = null;

        String path = "http://images.orkut.com/orkut/photos/OgAAAPFkX2-islDCSsXQEdQ-fsFvTX0VFjrAtmLus-lSaf-5YQi4a3xohTGagCF8ZJhw1nX4XUvX5pdF_VJv2_z5u5sAm1T1UBdVgRQUfv641rTuKBn4CHSM5nQu.jpg";
        URL url = new URL(path);
        BufferedImage image = ImageIO.read(url);

        //final String[] result = {"http://images.orkut.com/orkut/photos/OgAAAPFkX2-islDCSsXQEdQ-fsFvTX0VFjrAtmLus-lSaf-5YQi4a3xohTGagCF8ZJhw1nX4XUvX5pdF_VJv2_z5u5sAm1T1UBdVgRQUfv641rTuKBn4CHSM5nQu.jpg", "http://www.lia.ufc.br/~thiagoalves/euniver.jpg"};

        ImageGui imgui = new ImageGui();

        imgui.runGui(image);

        System.out.println("Load my FOAF Friends");
        GetModels getmodel = new GetModels();
	model = getmodel.buildModel(model);

        //System.out.println("\nSay Hello to My Photo");
	//getmodel.myImgQuery(model);

        //System.out.println("\nSay Hello to my friends");
	//getmodel.myFriends(model);

        //System.out.println("\nSay Hello to all Photos");
	//getmodel.allImgQuery(model);

        //System.out.println("\nSay Hello to all My Photo");
	//getmodel.mySelfImgQuery(model);

        //System.out.println("\nQuerying Dbpedia");
        //getmodel.dbQuery();

        //getmodel.runPellet(model);

        //getmodel.myResourcesQuery(model);

        //getmodel.allDantImgQuery(model);


    }


}