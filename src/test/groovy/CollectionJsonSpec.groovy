import groovy.json.JsonSlurper
import spock.lang.*

class CollectionJsonSpec extends Specification {
  CollectionJson collectionJson
  JsonSlurper jsonSlurper

  def setup() {
    collectionJson = new CollectionJson()
    jsonSlurper = new JsonSlurper()
  }

  def "create should not return null"() {

    expect:
    collectionJson.create() != null
  }

  def "valid collection object should have version and href property"() {
    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.version == null
    result.collection.href == null
  }

  def "version should be customisable"() {
    collectionJson.version = "1.0"
    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.version == "1.0"
  }

  def "href should be customisable"() {
    collectionJson.href = "www.test.com"
    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.href == "www.test.com"
  }

  def "collection object MAY have an items array property"() {
    Items items = new Items()
    collectionJson.addItems(items)

    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.items[0] != null
  }

  def "collectionjson can add multiple items"() {
    Items items = new Items()
    collectionJson.addItems(items).addItems(items)

    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.items.size() == 2
  }

  def "items array should have href property"() {
    Items items = new Items(href: "www.items.com")
    collectionJson.addItems(items)

    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.items[0].href == "www.items.com"
  }

  def "items array may have data array child property"() {
    Items items = new Items()
    Data data = new Data()
    items.addData(data)

    expect:
    items.data[0] != null
  }

  def "items array can store multiple data objects"() {
    Items items = new Items()
    Data data = new Data()
    items.addData(data).addData(data)

    expect:
    items.data.size() == 2
  }

  def "Data may have any of three possible properties: name, value, prompt"() {
    Data data = new Data(name: "data", value: "value", prompt: "prompt")

    expect:
    data != null
  }

  def "Data's name property should be of String type"() {
    Data data = new Data()
    data.name = false
    expect:
    data.name instanceof String
  }

  def "Data's prompt property should be of String type"() {
    Data data = new Data()
    data.prompt = false
    expect:
    data.prompt instanceof String
  }

  def "Data's value property MAY contain Boolean"() {
    given:
    Data data = new Data()

    when:
    data.value = true

    then:
    data.value instanceof Boolean
  }
  def "Data's value property MAY contain STRING"() {
    given:
    Data data = new Data()

    when:
    data.value = "string"

    then:
    data.value instanceof String
  }

  def "Data's value property MAY contain NUMBER"() {
    given:
    Data data = new Data()

    when:
    data.value =  0

    then:
    data.value instanceof Integer
  }

  def "Data's value property MAY contain null"() {
    given:
    Data data = new Data()

    when:
    data.value = null

    then:
    data.value  == null
  }

  def "Items' data array can store data objects with different value types"() {
    given:
    Data stringData = new Data(value: "string")
    Data integerData = new Data(value: 0)
    Data booleanData = new Data(value: false)
    Data nullData = new Data(value: null )

    when:
    Items items = new Items()
    items.addData(stringData).addData(integerData).addData(booleanData).addData(nullData)

    then:
    items.data[0].value == "string"
    items.data[1].value == 0
    items.data[2].value == false
    items.data[3].value == null
  }

  def "Items array may have links array child property"() {
    Items items = new Items()
    Links link = new Links()
    items.addLink(link)

    expect:
    items.links != null
  }


}
