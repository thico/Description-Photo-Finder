/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package descriptionphotofinder;

import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.query.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.reasoner.Reasoner;
import com.hp.hpl.jena.reasoner.ReasonerRegistry;
import com.hp.hpl.jena.util.FileManager;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 *
 * @author thiago
 */
public class GetModels {    
    static String defaultNameSpace = "http://www.lia.ufc.br/~thiagoalves/#";
    //static String dbNameSpace = "http://dbpedia.org/resource/#";

    List< String > links = new ArrayList< String >();

    List< Icon > images = new ArrayList< Icon >();

    String str;
    Integer cont;

    InfModel inferredModel = null;
    Model schema = ModelFactory.createOntologyModel();
    //public GetModels() throws IOException {
        //String str2 = "http://www.lia.ufc.br/~thiagoalves/euniver.jpg";
        //URL url2 = new URL(str2);
       // BufferedImage image2 = ImageIO.read(url2);
        //this.images.add(new ImageIcon(image2));

   // }
    

    

	//Model model = null;
	//Model schema = null;
	//InfModel inferredFriends = null;


        public Model buildModel(Model model) throws IOException{
		model = ModelFactory.createOntologyModel();
                //schema = ModelFactory.createOntologyModel();
		InputStream imagerdf = FileManager.get().open("PhotosRDF/ImageFOAF.rdf");
		model.read(imagerdf,defaultNameSpace);
                //schema.read("http://dbpedia.org/ontology/");
                //schema.read("http://www.w3.org/2002/07/owl");
                //schema.read("http://xmlns.com/foaf/spec/index.rdf");
                //model.read("http://dbpedia.org/ontology/");
                //model.read("http://www.w3.org/2002/07/owl");
                //model.read("http://xmlns.com/foaf/spec/index.rdf");
		imagerdf.close();
                return model;

	}



        public void myImgQuery(Model model) throws IOException{
		//Hello to Me - focused search
		runImgQuery(" select DISTINCT ?thumbnail where{ image:img1 foaf:thumbnail ?thumbnail  }", model);  //add the query string

	}

        public void myFriends(Model model) throws IOException{
		//Hello to just my friends - navigation
		runQuery(" select DISTINCT ?name where{  image:thiago foaf:knows ?friend. ?friend foaf:name ?name } ", model);  //add the query string

	}

        public void myResourcesQuery(Model model) throws IOException{
		//Hello to just my friends - navigation
		runResourcesQuery(" select distinct ?name where{  ?img foaf:depicts ?name } ", model);  //add the query string

	}

        private void runResourcesQuery(String queryRequest, Model model) throws IOException{

                Model modelaux = null;

                GetModels getmodel = new GetModels();
                modelaux = getmodel.buildModel(modelaux);

		StringBuilder queryStr = new StringBuilder();
		// Establish Prefixes
		//Set default Name space first
		queryStr.append("PREFIX image" + ": <" + defaultNameSpace + "> ");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX foaf" + ": <" + "http://xmlns.com/foaf/0.1/" + "> ");
                queryStr.append("PREFIX owl" + ": <" + "http://www.w3.org/2002/07/owl#" + "> ");
                queryStr.append("PREFIX dbpedia-owl" + ": <" + "http://dbpedia.org/ontology/" + "> ");
                queryStr.append("PREFIX dbpedia" + ": <" + "http://dbpedia.org/resource/" + "> ");

		//Now add query
		queryStr.append(queryRequest);
		Query query = QueryFactory.create(queryStr.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
		ResultSet response = qexec.execSelect();

		while( response.hasNext()){
                    
			QuerySolution soln = response.nextSolution();
			RDFNode name = soln.get("?name");
                        
			if( name != null ){
				System.out.println( "Hello to " + name.toString() );
                                modelaux.read(name.toString());
                                
			}
			else
				System.out.println("No Friends found!");
			}
		} finally { qexec.close();}
	model.add(modelaux);
        }

        public void allImgQuery(Model model) throws IOException{
		//Hello to just my friends - navigation

		runImgQuery(" select DISTINCT ?thumbnail where{  ?img foaf:thumbnail ?thumbnail } ", model);  //add the query string

	}

