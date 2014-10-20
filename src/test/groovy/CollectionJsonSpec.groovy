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

  def "should enable populating items"() {
    Items items = new Items()
    collectionJson.addItems(items)

    String json = collectionJson.create()
    def result = jsonSlurper.parseText(json)

    expect:
    result.collection.items[0] != null
  }

  def "should enable populating multiple items"() {
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

  def "data array in Items can store multiple data objects"() {
    Items items = new Items()
    Data data = new Data()
    items.addData(data).addData(data)

    expect:
    items.data.size() == 2
  }

}
