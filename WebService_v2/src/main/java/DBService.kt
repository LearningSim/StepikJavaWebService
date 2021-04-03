import entity.UserEntity
import org.hibernate.Session
import org.hibernate.SessionFactory
import org.hibernate.cfg.Configuration

class DBService : IDBService {
    private val sessionFactory: SessionFactory
    private val h2Config: Configuration
        get() {
            val config = Configuration()
            config.addAnnotatedClass(UserEntity::class.java)
            config.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
            config.setProperty("hibernate.connection.driver_class", "org.h2.Driver")
            config.setProperty("hibernate.connection.url", "jdbc:h2:./h2db")
            config.setProperty("hibernate.connection.username", "test")
            config.setProperty("hibernate.connection.password", "test")
            config.setProperty("hibernate.show_sql", "true")
            config.setProperty("hibernate.hbm2ddl.auto", "create")
            return config
        }

    init {
        sessionFactory = h2Config.buildSessionFactory()
    }

    override fun <T> useSessionInTransaction(action: (Session) -> T): T {
        sessionFactory.openSession().use { session ->
            val transaction = session.beginTransaction()
            val result = action(session)
            transaction.commit()
            return result
        }
    }

    override fun <T> useSession(action: (Session) -> T): T {
        sessionFactory.openSession().use { return action(it) }
    }

    override fun printConnectInfo() {
        sessionFactory.openSession().use { session ->
            session.doWork { con ->
                println("------ connect info ------")
                println("DB name: " + con.metaData.databaseProductName)
                println("DB version: " + con.metaData.databaseProductVersion)
                println("Driver: " + con.metaData.driverName)
                println("Autocommit: " + con.autoCommit)
                println("--------------------------")
            }
        }
    }
}