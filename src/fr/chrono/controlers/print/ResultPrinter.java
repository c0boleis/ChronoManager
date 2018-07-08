package fr.chrono.controlers.print;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;

import fr.chrono.controlers.CategoryControler;
import fr.chrono.controlers.CompetiteurControler;
import fr.chrono.model.interfaces.ICategory;
import fr.chrono.model.interfaces.ICompetiteur;

public class ResultPrinter{

	private static boolean startOrderResult = false;

	public static void main(String[] args) {
		if(args.length>0) {
			try {
				CompetiteurControler.load(new File(args[0]));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			exportResultToPdf(new File("resources/filePdf.pdf"));
		} catch (FOPException | TransformerException | IOException e) {
			e.printStackTrace();
		}		
		System.exit(0);
	}

	private static String printResult() throws FOPException, TransformerException, IOException {
		String result  ="";
		result+=getHead()+"\n";
		result+=getBody();
		return result;
	}

	private static File saveStringToFile(String text,String fileOutPath) throws IOException {
		File fileOut = new File(fileOutPath);
		BufferedWriter buf = new BufferedWriter(new FileWriter(fileOut));
		buf.write(text);
		buf.close();
		return fileOut;
	}

	public static void exportResultToPdf(File fileOut) throws FOPException, TransformerException, IOException {
		startOrderResult = false;
		exportPdfString(printResult(),fileOut);
	}

	public static void exportStartOrderToPdf(File fileOut) throws FOPException, TransformerException, IOException {
		startOrderResult = true;
		exportPdfString(printResult(),fileOut);
	}

	private static void exportPdfString(String xmlContent,File fileOut) throws FOPException, TransformerException, IOException {
		File fileXml = saveStringToFile(xmlContent, "resources/fileXml.xml");
		//				File xsltFile = new File("resources/template.xsl");
		File xsltFile = new File("resources/test_1.xsl");
		// the XML file which provides the input
		//				StreamSource xmlSource = new StreamSource(new File("resources/list.xml"));
		//		StreamSource xmlSource = new StreamSource(new File("resources/test_file.xml"));
		StreamSource xmlSource = new StreamSource(fileXml);
		// create an instance of fop factory
		FopFactory fopFactory = FopFactory.newInstance(new File(".").toURI());
		// a user agent is needed for transformation
		FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
		// Setup output
		OutputStream out;
		out = new java.io.FileOutputStream(fileOut.getAbsolutePath());

		try {
			// Construct fop with desired output format
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

			// Setup XSLT
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

			// Resulting SAX events (the generated FO) must be piped through to FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			// Start XSLT transformation and FOP processing
			// That's where the XML is first transformed to XSL-FO and then 
			// PDF is created
			transformer.transform(xmlSource, res);
			fileXml.deleteOnExit();
		} finally {
			out.close();
		}
	}

	private static String getHead() {
		String head = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

		return head;
	}

	private static String getBody() {
		String body = "";
		body+="<categories>\r\n";
		//		body+="<body>\r\n"+
		//				"<table style=\"width:100%\">\r\n";
		ICategory[] categories = CategoryControler.generateCategories();
		for(ICategory category : categories) {
			//			body += "		<tr>\r\n" + 
			//					"			<th colspan=\"3\" style=\"text-align: left\">"+category.getName()+"</th>\r\n" + 
			//					"		</tr>\r\n";
			body+="<category>\r\n";
			body+="<name>"+category.getName()+"</name>";
			//			body+="<competiteurs>\r\n";
			if(startOrderResult) {
				category.sortByStartOrder();
			}else {
				category.sortByRunTime();
			}
			ICompetiteur[] competiteurs = category.getCompetiteurs();
			int rang = 1;
			for(ICompetiteur competiteur : competiteurs) {
				body+= getCompetiteurResult(competiteur,rang)+"\r\n";
				rang++;
			}
			//			body+="</competiteurs>\r\n";
			body+="</category>\r\n";
		}
		body+="</categories>";
		//		body+="</table>\r\n"+
		//				"</body>";
		return body;
	}

	private static String getCompetiteurResult(ICompetiteur competiteur,int rang) {
		String competiteurText = "";
		if(startOrderResult) {
			competiteurText+="<competiteur>\r\n" + 
					"			<rang>"+competiteur.getStartOrder()+"</rang>\r\n" + 
					"			<start_order_name>"+competiteur.getName()+"</start_order_name>\r\n" + 
					"			<result>"+competiteur.getStartTimeString()+"</result>\r\n" +
					"		</competiteur>";
		}else {
			competiteurText+="<competiteur>\r\n" + 
					"			<rang>"+rang+"</rang>\r\n" + 
					"			<start_order_name>"+competiteur.getStartOrder()+"\t"+competiteur.getName()+"</start_order_name>\r\n" + 
					"			<result>"+competiteur.getRunTimeString()+"</result>\r\n" +
					"		</competiteur>";
		}

		return competiteurText;
	}
}
