import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.TypeAdapterFactory
import com.iamchiwon.utils.KotlinyJson
import com.iamchiwon.utils.toKotlinyJson
import org.junit.Assert
import kotlin.test.Test

class KotlinyJsonTest {

    @Test
    fun testCreation() {
        val jsonString = """
            {
                "name": "KotlinyJson",
                "author": "iamchiwon",
                "star": 5
            }
        """.trimIndent()

        val kotlinyJson = KotlinyJson.parse(jsonString)
        Assert.assertEquals(KotlinyJson::class.java, kotlinyJson::class.java)

        val jsonObject = JsonParser().parse(jsonString).asJsonObject
        val kotlinyJson2 = jsonObject.toKotlinyJson()
        Assert.assertEquals(KotlinyJson::class.java, kotlinyJson2::class.java)
    }

    @Test
    fun testGetValueManually() {
        val jsonString = """
            {
                "name": "KotlinyJson",
                "author": "iamchiwon",
                "star": 5
            }
        """.trimIndent()

        val kotlinyJson = KotlinyJson.parse(jsonString)

        val name = kotlinyJson["name"].asStringValue()
        val start = kotlinyJson["star"].asIntValue()

        Assert.assertEquals("KotlinyJson", name)
        Assert.assertEquals(5, start)

        val name2: String? = kotlinyJson["name"].asString()
        val start2: Int? = kotlinyJson["star"].asInt()
        val score: Int? = kotlinyJson["score"].asInt()

        Assert.assertEquals("KotlinyJson", name2)
        Assert.assertEquals(5, start2)
        Assert.assertNull(score)
    }

    @Test
    fun testRecursion() {
        val json = """
            {
                "foo": {
                    "bar": {
                        "level": 5
                    }
                }
            }
        """.trimIndent()

        val kotliny = KotlinyJson.parse(json)

        val level = kotliny["foo"]["bar"]["level"].asIntValue() // 5
        val fake = kotliny["foo"]["bar"]["fake"].asInt() // null
        val fake2 = kotliny["foo"]["bar"]["fake"].asIntValue() // 0

        Assert.assertEquals(5, level)
        Assert.assertNull(fake)
        Assert.assertEquals(0, fake2)
    }

    @Test
    fun testOptional() {
        val json = """
            {
                "foo": 5
            }
        """.trimIndent()

        val kotliny = KotlinyJson.parse(json)

        val foo = kotliny["star"].asInt() ?: kotliny["foo"].asInt() ?: 0

        Assert.assertEquals(5, foo)
    }

    @Test
    fun testArray() {
        val json = """
            {
                "foo": [
                    { "name": "bar1" },
                    { "name": "bar2" },
                    { "name": "bar3" }
                ]
            }
        """.trimIndent()

        val kotliny = KotlinyJson.parse(json)

        val bars = kotliny["foo"]
            .asArray()
            .map { it["name"].asStringValue() }
        val foos = kotliny["bar"]
            .asArray()
            .map { it["name"].asStringValue() }

        Assert.assertEquals(listOf("bar1", "bar2", "bar3"), bars)
        Assert.assertEquals(emptyList<String>(), foos)
    }

    @Test
    fun testUsingGson() {
        data class Sample(
            val name: String
        )

        val json = """
            {
                "foo": {
                    "name": "foo"
                },
                "bar": {
                    "name": "bar"
                }
            }
        """.trimIndent()

        val kotliny = KotlinyJson.parse(json)

        val foo = Gson().fromJson(kotliny["foo"].element(), Sample::class.java)
        val bar = Gson().fromJson(kotliny["bar"].element(), Sample::class.java)

        Assert.assertEquals(Sample(name = "foo"), foo)
        Assert.assertEquals(Sample(name = "bar"), bar)
    }
}