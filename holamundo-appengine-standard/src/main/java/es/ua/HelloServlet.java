package es.ua;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


public class HelloServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    PrintWriter out = resp.getWriter();

    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

    Entity palabraEntity = new Entity("Palabra"); 
    palabraEntity.setProperty("cadena", "mundo");

    Key palabraKey = datastore.put(palabraEntity);  
    long k= palabraKey.getId();

    try {
      Entity palabra2Entity = datastore.get(KeyFactory.createKey("Palabra",k));
      out.println("Hola, "+palabra2Entity.getProperty("cadena"));
    } catch (EntityNotFoundException e) {
      out.println("Error");
    }

  }

}

