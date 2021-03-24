package dao

import DBService
import entity.UserEntity

class UserDao(private val dbService: DBService) {
    fun getUser(id: Long): UserEntity? {
        return dbService.useSession { it.get(UserEntity::class.java, id) }
    }

    fun getUser(login: String): UserEntity? {
        return dbService.useSession { session ->
            val cb = session.criteriaBuilder
            val query = cb.createQuery(UserEntity::class.java)
            val user = query.from(UserEntity::class.java)
            query.where(cb.equal(user.get<String>("login"), login))
            session.createQuery(query).singleResult
        }
    }

    fun getUserId(login: String): Long? {
        return getUser(login)?.id
    }

    fun addUser(login: String, pass: String): Long {
        return dbService.useSessionInTransaction {
            it.save(UserEntity(login, pass)) as Long
        }
    }

    fun addUser(login: String): Long {
        return dbService.useSessionInTransaction {
            it.save(UserEntity(login)) as Long
        }
    }
}