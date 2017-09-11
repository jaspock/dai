package es.ua.dlsi.rest;

import java.io.IOException;
import java.util.Iterator;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.ContentType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

	public static void consultaTodas(String base, JSONParser parser)
			throws ClientProtocolException, IOException, ParseException {
		JSONObject j;
		JSONArray a;
		String s;

		// Consulta de todas las asignaturas:
		s = Request.Get(base + "asignaturas").execute().returnContent().asString();

		a = (JSONArray) parser.parse(s);
		Iterator<?> i = a.iterator();
		System.out.print("---- Consulta de todas las asignaturas: ");
		while (i.hasNext()) {
			j = (JSONObject) i.next();
			System.out.print(" " + j.get("código"));
		}
		System.out.println(": " + s);
	}

	public static void main(String[] args) {
		JSONObject j;
		String s;

		String base = "http://localhost:3000/";
		JSONParser parser = new JSONParser();

		try {

			consultaTodas(base, parser);

			// Consulta por id:
			s = Request.Get(base + "asignaturas/2").execute().returnContent().asString();
			j = (JSONObject) parser.parse(s);
			System.out.println("---- Consulta por id: " + j.get("descripción"));

			// Búsqueda compuesta:
			s = Request.Get(base + "asignaturas?créditos=6&código=34066").execute().returnContent().asString();
			System.out.println("---- Búsqueda compuesta: " + s);

			// Nueva asignatura:
			j = new JSONObject();
			j.put("créditos", new Integer(6));
			j.put("código", "34068");
			j.put("nombre", "Interconexión de Redes");
			j.put("descripción",
					"La asignatura aborda aspectos avanzados de las redes de comunicaciones IP, como ampliación de los conceptos introducidos en la asignatura obligatoria Redes de Computadores.");
			Request.Post(base + "asignaturas").addHeader("X-Custom-header", "stuff")
					.bodyString(j.toJSONString(), ContentType.APPLICATION_JSON).execute().returnContent().asString();
			System.out.println("---- Nueva asignatura: " + s);

			consultaTodas(base, parser);

			// Modificación de asignatura:
			j = new JSONObject();
			j.put("créditos", new Integer(6));
			j.put("id", new Integer(2));
			j.put("código", "34063");
			j.put("nombre", "Desarrollo de Aplicaciones en Internet");
			j.put("descripción",
					"Las posibilidades de interacción entre usuarios que brinda la web han permitido, además, la aparición de numerosas aplicaciones que explotan esta componente social de formas inimaginables hace unos años.");
			Request.Put(base + "asignaturas/2").bodyString(j.toJSONString(), ContentType.APPLICATION_JSON).execute()
					.returnContent().asString();
			System.out.println("---- Modificación de asignatura: " + s);

			consultaTodas(base, parser);

			// Borrado de asignatura:
			s = Request.Delete(base + "asignaturas/1").execute().returnContent().asString();
			System.out.println("---- Borrado de asignatura: " + s);

			consultaTodas(base, parser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
