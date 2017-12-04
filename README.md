# RxKotlinCache

It's a simple cache for [RxJava2](https://github.com/ReactiveX/RxJava) and Kotlin.

## How to use it?

It can wrap a FunctionX(method reference) with cache(before result).

```kotlin
class UserDao {
    fun getUser(userId: Int) : Single<User> {
        // body
    }
}

class UserRepository(private val userDao: UserDao) {
    private val getUser = rxCache(userDao::getUser)

    fun getUser(userId: Int) : Single<User> {
        return getUser(userId) // get from cache or userDao#getUser
    }
}
```
