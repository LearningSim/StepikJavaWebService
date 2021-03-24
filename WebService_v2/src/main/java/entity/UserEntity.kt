package entity

import java.io.Serializable
import javax.persistence.*

@Entity
@Table(name = "users")
class UserEntity : Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @Column(unique = true, updatable = false)
    var login: String? = null

    @Column(updatable = false)
    var password: String? = null

    //Important to Hibernate!
    constructor()

    constructor(login: String) {
        id = -1
        this.login = login
        this.password = login
    }

    constructor(login: String, password: String) {
        id = -1
        this.login = login
        this.password = password
    }

    override fun toString(): String {
        return "${this.javaClass.simpleName}{id=$id, login='$login', pass='$password'}"
    }
}