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

	public static void consultaTodas(String base)
			throws ClientProtocolException, IOException, ParseException {
		JSONObject j;
		JSONArray a;
		String s;
    JSONParser parser = new JSONParser();

		// Consulta de todas las asignaturas:
    // curl -i -H "Accept: application/json" -X GET http://localhost:3000/asignaturas/
		s = Request.Get(base + "asignaturas").execute().returnContent().asString();
		a = (JSONArray) parser.parse(s);
		Iterator<?> i = a.iterator();
		System.out.print("---- Consulta de todas las asignaturas (códigos y nombres):");
		while (i.hasNext()) {
			j = (JSONObject) i.next();
			System.out.print(" " + j.get("código") + " (" + j.get("nombre") + ")");
		}
		System.out.println("\n---- Consulta de todas las asignaturas (JSON retornado):\n"+s);
	}


	public static void main(String[] args) {
		String base = "http://localhost:3000/";
		JSONObject j;
		String s;
		JSONParser parser = new JSONParser();

		try {

			consultaTodas(base);

			// Consulta por id:
			// curl -i -H "Accept: application/json" -X GET http://localhost:3000/asignaturas/2
			s = Request.Get(base + "asignaturas/2").addHeader("Accept", "application/json")
			    .execute().returnContent().asString();
			System.out.println("---- Consulta por id (JSON retornado):\n" + s);

			// Búsqueda compuesta:
			s = Request.Get(base + "asignaturas?créditos=6&código=34066").execute().returnContent().asString();
			System.out.println("---- Búsqueda compuesta (JSON retornado):\n" + s);

			// Nueva asignatura:
			// curl -i -H "Content-Type: application/json" --data '{"créditos":6,"código":"34068","nombre":"Interconexión de Redes","descripción:":"La asignatura aborda..."}' -X POST http://localhost:3000/asignaturas
			j = new JSONObject();
			j.put("créditos", new Integer(6));
			j.put("código", "34068");
			j.put("nombre", "Interconexión de Redes");
			j.put("descripción",
					"La asignatura aborda aspectos avanzados de las redes de comunicaciones IP, como ampliación "
					+ "de los conceptos introducidos en la asignatura obligatoria Redes de Computadores.");
			Request.Post(base + "asignaturas")
					.bodyString(j.toJSONString(), ContentType.APPLICATION_JSON).execute().returnContent().asString();
			System.out.println("---- Nueva asignatura añadida");
			consultaTodas(base);

			// Modificación de asignatura:
			j = new JSONObject();
			j.put("créditos", new Integer(6));
			j.put("id", new Integer(6));
			j.put("código", "34068");
			j.put("nombre", "Interconexión de Redes");
			j.put("descripción",
					"La asignatura aborda aspectos avanzados de las redes de comunicaciones IP.");
			Request.Put(base + "asignaturas/4")
			    .bodyString(j.toJSONString(), ContentType.APPLICATION_JSON).execute().returnContent().asString();
			System.out.println("---- Modificación de asignatura realizada");
			consultaTodas(base);

			// Borrado de asignatura:
			// curl -i -X DELETE http://localhost:3000/asignaturas/4
			s = Request.Delete(base + "asignaturas/4").execute().returnContent().asString();
			System.out.println("---- Borrado de asignatura realizado");
			consultaTodas(base);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
