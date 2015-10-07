package usernametoname;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;

//import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class usernametoname {

	/**
	 * @param args
	 */
	static boolean debug = false;

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		String Name = "";
		console.writeLine("enter username");
		String user = console.readLine();
		console.writeLine("looking up name");
		Name = getPersonName(user);
		console.writeLine("user name: " + user + "\nmaps to: " + Name+"\npress enter");
		if (!Name.equals("Name not found")) {
			String imageurl = getPersonpicture(user);
			String ascii = "";
			int size = 400;
			String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!\"£$%^&*()_+";
			try {
				ascii = "http://www.degraeve.com/img2txt-yay.php?url="
						+ java.net.URLEncoder.encode(imageurl, "ISO-8859-1")
						+ ".jpg&mode=A&size=" + size + "&charstr=" + charset
						+ "&order=O&invert=N";
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			console.readLine();
			if (!ascii.equals("")) {
				console.writeLine(getItm(ascii, "pre"));
			}

			console.writeLine("redirect to his page hit enter");
			console.readLine();

			try {
				if (Desktop.isDesktopSupported()) {
					Desktop.getDesktop().browse(
							new URI("http://www.google.com/search?q="
									+ Name.replace(" ", "+") + "&btnI"));
				}
			} catch (Exception e) {
				if (debug)
					e.printStackTrace();
			}
		}

	}

	private static String getPersonName(String user) {

		String url = "http://www.ecs.soton.ac.uk/people/" + user;
		String itm = "title";
		String Name = getItm(url, itm).split(" \\|")[0];
		return (Name.equals("ECS People") | Name.equals("")) ? "Name not found"
				: Name;
	}

	private static String getPersonpicture(String user) {
		String url = "http://www.ecs.soton.ac.uk/people/" + user;
		String itm = "img";
		String attr = "src";
		String Name = getmeta(url, itm, attr);
		return (Name.equals("ECS People") | Name.equals("")) ? "image not found"
				: Name;
	}

	private static String getItm(String url, String itm) {
		try {
			Document doc = Jsoup.connect(url).followRedirects(true).get();
			Elements newsHeadlines = doc.getElementsByTag(itm);
			String Name = newsHeadlines.first().ownText();
			return Name;
		} catch (Exception e) {
			if (debug)
				e.printStackTrace();
			return "";
		}
	}

	private static String getmeta(String url, String itm, String attr) {
		try {
			Document doc = Jsoup.connect(url).followRedirects(true).get();
			Elements newsHeadlines = doc.getElementsByTag(itm);
			String Name = newsHeadlines.first().attr(attr);
			return Name;
		} catch (Exception e) {
			if (debug)
				e.printStackTrace();
			return "ECS People";
		}
	}
}

class console {
	static BufferedReader br = new BufferedReader(new InputStreamReader(
			System.in));

	public static boolean writeLine(Object item) {
		try {
			System.out.println(item.toString());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static String readLine() {
		try {
			return console.br.readLine();
		} catch (Exception e) {
			return null;
		}
	}
}