        public void allDantImgQuery(Model model) throws IOException{
		//Hello to just my friends - navigation

		runImgQuery(" select DISTINCT ?thumbnail where{  ?img foaf:depicts ?depic. ?depic foaf:name \"Daniel\". ?img foaf:thumbnail ?thumbnail } ", model);  //add the query string

	}

        public void mySelfImgQuery(Model model) throws IOException{
		//Hello to just my friends - navigation
                //StringBuilder query = new StringBuilder();
		runImgQuery(" select DISTINCT ?thumbnail where{ ?img foaf:depicts image:thiago. ?img foaf:thumbnail ?thumbnail } ", model);  //add the query string

	}

        public void dbQuery() throws IOException{
		//Hello to Me - focused search
		runDbQuery(" select DISTINCT ?person where{ ?person <http://dbpedia.org/ontology/birthPlace>  <http://dbpedia.org/resource/Porto_Alegre>  } LIMIT 10");  //add the query string

	}

        public void inputQueryModel(String query, Model model) throws IOException{
		
		runInputQueryModel(query, model);  //add the query string

	}

        public void inputQuery(String query, Model model) throws IOException{

		runInputQuery(query, model);  //add the query string

	}

        public void runInputQuery(String queryRequest, Model givenModel) throws IOException{

		StringBuilder queryStr = new StringBuilder();
		// Establish Prefixes
		//Set default Name space first
		queryStr.append("PREFIX image" + ": <" + defaultNameSpace + "> ");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX foaf" + ": <" + "http://xmlns.com/foaf/0.1/" + "> ");
                queryStr.append("PREFIX owl" + ": <" + "http://www.w3.org/2002/07/owl#" + "> ");
                queryStr.append("PREFIX dbpedia-owl" + ": <" + "http://dbpedia.org/ontology/" + "> ");
                queryStr.append("PREFIX dbpedia" + ": <" + "http://dbpedia.org/resource/" + "> ");
                queryStr.append("PREFIX dbpprop" + ": <" + "http://dbpedia.org/property/" + "> ");


                //String queryReplaced;
                //queryReplaced = queryRequest.replace("select distinct ?depic where{", "select distinct ?thumbnail where{?img foaf:depicts ?depic. ?img foaf:thumbnail ?thumbnail.");

                //System.out.println( queryReplaced );

                queryStr.append(queryRequest);
                Query query = QueryFactory.create(queryStr.toString());
                QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
                try {

		ResultSet response = qexec.execSelect();

                //ResultSetFormatter.out(System.out, response, query);

		while( response.hasNext()){
			QuerySolution soln = response.nextSolution();
			RDFNode depic = soln.get("?depic");
			if( depic != null ){
				System.out.println( "Hello to " + depic.toString());
                                givenModel.read(depic.toString());
                                //givenModel.read("http://dbpedia.org/resource/Alan_Turing");
                                //givenModel.read("http://dbpedia.org/ontology/Scientist");
                                
			}
			else
				System.out.println("No Depics found!");
			}
		} finally { qexec.close();}
		}

        public void runInputQueryModel(String queryRequest, Model model) throws IOException{

		StringBuilder queryStr = new StringBuilder();
		// Establish Prefixes
		//Set default Name space first
		queryStr.append("PREFIX image" + ": <" + defaultNameSpace + "> ");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX foaf" + ": <" + "http://xmlns.com/foaf/0.1/" + "> ");
                queryStr.append("PREFIX owl" + ": <" + "http://www.w3.org/2002/07/owl#" + "> ");
                queryStr.append("PREFIX dbpedia-owl" + ": <" + "http://dbpedia.org/ontology/" + "> ");
                queryStr.append("PREFIX dbpedia" + ": <" + "http://dbpedia.org/resource/" + "> ");
                queryStr.append("PREFIX dbpprop" + ": <" + "http://dbpedia.org/property/" + "> ");


                String queryReplaced;
                queryReplaced = queryRequest.replace("select distinct ?depic where{", "select distinct ?thumbnail where{?img foaf:depicts ?depic. ?img foaf:thumbnail ?thumbnail.");

                System.out.println( queryReplaced );
		//Now add query
		queryStr.append(queryReplaced);
		Query query = QueryFactory.create(queryStr.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, model);

                //QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);


		try {
		ResultSet response = qexec.execSelect();

                //ResultSetFormatter.out(System.out, response, query);

		while( response.hasNext()){
			QuerySolution soln = response.nextSolution();
			RDFNode thumb = soln.get("?thumbnail");
			if( thumb != null ){
                                
                                String thumbs = thumb.toString();
                                links.add(thumbs);
                                URL url = new URL(thumbs);
                                BufferedImage image = ImageIO.read(url);
                                images.add(new ImageIcon(image));
				System.out.println( "Hello to " + thumb.toString() );
                                

			}
			else
				System.out.println("No Images found!");
			}
		} finally { qexec.close();}
		}

