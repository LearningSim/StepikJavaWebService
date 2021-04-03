import org.hibernate.Session

interface IDBService {
    fun <T> useSessionInTransaction(action: (Session) -> T): T
    fun <T> useSession(action: (Session) -> T): T
    fun printConnectInfo()
}