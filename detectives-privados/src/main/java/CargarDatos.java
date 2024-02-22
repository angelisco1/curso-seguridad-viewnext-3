import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import com.curso.models.Informe;
import com.curso.models.User;
import com.curso.utils.DatabaseUtil;
import com.curso.utils.PasswordUtil;

public class CargarDatos {

	public static void main(String[] args) {
		
		Map<String, String> settings = new HashMap<>();
		settings.put(Environment.DRIVER, "org.h2.Driver");
		settings.put(Environment.URL, DatabaseUtil.URL);
		settings.put(Environment.USER, DatabaseUtil.USER);
		settings.put(Environment.PASS, DatabaseUtil.PASSWORD);
		settings.put(Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
		settings.put(Environment.HBM2DDL_AUTO, "update");
		settings.put(Environment.SHOW_SQL, "true");
		settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
		
		User user1 = new User(null, "Chuck Norris", "a", PasswordUtil.hashPassword("a"), "https://api.chucknorris.io/", "ADMIN");
		User user2 = new User(null, "Silvester Stallone", "b", PasswordUtil.hashPassword("b"), "https://es.wikipedia.org/wiki/Silvester_Stallone", "USER");
		User user3 = new User(null, "Jason Statham", "c", PasswordUtil.hashPassword("c"), "https://es.wikipedia.org/wiki/Jason_Statham", "USER");
		
		Informe informe = new Informe(null, "El canario está en la jaula", "Una descripción corta del informe.", "Había una vez, un canario en la jaula. Piaba y piaba, y su dueño solo le daba agua.", "lightgray", 3);
		
		StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().applySettings(settings).build();
		
		MetadataSources sources = new MetadataSources(standardRegistry);
		sources.addAnnotatedClass(User.class);
		sources.addAnnotatedClass(Informe.class);
		
		SessionFactory sf = sources.getMetadataBuilder().build().buildSessionFactory();
		Session s = sf.getCurrentSession();
		
		s.beginTransaction();
		s.persist(user1);
		s.persist(user2);
		s.persist(user3);
		s.persist(informe);
		s.getTransaction().commit();
		s.close();
		
		sf.close();

	}
	
}
