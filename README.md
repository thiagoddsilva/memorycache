# MemoryCache

MemoryCache is a simple in-memory cache implementation in Kotlin. It uses a ConcurrentHashMap to store cache items and provides basic cache operations such as adding, retrieving, and removing items.

## Getting Started

To use the MemoryCache in your project, you need to first create an instance of the `MemoryCache` class. You can optionally provide `CacheOptions` during the creation of the `MemoryCache` instance.

```kotlin
val cache = MemoryCache()
```

## Usage

### Adding Items

You can add items to the cache using the `add` method. The `add` method requires a key and a value.

```kotlin
cache.add("key", "value")
```

### Retrieving Items

You can retrieve items from the cache using the `get` method. The `get` method requires a key and returns the value if found, null otherwise.

```kotlin
val value = cache.get("key")
```

### Removing Items

You can remove items from the cache using the `remove` method. The `remove` method requires a key.

```kotlin
cache.remove("key")
```

### Counting Items

You can get the count of items in the cache using the `getCount` method.

```kotlin
val count = cache.getCount()
```

## Cache Options

The `MemoryCache` class supports cache options which can be used to control the behavior of the cache. The `CacheOptions` class has two properties: `absolutDuration` and `slidingDuration` which can be used to set the expiration mode of the cache items.

```kotlin
val options = CacheOptions(2.seconds, null)
val cache = MemoryCache(options)
```

In the above example, the `absolutDuration` is set to 2 seconds which means the cache items will expire after 2 seconds.

## Contributing

Contributions are welcome. Please feel free to submit a pull request.

## License

This project is licensed under the MIT License.
