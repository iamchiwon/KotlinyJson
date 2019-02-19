# KotlinyJson

Convenience JSON handler inspired [SwiftyJSON](https://github.com/SwiftyJSON/SwiftyJSON)

## Installation

```
Just copy file : KotlinyJson.kt
```

#### Dependancies

* `implementation 'com.google.code.gson:gson:2.8.5'`


## Create Instance

#### 1. parsing JSON

```kotlin
val jsonString = """
{
    "name": "KotlinyJson",
    "author": "iamchiwon",
    "star": 5
}
"""

val kotlinyJson = KotlinyJson.parse(jsonString)
```

#### 2. from JsonElement

```kotlin
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser

val jsonString = """
{
    "name": "KotlinyJson",
    "author": "iamchiwon",
    "star": 5
}
"""

val jsonObject = JsonParser().parse(jsonString).asJsonObject

val kotlinyJson = jsonObject.toKotlinyJson()

```

## Get values manually

```kotlin
val name = kotlinyJson["name"].asStringValue()
val start = kotlinyJson["star"].asIntValue()

val name2: String? = kotlinyJson["name"].asString()
val start2: Int? = kotlinyJson["star"].asInt()
```

#### Examples

Recursion

```kotlin
val json = """
{
    "foo": {
        "bar": {
            "level": 5
        }
    }
}
"""

val kotliny = KotlinyJson.parse(json)

val level = kotliny["foo"]["bar"]["level"].asIntValue() // 5
val fake = kotliny["foo"]["bar"]["fake"].asInt() // null
val fake2 = kotliny["foo"]["bar"]["fake"].asIntValue() // 0
```

Array

```kotlin
val json = """
{
    "foo": [
        { "name": "bar1" },
        { "name": "bar2" },
        { "name": "bar3" },
    ]
}
"""

val kotliny = KotlinyJson.parse(json)

val bars = kotliny["foo"]
                .asArray()
                .map { it["name"] }
//["bar1", "bar2", "bar3"]

val foos = kotliny["bar"]
                .asArray()
                .map { it["name"] }
//[]
```

Decode using GSon

```kotlin
val object = Gson().fromJson(kotliny["data"].element(), SomeObject::class.java)
```

<br/>

## License

MIT License.
