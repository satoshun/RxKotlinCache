# RxKotlinCache

It's a simple cache for [RxJava2](https://github.com/ReactiveX/RxJava) and Kotlin.

RxKotlinCache don't cache a Error, only cache success and complete events.


## How to install?

use Gradle

```groovy
implementation 'com.github.satoshun.RxKotlinCache:rxkotlincache:0.1.1'
```

## How to use it?

It can wrap a FunctionX(method reference) with cache(before result).

```kotlin
class UserDao {
    fun getUser(userId: Int) : Single<User> {
        // body
    }
}

class UserRepository(private val userDao: UserDao) {
    private val getUserFromCache = rxCache(userDao::getUser)

    fun getUser(userId: Int) : Single<User> {
        return getUserFromCache(userId) // get userDao::getUser if not exists a cache
    }
}
```
