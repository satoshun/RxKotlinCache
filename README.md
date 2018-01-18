[![](https://jitpack.io/v/satoshun/RxKotlinCache.svg)](https://jitpack.io/#satoshun/RxKotlinCache)

# RxKotlinCache

It's a simple cache for [RxJava2](https://github.com/ReactiveX/RxJava) and Kotlin.

RxKotlinCache don't cache a Error, only cache success and complete events. 


## How to install?

use Gradle

```gradle
repositories {
    maven { url 'https://jitpack.io' }
}

implementation com.github.satoshun:RxKotlinCache:0.1.0
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