        private void runDbQuery(String queryRequest) {

                StringBuilder queryStr = new StringBuilder();
		// Establish Prefixes
		//Set default Name space first
		queryStr.append("PREFIX image" + ": <" + defaultNameSpace + "> ");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX foaf" + ": <" + "http://xmlns.com/foaf/0.1/" + "> ");
                queryStr.append(queryRequest);
                Query query = QueryFactory.create(queryStr.toString());
                QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
                try {

		ResultSet response = qexec.execSelect();

                //ResultSetFormatter.out(System.out, response, query);

		while( response.hasNext()){
			QuerySolution soln = response.nextSolution();
			RDFNode name = soln.get("?person");
			if( name != null ){
				System.out.println( "Hello to " + name.toString() );
			}
			else
				System.out.println("No Persons found!");
			}
		} finally { qexec.close();}
	}

        private void runQuery(String queryRequest, Model model){

		StringBuilder queryStr = new StringBuilder();
		// Establish Prefixes
		//Set default Name space first
		queryStr.append("PREFIX image" + ": <" + defaultNameSpace + "> ");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX foaf" + ": <" + "http://xmlns.com/foaf/0.1/" + "> ");

		//Now add query
		queryStr.append(queryRequest);
		Query query = QueryFactory.create(queryStr.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
		ResultSet response = qexec.execSelect();

                ResultSetFormatter.out(System.out, response, query);

		while( response.hasNext()){
			QuerySolution soln = response.nextSolution();
			RDFNode name = soln.get("?name");
			if( name != null ){
				System.out.println( "Hello to " + name.toString() );
			}
			else
				System.out.println("No Friends found!");
			}
		} finally { qexec.close();}
		}

        public void runImgQuery(String queryRequest, Model model) throws IOException{

		StringBuilder queryStr = new StringBuilder();
		// Establish Prefixes
		//Set default Name space first
		queryStr.append("PREFIX image" + ": <" + defaultNameSpace + "> ");
		queryStr.append("PREFIX rdfs" + ": <" + "http://www.w3.org/2000/01/rdf-schema#" + "> ");
		queryStr.append("PREFIX rdf" + ": <" + "http://www.w3.org/1999/02/22-rdf-syntax-ns#" + "> ");
		queryStr.append("PREFIX foaf" + ": <" + "http://xmlns.com/foaf/0.1/" + "> ");

		//Now add query
		queryStr.append(queryRequest);
		Query query = QueryFactory.create(queryStr.toString());
		QueryExecution qexec = QueryExecutionFactory.create(query, model);
		try {
		ResultSet response = qexec.execSelect();

                ResultSetFormatter.out(System.out, response, query);

		while( response.hasNext()){
			QuerySolution soln = response.nextSolution();
			RDFNode thumb = soln.get("?thumbnail");
			if( thumb != null ){
				System.out.println( "Hello to " + thumb.toString() );

			}
			else
				System.out.println("No Images found!");
			}
		} finally { qexec.close();}
		}

        public InfModel goReasoner(Model model){
	    Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
	    reasoner = reasoner.bindSchema(schema);
	    return ModelFactory.createInfModel(reasoner, model);

	}

        public void runPellet(Model model){
            OntModel ont = ModelFactory.createOntologyModel();
            ont.read("http://dbpedia.org/ontology/");
            ont.add(model);
	    OntClass Person = ont.getOntClass( "http://xmlns.com/foaf/0.1/Person" );   
            Iterator instances = Person.listInstances();

	}


}